package com.blocklings.items;

import com.blocklings.main.Blocklings;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Blocklings.MODID)
public class BlocklingsItems
{
    public static Item itemBlockling;

    public static void init()
    {
        itemBlockling = new ItemBlockling("blockling");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(itemBlockling);
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event)
    {
        ModelBakery.registerItemVariants(itemBlockling, new ModelResourceLocation(itemBlockling.getRegistryName(), "inventory"));
        ModelBakery.registerItemVariants(itemBlockling, new ModelResourceLocation(itemBlockling.getRegistryName() + "_8", "inventory"));
        ModelBakery.registerItemVariants(itemBlockling, new ModelResourceLocation(itemBlockling.getRegistryName() + "_20", "inventory"));

        ModelLoader.setCustomMeshDefinition(itemBlockling, new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(itemBlockling.getRegistryName() + "_8", "inventory");
            }
        });
    }
}
