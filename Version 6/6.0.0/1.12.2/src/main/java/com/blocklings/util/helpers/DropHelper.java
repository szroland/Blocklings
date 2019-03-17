package com.blocklings.util.helpers;

import com.blocklings.entities.EntityBlockling;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DropHelper
{
    public static NonNullList<ItemStack> getDops(EntityBlockling blockling, World world, BlockPos blockPos)
    {
        NonNullList<ItemStack> dropStacks = NonNullList.create();

        IBlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        block.getDrops(dropStacks, world, blockPos, blockState, 0);

        return dropStacks;
    }
}
