package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AttackingHandMessage implements IMessage
{
    EnumHand value;
    int id;

    public AttackingHandMessage()
    {
    }

    public AttackingHandMessage(EnumHand value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = EnumHand.values()[buf.readInt()];
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.value.ordinal());
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<AttackingHandMessage, IMessage>
    {
        public IMessage onMessage(AttackingHandMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
                    blockling.setAttackingHand(message.value);
                }
            }

            return null;
        }
    }
}