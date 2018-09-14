package com.blocklings.gui;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerInventoryBlockling extends Container
{
    private InventoryBlockling blocklingInv;
    private InventoryPlayer playerInv;

    private int blocklingInventoryX = 101;
    private int blocklingInventoryY = 17 + GuiHelper.YOFFSET;
    private int playerInventoryX = 36;
    private int playerInventoryY = 84 + GuiHelper.YOFFSET;

    public ContainerInventoryBlockling(InventoryPlayer playerInv, InventoryBlockling blocklingInv)
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
        addSlotToContainer(new Slot(blocklingInv, 0, 36, 62 + GuiHelper.YOFFSET));
        addSlotToContainer(new Slot(blocklingInv, 1, 72, 62 + GuiHelper.YOFFSET));

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                addSlotToContainer(new Slot(blocklingInv, j + (i * 5) + 2, blocklingInventoryX + (j * 18), blocklingInventoryY + (i * 18)));
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
}
