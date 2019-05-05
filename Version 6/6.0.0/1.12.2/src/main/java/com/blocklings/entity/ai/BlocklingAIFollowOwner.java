package com.blocklings.entity.ai;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.entity.enums.State;
import com.blocklings.util.BlocklingType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlocklingAIFollowOwner extends EntityAIBase
{
    private EntityLivingBase owner;
    World world;
    private final PathNavigate petPathfinder;
    private int timeToRecalcPath;
    float maxDist;
    float minDist;
    private float oldWaterCost;

    private EntityBlockling blockling;

    public BlocklingAIFollowOwner(EntityTameable tameableIn, float minDistIn, float maxDistIn)
    {
        this.blockling = (EntityBlockling)tameableIn;
        this.world = tameableIn.world;
        this.petPathfinder = tameableIn.getNavigator();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (blockling.getState() != State.FOLLOW)
        {
            return false;
        }

        if (blockling.hasTarget() || blockling.getAttackTarget() != null)
        {
            return false;
        }

        EntityLivingBase entitylivingbase = this.blockling.getOwner();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).isSpectator())
        {
            return false;
        }
        else if (this.blockling.isSitting())
        {
            return false;
        }
        else if (this.blockling.getDistanceSq(entitylivingbase) < (double)(this.minDist * this.minDist))
        {
            return false;
        }
        else
        {
            this.owner = entitylivingbase;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.petPathfinder.noPath() && this.blockling.getDistanceSq(this.owner) > (double)(this.maxDist * this.maxDist) && !this.blockling.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.blockling.getPathPriority(PathNodeType.WATER);
        this.blockling.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.owner = null;
        this.petPathfinder.clearPath();
        this.blockling.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        this.blockling.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float)this.blockling.getVerticalFaceSpeed());

        if (!this.blockling.isSitting())
        {
            if (--this.timeToRecalcPath <= 0)
            {
                this.timeToRecalcPath = 10;

                if (!this.petPathfinder.tryMoveToEntityLiving(this.owner, this.blockling.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 1.5))
                {
                    if (!this.blockling.getLeashed() && !this.blockling.isRiding())
                    {
                        if (this.blockling.getDistanceSq(this.owner) >= 144.0D)
                        {
                            int i = MathHelper.floor(this.owner.posX) - 2;
                            int j = MathHelper.floor(this.owner.posZ) - 2;
                            int k = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                            for (int l = 0; l <= 4; ++l)
                            {
                                for (int i1 = 0; i1 <= 4; ++i1)
                                {
                                    if (blockling.blocklingType != BlocklingType.blocklingTypes.get(11) && (l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                                    {
                                        this.blockling.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.blockling.rotationYaw, this.blockling.rotationPitch);
                                        this.petPathfinder.clearPath();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(this.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.blockling) && this.world.isAirBlock(blockpos.up()) && this.world.isAirBlock(blockpos.up(2));
    }
}