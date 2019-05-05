package com.blocklings.network.messages;

import com.blocklings.abilities.AbilityGroup;
import com.blocklings.abilities.AbilityGroupType;
import com.blocklings.entity.entities.EntityBlockling;
import com.blocklings.main.Blocklings;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
        generalAbilities = new AbilityGroup(AbilityGroupType.GENERAL);
        combatAbilities = new AbilityGroup(AbilityGroupType.COMBAT);
        miningAbilities = new AbilityGroup(AbilityGroupType.MINING);
        woodcuttingAbilities = new AbilityGroup(AbilityGroupType.WOODCUTTING);
        farmingAbilities = new AbilityGroup(AbilityGroupType.FARMING);

        generalAbilities.readFromBuf(buf);
        combatAbilities.readFromBuf(buf);
        miningAbilities.readFromBuf(buf);
        woodcuttingAbilities.readFromBuf(buf);
        farmingAbilities.readFromBuf(buf);

        this.id = buf.readInt();
    }

    public void toBytes(ByteBuf buf)
    {
        generalAbilities.writeToBuf(buf);
        combatAbilities.writeToBuf(buf);
        miningAbilities.writeToBuf(buf);
        woodcuttingAbilities.writeToBuf(buf);
        farmingAbilities.writeToBuf(buf);

        buf.writeInt(this.id);
    }

    public static class Handler implements net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler<AbilitiesMessage, IMessage>
    {
        public IMessage onMessage(AbilitiesMessage message, MessageContext ctx)
        {
            EntityPlayer player = Blocklings.proxy.getPlayer(ctx);

            if (player != null)
            {
                EntityBlockling blockling = (EntityBlockling) player.world.getEntityByID(message.id);
                if (blockling != null)
                {
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