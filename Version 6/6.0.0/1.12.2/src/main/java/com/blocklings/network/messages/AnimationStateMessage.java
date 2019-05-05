package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.entity.entities.EntityBlockling.AnimationState;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AnimationStateMessage implements IMessage
{
    AnimationState value;
    int id;

    public AnimationStateMessage()
    {
    }

    public AnimationStateMessage(AnimationState value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = AnimationState.values()[buf.readInt()];
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.value.ordinal());
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<AnimationStateMessage, IMessage>
    {
        public IMessage onMessage(AnimationStateMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
                    if (ctx.side.isServer())
                    {
                        blockling.setAnimationState(message.value);
                    }
                    else
                    {
                        blockling.animationState = message.value;
                    }
                }
            }

            return null;
        }
    }
}