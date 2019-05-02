package com.blocklings.util.helpers;

import com.blocklings.util.BlocklingType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemHelper
{
    /**
     * Used to determine if an item is of type 'flower'
     */
    public static boolean isFlower(Item item)
    {
        return item == Item.getItemFromBlock(Blocks.RED_FLOWER) || item == Item.getItemFromBlock(Blocks.YELLOW_FLOWER) || item == Item.getItemFromBlock(Blocks.CHORUS_FLOWER);
    }

    private static List<ItemStack> upgradeMaterials = new ArrayList<ItemStack>();
    static
    {
        for (BlocklingType type : BlocklingType.blocklingTypes)
        {
            upgradeMaterials.addAll(Arrays.asList(type.upgradeMaterials));
        }
    }

    public static boolean isUpgradeMaterial(ItemStack itemStack)
    {
        for (ItemStack material : upgradeMaterials)
        {
            if (material.getItem() == itemStack.getItem() && material.getMetadata() == itemStack.getMetadata())
            {
                return true;
            }
        }

        return false;
    }

    // SAPLINGS

    public static HashMap<IBlockState, ItemStack> saplings = new HashMap<>();
    public static ItemStack getSaplingForLog(IBlockState log)
    {
        return saplings.get(log);
    }

    // END SAPLINGS
}
