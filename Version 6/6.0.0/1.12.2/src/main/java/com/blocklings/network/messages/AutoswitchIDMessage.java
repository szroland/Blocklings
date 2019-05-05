package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AutoswitchIDMessage implements IMessage
{
    byte value;
    int id;

    public AutoswitchIDMessage()
    {
    }

    public AutoswitchIDMessage(byte value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = buf.readByte();
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(this.value);
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<AutoswitchIDMessage, IMessage>
    {
        public IMessage onMessage(AutoswitchIDMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
                    blockling.setAutoswitchID(message.value);
                }
            }

            return null;
        }
    }
}