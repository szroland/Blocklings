package com.blocklings.entities;

import com.blocklings.abilities.AbilityGroup;
import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.network.*;
import com.blocklings.util.helpers.*;
import com.blocklings.util.helpers.GuiHelper.Tab;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.tools.Tool;
import java.util.Random;

public class EntityBlockling extends EntityTameable implements IEntityAdditionalSpawnData
{
    public static final Random RANDOM = new Random();

    public enum AnimationState { IDLE, MINING }

    public static final double BASE_MAX_HEALTH = 8;
    public static final double BASE_MOVEMENT_SPEED = 0.6;
    public static final double BASE_ATTACK_DAMAGE = 4;

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

    private EntityHelper.Task task = EntityHelper.Task.IDLE;
    private EntityHelper.Guard guard = EntityHelper.Guard.NOGUARD;
    private EntityHelper.State state = EntityHelper.State.WANDER;

    private int generalLevel = 4, combatLevel = 13, miningLevel = 7, woodcuttingLevel = 6;
    private int generalXp = 0, combatXp = 0, miningXp = 0, woodcuttingXp = 0;
    private int attackInterval = 20, miningInterval = 20, choppingInterval = 20;
    private int miningTimer = -1, choppingTimer = -1;

    private byte autoswitchID = 0;

    private BlocklingAIFollowOwner aiFollow;
    private BlocklingAIWanderAvoidWater aiWander;

    private BlocklingAIAttackMelee aiAttackMelee;
    private BlocklingAIOwnerHurtByTarget aiOwnerHurtBy;
    private BlocklingAIOwnerHurtTarget aiOwnerHurt;

    private ItemStack leftHandStack = ItemStack.EMPTY, rightHandStack = ItemStack.EMPTY;

