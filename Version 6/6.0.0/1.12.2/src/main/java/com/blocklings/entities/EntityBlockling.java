package com.blocklings.entities;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.network.GuiIDMessage;
import com.blocklings.network.OpenGuiMessage;
import com.blocklings.network.GeneralLevelMessage;
import com.blocklings.network.CombatLevelMessage;
import com.blocklings.network.MiningLevelMessage;
import com.blocklings.network.WoodcuttingLevelMessage;
import com.blocklings.util.helpers.NetworkHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityBlockling extends EntityTameable implements IEntityAdditionalSpawnData
{
    public static final double BASE_MAX_HEALTH = 10;
    public static final double BASE_MOVEMENT_SPEED = 0.3;
    public static final double BASE_ATTACK_DAMAGE = 1;

    public InventoryBlockling inv;

    private int guiID = 1;

    private int generalLevel = 1, combatLevel = 1, miningLevel = 1, woodcuttingLevel = 1;
    private int generalXp = 0, combatXp = 0, miningXp = 0, wooducttingXp = 0;

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

    // Used to save entity data (variables) when the entity is unloaded
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    // Used to load entity data (variables) when the entity is loaded
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
    }

    // Used to save the data (variables) that need to be saved and synced
    @Override
    public void writeSpawnData(ByteBuf buf)
    {

    }

    // Used to sync client with server on spawn
    @Override
    public void readSpawnData(ByteBuf buf)
    {

    }

    // Called once every tick
    // Used by skeles to check if they are in the sun
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    // Also called once every tick
    // Not sure what the difference is between the two update methods
    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    // Called when a player interacts (right clicks) on entity
    // Is called on both client and server
    // And called for each hand
    // Client and server is useful for taming
    // This is because we want to set tamed on server but also play effects client side etc
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
                            setTamed(player);
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
            if (guiID == Tab.STATS.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.INVENTORY.id)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            else if (guiID == Tab.GENERAL.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.COMBAT.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.MINING.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.WOODCUTTING.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
        }
        else
        {
            if (guiID == Tab.STATS.id)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            else if (guiID == Tab.INVENTORY.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.GENERAL.id)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            else if (guiID == Tab.COMBAT.id)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            else if (guiID == Tab.MINING.id)
                NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
            else if (guiID == Tab.WOODCUTTING.id)
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
        //aiSit.setSitting(true);
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

    // GETTERS
    // AND
    // SETTERS

    public int getGuiID()
    {
        return guiID;
    }

    public void setGuiID(int value)
    {
        guiID = value;
        NetworkHelper.sync(world, new GuiIDMessage(value, getEntityId()));
    }

    public void setGuiIDFromPacket(int value)
    {
        guiID = value;
    }
    
    
    
    public int getGeneralLevel()
    {
        return generalLevel;
    }

    public void setGeneralLevel(int value)
    {
        generalLevel = value;
        NetworkHelper.sync(world, new GeneralLevelMessage(value, getEntityId()));
    }

    public void setGeneralLevelFromPacket(int value)
    {
        generalLevel = value;
    }



    public int getCombatLevel()
    {
        return combatLevel;
    }

    public void setCombatLevel(int value)
    {
        combatLevel = value;
        NetworkHelper.sync(world, new CombatLevelMessage(value, getEntityId()));
    }

    public void setCombatLevelFromPacket(int value)
    {
        combatLevel = value;
    }



    public int getMiningLevel()
    {
        return miningLevel;
    }

    public void setMiningLevel(int value)
    {
        miningLevel = value;
        NetworkHelper.sync(world, new MiningLevelMessage(value, getEntityId()));
    }

    public void setMiningLevelFromPacket(int value)
    {
        miningLevel = value;
    }



    public int getWoodcuttingLevel()
    {
        return woodcuttingLevel;
    }

    public void setWoodcuttingLevel(int value)
    {
        woodcuttingLevel = value;
        NetworkHelper.sync(world, new WoodcuttingLevelMessage(value, getEntityId()));
    }

    public void setWoodcuttingLevelFromPacket(int value)
    {
        woodcuttingLevel = value;
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