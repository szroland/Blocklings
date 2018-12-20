package com.blocklings.entities;

import com.blocklings.util.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.jline.utils.Log;

public class BlocklingAIMining extends EntityAIBase
{
    private static final int X_RADIUS = 10, Y_RADIUS = 10;
    private static Block[][][] ores = new Block[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];

    private EntityBlockling blockling;

    public BlocklingAIMining(EntityBlockling blockling)
    {
        this.blockling = blockling;
    }

    @Override
    public boolean shouldExecute()
    {
        boolean foundOre = false;
        ores = new Block[X_RADIUS * 2][Y_RADIUS * 2][X_RADIUS * 2];

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
                            foundOre = true;
                            ores[i][j][k] = block;
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

    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute();
    }

    public void updateTask()
    {

    }

    private boolean canSeeBlock(int x, int y, int z)
    {
        Vec3d blockVec = new Vec3d(x, y, z);

        if (blockVec != null)
        {
            double height = 0.6F * this.blockling.getBlocklingScale();
            for (int it = 0; it < 2; it++)
            {
                double xStart = this.blockling.posX;
                double yStart;
                if (it == 0) {
                    yStart = this.blockling.posY + height * 0.2D;
                } else {
                    yStart = this.blockling.posY + height * 0.8D;
                }
                double zStart = this.blockling.posZ;
                Vec3d blocklingVec = new Vec3d(xStart, yStart, zStart);
                for (double i = 0.03D; i <= 0.97D; i += 0.94D) {
                    for (double j = 0.03D; j <= 0.97D; j += 0.94D) {
                        for (double k = 0.03D; k <= 0.97D; k += 0.94D)
                        {
                            Vec3d testVec = new Vec3d(Math.floor(blockVec.x) + i, Math.floor(blockVec.y) + j, Math.floor(blockVec.z) + k);

                            RayTraceResult result = blockling.world.rayTraceBlocks(blocklingVec, testVec, true, true, true);
                            if (result != null)
                            {
                                BlockPos pos = result.getBlockPos();
                                if (pos.equals(new BlockPos(blockVec))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private Block getBlockAt(int x, int y, int z)
    {
        return blockling.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }

    private double getValue(double xDist, double yDist, boolean[] surrounding)
    {
        double value = 0;



        return value;
    }

    // Inputs:
    // All ores it can see: X distance, Y distance, Surrounding
    // Output:
    // Value between 0-1
}
