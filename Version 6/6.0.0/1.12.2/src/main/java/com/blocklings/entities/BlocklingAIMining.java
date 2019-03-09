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

    private Set<BlockPos> vein = new HashSet<>();
    private Set<BlockPos> toCheck = new HashSet<>();

    private boolean initialTarget = false;

    public BlocklingAIMining(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
        boolean foundOre = false;

        resetTarget();
        vein.clear();
        targetPathSquareDistance = 10000;

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
                                setTarget(blockPos);
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
                                    setTarget(blockPos);
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

        if (foundOre)
        {
            initialTarget = true;
            toCheck.clear();
            toCheck.add(targetPos);
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
        if (!findVein())
        {
            return;
        }

        if (!findTarget())
        {
            return;
        }

        if (hasTarget())
        {
            //world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
            if (blockling.getPositionVector().distanceTo(targetVec) < range)
            {
                world.setBlockToAir(targetPos);
                //world.setBlockState(targetPos, Blocks.BONE_BLOCK.getDefaultState());
                resetTarget();
            }
            else
            {
                moveToBlock(targetPos);
            }
        }
    }

    private boolean findVein()
    {
        if (toCheck.isEmpty())
        {
            return true;
        }

        BlockPos checkPos = (BlockPos) toCheck.toArray()[0];

        // Check all the blocks around the target
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                for (int k = -1; k < 2; k++)
                {
                    BlockPos testPos = new BlockPos(checkPos.getX() + i, checkPos.getY() + j, checkPos.getZ() + k);

                    if (BlockHelper.isOre(getBlockFromPos(testPos)))
                    {
                        if (!vein.contains(testPos))
                        {
                            toCheck.add(testPos);
                            vein.add(testPos);
                        }
                    }
                }
            }
        }

        toCheck.remove(checkPos);

        return false;
    }

    private boolean findTarget()
    {
        if (hasTarget() && !initialTarget)
        {
            return true;
        }

        if (vein.isEmpty())
        {
            return false;
        }

        for (BlockPos testPos : vein)
        {
            if (getSafishPathTo(testPos) != null && !isBlockSupporting(testPos))
            {
                setTarget(testPos);
                initialTarget = false;
                return true;
            }
        }

        return false;
    }

    private boolean isBlockInTheWay(BlockPos blockPos)
    {
        return false;
    }

    private boolean isBlockSupporting(BlockPos blockPos)
    {
        Set<BlockPos> canReach = new HashSet<>();

        if (getBlockFromPos(blockPos) == Blocks.IRON_ORE)
        {
            canReach.clear();
        }

        for (BlockPos testPos : vein)
        {
            if (testPos.equals(blockPos))
            {
                continue;
            }

            if (getSafishPathTo(testPos) != null)
            {
                canReach.add(testPos);
            }
        }

        for (BlockPos reachablePos : canReach)
        {
            Path path = getSafishPathToWithRemovedBlock(reachablePos, blockPos);
            if (path == null)
            {
                return true;
            }
            else
            {
                Vec3d finalVec = getVecFromPathPoint(path.getFinalPathPoint());
                Vec3d reachableVec = getVecFromBlockPos(reachablePos);

                if (finalVec.distanceTo(reachableVec) >= range)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
