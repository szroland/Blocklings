package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int entityID, int unused1, int unused2)
    {
        Entity entity = world.getEntityByID(entityID);

        if (entity != null && entity instanceof EntityBlockling)
        {
            EntityBlockling blockling = (EntityBlockling) entity;

            if (id == 0)
            {
                return new ContainerInventoryBlockling(player.inventory, blockling.inv);
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int entityID, int unused1, int unused2)
    {
        Entity entity = world.getEntityByID(entityID);

        if (entity != null && entity instanceof EntityBlockling)
        {
            EntityBlockling blockling = (EntityBlockling) entity;

            if (id == 0)
            {
                return new GuiBlocklingInventory(player.inventory, blockling.inv, blockling, player);
            }
            else if (id == 1)
            {
                return new GuiBlocklingOverall(blockling, player);
            }
            else if (id == 2)
            {
                return new GuiBlocklingOverall(blockling, player);
            }
        }

        return null;
    }
}
