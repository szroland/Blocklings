package com.blocklings.util.helpers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
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

    private static List<Item> upgradeMaterials = new ArrayList<Item>();
    static
    {
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.DIRT));
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.GRASS));
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.LOG));
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.LOG2));
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.STONE));
        upgradeMaterials.add(Items.IRON_INGOT);
        upgradeMaterials.add(Items.QUARTZ);
        upgradeMaterials.add(Items.GOLD_INGOT);
        upgradeMaterials.add(Items.DYE);
        upgradeMaterials.add(Items.EMERALD);
        upgradeMaterials.add(Items.DIAMOND);
        upgradeMaterials.add(Item.getItemFromBlock(Blocks.OBSIDIAN));
    }

    public static boolean isUpgradeMaterial(Item item)
    {
        return upgradeMaterials.contains(item);
    }
}
