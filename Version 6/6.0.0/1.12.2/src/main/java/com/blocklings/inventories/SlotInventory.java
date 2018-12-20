package com.blocklings.inventories;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.helpers.ItemHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInventory extends Slot
{
    EntityBlockling blockling;
    int index;

    public SlotInventory(EntityBlockling blockling, IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
        this.blockling = blockling;
        this.index = index;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        int unlockedSlots = blockling.getUnlockedSlots();
        int u = unlockedSlots / 12;
        if (unlockedSlots < 36)
        {
            if (index < 2 || (index >= 5 + (3 * (u - 1)) && index < 11) || (index >= 14 + (3 * (u - 1)) && index < 20) || (index >= 23 + (3 * (u - 1)) && index < 29) || (index >= 32 + (3 * (u - 1)) && index < 38))
            {
                return false;
            }
        }

        return true;
    }
}
