package com.blocklings.entities;

import com.blocklings.util.helpers.BlockHelper;
import com.blocklings.util.helpers.DropHelper;
import com.blocklings.util.helpers.EntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlocklingAIFarming extends BlocklingAIBase
{
    private static final int X_RADIUS = 10, Y_RADIUS = 10;

    private int targetYValue;

    public BlocklingAIFarming(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
        if (blockling.getTask() != EntityHelper.Task.FARM)
        {
            return false;
        }

        if (blockling.getAttackTarget() != null || !blockling.hasHoe())
        {
            return false;
        }

        boolean foundCrop = false;

        resetTarget();
        targetPathSquareDistance = 10000;
        targetYValue = -1000;

        for (int x = (int) blockling.posX - X_RADIUS; x < blockling.posX + X_RADIUS; x++)
        {
            for (int y = (int) blockling.posY + Y_RADIUS; y > blockling.posY - Y_RADIUS; y--)
            {
                for (int z = (int) blockling.posZ - X_RADIUS; z < blockling.posZ + X_RADIUS; z++)
                {
                    Block block = getBlockAt(x, y, z);
                    if (BlockHelper.isCrop(block))
                    {
                        // Check block is grown
                        int grownAge = BlockHelper.getGrownAge(block);
                        if (grownAge != -1)
                        {
                            int age = BlockHelper.getAge(world.getBlockState(new BlockPos(x, y, z)));
                            if (age < grownAge)
                            {
                                continue;
                            }
                        }

                        double xx = x + 0.5f;
                        double yy = y + 0.5f;
                        double zz = z + 0.5f;
                        BlockPos blockPos = new BlockPos(x, y, z);
                        Vec3d blockVec = getVecFromBlockPos(blockPos);

                        // If we are already in range to mine the block then set it as target
                        if (y >= targetYValue && blockling.getPositionVector().distanceTo(blockVec) < range)
                        {
                            targetPathSquareDistance = 1;
                            targetYValue = y;
                            setTarget(blockPos);
                            foundCrop = true;
                        }

                        Path pathToBlock = getSafishPathTo(blockPos);
                        if (pathToBlock != null)
                        {
                            if (isPathDestInRange(pathToBlock, blockPos))
                            {
                                // Find the closest block (using path distance)
                                double pathSquareDistance = getPathSquareDistance(pathToBlock);
                                if (y >= targetYValue && (pathSquareDistance - 10) < targetPathSquareDistance)
                                {
                                    targetPathSquareDistance = pathSquareDistance;
                                    targetYValue = y;
                                    setTarget(blockPos, pathToBlock);
                                    foundCrop = true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return foundCrop;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return (hasTarget() && blockling.getAttackTarget() == null && world.getBlockState(targetPos).getBlock() != Blocks.AIR && blockling.hasHoe());
    }

    @Override
    public void updateTask()
    {
        if (hasTarget())
        {
            if (isBlocklingInRange(targetPos))
            {
                if (tryHarvestTarget())
                {
                    resetTarget();
                }
            }
            else
            {
                if (!moveToTarget())
                {
                    resetTarget();
                }
            }
        }
    }

    private boolean tryHarvestTarget()
    {
        if (!blockling.isMining())
        {
            blockling.startMining();
        }

        if (blockling.getMiningTimer() >= blockling.getMiningInterval())
        {
            harvestBlock();
            blockling.stopMining();
            world.sendBlockBreakProgress(blockling.getEntityId(), targetPos, -1);
            return true;
        }
        else
        {
            int progress = (int)(((float)(blockling.getMiningTimer()) / (float)blockling.getMiningInterval()) * 9.0f);
            world.sendBlockBreakProgress(blockling.getEntityId(), targetPos, progress);
            return false;
        }
    }

    private void harvestBlock()
    {
        NonNullList<ItemStack> dropStacks = DropHelper.getDops(blockling, world, targetPos);
        for (ItemStack dropStack : dropStacks)
        {
            ItemStack leftoverStack = blockling.inv.addItem(dropStack);
            if (!leftoverStack.isEmpty())
            {
                blockling.entityDropItem(leftoverStack, 0);
            }
        }

        if (blockling.isUsingHoeRight())
        {
            blockling.damageItem(EnumHand.MAIN_HAND);
        }
        if (blockling.isUsingHoeLeft())
        {
            blockling.damageItem(EnumHand.OFF_HAND);
        }

        Item seed = BlockHelper.getSeed(getBlockFromPos(targetPos));
        if (seed != Items.AIR)
        {
            int slot = blockling.inv.find(seed);
            world.setBlockToAir(targetPos);
            if (slot != -1)
            {
                blockling.inv.getStackInSlot(slot).shrink(1);
                world.setBlockState(targetPos, targetBlock.getDefaultState());
            }
        }
        else
        {
            world.setBlockToAir(targetPos);
        }
    }

//    private void setTargetToRandom()
//    {
//        if (getBlockFromPos(targetPos) != Blocks.DIAMOND_ORE)
//        {
//            world.setBlockState(targetPos, Blocks.DIAMOND_ORE.getDefaultState());
//        }
//        else
//        {
//            world.setBlockState(targetPos, Blocks.GOLD_ORE.getDefaultState());
//        }
//    }
}
