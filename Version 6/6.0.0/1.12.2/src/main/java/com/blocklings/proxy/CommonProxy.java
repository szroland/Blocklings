package com.blocklings.proxy;

import com.blocklings.guis.GuiHandler;
import com.blocklings.main.Blocklings;
import com.blocklings.util.helpers.EntityHelper;
import com.blocklings.util.helpers.NetworkHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@Mod.EventBusSubscriber
public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        EntityHelper.registerEntities();
        NetworkHelper.registerMessages();
        NetworkRegistry.INSTANCE.registerGuiHandler(Blocklings.instance, new GuiHandler());
    }

    public void init(FMLInitializationEvent e)
    {

    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {

    }

    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().player;
    }
}
