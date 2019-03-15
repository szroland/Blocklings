package com.blocklings.entities;

import com.blocklings.util.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;

public class BlocklingAIMining extends BlocklingAIBase
{
    private static final int X_RADIUS = 10, Y_RADIUS = 10;

    private int targetYValue;

    public BlocklingAIMining(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
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
                        if (canSeeBlock(x, y, z))
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
        boolean flag = (hasTarget() && world.getBlockState(targetPos).getBlock() != Blocks.AIR);

        return flag;
    }

    @Override
    public void updateTask()
    {
        if (hasTarget())
        {
            setTargetToRandom();
            //world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
            if (isBlocklingInRange(targetPos))
            {
                world.setBlockToAir(targetPos);
                //world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
                resetTarget();
            }
            else
            {
                moveToTarget();
            }
        }
    }

    private void setTargetToRandom()
    {
        if (getBlockFromPos(targetPos) != Blocks.DIAMOND_ORE)
        {
            world.setBlockState(targetPos, Blocks.DIAMOND_ORE.getDefaultState());
        }
        else
        {
            world.setBlockState(targetPos, Blocks.GOLD_ORE.getDefaultState());
        }
    }
}
