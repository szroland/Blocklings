package com.blocklings.inventories;

import com.blocklings.entities.EntityBlockling;

import com.blocklings.util.helpers.ItemHelper;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventoryBlockling extends InventoryBasic
{
    private EntityBlockling blockling;

    public InventoryBlockling(EntityBlockling blockling, String inventoryTitle, int slotCount)
    {
        super(inventoryTitle, true, slotCount);

        this.blockling = blockling;
    }
}
