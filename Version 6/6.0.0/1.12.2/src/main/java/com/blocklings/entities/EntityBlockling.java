package com.blocklings.entities;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.network.*;
import com.blocklings.util.helpers.ItemHelper;
import com.blocklings.util.helpers.NetworkHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;
import com.blocklings.abilities.Ability;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import org.jline.utils.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityBlockling extends EntityTameable implements IEntityAdditionalSpawnData
{
    public static final double BASE_MAX_HEALTH = 10;
    public static final double BASE_MOVEMENT_SPEED = 0.3;
    public static final double BASE_ATTACK_DAMAGE = 1;

    public InventoryBlockling inv;

    public List<Ability> generalAbilities;
    public List<Ability> combatAbilities;
    public List<Ability> miningAbilities;
    public List<Ability> woodcuttingAbilities;

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

        generalAbilities = new ArrayList<Ability>();
        combatAbilities = new ArrayList<Ability>();
        miningAbilities = new ArrayList<Ability>();
        woodcuttingAbilities = new ArrayList<Ability>();

        {
            Ability ability0 = new Ability(0, null, -10, -20, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 30, 30, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 110, 30, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -40, 90, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -76, 90, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 90, 140, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 20, 130, 24, 0, "Ability 4", "Ability 4 description 123456");

            ability0.colour = new Color(0xaa55aa);
            ability1.colour = new Color(0x500F89);
            ability0.colour = new Color(0xB98F2C);
            ability2.colour = new Color(0x920C07);
            ability5.colour = new Color(0x0A8C2E);

            generalAbilities.add(ability0);
            generalAbilities.add(ability1);
            generalAbilities.add(ability2);
            generalAbilities.add(ability3);
            generalAbilities.add(ability4);
            generalAbilities.add(ability5);
            generalAbilities.add(ability6);
        }
        {
            Ability ability0 = new Ability(0, null, -40, -40, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 10, 11, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 120, 15, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -20, 70, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -90, 90, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 56, 120, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 20, 110, 24, 0, "Ability 4", "Ability 4 description 123456");

            ability0.colour = new Color(0xaa55aa);
            ability1.colour = new Color(0x500F89);
            ability0.colour = new Color(0xB98F2C);
            ability2.colour = new Color(0x920C07);
            ability5.colour = new Color(0x0A8C2E);

            combatAbilities.add(ability0);
            combatAbilities.add(ability1);
            combatAbilities.add(ability2);
            combatAbilities.add(ability3);
            combatAbilities.add(ability4);
            combatAbilities.add(ability5);
            combatAbilities.add(ability6);
        }
        {
            Ability ability0 = new Ability(0, null, -2, -3, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 45, 45, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 150, 90, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -20, 95, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -56, 99, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 100, 150, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 34, 120, 24, 0, "Ability 4", "Ability 4 description 123456");

            ability0.colour = new Color(0xaa55aa);
            ability1.colour = new Color(0x500F89);
            ability0.colour = new Color(0xB98F2C);
            ability2.colour = new Color(0x920C07);
            ability5.colour = new Color(0x0A8C2E);

            miningAbilities.add(ability0);
            miningAbilities.add(ability1);
            miningAbilities.add(ability2);
            miningAbilities.add(ability3);
            miningAbilities.add(ability4);
            miningAbilities.add(ability5);
            miningAbilities.add(ability6);
        }
        {
            Ability ability0 = new Ability(0, null, -10, -2, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 33, 45, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 130, 27, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -56, 120, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -80, 98, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 56, 130, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 20, 134, 24, 0, "Ability 4", "Ability 4 description 123456");

            ability0.colour = new Color(0xaa55aa);
            ability1.colour = new Color(0x500F89);
            ability0.colour = new Color(0xB98F2C);
            ability2.colour = new Color(0x920C07);
            ability5.colour = new Color(0x0A8C2E);

            woodcuttingAbilities.add(ability0);
            woodcuttingAbilities.add(ability1);
            woodcuttingAbilities.add(ability2);
            woodcuttingAbilities.add(ability3);
            woodcuttingAbilities.add(ability4);
            woodcuttingAbilities.add(ability5);
            woodcuttingAbilities.add(ability6);
        }
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
        syncAbilities();
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
        Item item = stack.getItem();
        boolean isMainHand = hand.equals(EnumHand.MAIN_HAND);

        if (isMainHand)
        {
            if (!player.isSneaking())
            {
                if (!isTamed())
                {
                    if (player != getOwner())
                    {
                        if (ItemHelper.isFlower(item))
                        {
                            if (!player.capabilities.isCreativeMode)
                            {
                                stack.shrink(1);
                            }

                            if (!world.isRemote)
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

    public void syncAbilities()
    {
        NetworkHelper.sync(world, new AbilitiesMessage(generalAbilities, combatAbilities, miningAbilities, woodcuttingAbilities, getEntityId()));
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