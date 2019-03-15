package com.blocklings.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockHelper
{
    // ORES

    private static List<Block> ores = new ArrayList<>();
    static
    {
        ores.add(Blocks.QUARTZ_ORE);
        ores.add(Blocks.COAL_ORE);
        ores.add(Blocks.IRON_ORE);
        ores.add(Blocks.GOLD_ORE);
        ores.add(Blocks.LAPIS_ORE);
        ores.add(Blocks.REDSTONE_ORE);
        ores.add(Blocks.LIT_REDSTONE_ORE);
        ores.add(Blocks.EMERALD_ORE);
        ores.add(Blocks.DIAMOND_ORE);
    }

    public static boolean isOre(Block block)
    {
        return ores.contains(block);
    }
    public static double getOreValue(Block block) { return ores.indexOf(block); }

    // ORES END


    // LOGS

    private static List<Block> logs = new ArrayList<>();
    static
    {
        logs.add(Blocks.LOG);
        logs.add(Blocks.LOG2);
    }

    public static boolean isLog(Block block)
    {
        return logs.contains(block);
    }

    // LOGS END


    // LEAVES

    private static List<Block> leaves = new ArrayList<>();
    static
    {
        leaves.add(Blocks.LEAVES);
        leaves.add(Blocks.LEAVES2);
    }

    public static boolean isLeaf(Block block)
    {
        return leaves.contains(block);
    }

    // LEAVES END
}
