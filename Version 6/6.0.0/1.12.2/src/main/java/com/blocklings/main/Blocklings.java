package com.blocklings.main;

import org.apache.logging.log4j.Logger;

import com.blocklings.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Blocklings.MODID, name = Blocklings.MODNAME, version = Blocklings.VERSION, useMetadata = true)
public class Blocklings {

    public static final String MODID = "blocklings";
    public static final String MODNAME = "Blocklings";
    public static final String VERSION = "5.0.0a";

    @SidedProxy(clientSide = "com.blocklings.proxy.ClientProxy", serverSide = "com.blocklings.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Blocklings instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
    
}