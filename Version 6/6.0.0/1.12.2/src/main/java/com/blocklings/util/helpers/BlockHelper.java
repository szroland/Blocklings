package com.blocklings.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.List;

public class BlockHelper
{
    private static List<Block> ores = new ArrayList();

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

    public static double getOreValue(Block block) { return ores.indexOf(block); }
    public static boolean isOre(Block block)
    {
        return ores.contains(block);
    }
}
