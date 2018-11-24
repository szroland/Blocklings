package com.blocklings.guis;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jline.utils.Log;

public class ContainerInventoryBlockling extends Container
{
    private InventoryBlockling blocklingInv;
    private InventoryPlayer playerInv;

    private int blocklingInventoryX = 36;
    private int blocklingInventoryY = 8 + GuiHelper.YOFFSET;
    private int playerInventoryX = 36;
    private int playerInventoryY = 84 + GuiHelper.YOFFSET;

    public ContainerInventoryBlockling(InventoryPlayer playerInv, InventoryBlockling blocklingInv)
    {
        this.blocklingInv = blocklingInv;
        this.playerInv = playerInv;

        //bindBlocklingInventory();
        bindPlayerInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    private void bindBlocklingInventory()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(blocklingInv, j + (i * 9), blocklingInventoryX + (j * 18), blocklingInventoryY + (i * 18)));
            }
        }
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

//    @Override
//    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
//    {
//        ItemStack stack = null;
//        Slot slot = inventorySlots.get(slotIndex);
//        Log.info(slotIndex);
//        if (slot != null && slot.getHasStack())
//        {
//            ItemStack slotItemStack = slot.getStack();
//            stack = slotItemStack.copy();
//        }
//
//        return stack;
//    }
}
