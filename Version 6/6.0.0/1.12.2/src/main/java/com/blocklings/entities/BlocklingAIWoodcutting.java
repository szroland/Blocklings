package com.blocklings.entities;

import com.blocklings.util.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BlocklingAIWoodcutting extends BlocklingAIBase
{
    private static final int X_RADIUS = 10, Y_RADIUS = 10;

    private int treeSearchCount = 0;
    private int leafCount = 0;
    private List<BlockPos> tree = new ArrayList<>();
    private List<BlockPos> logsToCheck = new ArrayList<>();

    private int badLogResetCount = 0;
    private List<BlockPos> logsThatAreNotTrees = new ArrayList<>();

    public BlocklingAIWoodcutting(EntityBlockling blockling)
    {
        super(blockling);
    }

    @Override
    public boolean shouldExecute()
    {
        badLogResetCount++;

        if (badLogResetCount > 600)
        {
            logsThatAreNotTrees.clear();
            badLogResetCount = 0;
        }

        boolean foundLog = false;

        resetTarget();
        targetPathSquareDistance = 10000;

        for (int x = (int) blockling.posX - X_RADIUS; x < blockling.posX + X_RADIUS; x++)
        {
            for (int y = (int) blockling.posY + Y_RADIUS; y > blockling.posY - Y_RADIUS; y--)
            {
                for (int z = (int) blockling.posZ - X_RADIUS; z < blockling.posZ + X_RADIUS; z++)
                {
                    Block block = getBlockAt(x, y, z);
                    if (BlockHelper.isLog(block))
                    {
                        BlockPos blockPos = new BlockPos(x, y, z);
                        Vec3d blockVec = getVecFromBlockPos(blockPos);

                        // We have already decided before that this is not a tree;
                        if (logsThatAreNotTrees.contains(blockPos))
                        {
                            continue;
                        }

                        if (canSeeBlock(x, y, z))
                        {
                            double xx = x + 0.5f;
                            double yy = y + 0.5f;
                            double zz = z + 0.5f;

                            // If we are already in range to mine the block then set it as target
                            if (blockling.getPositionVector().distanceTo(blockVec) < range)
                            {
                                targetPathSquareDistance = 1;
                                setTarget(blockPos);
                                foundLog = true;
                            }

                            Path pathToBlock = getSafishPathTo(blockPos);
                            if (pathToBlock != null)
                            {
                                if (isPathDestInRange(pathToBlock, blockPos))
                                {
                                    // Find the closest block (using path distance)
                                    double pathSquareDistance = getPathSquareDistance(pathToBlock);
                                    if (pathSquareDistance < targetPathSquareDistance)
                                    {
                                        targetPathSquareDistance = pathSquareDistance;
                                        setTarget(blockPos, pathToBlock);
                                        foundLog = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return foundLog;
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
            if (findTree())
            {
                setTargetToRandom();

                if (isTree())
                {
                    if (isBlocklingInRange(targetPos))
                    {
                        for (BlockPos logPos : tree)
                        {
                            world.setBlockToAir(logPos);
                        }

                        resetTarget();
                    }
                    else
                    {
                        moveToTarget();
                    }
                }
                else
                {
                    logsThatAreNotTrees.addAll(tree);
                    resetTarget();
                }
            }
        }
    }

    private boolean isTree()
    {
        return true;
    }

    private boolean findTree()
    {
        if (logsToCheck.isEmpty())
        {
            if (tree.isEmpty())
            {
                treeSearchCount = 0;
                leafCount = 0;
                logsToCheck.add(targetPos);
                tree.add(targetPos);
            }
            else
            {
                return true;
            }
        }
        else
        {
            if (treeSearchCount > 50)
            {
                return true;
            }

            int localCount = 0;
            while (localCount < 2)
            {
                BlockPos testLog = logsToCheck.get(0);

                for (int i = -1; i < 2; i++)
                {
                    for (int j = -1; j < 2; j++)
                    {
                        for (int k = -1; k < 2; k++)
                        {
                            if (i == 0 && j == 0 && k == 0)
                            {
                                continue;
                            }

                            BlockPos surroundingPos = new BlockPos(testLog.getX() + i, testLog.getY() + j, testLog.getZ() + k);
                            Block surroundingBlock = getBlockFromPos(surroundingPos);

                            if (BlockHelper.isLog(surroundingBlock))
                            {
                                if (!tree.contains(surroundingPos))
                                {
                                    tree.add(surroundingPos);
                                    logsToCheck.add(surroundingPos);
                                }
                            }
                            else if (BlockHelper.isLeaf(surroundingBlock))
                            {
                                leafCount++;
                            }

                            localCount++;
                        }
                    }
                }

                logsToCheck.remove(0);
            }
        }

        treeSearchCount++;

        return false;
    }

    @Override
    void resetTarget()
    {
        super.resetTarget();
        tree.clear();
        logsToCheck.clear();
    }

    private void setTargetToRandom()
    {
        if (getBlockFromPos(targetPos) != Blocks.LOG)
        {
            world.setBlockState(targetPos, Blocks.LOG.getDefaultState());
        }
        else
        {
            world.setBlockState(targetPos, Blocks.LOG2.getDefaultState());
        }
    }
}
