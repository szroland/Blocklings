package com.blocklings.util;

import com.blocklings.items.BlocklingsItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
            0.2, 0.0, 0.0, 0.0, 0.0));
        blocklingTypes.add(new BlocklingType("blockling_1", new ItemStack[] { new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Blocks.LOG, 1, 1), new ItemStack(Blocks.LOG, 1, 2), new ItemStack(Blocks.LOG, 1, 3), new ItemStack(Blocks.LOG2, 1, 0), new ItemStack(Blocks.LOG2, 1, 1) },
                0.2, 2.0, 1.0, 2.0, 1.0));
        blocklingTypes.add(new BlocklingType("blockling_2", new ItemStack(Blocks.STONE),
                0.2, 5.0, 1.0, 1.0, -1.0));
        blocklingTypes.add(new BlocklingType("blockling_3", new ItemStack(Blocks.IRON_BLOCK),
                1.0, 7.0, 3.0, 2.0, 0.0));
        blocklingTypes.add(new BlocklingType("blockling_4", new ItemStack(Blocks.QUARTZ_BLOCK),
                0.5, 0.0, 4.0, 3.0, 2.0));
        blocklingTypes.add(new BlocklingType("blockling_5", new ItemStack(Blocks.GOLD_BLOCK),
                1.0, 0.0, 3.0, 5.0, 5.0));
        blocklingTypes.add(new BlocklingType("blockling_6", new ItemStack(Blocks.LAPIS_BLOCK),
                0.5, 3.0, 3.0, 3.0, 2.0));
        blocklingTypes.add(new BlocklingType("blockling_7", new ItemStack(Blocks.EMERALD_BLOCK),
                1.0, 8.0, 5.0, 2.0, 2.0));
        blocklingTypes.add(new BlocklingType("blockling_8", new ItemStack(Blocks.DIAMOND_BLOCK),
                1.0, 10.0, 10.0, 5.0, 3.0));
        blocklingTypes.add(new BlocklingType("blockling_9", new ItemStack(Blocks.OBSIDIAN),
                0.5, 50.0, 10.0, -5.0, -5.0));
        blocklingTypes.add(new BlocklingType("blockling_10", new ItemStack(Blocks.PUMPKIN),
                0.5, 0.0, 3.0, 3.0, 3.0));
        blocklingTypes.add(new BlocklingType("blockling_11", new ItemStack(Blocks.BEDROCK),
                1.0, 5000.0, 2000.0, 3000.0, 0.001));
    }

    public static BlocklingType getTypeFromTextureName(String textureName)
    {
        for (BlocklingType blocklingType : blocklingTypes)
        {
            if (textureName.equals(blocklingType.textureName))
            {
                return blocklingType;
            }
        }

        return blocklingTypes.get(0);
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


    public String textureName;
    public ResourceLocation entityTexture;
    public ModelResourceLocation itemModel;
    public ItemStack[] upgradeMaterials;
    public double upgradeChance, bonusHealth, bonusAttackDamage, bonusAttackSpeed, bonusMovementSpeed;

    public BlocklingType(String textureName, ItemStack itemStack, double upgradeChance, double bonusHealth, double bonusAttackDamage, double bonusAttackSpeed, double bonusMovementSpeed)
    {
        this.upgradeMaterials = new ItemStack[] { itemStack };
        this.textureName = textureName;
        this.entityTexture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
        this.itemModel = new ModelResourceLocation(BlocklingsItems.itemBlockling.getRegistryName() + textureName.substring(textureName.lastIndexOf("_")), "inventory");
        this.upgradeChance = upgradeChance;
        this.bonusHealth = bonusHealth;
        this.bonusAttackDamage = bonusAttackDamage;
        this.bonusAttackSpeed = bonusAttackSpeed;
        this.bonusMovementSpeed = bonusMovementSpeed;
    }

    public BlocklingType(String textureName, ItemStack[] upgradeMaterials, double upgradeChance, double bonusHealth, double bonusAttackDamage, double bonusAttackSpeed, double bonusMovementSpeed)
    {
        this.upgradeMaterials = upgradeMaterials;
        this.textureName = textureName;
        this.entityTexture = new ResourceLocationBlocklings("textures/entities/blockling/" + textureName + ".png");
        this.itemModel = new ModelResourceLocation(BlocklingsItems.itemBlockling.getRegistryName() + textureName.substring(textureName.lastIndexOf("_")), "inventory");
        this.upgradeChance = upgradeChance;
        this.bonusHealth = bonusHealth;
        this.bonusAttackDamage = bonusAttackDamage;
        this.bonusAttackSpeed = bonusAttackSpeed;
        this.bonusMovementSpeed = bonusMovementSpeed;
    }
}
