package com.blocklings.entities;

import com.blocklings.util.helpers.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.blocklings.util.helpers.DropHelper.getDops;

public class BlocklingAIMining extends BlocklingAIBase
{
    private int X_RADIUS = 10, Y_RADIUS = 10;

    private int targetYValue;

    public BlocklingAIMining(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
        if (blockling.miningAbilities.isAbilityAcquired(AbilityHelper.dwarvenSenses1))
        {
            X_RADIUS = 20;
            Y_RADIUS = 20;
        }
        else
        {
            X_RADIUS = 10;
            Y_RADIUS = 10;
        }

        if (blockling.getTask() != EntityHelper.Task.MINE)
        {
            return false;
        }

        if (blockling.getAttackTarget() != null || !blockling.hasPickaxe())
        {
            return false;
        }

        boolean foundOre = false;

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
                    if (BlockHelper.isOre(block))
                    {
                        if (blockling.miningAbilities.isAbilityAcquired(AbilityHelper.dwarvenSenses2) || canSeeBlock(x, y, z))
                        {
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
                                foundOre = true;
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
                                        foundOre = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return foundOre;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return (hasTarget() && blockling.getAttackTarget() == null && world.getBlockState(targetPos).getBlock() != Blocks.AIR && blockling.hasPickaxe());
    }

    @Override
    public void updateTask()
    {
        if (hasTarget())
        {
            if (isBlocklingInRange(targetPos))
            {
                if (tryMineTarget())
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

    private boolean tryMineTarget()
    {
        blockling.getLookHelper().setLookPosition(targetVec.x, targetVec.y, targetVec.z, 1000, 100);

        if (!blockling.isMining())
        {
            blockling.startMining();
        }

        if (blockling.getMiningTimer() == 0 && blockling.miningAbilities.isAbilityAcquired(AbilityHelper.brittleBlock))
        {
            if (rand.nextFloat() < 0.1f)
            {
                mineTarget();
                blockling.stopMining();
                world.sendBlockBreakProgress(blockling.getEntityId(), targetPos, -1);
                return true;
            }
        }

        if (blockling.getMiningTimer() >= blockling.getMiningInterval())
        {
            mineTarget();
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

    private void mineTarget()
    {
        NonNullList<ItemStack> dropStacks = DropHelper.getDops(blockling, world, targetPos);
        for (ItemStack dropStack : dropStacks)
        {
            if (blockling.miningAbilities.isAbilityAcquired(AbilityHelper.blocksmith))
            {
                ItemStack smeltResult = DropHelper.getFurnaceResult(blockling, dropStack);
                dropStack = smeltResult != ItemStack.EMPTY ? smeltResult : dropStack;
            }

            ItemStack leftoverStack = blockling.inv.addItem(dropStack);
            if (!leftoverStack.isEmpty())
            {
                blockling.entityDropItem(leftoverStack, 0);
            }
        }

        if (blockling.isUsingPickaxeRight())
        {
            blockling.damageItem(EnumHand.MAIN_HAND);
        }
        if (blockling.isUsingPickaxeLeft())
        {
            blockling.damageItem(EnumHand.OFF_HAND);
        }

        blockling.incrementMiningXp(5);
        world.setBlockToAir(targetPos);
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
