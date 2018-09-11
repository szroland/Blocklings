package com.blocklings.entities;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.network.GeneralLevelMessage;
import com.blocklings.network.GuiIDMessage;
import com.blocklings.network.OpenGuiMessage;
import com.blocklings.util.helpers.NetworkHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.jline.utils.Log;

public class EntityBlockling extends EntityTameable implements IEntityAdditionalSpawnData
{

    public static final double BASE_MAX_HEALTH = 10;
    public static final double BASE_MOVEMENT_SPEED = 0.3;
    public static final double BASE_ATTACK_DAMAGE = 1;

    public InventoryBlockling inv;

    private int guiID = 1;

    private int generalLevel = 1;

    private EntityAIFollowOwner aiFollow;
    private EntityAIWander aiWander;

    private EntityAIOwnerHurtByTarget aiOwnerHurtBy;
    private EntityAIOwnerHurtTarget aiOwnerHurt;

    public EntityBlockling(World worldIn)
    {
        super(worldIn);

        setSize(1.0f, 1.0f);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();

        setCanPickUpLoot(true);
        setupInventory();
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(BASE_MAX_HEALTH);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(BASE_MOVEMENT_SPEED);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(BASE_ATTACK_DAMAGE);
    }

    @Override
    protected void initEntityAI()
    {
        aiSit = new EntityAISit(this);
        aiFollow = new EntityAIFollowOwner(this, 1, 2, 8);
        aiWander = new EntityAIWander(this, 1);
        aiOwnerHurtBy = new EntityAIOwnerHurtByTarget(this);
        aiOwnerHurt = new EntityAIOwnerHurtTarget(this);

        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, true));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        tasks.addTask(6, new EntityAILookIdle(this));
        tasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        tasks.addTask(1, new EntityAIFollowOwner(this, 1, 2, 8));
        tasks.addTask(2, aiOwnerHurtBy);
        tasks.addTask(3, aiOwnerHurt);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }

    @Override
    public void writeSpawnData(ByteBuf buf)
    {

    }

    // Used to sync client with server on spawn
    @Override
    public void readSpawnData(ByteBuf buf)
    {

    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(!world.isRemote){
            setGeneralLevel(++generalLevel);
        }
        Log.info(generalLevel, " ", world.isRemote);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        boolean isMainHand = hand.equals(EnumHand.MAIN_HAND);

        if (isMainHand)
        {
            if (!player.isSneaking())
            {
                if (!isTamed())
                {
                    if (player != getOwner())
                    {
                        if (rand.nextInt(3) == 0)
                        {
                            setTamedBy(player);
                            playTameEffect(true);
                            world.setEntityState(this, (byte) 7);
                        }
                        else
                        {
                            playTameEffect(false);
                            world.setEntityState(this, (byte) 6);
                        }
                    }
                }
            }
            else
            {
                if (isTamed())
                {
                    if (player == getOwner())
                    {
                        openGui(player);
                    }
                }
            }
        }
        else
        {

        }

        return super.processInteract(player, hand);
    }

    @Override
    protected void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        ItemStack stack = itemEntity.getItem();
        itemEntity.setItem(inv.addItem(stack));
    }

    @Override
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier)
    {
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty())
            {
                entityDropItem(stack, 0.0f);
            }
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return null;
    }

    public void openGui(EntityPlayer player)
    {
        if (world.isRemote)
        {
            if (guiID == 0)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            if (guiID == 1)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
        }
        else
        {
            if (guiID == 0)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            if (guiID == 1)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
        }
    }

    public void openGui(int guiID, EntityPlayer player)
    {
        setGuiID(guiID);
        openGui(player);
    }

    private void setTamed(EntityPlayer player)
    {
        setTamedBy(player);
        navigator.clearPath();
        setAttackTarget((EntityLivingBase) null);
        aiSit.setSitting(true);
        playTameEffect(true);
        world.setEntityState(this, (byte) 7);
    }

    private void setupInventory()
    {
        InventoryBlockling invTemp = inv;
        inv = new InventoryBlockling(this, "Inventory", 17);
        inv.setCustomName("Blockling Inventory");

        if (invTemp != null)
        {
            int slotsToCheck = Math.min(invTemp.getSizeInventory(), inv.getSizeInventory());

            for (int i = 0; i < slotsToCheck; i++)
            {
                ItemStack stack = invTemp.getStackInSlot(i);

                if (stack != null)
                {
                    inv.setInventorySlotContents(i, stack);
                }
            }
        }
    }

    public int getGuiID()
    {
        return guiID;
    }

    public void setGuiID(int guiIDValue)
    {
        guiID = guiIDValue;
        NetworkHelper.sync(world, new GuiIDMessage(guiIDValue, getEntityId()));
    }

    public void setGuiIDFromPacket(int guiIDValue)
    {
        guiID = guiIDValue;
    }


    public int getGeneralLevel()
    {
        return generalLevel;
    }

    public void setGeneralLevel(int generalLevelValue)
    {
        generalLevel = generalLevelValue;
        NetworkHelper.sync(world, new GeneralLevelMessage(generalLevelValue, getEntityId()));
    }

    public void setGeneralLevelFromPacket(int generalLevelValue)
    {
        generalLevel = generalLevelValue;
    }

}



/*
 * When first spawned:
 *
 * [SERVER] entityInit
 * [SERVER] constructor
 * [SERVER] writeSpawnData
 * [SERVER] writeEntityToNBT
 * [SERVER] readEntityFromNBT
 * [CLIENT] entityInit
 * [CLIENT] constructor
 * [CLIENT] readSpawnData
 */

/*
 * When spawned from then on:
 *
 * [SERVER] entityInit
 * [SERVER] constructor
 * [SERVER] readEntityFromNBT
 * [SERVER] writeEntityToNBT
 * [SERVER] writeSpawnData
 * [CLIENT] entityInit
 * [CLIENT] constructor
 * [CLIENT] readSpawnData
 */