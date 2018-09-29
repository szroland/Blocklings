package com.blocklings.network;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.commons.lang3.SerializationUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesMessage implements IMessage
{
    List<Ability> generalAbilities;
    List<Ability> combatAbilities;
    List<Ability> miningAbilities;
    List<Ability> woodcuttingAbilities;
    int id;

    public AbilitiesMessage()
    {
    }

    public AbilitiesMessage(List<Ability> generalAbilities, List<Ability> combatAbilities, List<Ability> miningAbilities, List<Ability> woodcuttingAbilities, int entityID)
    {
        this.generalAbilities = generalAbilities;
//        this.combatAbilities = combatAbilities;
//        this.miningAbilities = miningAbilities;
//        this.woodcuttingAbilities = woodcuttingAbilities;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        int g = buf.readInt();
        int c = buf.readInt();
        int m = buf.readInt();
        int w = buf.readInt();

        generalAbilities = new ArrayList<Ability>();
        combatAbilities = new ArrayList<Ability>();
        miningAbilities = new ArrayList<Ability>();
        woodcuttingAbilities = new ArrayList<Ability>();

        for (int i = 0; i < g; i++)
        {
            Ability ability = new Ability();
            ability.id = buf.readInt();
            ability.parentId = buf.readInt();
            ability.state = Ability.State.values()[buf.readInt()];
            ability.colour = new Color(buf.readInt());
            ability.textureX = buf.readInt();
            ability.textureY = buf.readInt();
            ability.width = buf.readInt();
            ability.height = buf.readInt();
            ability.x = buf.readInt();
            ability.y = buf.readInt();
            ability.name = ByteBufUtils.readUTF8String(buf);
            ability.description = ByteBufUtils.readUTF8String(buf);
            generalAbilities.add(ability);
        }
        for (Ability ability : generalAbilities)
        {
            for (Ability ability2 : generalAbilities)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < g; i++)
        {
            Ability ability = new Ability();
            ability.id = buf.readInt();
            ability.parentId = buf.readInt();
            ability.state = Ability.State.values()[buf.readInt()];
            ability.colour = new Color(buf.readInt());
            ability.textureX = buf.readInt();
            ability.textureY = buf.readInt();
            ability.width = buf.readInt();
            ability.height = buf.readInt();
            ability.x = buf.readInt();
            ability.y = buf.readInt();
            ability.name = ByteBufUtils.readUTF8String(buf);
            ability.description = ByteBufUtils.readUTF8String(buf);
            combatAbilities.add(ability);
        }
        for (Ability ability : combatAbilities)
        {
            for (Ability ability2 : combatAbilities)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < g; i++)
        {
            Ability ability = new Ability();
            ability.id = buf.readInt();
            ability.parentId = buf.readInt();
            ability.state = Ability.State.values()[buf.readInt()];
            ability.colour = new Color(buf.readInt());
            ability.textureX = buf.readInt();
            ability.textureY = buf.readInt();
            ability.width = buf.readInt();
            ability.height = buf.readInt();
            ability.x = buf.readInt();
            ability.y = buf.readInt();
            ability.name = ByteBufUtils.readUTF8String(buf);
            ability.description = ByteBufUtils.readUTF8String(buf);
            miningAbilities.add(ability);
        }
        for (Ability ability : miningAbilities)
        {
            for (Ability ability2 : miningAbilities)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < g; i++)
        {
            Ability ability = new Ability();
            ability.id = buf.readInt();
            ability.parentId = buf.readInt();
            ability.state = Ability.State.values()[buf.readInt()];
            ability.colour = new Color(buf.readInt());
            ability.textureX = buf.readInt();
            ability.textureY = buf.readInt();
            ability.width = buf.readInt();
            ability.height = buf.readInt();
            ability.x = buf.readInt();
            ability.y = buf.readInt();
            ability.name = ByteBufUtils.readUTF8String(buf);
            ability.description = ByteBufUtils.readUTF8String(buf);
            woodcuttingAbilities.add(ability);
        }
        for (Ability ability : woodcuttingAbilities)
        {
            for (Ability ability2 : woodcuttingAbilities)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }

        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(generalAbilities.size());
        buf.writeInt(combatAbilities.size());
        buf.writeInt(miningAbilities.size());
        buf.writeInt(woodcuttingAbilities.size());

        for (Ability ability : generalAbilities)
        {
            buf.writeInt(ability.id);
            if (ability.parentAbility != null) buf.writeInt(ability.parentAbility.id);
            else buf.writeInt(-1);
            buf.writeInt(ability.state.ordinal());
            buf.writeInt(ability.colour.getRGB());
            buf.writeInt(ability.textureX);
            buf.writeInt(ability.textureY);
            buf.writeInt(ability.width);
            buf.writeInt(ability.height);
            buf.writeInt(ability.x);
            buf.writeInt(ability.y);
            ByteBufUtils.writeUTF8String(buf, ability.name);
            ByteBufUtils.writeUTF8String(buf, ability.description);
        }
        for (Ability ability : combatAbilities)
        {
            buf.writeInt(ability.id);
            if (ability.parentAbility != null) buf.writeInt(ability.parentAbility.id);
            else buf.writeInt(-1);
            buf.writeInt(ability.state.ordinal());
            buf.writeInt(ability.colour.getRGB());
            buf.writeInt(ability.textureX);
            buf.writeInt(ability.textureY);
            buf.writeInt(ability.width);
            buf.writeInt(ability.height);
            buf.writeInt(ability.x);
            buf.writeInt(ability.y);
            ByteBufUtils.writeUTF8String(buf, ability.name);
            ByteBufUtils.writeUTF8String(buf, ability.description);
        }
        for (Ability ability : miningAbilities)
        {
            buf.writeInt(ability.id);
            if (ability.parentAbility != null) buf.writeInt(ability.parentAbility.id);
            else buf.writeInt(-1);
            buf.writeInt(ability.state.ordinal());
            buf.writeInt(ability.colour.getRGB());
            buf.writeInt(ability.textureX);
            buf.writeInt(ability.textureY);
            buf.writeInt(ability.width);
            buf.writeInt(ability.height);
            buf.writeInt(ability.x);
            buf.writeInt(ability.y);
            ByteBufUtils.writeUTF8String(buf, ability.name);
            ByteBufUtils.writeUTF8String(buf, ability.description);
        }
        for (Ability ability : woodcuttingAbilities)
        {
            buf.writeInt(ability.id);
            if (ability.parentAbility != null) buf.writeInt(ability.parentAbility.id);
            else buf.writeInt(-1);
            buf.writeInt(ability.state.ordinal());
            buf.writeInt(ability.colour.getRGB());
            buf.writeInt(ability.textureX);
            buf.writeInt(ability.textureY);
            buf.writeInt(ability.width);
            buf.writeInt(ability.height);
            buf.writeInt(ability.x);
            buf.writeInt(ability.y);
            ByteBufUtils.writeUTF8String(buf, ability.name);
            ByteBufUtils.writeUTF8String(buf, ability.description);
        }

        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<AbilitiesMessage, IMessage>
    {
        public IMessage onMessage(AbilitiesMessage message, MessageContext ctx)
        {
            Entity entity = null;

            if ((ctx.side.isClient()) && (Blocklings.proxy.getPlayer(ctx) != null))
            {
                entity = Blocklings.proxy.getPlayer(ctx).world.getEntityByID(message.id);

                if (entity instanceof EntityBlockling)
                {
                    EntityBlockling blockling = (EntityBlockling) entity;

                    blockling.generalAbilities = message.generalAbilities;
                    blockling.combatAbilities = message.combatAbilities;
                    blockling.miningAbilities = message.miningAbilities;
                    blockling.woodcuttingAbilities = message.woodcuttingAbilities;
                }
            }
            else if (ctx.side.isServer() && Blocklings.proxy.getPlayer(ctx) != null)
            {
                entity = Blocklings.proxy.getPlayer(ctx).world.getEntityByID(message.id);

                if ((entity instanceof EntityBlockling))
                {
                    EntityBlockling blockling = (EntityBlockling) entity;

                    blockling.generalAbilities = message.generalAbilities;
                    blockling.combatAbilities = message.combatAbilities;
                    blockling.miningAbilities = message.miningAbilities;
                    blockling.woodcuttingAbilities = message.woodcuttingAbilities;
                }
            }

            return null;
        }
    }
}