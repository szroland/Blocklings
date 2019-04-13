package com.blocklings.proxy;

import com.blocklings.render.BlocklingsResourcePack;
import com.blocklings.util.helpers.EntityHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
        EntityHelper.registerRenderers();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
//        List<IResourcePack> defaultResourcePacks = ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks");
//        defaultResourcePacks.add(new BlocklingsResourcePack());
//        Minecraft.getMinecraft().refreshResources();
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayer(ctx);
    }
}