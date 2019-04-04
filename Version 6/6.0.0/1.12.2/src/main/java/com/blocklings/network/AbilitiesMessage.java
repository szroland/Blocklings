package com.blocklings.network;

import com.blocklings.abilities.Ability;
import com.blocklings.abilities.AbilityGroup;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import com.blocklings.util.helpers.AbilityHelper;
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

        AbilityHelper.readAbilityGroupFromBuf(buf, generalAbilitiesList, g);
        AbilityHelper.readAbilityGroupFromBuf(buf, combatAbilitiesList, c);
        AbilityHelper.readAbilityGroupFromBuf(buf, miningAbilitiesList, m);
        AbilityHelper.readAbilityGroupFromBuf(buf, woodcuttingAbilitiesList, w);
        AbilityHelper.readAbilityGroupFromBuf(buf, farmingAbilitiesList, f);

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

        AbilityHelper.writeAbilityGroupToBuf(buf, generalAbilities.abilities);
        AbilityHelper.writeAbilityGroupToBuf(buf, combatAbilities.abilities);
        AbilityHelper.writeAbilityGroupToBuf(buf, miningAbilities.abilities);
        AbilityHelper.writeAbilityGroupToBuf(buf, woodcuttingAbilities.abilities);
        AbilityHelper.writeAbilityGroupToBuf(buf, farmingAbilities.abilities);

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