package com.blocklings.main;

import com.blocklings.util.helpers.BlockHelper;
import com.blocklings.util.helpers.EntityHelper;
import com.blocklings.util.helpers.ItemHelper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import scala.Int;
import scala.collection.mutable.MultiMap;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

public class BlocklingsConfig
{
	private static final String CATEGORY_BLOCKLINGS = "blocklings";

	public static int SPAWN_RATE;

	public static void load()
	{
		File configFile = new File("config/blocklings.cfg");
		Configuration config = new Configuration(configFile);
		config.load();

		Property spawn_rate = config.get(CATEGORY_BLOCKLINGS, "spawn rate",10);
		spawn_rate.setComment("Higher = more often");
		int[] bids = new int[EntityHelper.biomes.size()]; int i = 0;
		for (int id : EntityHelper.biomes)
		{
			bids[i] = id;
			i++;
		}
		Property biomes = config.get(CATEGORY_BLOCKLINGS, "biomes", bids);
		biomes.setComment("List of all biome ids blocklings can spawn in (they still need grass to spawn on)");

		Property xp_multiplier = config.get(CATEGORY_BLOCKLINGS, "xp multiplier",1.0);
		xp_multiplier.setComment("Multiplies gained xp by the given value");

		Property ores = config.get(CATEGORY_BLOCKLINGS, "ores", new int[] { 14, 15, 16, 21, 56, 73, 129, 153 });
		ores.setComment("List of block IDs for ores");
		Property logs = config.get(CATEGORY_BLOCKLINGS, "logs", new String[] { "17:0,6:0", "17:1,6:1", "17:2,6:2", "17:3,6:3", "162:0,6:4", "162:1,6:5" });
		logs.setComment("A:B,C:D (A = Log ID, B = Log Meta, C = Sapling ID, D = Sapling Meta)");
		Property leaves = config.get(CATEGORY_BLOCKLINGS, "leaves", new int[] { 18, 161 });
		leaves.setComment("List of block IDs for leaves");

		List<String> properties = new ArrayList<>();
		properties.add(spawn_rate.getName());
		properties.add(biomes.getName());
		properties.add(xp_multiplier.getName());
		properties.add(ores.getName());
		properties.add(logs.getName());
		properties.add(leaves.getName());
		config.setCategoryPropertyOrder(CATEGORY_BLOCKLINGS, properties);

		spawn_rate.set(spawn_rate.getInt());
		biomes.set(biomes.getIntList());
		xp_multiplier.set(xp_multiplier.getDouble());
		ores.set(ores.getIntList());
		logs.set(logs.getStringList());
		leaves.set(leaves.getIntList());
		config.save();

		SPAWN_RATE = spawn_rate.getInt();
		EntityHelper.XP_MULTIPLIER = xp_multiplier.getDouble();

		getBiomes(biomes.getIntList());
		getOres(ores.getIntList());
		getLogs(logs.getStringList());
		getLeaves(leaves.getIntList());
	}

	public static void getBiomes(int[] biomeList)
	{
		for (int id : biomeList)
		{
			if (id >= 0)
			{
				EntityHelper.biomes.add(id);
			}
		}
	}

	public static void getOres(int[] oreList)
	{
		for (int id : oreList)
		{
			Block block = Block.getBlockFromItem(new ItemStack(Item.getItemById(id), 1, 0).getItem());
			if (block != Blocks.AIR) BlockHelper.ores.add(block);
		}
	}

	public static void getLogs(String[] logList)
	{
		for (String str : logList)
		{
			String log = str.split(",")[0];
			int logID = Integer.parseInt(log.split(":")[0]);
			int logMeta = Integer.parseInt(log.split(":")[1]);
			String sapling = str.split(",")[1];
			int saplingID = Integer.parseInt(sapling.split(":")[0]);
			int saplingMeta = Integer.parseInt(sapling.split(":")[1]);

			Block logBlock = Block.getBlockFromItem(new ItemStack(Item.getItemById(logID), 1, 0).getItem());
			if (logBlock != Blocks.AIR) BlockHelper.logs.add(logBlock);

			ItemHelper.saplings.put(logBlock.getStateFromMeta(logMeta), new ItemStack(Item.getItemById(saplingID), 1, saplingMeta));
		}
	}

	public static void getLeaves(int[] leafList)
	{
		for (int id : leafList)
		{
			Block block = Block.getBlockFromItem(new ItemStack(Item.getItemById(id), 1, 0).getItem());
			if (block != Blocks.AIR) BlockHelper.leaves.add(block);
		}
	}
}
