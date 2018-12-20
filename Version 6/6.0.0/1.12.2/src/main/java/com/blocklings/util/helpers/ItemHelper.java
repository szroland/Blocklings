package com.blocklings.util.helpers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

    public static boolean isEquipment(Item item)
    {
        // --------------------------------------------------------------------------- \\
        return item == Items.WOODEN_SWORD || item == Items.STONE_SWORD ||item == Items.IRON_SWORD ||item == Items.GOLDEN_SWORD ||item == Items.DIAMOND_SWORD ||
               item == Items.WOODEN_PICKAXE || item == Items.STONE_PICKAXE ||item == Items.IRON_PICKAXE ||item == Items.GOLDEN_PICKAXE ||item == Items.DIAMOND_PICKAXE ||
               item == Items.WOODEN_AXE || item == Items.STONE_AXE ||item == Items.IRON_AXE ||item == Items.GOLDEN_AXE ||item == Items.DIAMOND_AXE ||
               item == Items.WOODEN_SHOVEL || item == Items.STONE_SHOVEL ||item == Items.IRON_SHOVEL ||item == Items.GOLDEN_SHOVEL ||item == Items.DIAMOND_SHOVEL ||
               item == Items.WOODEN_HOE || item == Items.STONE_SWORD ||item == Items.IRON_SWORD ||item == Items.GOLDEN_SWORD ||item == Items.DIAMOND_SWORD;
    }
}
