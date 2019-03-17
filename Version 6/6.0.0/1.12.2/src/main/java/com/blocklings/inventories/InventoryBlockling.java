package com.blocklings.inventories;

import com.blocklings.entities.EntityBlockling;

import com.blocklings.util.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
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

    @Override
    public ItemStack addItem(ItemStack stack)
    {
        ItemStack itemstack = stack.copy();
        int unlockedSlots = blockling.getUnlockedSlots();
        int u = unlockedSlots / 12;

        // Easch row
        for (int p = 0; p < 4; p++)
        {
            // Cols unlocked in each row
            int startIndex = 2 + (9 * p);
            int endIndex = 2 + (3 * u) + (9 * p);
            for (int i = startIndex; i < endIndex; ++i)
            {
                ItemStack itemstack1 = this.getStackInSlot(i);

                if (itemstack1.isEmpty())
                {
                    this.setInventorySlotContents(i, itemstack);
                    this.markDirty();
                    return ItemStack.EMPTY;
                }

                if (ItemStack.areItemsEqual(itemstack1, itemstack))
                {
                    int j = Math.min(this.getInventoryStackLimit(), itemstack1.getMaxStackSize());
                    int k = Math.min(itemstack.getCount(), j - itemstack1.getCount());

                    if (k > 0)
                    {
                        itemstack1.grow(k);
                        itemstack.shrink(k);

                        if (itemstack.isEmpty())
                        {
                            this.markDirty();
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }
        }

        if (itemstack.getCount() != stack.getCount())
        {
            this.markDirty();
        }

        return itemstack;
    }
}
