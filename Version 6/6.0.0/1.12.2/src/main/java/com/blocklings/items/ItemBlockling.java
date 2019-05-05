package com.blocklings.items;

import com.blocklings.abilities.AbilityGroup;
import com.blocklings.abilities.AbilityGroupType;
import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.entity.enums.Guard;
import com.blocklings.entity.enums.State;
import com.blocklings.entity.enums.Task;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemBlockling extends Item
{
    private String name;

    public ItemBlockling(String name)
    {
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        NBTTagCompound c = stack.getTagCompound();
        if (c != null)
        {
            return TextFormatting.GOLD + c.getString("Name");
        }

        return TextFormatting.WHITE + "Blockling";
    }

    public static ItemStack createStack(EntityBlockling blockling, boolean saveProgress)
    {
        ItemStack stack = new ItemStack(BlocklingsItems.itemBlockling, 1, 0);
        NBTTagCompound c = stack.getTagCompound();
        if (c == null) c = new NBTTagCompound();

        if (blockling != null)
        {
            blockling.writeEntityToNBT(c);
            c.setInteger("TaskID", Task.IDLE.id);
            c.setInteger("GuardID", Guard.NOGUARD.id);
            c.setInteger("StateID", State.FOLLOW.id);

            if (!saveProgress)
            {
                c.removeTag("items");

                c.setInteger("CombatLevel", 1);
                c.setInteger("MiningLevel", 1);
                c.setInteger("WoodcuttingLevel", 1);
                c.setInteger("FarmingLevel", 1);

                c.setInteger("CombatXp", 0);
                c.setInteger("MiningXp", 0);
                c.setInteger("WoodcuttingXp", 0);
                c.setInteger("FarmingXp", 0);

                c.setInteger("SkillPoints", 0);

                AbilityGroup generalAbilities = new AbilityGroup(AbilityGroupType.GENERAL);
                AbilityGroup combatAbilities = new AbilityGroup(AbilityGroupType.COMBAT);
                AbilityGroup miningAbilities = new AbilityGroup(AbilityGroupType.MINING);
                AbilityGroup woodcuttingAbilities = new AbilityGroup(AbilityGroupType.WOODCUTTING);
                AbilityGroup farmingAbilities = new AbilityGroup(AbilityGroupType.FARMING);

                generalAbilities.writeToNBT(c);
                combatAbilities.writeToNBT(c);
                miningAbilities.writeToNBT(c);
                woodcuttingAbilities.writeToNBT(c);
                farmingAbilities.writeToNBT(c);
            }
        }

        stack.setTagCompound(c);
        return stack;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote || hand != EnumHand.MAIN_HAND)
        {
            return EnumActionResult.SUCCESS;
        }

        ItemStack stack = player.getHeldItem(hand);
        NBTTagCompound c = stack.getTagCompound();

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.0;
        double z = pos.getZ() + 0.5;

        if (c != null)
        {
            EntityBlockling blockling = new EntityBlockling(worldIn);
            blockling.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
            blockling.readEntityFromNBT(c);
            blockling.setOwnerId(player.getUniqueID());

            worldIn.spawnEntity(blockling);
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            return EnumActionResult.PASS;
        }
        else
        {
            EntityBlockling blockling = new EntityBlockling(worldIn);
            blockling.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);

            worldIn.spawnEntity(blockling);
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            return EnumActionResult.PASS;
        }
    }
}
