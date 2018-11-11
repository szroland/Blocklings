package com.blocklings.util.helpers;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.render.RenderBlockling;

import com.blocklings.util.ResourceLocationBlocklings;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class EntityHelper
{
    public static final float BASE_SCALE = 0.75f;
    public static final float BASE_SCALE_FOR_HITBOX = BASE_SCALE * 1.1f;

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
