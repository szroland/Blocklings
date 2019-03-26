package com.blocklings.network;

import com.blocklings.abilities.Ability;
import com.blocklings.abilities.AbilityGroup;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilitiesMessage implements IMessage
{
    AbilityGroup generalAbilities;
    AbilityGroup combatAbilities;
    AbilityGroup miningAbilities;
    AbilityGroup woodcuttingAbilities;
    AbilityGroup farmingAbilities;
    int id;

    public AbilitiesMessage()
    {
    }

    public AbilitiesMessage(AbilityGroup generalAbilities, AbilityGroup combatAbilities, AbilityGroup miningAbilities, AbilityGroup woodcuttingAbilities, AbilityGroup farmingAbilities, int entityID)
    {
        this.generalAbilities = generalAbilities;
        this.combatAbilities = combatAbilities;
        this.miningAbilities = miningAbilities;
        this.woodcuttingAbilities = woodcuttingAbilities;
        this.farmingAbilities = farmingAbilities;
        this.id = entityID;
    }

    public void fromBytes(ByteBuf buf)
    {
        int g = buf.readInt();
        int c = buf.readInt();
        int m = buf.readInt();
        int w = buf.readInt();
        int f = buf.readInt();

        generalAbilities = new AbilityGroup();
        combatAbilities = new AbilityGroup();
        miningAbilities = new AbilityGroup();
        woodcuttingAbilities = new AbilityGroup();
        farmingAbilities = new AbilityGroup();

        generalAbilities.id = buf.readInt();
        combatAbilities.id = buf.readInt();
        miningAbilities.id = buf.readInt();
        woodcuttingAbilities.id = buf.readInt();
        farmingAbilities.id = buf.readInt();

        generalAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        combatAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        miningAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        woodcuttingAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        farmingAbilities.groupName = ByteBufUtils.readUTF8String(buf);

        List<Ability> generalAbilitiesList = new ArrayList<Ability>();
        List<Ability> combatAbilitiesList = new ArrayList<Ability>();
        List<Ability> miningAbilitiesList = new ArrayList<Ability>();
        List<Ability> woodcuttingAbilitiesList = new ArrayList<Ability>();
        List<Ability> farmingAbilitiesList = new ArrayList<Ability>();

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
            int descriptionSize = buf.readInt();
            List<String> description = new ArrayList<>();
            for (int j = 0; j < descriptionSize; j++)
            {
                description.add(ByteBufUtils.readUTF8String(buf));
            }
            ability.description = description;
            generalAbilitiesList.add(ability);
        }
        for (Ability ability : generalAbilitiesList)
        {
            for (Ability ability2 : generalAbilitiesList)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < c; i++)
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
            int descriptionSize = buf.readInt();
            List<String> description = new ArrayList<>();
            for (int j = 0; j < descriptionSize; j++)
            {
                description.add(ByteBufUtils.readUTF8String(buf));
            }
            ability.description = description;
            combatAbilitiesList.add(ability);
        }
        for (Ability ability : combatAbilitiesList)
        {
            for (Ability ability2 : combatAbilitiesList)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < m; i++)
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
            int descriptionSize = buf.readInt();
            List<String> description = new ArrayList<>();
            for (int j = 0; j < descriptionSize; j++)
            {
                description.add(ByteBufUtils.readUTF8String(buf));
            }
            ability.description = description;
            miningAbilitiesList.add(ability);
        }
        for (Ability ability : miningAbilitiesList)
        {
            for (Ability ability2 : miningAbilitiesList)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < w; i++)
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
            int descriptionSize = buf.readInt();
            List<String> description = new ArrayList<>();
            for (int j = 0; j < descriptionSize; j++)
            {
                description.add(ByteBufUtils.readUTF8String(buf));
            }
            ability.description = description;
            woodcuttingAbilitiesList.add(ability);
        }
        for (Ability ability : woodcuttingAbilitiesList)
        {
            for (Ability ability2 : woodcuttingAbilitiesList)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
        for (int i = 0; i < f; i++)
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
            int descriptionSize = buf.readInt();
            List<String> description = new ArrayList<>();
            for (int j = 0; j < descriptionSize; j++)
            {
                description.add(ByteBufUtils.readUTF8String(buf));
            }
            ability.description = description;
            farmingAbilitiesList.add(ability);
        }
        for (Ability ability : farmingAbilitiesList)
        {
            for (Ability ability2 : farmingAbilitiesList)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }

        generalAbilities.abilities = generalAbilitiesList;
        combatAbilities.abilities = combatAbilitiesList;
        miningAbilities.abilities = miningAbilitiesList;
        woodcuttingAbilities.abilities = woodcuttingAbilitiesList;
        farmingAbilities.abilities = farmingAbilitiesList;

        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(generalAbilities.abilities.size());
        buf.writeInt(combatAbilities.abilities.size());
        buf.writeInt(miningAbilities.abilities.size());
        buf.writeInt(woodcuttingAbilities.abilities.size());
        buf.writeInt(farmingAbilities.abilities.size());

        buf.writeInt(generalAbilities.id);
        buf.writeInt(combatAbilities.id);
        buf.writeInt(miningAbilities.id);
        buf.writeInt(woodcuttingAbilities.id);
        buf.writeInt(farmingAbilities.id);

        ByteBufUtils.writeUTF8String(buf, generalAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, combatAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, miningAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, woodcuttingAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, farmingAbilities.groupName);

        for (Ability ability : generalAbilities.abilities)
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
            buf.writeInt(ability.description.size());
            for (String string : ability.description)
            {
                ByteBufUtils.writeUTF8String(buf, string);
            }
        }
        for (Ability ability : combatAbilities.abilities)
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
            buf.writeInt(ability.description.size());
            for (String string : ability.description)
            {
                ByteBufUtils.writeUTF8String(buf, string);
            }
        }
        for (Ability ability : miningAbilities.abilities)
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
            buf.writeInt(ability.description.size());
            for (String string : ability.description)
            {
                ByteBufUtils.writeUTF8String(buf, string);
            }
        }
        for (Ability ability : woodcuttingAbilities.abilities)
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
            buf.writeInt(ability.description.size());
            for (String string : ability.description)
            {
                ByteBufUtils.writeUTF8String(buf, string);
            }
        }
        for (Ability ability : farmingAbilities.abilities)
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
            buf.writeInt(ability.description.size());
            for (String string : ability.description)
            {
                ByteBufUtils.writeUTF8String(buf, string);
            }
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
                    blockling.farmingAbilities = message.farmingAbilities;
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
                    blockling.farmingAbilities = message.farmingAbilities;
                }
            }

            return null;
        }
    }
}