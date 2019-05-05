package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.entity.enums.State;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StateIDMessage implements IMessage
{
    State value;
    int id;

    public StateIDMessage()
    {
    }

    public StateIDMessage(State value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = State.getFromID(buf.readInt());
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.value.id);
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<StateIDMessage, IMessage>
    {
        public IMessage onMessage(StateIDMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
                    blockling.setState(message.value);
                }
            }

            return null;
        }
    }
}