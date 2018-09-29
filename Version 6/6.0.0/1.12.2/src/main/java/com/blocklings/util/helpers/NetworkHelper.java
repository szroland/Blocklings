package com.blocklings.util.helpers;

import com.blocklings.main.Blocklings;
import com.blocklings.network.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHelper
{
    private static SimpleNetworkWrapper network = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(Blocklings.MODID);
    private static int id = 0;

    public static void registerMessages()
    {
        network.registerMessage(AbilitiesMessage.Handler.class, AbilitiesMessage.class, id++, Side.CLIENT);
        network.registerMessage(AbilitiesMessage.Handler.class, AbilitiesMessage.class, id++, Side.SERVER);
        network.registerMessage(GuiIDMessage.Handler.class, GuiIDMessage.class, id++, Side.CLIENT);
        network.registerMessage(GuiIDMessage.Handler.class, GuiIDMessage.class, id++, Side.SERVER);
        network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, id++, Side.CLIENT);
        network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, id++, Side.SERVER);
        network.registerMessage(GeneralLevelMessage.Handler.class, GeneralLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(GeneralLevelMessage.Handler.class, GeneralLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(CombatLevelMessage.Handler.class, CombatLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(CombatLevelMessage.Handler.class, CombatLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningLevelMessage.Handler.class, MiningLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(MiningLevelMessage.Handler.class, MiningLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(WoodcuttingLevelMessage.Handler.class, WoodcuttingLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(WoodcuttingLevelMessage.Handler.class, WoodcuttingLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(GeneralXpMessage.Handler.class, GeneralXpMessage.class, id++, Side.SERVER);
        network.registerMessage(GeneralXpMessage.Handler.class, GeneralXpMessage.class, id++, Side.SERVER);
        network.registerMessage(CombatXpMessage.Handler.class, CombatXpMessage.class, id++, Side.SERVER);
        network.registerMessage(CombatXpMessage.Handler.class, CombatXpMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningXpMessage.Handler.class, MiningXpMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningXpMessage.Handler.class, MiningXpMessage.class, id++, Side.SERVER);
        network.registerMessage(WoodcuttingXpMessage.Handler.class, WoodcuttingXpMessage.class, id++, Side.SERVER);
        network.registerMessage(WoodcuttingXpMessage.Handler.class, WoodcuttingXpMessage.class, id++, Side.SERVER);
    }

    public static void sendToAll(IMessage message)
    {
        network.sendToAll(message);
    }

    public static void sendToServer(IMessage message)
    {
        network.sendToServer(message);
    }

    public static void sync(World world, IMessage message)
    {
        if (!world.isRemote)
        {
            sendToAll(message);
        }
        else
        {
            sendToServer(message);
        }
    }
}