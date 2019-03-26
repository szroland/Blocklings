package com.blocklings.util.helpers;

import com.blocklings.abilities.Ability;
import com.blocklings.abilities.AbilityGroup;
import com.blocklings.entities.EntityBlockling;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilityHelper
{
    public static List<Ability> generalAbilities = new ArrayList<Ability>();
    public static List<Ability> combatAbilities = new ArrayList<Ability>();
    public static List<Ability> miningAbilities = new ArrayList<Ability>();
    public static List<Ability> woodcuttingAbilities = new ArrayList<Ability>();
    public static List<Ability> farmingAbilities = new ArrayList<Ability>();

    static
    {
        Ability ability0 = new Ability(0, null, -10, -20, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
        Ability ability1 = new Ability(1, ability0, 30, 30, 0, 0, "Ability 1", new String[] { "Ability 1 description 1 Line One", "Ability 1 description 1 Line 2", "Ability 1 description 1 Line Three" });
        Ability ability6 = new Ability(2, ability0, 110, 30, 0, 0, "Ability 6", "Ability 6 description 12");
        Ability ability2 = new Ability(3, ability0, -40, 90, 0, 0, "Ability 2", "Ability 2 description 123");
        Ability ability5 = new Ability(4, ability0, -76, 90, 0, 0, "Ability 5", "Ability 5 description 1234");
        Ability ability3 = new Ability(5, ability1, 90, 140, 24, 0, "Ability 3", "Ability 3 description 12345");
        Ability ability4 = new Ability(6, ability2, 20, 130, 24, 0, "Ability 4", "Ability 4 description 123456");

        ability0.colour = new Color(0xaa55aa);
        ability1.colour = new Color(0x500F89);
        ability0.colour = new Color(0xB98F2C);
        ability2.colour = new Color(0x920C07);
        ability5.colour = new Color(0x0A8C2E);

        generalAbilities.add(ability0);
        generalAbilities.add(ability1);
        generalAbilities.add(ability2);
        generalAbilities.add(ability3);
        generalAbilities.add(ability4);
        generalAbilities.add(ability5);
        generalAbilities.add(ability6);
    }
    static
    {
        Ability ability0 = new Ability(0, null, -40, -40, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
        Ability ability1 = new Ability(1, ability0, 10, 11, 0, 0, "Ability 1", "Ability 1 description 1");
        Ability ability6 = new Ability(2, ability0, 120, 15, 0, 0, "Ability 6", "Ability 6 description 12");
        Ability ability2 = new Ability(3, ability0, -20, 70, 0, 0, "Ability 2", "Ability 2 description 123");
        Ability ability5 = new Ability(4, ability0, -90, 90, 0, 0, "Ability 5", "Ability 5 description 1234");
        Ability ability3 = new Ability(5, ability1, 56, 120, 24, 0, "Ability 3", "Ability 3 description 12345");
        Ability ability4 = new Ability(6, ability2, 20, 110, 24, 0, "Ability 4", "Ability 4 description 123456");

        ability0.colour = new Color(0xaa55aa);
        ability1.colour = new Color(0x500F89);
        ability0.colour = new Color(0xB98F2C);
        ability2.colour = new Color(0x920C07);
        ability5.colour = new Color(0x0A8C2E);

        combatAbilities.add(ability0);
        combatAbilities.add(ability1);
        combatAbilities.add(ability2);
        combatAbilities.add(ability3);
        combatAbilities.add(ability4);
        combatAbilities.add(ability5);
        combatAbilities.add(ability6);
    }
    static
    {
        Ability ability0 = new Ability(0, null, -2, -3, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
        Ability ability1 = new Ability(1, ability0, 45, 45, 0, 0, "Ability 1", "Ability 1 description 1");
        Ability ability6 = new Ability(2, ability0, 150, 90, 0, 0, "Ability 6", "Ability 6 description 12");
        Ability ability2 = new Ability(3, ability0, -20, 95, 0, 0, "Ability 2", "Ability 2 description 123");
        Ability ability5 = new Ability(4, ability0, -56, 99, 0, 0, "Ability 5", "Ability 5 description 1234");
        Ability ability3 = new Ability(5, ability1, 100, 150, 24, 0, "Ability 3", "Ability 3 description 12345");
        Ability ability4 = new Ability(6, ability2, 34, 120, 24, 0, "Ability 4", "Ability 4 description 123456");

        ability0.colour = new Color(0xaa55aa);
        ability1.colour = new Color(0x500F89);
        ability0.colour = new Color(0xB98F2C);
        ability2.colour = new Color(0x920C07);
        ability5.colour = new Color(0x0A8C2E);

        miningAbilities.add(ability0);
        miningAbilities.add(ability1);
        miningAbilities.add(ability2);
        miningAbilities.add(ability3);
        miningAbilities.add(ability4);
        miningAbilities.add(ability5);
        miningAbilities.add(ability6);
    }
    static
    {
        Ability ability0 = new Ability(0, null, -10, -2, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
        Ability ability1 = new Ability(1, ability0, 33, 45, 0, 0, "Ability 1", "Ability 1 description 1");
        Ability ability6 = new Ability(2, ability0, 130, 27, 0, 0, "Ability 6", "Ability 6 description 12");
        Ability ability2 = new Ability(3, ability0, -56, 120, 0, 0, "Ability 2", "Ability 2 description 123");
        Ability ability5 = new Ability(4, ability0, -80, 98, 0, 0, "Ability 5", "Ability 5 description 1234");
        Ability ability3 = new Ability(5, ability1, 56, 130, 24, 0, "Ability 3", "Ability 3 description 12345");
        Ability ability4 = new Ability(6, ability2, 20, 134, 24, 0, "Ability 4", "Ability 4 description 123456");

        ability0.colour = new Color(0xaa55aa);
        ability1.colour = new Color(0x500F89);
        ability0.colour = new Color(0xB98F2C);
        ability2.colour = new Color(0x920C07);
        ability5.colour = new Color(0x0A8C2E);

        woodcuttingAbilities.add(ability0);
        woodcuttingAbilities.add(ability1);
        woodcuttingAbilities.add(ability2);
        woodcuttingAbilities.add(ability3);
        woodcuttingAbilities.add(ability4);
        woodcuttingAbilities.add(ability5);
        woodcuttingAbilities.add(ability6);
    }
    static
    {
        Ability ability0 = new Ability(0, null, -10, -2, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
        Ability ability1 = new Ability(1, ability0, 33, 45, 0, 0, "Ability 1", "Ability 1 description 1");
        Ability ability6 = new Ability(2, ability0, 130, 27, 0, 0, "Ability 6", "Ability 6 description 12");
        Ability ability2 = new Ability(3, ability0, -56, 120, 0, 0, "Ability 2", "Ability 2 description 123");
        Ability ability5 = new Ability(4, ability0, -80, 98, 0, 0, "Ability 5", "Ability 5 description 1234");
        Ability ability3 = new Ability(5, ability1, 56, 130, 24, 0, "Ability 3", "Ability 3 description 12345");
        Ability ability4 = new Ability(6, ability2, 20, 134, 24, 0, "Ability 4", "Ability 4 description 123456");

        ability0.colour = new Color(0xaa55aa);
        ability1.colour = new Color(0x500F89);
        ability0.colour = new Color(0xB98F2C);
        ability2.colour = new Color(0x920C07);
        ability5.colour = new Color(0x0A8C2E);

        farmingAbilities.add(ability0);
        farmingAbilities.add(ability1);
        farmingAbilities.add(ability2);
        farmingAbilities.add(ability3);
        farmingAbilities.add(ability4);
        farmingAbilities.add(ability5);
        farmingAbilities.add(ability6);
    }

    public static void readSpawnData(ByteBuf buf, EntityBlockling blockling)
    {
        int g = buf.readInt();
        int c = buf.readInt();
        int m = buf.readInt();
        int w = buf.readInt();
        int f = buf.readInt();

        blockling.generalAbilities.id = buf.readInt();
        blockling.combatAbilities.id = buf.readInt();
        blockling.miningAbilities.id = buf.readInt();
        blockling.woodcuttingAbilities.id = buf.readInt();
        blockling.farmingAbilities.id = buf.readInt();

        blockling.generalAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        blockling.combatAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        blockling.miningAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        blockling.woodcuttingAbilities.groupName = ByteBufUtils.readUTF8String(buf);
        blockling.farmingAbilities.groupName = ByteBufUtils.readUTF8String(buf);

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

        blockling.generalAbilities.abilities = generalAbilitiesList;
        blockling.combatAbilities.abilities = combatAbilitiesList;
        blockling.miningAbilities.abilities = miningAbilitiesList;
        blockling.woodcuttingAbilities.abilities = woodcuttingAbilitiesList;
        blockling.farmingAbilities.abilities = farmingAbilitiesList;
    }

    public static void writeSpawnData(ByteBuf buf, EntityBlockling blockling)
    {
        buf.writeInt(blockling.generalAbilities.abilities.size());
        buf.writeInt(blockling.combatAbilities.abilities.size());
        buf.writeInt(blockling.miningAbilities.abilities.size());
        buf.writeInt(blockling.woodcuttingAbilities.abilities.size());
        buf.writeInt(blockling.farmingAbilities.abilities.size());

        buf.writeInt(blockling.generalAbilities.id);
        buf.writeInt(blockling.combatAbilities.id);
        buf.writeInt(blockling.miningAbilities.id);
        buf.writeInt(blockling.woodcuttingAbilities.id);
        buf.writeInt(blockling.farmingAbilities.id);

        ByteBufUtils.writeUTF8String(buf, blockling.generalAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, blockling.combatAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, blockling.miningAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, blockling.woodcuttingAbilities.groupName);
        ByteBufUtils.writeUTF8String(buf, blockling.farmingAbilities.groupName);

        for (Ability ability : blockling.generalAbilities.abilities)
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
        for (Ability ability : blockling.combatAbilities.abilities)
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
        for (Ability ability : blockling.miningAbilities.abilities)
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
        for (Ability ability : blockling.woodcuttingAbilities.abilities)
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
        for (Ability ability : blockling.farmingAbilities.abilities)
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
    }
}
