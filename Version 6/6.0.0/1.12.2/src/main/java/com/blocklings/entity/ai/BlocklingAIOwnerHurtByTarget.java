package com.blocklings.entity.ai;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.entity.enums.Guard;
import com.blocklings.entity.enums.Task;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityTameable;

public class BlocklingAIOwnerHurtByTarget extends EntityAITarget
{
    EntityTameable tameable;
    EntityLivingBase attacker;
    private int timestamp;

    private EntityBlockling blockling;

    public BlocklingAIOwnerHurtByTarget(EntityTameable theDefendingTameableIn)
    {
        super(theDefendingTameableIn, false);
        this.blockling = (EntityBlockling)theDefendingTameableIn;
        this.tameable = theDefendingTameableIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (blockling.getGuard() != Guard.GUARD || blockling.getTask() != Task.HUNT)
        {
            return false;
        }

        if (!this.tameable.isTamed())
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.tameable.getOwner();

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
                this.attacker = entitylivingbase.getRevengeTarget();
                int i = entitylivingbase.getRevengeTimer();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.tameable.shouldAttackEntity(this.attacker, entitylivingbase);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = this.tameable.getOwner();

        if (entitylivingbase != null)
        {
            this.timestamp = entitylivingbase.getRevengeTimer();
        }

        super.startExecuting();
    }
}