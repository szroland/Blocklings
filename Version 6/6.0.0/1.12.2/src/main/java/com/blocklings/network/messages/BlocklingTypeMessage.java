package com.blocklings.network.messages;

import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.util.BlocklingType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class BlocklingTypeMessage implements IMessage
{
    BlocklingType value;
    int id;

    public BlocklingTypeMessage()
    {
    }

    public BlocklingTypeMessage(BlocklingType value, int entityID)
    {
        this.value = value;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        this.value = BlocklingType.getTypeFromTextureName(ByteBufUtils.readUTF8String(buf));
        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.value.textureName);
        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<BlocklingTypeMessage, IMessage>
    {
        public IMessage onMessage(BlocklingTypeMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);

                if (blockling != null)
                {
                    if (ctx.side.isClient())
                    {
                        blockling.blocklingType = message.value;
                    }
                    else
                    {
                        blockling.setBlocklingTypeFromPacketOnServer(message.value);
                    }
                }
            }

            return null;
        }
    }
}