package com.blocklings.util.helpers;

import com.blocklings.util.CropStruct;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BlockHelper
{
    // ORES

    public static List<Block> ores = new ArrayList<>();
    public static boolean isOre(Block block)
    {
        return ores.contains(block);
    }
    public static double getOreValue(Block block) { return ores.indexOf(block); }

    // ORES END


    // LOGS

    public static List<Block> logs = new ArrayList<>();
    public static boolean isLog(Block block)
    {
        return logs.contains(block);
    }

    // LOGS END


    // LEAVES

    public static List<Block> leaves = new ArrayList<>();
    public static boolean isLeaf(Block block)
    {
        return leaves.contains(block);
    }

    // LEAVES END


    // DIRT

    private static List<Block> dirt = new ArrayList<>();
    static
    {
        dirt.add(Blocks.DIRT);
        dirt.add(Blocks.GRASS);
    }

    public static boolean isDirt(Block block)
    {
        return dirt.contains(block);
    }

    // DIRT END


    // CROPS

    private static List<CropStruct> crops = new ArrayList<>();
    static
    {
        crops.add(new CropStruct(Blocks.WHEAT, Items.WHEAT_SEEDS, 7));
        crops.add(new CropStruct(Blocks.POTATOES, Items.POTATO, 7));
        crops.add(new CropStruct(Blocks.CARROTS, Items.CARROT, 7));
        crops.add(new CropStruct(Blocks.BEETROOTS, Items.BEETROOT_SEEDS, 3));

        crops.add(new CropStruct(Blocks.PUMPKIN, Items.AIR, -1));
        crops.add(new CropStruct(Blocks.MELON_BLOCK, Items.AIR, -1));
    }

    public static boolean isCrop(Block block)
    {
        for (CropStruct cropStruct : crops)
        {
            if (cropStruct.crop == block)
            {
                return true;
            }
        }

        return false;
    }

    public static Item getSeed(Block block)
    {
        for (CropStruct cropStruct : crops)
        {
            if (cropStruct.crop == block)
            {
                return cropStruct.seed;
            }
        }

        return Items.AIR;
    }

    public static boolean isGrown(IBlockState blockState)
    {
        int grownAge = getGrownAge(blockState.getBlock());
        if (grownAge != -1)
        {
            int age = BlockHelper.getAge(blockState);
            if (age < grownAge)
            {
                return false;
            }
        }

        return true;
    }

    public static int getGrownAge(Block block)
    {
        for (CropStruct cropStruct : crops)
        {
            if (cropStruct.crop == block)
            {
                return cropStruct.age;
            }
        }

        return 0;
    }

    public static int getAge(IBlockState blockState)
    {
        if (blockState.getBlock() == Blocks.BEETROOTS)
        {
            return blockState.getValue(BlockBeetroot.BEETROOT_AGE);
        }

        return blockState.getValue(BlockCrops.AGE);
    }

    // CROPS END
}
