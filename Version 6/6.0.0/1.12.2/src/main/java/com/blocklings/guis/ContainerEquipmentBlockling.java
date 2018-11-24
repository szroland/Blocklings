package com.blocklings.guis;

import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
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
        addSlotToContainer(new Slot(blocklingInv, 0, 45, 27 + GuiHelper.YOFFSET));
        addSlotToContainer(new Slot(blocklingInv, 1, 171, 27 + GuiHelper.YOFFSET));
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
