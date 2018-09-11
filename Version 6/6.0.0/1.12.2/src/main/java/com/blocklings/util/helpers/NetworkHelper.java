package com.blocklings.util.helpers;

import com.blocklings.main.Blocklings;
import com.blocklings.network.GuiIDMessage;
import com.blocklings.network.OpenGuiMessage;
import com.blocklings.network.GeneralLevelMessage;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHelper
{
	
  private static SimpleNetworkWrapper network = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(Blocklings.MODID);
  private static int id = 0;
  
  public static void registerMessages() {
    network.registerMessage(GuiIDMessage.Handler.class, com.blocklings.network.GuiIDMessage.class, id++, Side.CLIENT);
    network.registerMessage(GuiIDMessage.Handler.class, com.blocklings.network.GuiIDMessage.class, id++, Side.SERVER);
    network.registerMessage(OpenGuiMessage.Handler.class, com.blocklings.network.OpenGuiMessage.class, id++, Side.CLIENT);
    network.registerMessage(OpenGuiMessage.Handler.class, com.blocklings.network.OpenGuiMessage.class, id++, Side.SERVER);
    network.registerMessage(GeneralLevelMessage.Handler.class, com.blocklings.network.GeneralLevelMessage.class, id++, Side.CLIENT);
    network.registerMessage(GeneralLevelMessage.Handler.class, com.blocklings.network.GeneralLevelMessage.class, id++, Side.SERVER);
  }
  
  public static void sendToAll(IMessage message) {
    network.sendToAll(message);
  }
  
  public static void sendToServer(IMessage message) {
    network.sendToServer(message);
  }
  
  public static void sync(World world, IMessage message) {
	  if (!world.isRemote) {
		  sendToAll(message);
	  } else {
		  sendToServer(message);
	  }
  }
  
}