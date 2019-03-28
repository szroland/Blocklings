package com.blocklings.items;

import com.blocklings.abilities.AbilityGroup;
import com.blocklings.entities.EntityBlockling;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemBlockling extends Item implements ItemMeshDefinition
{
    private String name;

    public ItemBlockling(String name)
    {
        this.name = name;
        setRegistryName(name);
        setUnlocalizedName(name);
        setMaxStackSize(1);
    }

    public static ItemStack createStack(EntityBlockling blockling)
    {
        ItemStack stack = new ItemStack(BlocklingsItems.itemBlockling, 1, 0);
        NBTTagCompound c = stack.getTagCompound();
        if (c == null) c = new NBTTagCompound();

        if (blockling != null)
        {
            blockling.generalAbilities.writeToNBT(c);
            blockling.combatAbilities.writeToNBT(c);
            blockling.miningAbilities.writeToNBT(c);
            blockling.woodcuttingAbilities.writeToNBT(c);
            blockling.farmingAbilities.writeToNBT(c);
        }

        stack.setTagCompound(c);
        return stack;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
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
            EntityBlockling blockling = new EntityBlockling(
                worldIn,
                AbilityGroup.createFromNBTAndId(c, 0),
                AbilityGroup.createFromNBTAndId(c, 1),
                AbilityGroup.createFromNBTAndId(c, 2),
                AbilityGroup.createFromNBTAndId(c, 3),
                AbilityGroup.createFromNBTAndId(c, 4)
            );
            blockling.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);

            worldIn.spawnEntity(blockling);

            return EnumActionResult.PASS;
        }

        return EnumActionResult.FAIL;
    }

    @Override
    public ModelResourceLocation getModelLocation(ItemStack stack)
    {
        return new ModelResourceLocation(this.getRegistryName() + "_8", "inventory");
    }
}
