package com.blocklings.network;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;

import io.netty.buffer.ByteBuf;
import org.jline.utils.Log;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuiIDMessage implements IMessage {
	
	int guiID;
	int id;

	public GuiIDMessage() {
	}

	public GuiIDMessage(int guiIDValue, int entityID) {
		this.guiID = guiIDValue;
		this.id = entityID;
	}

	public void fromBytes(ByteBuf buf) {
		this.guiID = buf.readInt();
		this.id = buf.readInt();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.guiID);
		buf.writeInt(this.id);
	}

	public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<GuiIDMessage, IMessage> {
		public IMessage onMessage(GuiIDMessage message, MessageContext ctx) {
			Entity entity = null;

			if ((ctx.side.isClient()) && (Blocklings.proxy.getPlayer(ctx) != null)) {
				entity = Blocklings.proxy.getPlayer(ctx).world.getEntityByID(message.id);

				if (entity instanceof EntityBlockling) {
					EntityBlockling blockling = (EntityBlockling) entity;

					blockling.setGuiIDFromPacket(message.guiID);
				}
			} else if (ctx.side.isServer() && Blocklings.proxy.getPlayer(ctx) != null) {
				entity = Blocklings.proxy.getPlayer(ctx).world.getEntityByID(message.id);

				if ((entity instanceof EntityBlockling)) {
					EntityBlockling blockling = (EntityBlockling) entity;
					
					blockling.setGuiIDFromPacket(message.guiID);
				}
			}

			return null;
		}
	}
	
}