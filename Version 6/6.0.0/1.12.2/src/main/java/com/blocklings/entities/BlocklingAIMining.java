package com.blocklings.entities;

import com.blocklings.util.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;

public class BlocklingAIMining extends BlocklingAIBase
{
    private static final int X_RADIUS = 10, Y_RADIUS = 10;

    private Block[][][] ores = new Block[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];
    private double[][][] values = new double[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];

    private Set<BlockPos> targetsChecked = new HashSet<>();

    public BlocklingAIMining(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
        boolean foundOre = false;
        ores = new Block[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];
        values = new double[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];
        resetTarget();

        int i = 0;
        for (int x = (int) blockling.posX - X_RADIUS; x < blockling.posX + X_RADIUS; x++)
        {
            int j = 0;
            for (int y = (int) blockling.posY + Y_RADIUS; y > blockling.posY - Y_RADIUS; y--)
            {
                int k = 0;
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
                            Vec3d blockVec = new Vec3d(xx, yy, zz);

                            //ores[i][j][k] = block;

                            if (blockling.getPositionVector().distanceTo(blockVec) < range)
                            {
                                targetPathSquareDistance = 1;
                                targetPos = blockPos;
                                targetVec = blockVec;
                                return true;
                            }

                            Path pathToBlock = getSafishPathTo(blockPos);
                            if (pathToBlock != null)
                            {
                                PathPoint finalPoint = pathToBlock.getFinalPathPoint();
                                Vec3d finalVec = new Vec3d(finalPoint.x + 0.5, finalPoint.y + 0.5, finalPoint.z + 0.5);

                                // If we can't get in range of the block skip to next one
                                if (blockVec.distanceTo(finalVec) >= range)
                                {
                                    continue;
                                }

                                // Find the closest block (using path distance)
                                double pathSquareDistance = getPathSquareDistance(pathToBlock);
                                if (pathSquareDistance < targetPathSquareDistance)
                                {
                                    targetPathSquareDistance = pathSquareDistance;
                                    targetPos = blockPos;
                                    targetVec = blockVec;
                                    foundOre = true;
                                }
                            }
                        }
                    }

                    k++;
                }

                j++;
            }

            i++;
        }

        return foundOre;
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return hasTarget() && world.getBlockState(targetPos).getBlock() != Blocks.AIR;
    }

    @Override
    public void updateTask()
    {
        // We changed the target so keep on trying until we are done.
        if (tryChangeTargetBlock())
        {
            return;
        }

        if (hasTarget())
        {
            world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
            if (blockling.getPositionVector().distanceTo(targetVec) < range)
            {
                world.destroyBlock(targetPos, false);
                //world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
                targetsChecked.clear();
                resetTarget();
            }
            else
            {
                moveToBlock(targetPos);
            }
        }
    }

    private boolean tryChangeTargetBlock()
    {
        if (!hasTarget())
        {
            return false;
        }

        boolean targetChanged = false;

        targetsChecked.add(targetPos);

        // Check all the blocks around the target
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                for (int k = -1; k < 2; k++)
                {
                    BlockPos testPos = new BlockPos(targetPos.getX() + i, targetPos.getY() + j, targetPos.getZ() + k);

                    if (targetsChecked.contains(testPos))
                    {
                        continue;
                    }

                    if (!BlockHelper.isOre(world.getBlockState(testPos).getBlock()))
                    {
                        continue;
                    }

                    Path pathToBlock = getSafishPathTo(testPos);
                    if (pathToBlock != null)
                    {
                        Vec3d finalVec = getVecFromPathPoint(pathToBlock.getFinalPathPoint());
                        Vec3d testVec = new Vec3d(testPos.getX() + 0.5, testPos.getY() + 0.5, testPos.getZ() + 0.5);

                        // If we can't get in range of the block skip to next one
                        if (testVec.distanceTo(finalVec) >= range)
                        {
                            continue;
                        }

                        // Find the hardest to reach block (using path distance)
                        double pathSquareDistance = getPathSquareDistance(pathToBlock);
                        if (pathSquareDistance > targetPathSquareDistance)
                        {
                            targetPathSquareDistance = pathSquareDistance;
                            targetPos = testPos;
                            targetVec = new Vec3d(testPos.getX() + 0.5,testPos.getY() + 0.5, testPos.getZ() + 0.5);
                            targetChanged = true;
                        }
                    }
                }
            }
        }

        return targetChanged;
    }

    private boolean isBlockInTheWay(BlockPos blockPos)
    {
        return false;
    }

    private boolean isBlockSupporting(BlockPos blockPos)
    {
        return false;
    }
}
