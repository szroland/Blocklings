package com.blocklings.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerProxy extends CommonProxy
{
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().player;
    }
}