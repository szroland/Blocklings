package com.blocklings.entities;

import com.blocklings.abilities.AbilityGroup;
import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.network.*;
import com.blocklings.util.helpers.AbilityHelper;
import com.blocklings.util.helpers.EntityHelper;
import com.blocklings.util.helpers.ItemHelper;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class EntityBlockling extends EntityTameable implements IEntityAdditionalSpawnData
{
    public static final Random RANDOM = new Random();

    public enum AnimationState { IDLE }

    public static final double BASE_MAX_HEALTH = 10;
    public static final double BASE_MOVEMENT_SPEED = 0.6;
    public static final double BASE_ATTACK_DAMAGE = 1;

    public InventoryBlockling inv;
    private int unlockedSlots = 12;

    public AbilityGroup generalAbilities;
    public AbilityGroup combatAbilities;
    public AbilityGroup miningAbilities;
    public AbilityGroup woodcuttingAbilities;

    @SideOnly(Side.CLIENT)
    public boolean isInGui = false;

    private float scale;

    private AnimationState animationState = AnimationState.IDLE;

    private int guiID = 1;

    private int generalLevel = 4, combatLevel = 13, miningLevel = 7, woodcuttingLevel = 6;
    private int generalXp = 0, combatXp = 0, miningXp = 0, woodcuttingXp = 0;

    private byte autoswitchID = 0;

    private BlocklingAIFollowOwner aiFollow;
    private EntityAIWander aiWander;

    private EntityAIOwnerHurtByTarget aiOwnerHurtBy;
    private EntityAIOwnerHurtTarget aiOwnerHurt;

    private ItemStack leftHandStack = ItemStack.EMPTY, rightHandStack = ItemStack.EMPTY;

    private BlocklingAIMining aiMining;

    // CLIENT SERVER
    public EntityBlockling(World worldIn)
    {
        super(worldIn);
        setSize(1.0f, 1.0f);
    }

    // CLIENT SERVER
    @Override
    protected void entityInit()
    {
        super.entityInit();

        aiMining = new BlocklingAIMining(this);

        setupInventory();

        unlockedSlots = 12;

        if ((generalAbilities == null || generalAbilities.abilities.size() == 0))
        {
            generalAbilities = new AbilityGroup(0, "General", AbilityHelper.generalAbilities);
            combatAbilities = new AbilityGroup(1, "Combat", AbilityHelper.combatAbilities);
            miningAbilities = new AbilityGroup(2, "Mining", AbilityHelper.miningAbilities);
            woodcuttingAbilities = new AbilityGroup(3, "Woodcutting", AbilityHelper.woodcuttingAbilities);
        }

        if (!world.isRemote)
        {
            do
            {
                scale = 1.0f + ((float) RANDOM.nextGaussian() / 15.0f);
            }
            while (scale < 0.75f || scale > 1.25f);
        }
    }

    /**
     * Returns new PathNavigateGround instance
     */
    @Override
    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateGroundBlockling(this, worldIn);
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
        aiFollow = new BlocklingAIFollowOwner(this, 1, 2, 8);
        aiWander = new EntityAIWander(this, 1);
        aiOwnerHurtBy = new EntityAIOwnerHurtByTarget(this);
        aiOwnerHurt = new EntityAIOwnerHurtTarget(this);

        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        tasks.addTask(1, new BlocklingAIFollowOwner(this, 1, 2, 8));
        tasks.addTask(2, aiSit);
        tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, true));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        tasks.addTask(6, new EntityAILookIdle(this));
        tasks.addTask(1, aiMining);
        tasks.addTask(2, aiOwnerHurtBy);
        tasks.addTask(3, aiOwnerHurt);
    }

    // Used to save entity data (variables) when the entity is unloaded
    // SERVER
    @Override
    public void writeEntityToNBT(NBTTagCompound c)
    {
        super.writeEntityToNBT(c);

        c.setInteger("UnlockedSlots", unlockedSlots);
        c.setFloat("Scale", scale);
        c.setInteger("GuiID", guiID);

        c.setByte("AutoswitchID", autoswitchID);

        generalAbilities.writeToNBT(c);
        combatAbilities.writeToNBT(c);
        miningAbilities.writeToNBT(c);
        woodcuttingAbilities.writeToNBT(c);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.inv.getSizeInventory(); i++)
        {
            ItemStack itemstack = this.inv.getStackInSlot(i);
            if (itemstack != null && !itemstack.isEmpty())
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("slot", (byte) i);
                itemstack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        c.setTag("items", nbttaglist);
    }

    // Used to load entity data (variables) when the entity is loaded
    // SERVER
    @Override
    public void readEntityFromNBT(NBTTagCompound c)
    {
        super.readEntityFromNBT(c);

        unlockedSlots = c.getInteger("UnlockedSlots");
        scale = c.getFloat("Scale");
        guiID = c.getInteger("GuiID");

        autoswitchID = c.getByte("AutoswitchID");

        generalAbilities = AbilityGroup.createFromNBTAndId(c, 0);
        combatAbilities = AbilityGroup.createFromNBTAndId(c, 1);
        miningAbilities = AbilityGroup.createFromNBTAndId(c, 2);
        woodcuttingAbilities = AbilityGroup.createFromNBTAndId(c, 3);

        NBTTagList tag = c.getTagList("items", 10);
        for (int i = 0; i < tag.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = tag.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("slot") & 0xFF;
            if ((j >= 0) && (j < this.inv.getSizeInventory())) {
                this.inv.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
            }
        }
    }

    // Used to save the data (variables) that need to be synced on spawn
    // SERVER
    @Override
    public void writeSpawnData(ByteBuf buf)
    {
        AbilityHelper.writeSpawnData(buf, this);

        buf.writeInt(unlockedSlots);
        buf.writeFloat(scale);
        buf.writeInt(animationState.ordinal());
        buf.writeInt(guiID);

        buf.writeByte(autoswitchID);

        setSize(EntityHelper.BASE_SCALE_FOR_HITBOX * scale, EntityHelper.BASE_SCALE_FOR_HITBOX * scale);
    }

    // Used to sync client with server on spawn
    // CLIENT
    @Override
    public void readSpawnData(ByteBuf buf)
    {
        AbilityHelper.readSpawnData(buf, this);

        unlockedSlots = buf.readInt();
        scale = buf.readFloat();
        animationState = AnimationState.values()[buf.readInt()];
        guiID = buf.readInt();

        autoswitchID = buf.readByte();

        setSize(EntityHelper.BASE_SCALE_FOR_HITBOX * scale, EntityHelper.BASE_SCALE_FOR_HITBOX * scale);
    }

    // Called once every tick
    // Used by skeles to check if they are in the sun
    // CLIENT SERVER
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    }

    // Also called once every tick
    // Not sure what the difference is between the two update methods
    // CLIENT SERVER
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
            {openGui(player);
                if (isTamed())
                {
                    if (player == getOwner())
                    {
                        //openGui(player);
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
            if (guiID != Tab.INVENTORY.id && guiID != Tab.EQUIPMENT.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
        }
        else
        {
            if (guiID == Tab.INVENTORY.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
            else if (guiID == Tab.EQUIPMENT.id)
                player.openGui(Blocklings.instance, guiID, world, getEntityId(), 0, 0);
        }
    }

    /**
     * Updates the guiID in both sides, then opens whatever it needs to on both sides.
     */
    public void openGui(int guiID, EntityPlayer player)
    {
        setGuiID(guiID);
        openGui(player);
        NetworkHelper.sync(world, new OpenGuiMessage(getEntityId()));
    }

    private void setTamed(EntityPlayer player)
    {
        setTamedBy(player);
        navigator.clearPath();
        setAttackTarget((EntityLivingBase) null);
        //aiSit.setSitting(true);
        playTameEffect(true);
        world.setEntityState(this, (byte) 7);
        setName(!getCustomNameTag().equals("") ? getCustomNameTag() : "Blockling");
    }

    private void setupInventory()
    {
        InventoryBlockling invTemp = inv;
        inv = new InventoryBlockling(this, "Inventory", 38);
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
        if (generalAbilities == null || combatAbilities == null || miningAbilities == null || woodcuttingAbilities == null)
        {
            return;
        }

        NetworkHelper.sync(world, new AbilitiesMessage(generalAbilities, combatAbilities, miningAbilities, woodcuttingAbilities, getEntityId()));
    }

    // GETTERS
    // AND
    // SETTERS

    public int getUnlockedSlots()
    {
        return unlockedSlots;
    }

    public void setUnlockedSlots(int value)
    {
        unlockedSlots = value;
        NetworkHelper.sync(world, new UnlockedSlotsMessage(value, getEntityId()));
    }

    public void setUnlockedSlotsFromPacket(int value)
    {
        unlockedSlots = value;
    }

    public float getBlocklingScale()
    {
        return scale;
    }

    public void setBlocklingScale(float value)
    {
        scale = value;
        NetworkHelper.sync(world, new ScaleMessage(value, getEntityId()));
    }

    public void setScaleFromPacket(float value)
    {
        scale = value;
    }



    @SideOnly(Side.CLIENT)
    public void setName(String value)
    {
        if (value == null || value.equals(""))
        {
            setCustomNameTag("Blockling");
        }
        else
        {
            setCustomNameTag(value);
        }
        NetworkHelper.sync(world, new NameMessage(getCustomNameTag(), getEntityId()));
    }

    public void setNameFromPacket(String value)
    {
        setCustomNameTag(value);
    }



    public AnimationState getAnimationState()
    {
        return animationState;
    }

    public void setAnimationState(AnimationState value)
    {
        animationState = value;
        NetworkHelper.sync(world, new AnimationStateMessage(value, getEntityId()));
    }

    public void setAnimationStateFromPacket(AnimationState value)
    {
        animationState = value;
    }



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



    public int getGeneralXp()
    {
        return generalXp;
    }

    public void setGeneralXp(int value)
    {
        generalXp = value;
        NetworkHelper.sync(world, new GeneralXpMessage(value, getEntityId()));
    }

    public void setGeneralXpFromPacket(int value)
    {
        generalXp = value;
    }



    public int getCombatXp()
    {
        return combatXp;
    }

    public void setCombatXp(int value)
    {
        combatXp = value;
        NetworkHelper.sync(world, new CombatXpMessage(value, getEntityId()));
    }

    public void setCombatXpFromPacket(int value)
    {
        combatXp = value;
    }



    public int getMiningXp()
    {
        return miningXp;
    }

    public void setMiningXp(int value)
    {
        miningXp = value;
        NetworkHelper.sync(world, new MiningXpMessage(value, getEntityId()));
    }

    public void setMiningXpFromPacket(int value)
    {
        miningXp = value;
    }



    public int getWoodcuttingXp()
    {
        return woodcuttingXp;
    }

    public void setWoodcuttingXp(int value)
    {
        woodcuttingXp = value;
        NetworkHelper.sync(world, new WoodcuttingXpMessage(value, getEntityId()));
    }

    public void setWoodcuttingXpFromPacket(int value)
    {
        woodcuttingXp = value;
    }



    public boolean getAutoswitchLeft()
    {
        return (autoswitchID & 2) > 0;
    }

    public boolean getAutoswitchRight()
    {
        return (autoswitchID & 1) > 0;
    }

    public void setAutoswitchLeft(boolean on)
    {
        byte result = autoswitchID;
        if (on)
        {
            result = (byte) (autoswitchID | 2); // 10
        }
        else
        {
            result = (byte) (autoswitchID & 1); // 01
        }

        setAutoswitchID(result);
    }

    public void setAutoswitchRight(boolean on)
    {
        byte result = autoswitchID;
        if (on)
        {
            result = (byte) (autoswitchID | 1); // 01
        }
        else
        {
            result = (byte) (autoswitchID & 2); // 10
        }

        setAutoswitchID(result);
    }

    private void setAutoswitchID(byte value)
    {
        autoswitchID = value;
        NetworkHelper.sync(world, new WoodcuttingXpMessage(value, getEntityId()));
    }

    public void setAutoswitchIDFromPacket(byte value)
    {
        autoswitchID = value;
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