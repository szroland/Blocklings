package com.blocklings.util.helpers;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class ItemHelper
{
    /**
     * Used to determine if an item is of type 'flower'
     */
    public static boolean isFlower(Item item)
    {
        return item == Item.getItemFromBlock(Blocks.RED_FLOWER) || item == Item.getItemFromBlock(Blocks.YELLOW_FLOWER) || item == Item.getItemFromBlock(Blocks.CHORUS_FLOWER);
    }
}
