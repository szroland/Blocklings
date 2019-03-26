package com.blocklings.util;

import jdk.nashorn.internal.ir.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class BlocklingType
{
    public static List<BlocklingType> blocklingTypes = new ArrayList<>();
    static
    {
        blocklingTypes.add(new BlocklingType("blockling_0", new ItemStack[] { new ItemStack(Blocks.GRASS), new ItemStack(Blocks.DIRT) }));
        blocklingTypes.add(new BlocklingType("blockling_1", new ItemStack[] { new ItemStack(Blocks.LOG) }));
        blocklingTypes.add(new BlocklingType("blockling_2", new ItemStack(Blocks.STONE)));
        blocklingTypes.add(new BlocklingType("blockling_3", new ItemStack(Items.IRON_INGOT)));
        blocklingTypes.add(new BlocklingType("blockling_4", new ItemStack(Items.QUARTZ)));
        blocklingTypes.add(new BlocklingType("blockling_5", new ItemStack(Items.GOLD_INGOT)));
        blocklingTypes.add(new BlocklingType("blockling_6", new ItemStack(Items.DYE, 1, 4)));
        blocklingTypes.add(new BlocklingType("blockling_7", new ItemStack(Items.EMERALD)));
        blocklingTypes.add(new BlocklingType("blockling_8", new ItemStack(Items.DIAMOND)));
        blocklingTypes.add(new BlocklingType("blockling_9", new ItemStack(Blocks.OBSIDIAN)));
    }

    public static BlocklingType getTypeFromItemStack(ItemStack itemStack)
    {
        for (BlocklingType blocklingType : blocklingTypes)
        {
            for (ItemStack typeStack : blocklingType.upgradeMaterials)
            {
                if (itemStack.getItem().equals(typeStack.getItem()) && itemStack.getMetadata() == typeStack.getMetadata())
                {
                    return blocklingType;
                }
            }
        }

        return null;
    }


    public ResourceLocation texture;
    public ItemStack[] upgradeMaterials;

    public BlocklingType(String textureName, ItemStack itemStack)
    {
        this.upgradeMaterials = new ItemStack[] { itemStack };
        this.texture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
    }

    public BlocklingType(String textureName, ItemStack[] upgradeMaterials)
    {
        this.upgradeMaterials = upgradeMaterials;
        this.texture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
    }
}
