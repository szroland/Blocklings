package com.blocklings.util.helpers;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ToolHelper
{
    public static List<Item> weapons = new ArrayList<>();
    public static List<Item> pickaxes = new ArrayList<>();
    public static List<Item> axes = new ArrayList<>();

    static
    {
        weapons.add(Items.WOODEN_SWORD);
        weapons.add(Items.STONE_SWORD);
        weapons.add(Items.IRON_SWORD);
        weapons.add(Items.GOLDEN_SWORD);
        weapons.add(Items.DIAMOND_SWORD);

        pickaxes.add(Items.WOODEN_PICKAXE);
        pickaxes.add(Items.STONE_PICKAXE);
        pickaxes.add(Items.IRON_PICKAXE);
        pickaxes.add(Items.GOLDEN_PICKAXE);
        pickaxes.add(Items.DIAMOND_PICKAXE);

        axes.add(Items.WOODEN_AXE);
        axes.add(Items.STONE_AXE);
        axes.add(Items.IRON_AXE);
        axes.add(Items.GOLDEN_AXE);
        axes.add(Items.DIAMOND_AXE);
    }

    public static boolean isEquipment(Item item)
    {
        return weapons.contains(item) || pickaxes.contains(item) || axes.contains(item);
    }

    public static boolean isWeapon(Item item)
    {
        return weapons.contains(item);
    }

    public static boolean isPickaxe(Item item)
    {
        return pickaxes.contains(item);
    }

    public static boolean isAxe(Item item)
    {
        return axes.contains(item);
    }
}
