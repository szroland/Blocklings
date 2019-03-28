package com.blocklings.util;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BlocklingType
{
    public static List<BlocklingType> blocklingTypes = new ArrayList<>();
    static
    {
        blocklingTypes.add(new BlocklingType("blockling_0", new ItemStack[] { new ItemStack(Blocks.GRASS), new ItemStack(Blocks.DIRT) },
            5.0, 2.0, 3.0, 3.0));
        blocklingTypes.add(new BlocklingType("blockling_1", new ItemStack[] { new ItemStack(Blocks.LOG) },
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_2", new ItemStack(Blocks.STONE),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_3", new ItemStack(Items.IRON_INGOT),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_4", new ItemStack(Items.QUARTZ),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_5", new ItemStack(Items.GOLD_INGOT),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_6", new ItemStack(Items.DYE, 1, 4),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_7", new ItemStack(Items.EMERALD),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_8", new ItemStack(Items.DIAMOND),
            20.0, 10.0, 5.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_9", new ItemStack(Blocks.OBSIDIAN),
            5.0, 2.0, 3.0, 4.0));
        blocklingTypes.add(new BlocklingType("blockling_10", new ItemStack(Blocks.PUMPKIN),
            5.0, 2.0, 3.0, 4.0));
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
    public double bonusHealth, bonusAttackDamage, bonusAttackSpeed, bonusMovementSpeed;

    public BlocklingType(String textureName, ItemStack itemStack, double bonusHealth, double bonusAttackDamage, double bonusAttackSpeed, double bonusMovementSpeed)
    {
        this.upgradeMaterials = new ItemStack[] { itemStack };
        this.texture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
        this.bonusHealth = bonusHealth;
        this.bonusAttackDamage = bonusAttackDamage;
        this.bonusAttackSpeed = bonusAttackSpeed;
        this.bonusMovementSpeed = bonusMovementSpeed;
    }

    public BlocklingType(String textureName, ItemStack[] upgradeMaterials, double bonusHealth, double bonusAttackDamage, double bonusAttackSpeed, double bonusMovementSpeed)
    {
        this.upgradeMaterials = upgradeMaterials;
        this.texture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
        this.bonusHealth = bonusHealth;
        this.bonusAttackDamage = bonusAttackDamage;
        this.bonusAttackSpeed = bonusAttackSpeed;
        this.bonusMovementSpeed = bonusMovementSpeed;
    }
}
