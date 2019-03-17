package com.blocklings.util.helpers;

import com.blocklings.main.Blocklings;
import com.blocklings.network.*;
import javafx.scene.transform.Scale;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.security.Guard;

public class NetworkHelper
{
    private static SimpleNetworkWrapper network = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(Blocklings.MODID);
    private static int id = 0;

    public static void registerMessages()
    {
        network.registerMessage(AbilitiesMessage.Handler.class, AbilitiesMessage.class, id++, Side.CLIENT);
        network.registerMessage(AbilitiesMessage.Handler.class, AbilitiesMessage.class, id++, Side.SERVER);
        network.registerMessage(UnlockedSlotsMessage.Handler.class, UnlockedSlotsMessage.class, id++, Side.CLIENT);
        network.registerMessage(UnlockedSlotsMessage.Handler.class, UnlockedSlotsMessage.class, id++, Side.SERVER);
        network.registerMessage(ScaleMessage.Handler.class, ScaleMessage.class, id++, Side.CLIENT);
        network.registerMessage(ScaleMessage.Handler.class, ScaleMessage.class, id++, Side.SERVER);
        network.registerMessage(NameMessage.Handler.class, NameMessage.class, id++, Side.CLIENT);
        network.registerMessage(NameMessage.Handler.class, NameMessage.class, id++, Side.SERVER);
        network.registerMessage(AnimationStateMessage.Handler.class, AnimationStateMessage.class, id++, Side.CLIENT);
        network.registerMessage(AnimationStateMessage.Handler.class, AnimationStateMessage.class, id++, Side.SERVER);
        network.registerMessage(GuiIDMessage.Handler.class, GuiIDMessage.class, id++, Side.CLIENT);
        network.registerMessage(GuiIDMessage.Handler.class, GuiIDMessage.class, id++, Side.SERVER);
        network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, id++, Side.CLIENT);
        network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, id++, Side.SERVER);
        network.registerMessage(TaskIDMessage.Handler.class, TaskIDMessage.class, id++, Side.CLIENT);
        network.registerMessage(TaskIDMessage.Handler.class, TaskIDMessage.class, id++, Side.SERVER);
        network.registerMessage(GuardIDMessage.Handler.class, GuardIDMessage.class, id++, Side.CLIENT);
        network.registerMessage(GuardIDMessage.Handler.class, GuardIDMessage.class, id++, Side.SERVER);
        network.registerMessage(StateIDMessage.Handler.class, StateIDMessage.class, id++, Side.CLIENT);
        network.registerMessage(StateIDMessage.Handler.class, StateIDMessage.class, id++, Side.SERVER);
        network.registerMessage(GeneralLevelMessage.Handler.class, GeneralLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(GeneralLevelMessage.Handler.class, GeneralLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(CombatLevelMessage.Handler.class, CombatLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(CombatLevelMessage.Handler.class, CombatLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningLevelMessage.Handler.class, MiningLevelMessage.class, id++, Side.CLIENT);
        network.registerMessage(MiningLevelMessage.Handler.class, MiningLevelMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningIntervalMessage.Handler.class, MiningIntervalMessage.class, id++, Side.CLIENT);
        network.registerMessage(MiningIntervalMessage.Handler.class, MiningIntervalMessage.class, id++, Side.SERVER);
        network.registerMessage(MiningTimerMessage.Handler.class, MiningTimerMessage.class, id++, Side.CLIENT);
        network.registerMessage(MiningTimerMessage.Handler.class, MiningTimerMessage.class, id++, Side.SERVER);
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
        network.registerMessage(AutoswitchIDMessage.Handler.class, AutoswitchIDMessage.class, id++, Side.SERVER);
        network.registerMessage(AutoswitchIDMessage.Handler.class, AutoswitchIDMessage.class, id++, Side.SERVER);
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