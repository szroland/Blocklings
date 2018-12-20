package com.blocklings.inventories;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jline.utils.Log;

public class ContainerEquipmentBlockling extends Container
{
    private InventoryBlockling blocklingInv;
    private InventoryPlayer playerInv;

    private int playerInventoryX = 36;
    private int playerInventoryY = 84 + GuiHelper.YOFFSET;

    public ContainerEquipmentBlockling(InventoryPlayer playerInv, InventoryBlockling blocklingInv)
    {
        this.blocklingInv = blocklingInv;
        this.playerInv = playerInv;

        bindBlocklingInventory();
        bindPlayerInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    private void bindBlocklingInventory()
    {
        addSlotToContainer(new SlotEquipment(blocklingInv, 0, 54, 30 + GuiHelper.YOFFSET));
        addSlotToContainer(new SlotEquipment(blocklingInv, 1, 162, 30 + GuiHelper.YOFFSET));
    }

    private void bindPlayerInventory()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, playerInventoryX + (j * 18), playerInventoryY + (i * 18)));
            }
        }

        for (int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(playerInv, i, playerInventoryX + (i * 18), playerInventoryY + 58));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            stack = itemStack.copy();

            if (slotIndex == 0 || slotIndex == 1)
            {
                if (!this.mergeItemStack(itemStack, 2, 38, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (!this.mergeItemStack(itemStack, 0, 2, false))
                {
                    return ItemStack.EMPTY;
                }
            }
        }

        return stack;
    }
}
