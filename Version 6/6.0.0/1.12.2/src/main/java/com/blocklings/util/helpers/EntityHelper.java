package com.blocklings.util.helpers;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.render.RenderBlockling;

import com.blocklings.util.ResourceLocationBlocklings;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHelper
{
    public static void registerEntities()
    {
        int id = 1;
        EntityRegistry.registerModEntity(new ResourceLocationBlocklings("entity_blockling"), EntityBlockling.class, "Blockling", id++, Blocklings.instance, 64, 3, true, 0xffff00, 0x00ffff);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBlockling.class, RenderBlockling.FACTORY);
    }
}