    private BlocklingAIMining aiMining;
    private BlocklingAIWoodcutting aiWoodcutting;
    private BlocklingAIHunt aiHunt;

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
        aiOwnerHurtBy = new BlocklingAIOwnerHurtByTarget(this);
        aiOwnerHurt = new BlocklingAIOwnerHurtTarget(this);
        aiAttackMelee = new BlocklingAIAttackMelee(this, true);
        aiFollow = new BlocklingAIFollowOwner(this, 2, 8);
        aiWander = new BlocklingAIWanderAvoidWater(this, 0.5F);
        aiMining = new BlocklingAIMining(this);
        aiWoodcutting= new BlocklingAIWoodcutting(this);
        aiHunt = new BlocklingAIHunt(this);

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, aiSit);
        this.tasks.addTask(3, aiAttackMelee);
        this.tasks.addTask(4, aiMining);
        this.tasks.addTask(4, aiWoodcutting);
        this.tasks.addTask(6, aiFollow);
        this.tasks.addTask(8, aiWander);
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, aiOwnerHurtBy);
        this.targetTasks.addTask(2, aiOwnerHurt);
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(4, aiHunt);
    }

    private void UpdateAI()
    {

    }

    @Override
    public void setAttackTarget(EntityLivingBase ent)
    {
        super.setAttackTarget(ent);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());

        return super.attackEntityAsMob(entityIn);
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

        c.setInteger("TaskID", task.id);
        c.setInteger("GuardID", guard.id);
        c.setInteger("StateID", state.id);

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

        task = EntityHelper.Task.getFromID(c.getInteger("TaskID"));
        guard = EntityHelper.Guard.getFromID(c.getInteger("GuardID"));
        state = EntityHelper.State.getFromID(c.getInteger("StateID"));

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

        setHeldItem(getHeldItemMainhand(), EnumHand.MAIN_HAND);
        setHeldItem(getHeldItemOffhand(), EnumHand.OFF_HAND);
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

        buf.writeInt(task.id);
        buf.writeInt(guard.id);
        buf.writeInt(state.id);

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ByteBufUtils.writeItemStack(buf, inv.getStackInSlot(i));
        }

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

        task = EntityHelper.Task.getFromID(buf.readInt());
        guard = EntityHelper.Guard.getFromID(buf.readInt());
        state = EntityHelper.State.getFromID(buf.readInt());

        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            inv.setInventorySlotContents(i, ByteBufUtils.readItemStack(buf));
        }

        setSize(EntityHelper.BASE_SCALE_FOR_HITBOX * scale, EntityHelper.BASE_SCALE_FOR_HITBOX * scale);
    }

    // Called once every tick
    // Used by skeles to check if they are in the sun
    // CLIENT SERVER
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!world.isRemote)
        {
            checkTimers();
        }
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
                else // Is tamed
                {
                    if (player == getOwner())
                    {
                        if (ToolHelper.isEquipment(item))
                        {
                            setHeldItemFromInteract(stack, EnumHand.MAIN_HAND, player);
                        }
                    }
                }
            }
            else // Is sneaking
            {openGui(player);
                if (isTamed())
                {
                    if (player == getOwner())
                    {
                        if (ToolHelper.isEquipment(item))
                        {
                            setHeldItemFromInteract(stack, EnumHand.OFF_HAND, player);
                        }

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

    private void setHeldItemFromInteract(ItemStack stack, EnumHand hand, EntityPlayer player)
    {
        int index = hand == EnumHand.MAIN_HAND ? 0 : 1;
        ItemStack currentStack = inv.getStackInSlot(index);

        if (player.capabilities.isCreativeMode)
        {
            player.setHeldItem(EnumHand.MAIN_HAND, currentStack);
            setHeldItem(stack, hand);
        }
        else
        {
            if (player.addItemStackToInventory(currentStack))
            {
                setHeldItem(stack, hand);
            }
        }
    }

    @Override
    public ItemStack getHeldItem(EnumHand hand)
    {
        if (hand == EnumHand.MAIN_HAND)
        {
            return getHeldItemMainhand();
        }
        else if (hand == EnumHand.OFF_HAND)
        {
            return getHeldItemOffhand();
        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getHeldItemOffhand()
    {
        return inv.getStackInSlot(1);
    }

    @Override
    public ItemStack getHeldItemMainhand()
    {
        return inv.getStackInSlot(0);
    }

    private void setHeldItem(ItemStack stack, EnumHand hand)
    {
        if (stack != null && !stack.isEmpty())
        {
            int index = hand == EnumHand.MAIN_HAND ? 0 : 1;
            inv.setInventorySlotContents(index, stack);
        }
    }

    public void damageItem(EnumHand hand)
    {
        getHeldItemMainhand().damageItem(1, this);
        getHeldItemOffhand().damageItem(1, this);
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
        setAttackTarget(null);
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

    private void checkTimers()
    {
        if (isMining())
        {
            setAnimationState(AnimationState.MINING);
        }
        else
        {
            setAnimationState(AnimationState.IDLE);
        }

        if (miningTimer >= 0)
        {
            incrementMiningTimer();
            if (miningTimer > miningInterval)
            {
                stopMining();
            }
        }
    }

    public void startMining()
    {
        setMiningTimer(0);
    }

    public void stopMining()
    {
        setMiningTimer(-1);
    }

    public boolean isMining()
    {
        return miningTimer != -1;
    }

    public boolean hasPickaxe()
    {
        return hasPickaxe(EnumHand.MAIN_HAND) || hasPickaxe(EnumHand.OFF_HAND);
    }

    public boolean hasPickaxe(EnumHand hand)
    {
        return ToolHelper.isPickaxe(getHeldItem(hand).getItem());
    }

    public boolean hasAxe()
    {
        return hasAxe(EnumHand.MAIN_HAND) || hasAxe(EnumHand.OFF_HAND);
    }

    public boolean hasAxe(EnumHand hand)
    {
        return ToolHelper.isAxe(getHeldItem(hand).getItem());
    }



    // SPECIAL
    // GETTERS
    // AND
    // SETTERS

    public EntityHelper.Task getTask()
    {
        return task;
    }

    public void setTask(EntityHelper.Task value)
    {
        task = value;
        NetworkHelper.sync(world, new TaskIDMessage(value.id, getEntityId()));
        UpdateAI();
    }

    public void setTaskFromPacket(int value)
    {
        task = EntityHelper.Task.getFromID(value);
    }

    @SideOnly(Side.CLIENT)
    public void cycleTask()
    {
        int id = task.id + 1;
        if (id > EntityHelper.Task.values().length)
        {
            id = 1;
        }

        task = EntityHelper.Task.getFromID(id);
    }


    public EntityHelper.Guard getGuard()
    {
        return guard;
    }

    public void setGuard(EntityHelper.Guard value)
    {
        guard = value;
        NetworkHelper.sync(world, new GuardIDMessage(value.id, getEntityId()));
        UpdateAI();
    }

    public void setGuardFromPacket(int value)
    {
        guard = EntityHelper.Guard.getFromID(value);
    }

    @SideOnly(Side.CLIENT)
    public void cycleGuard()
    {
        int id = guard.id + 1;
        if (id > EntityHelper.Guard.values().length)
        {
            id = 1;
        }

        guard = EntityHelper.Guard.getFromID(id);
    }


    public EntityHelper.State getState()
    {
        return state;
    }

    public void setState(EntityHelper.State value)
    {
        state = value;
        NetworkHelper.sync(world, new StateIDMessage(value.id, getEntityId()));
        UpdateAI();
    }

    public void setStateFromPacket(int value)
    {
        state = EntityHelper.State.getFromID(value);
    }

    @SideOnly(Side.CLIENT)
    public void cycleState()
    {
        int id = state.id + 1;
        if (id > EntityHelper.State.values().length)
        {
            id = 1;
        }

        state = EntityHelper.State.getFromID(id);
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



    public int getMiningInterval()
    {
        return miningInterval;
    }

    public void setMiningInterval(int value)
    {
        miningInterval = value;
        NetworkHelper.sync(world, new MiningIntervalMessage(value, getEntityId()));
    }

    public void setMiningIntervalFromPacket(int value)
    {
        miningInterval = value;
    }



    public int getMiningTimer()
    {
        return miningTimer;
    }

    public void incrementMiningTimer()
    {
        setMiningTimer(miningTimer + 1);
    }

    public void setMiningTimer(int value)
    {
        miningTimer = value;
        NetworkHelper.sync(world, new MiningTimerMessage(value, getEntityId()));
    }

    public void setMiningTimerFromPacket(int value)
    {
        miningTimer = value;
    }



    public boolean hasTarget()
    {
        return aiMining.hasTarget() || aiWoodcutting.hasTarget();
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