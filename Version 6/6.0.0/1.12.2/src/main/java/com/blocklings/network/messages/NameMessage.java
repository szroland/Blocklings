package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class NameMessage implements IMessage
{
    String value;
    int id;

    public NameMessage()
    {
    }

    public NameMessage(String value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = ByteBufUtils.readUTF8String(buf);
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, value);
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<NameMessage, IMessage>
    {
        public IMessage onMessage(NameMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
                    blockling.setName(message.value);
                }
            }

            return null;
        }
    }
}