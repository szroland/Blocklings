package com.blocklings.util.helpers;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbilityHelper
{
    // GENERAL

    public static Ability mule1 = new Ability()
        .initInfo(0, "Mule I", new String[] { "Increases inventory", "slots by 12" }, null, 1)
        .initGui(0, 0, 0, 0, 0, 0, new Color(0xFFBF79));
    public static Ability mule2 = new Ability()
        .initInfo(1, "Mule II", new String[] { "Increases inventory", "slots by 12" }, mule1, 1)
        .initGui(0, 80, 24, 0, 0, 0, new Color(0xFFBF79));

    public static Ability flowerPower = new Ability()
        .initInfo(2, "Flower Power", new String[] { "Drop XP when healed" }, null, 1)
        .initGui(50, 0, 24 * 2, 0, 0, 0, new Color(0xAFFF48));

    public static Ability packling = new Ability()
        .initInfo(5, "Packling", new String[] { "Pick blockling up", "by using a flower" }, null, 1)
        .initGui(100, 0, 24 * 8, 0, 0, 0, new Color(0xBEA60C));

    public static Ability armadillo = new Ability()
        .initInfo(3, "Armadillo", new String[] { "Blockling drops as", "item instead of dying" }, packling, 1)
        .initGui(100, 80, 24 * 3, 0, 0, 0, new Color(0x945A51));

    public static Ability enderBoye = new Ability()
        .initInfo(4, "Ender Boye", new String[] { "Chance to teleport", "to destination" }, null, 1)
        .initGui(150, 0, 24 * 7, 0, 0, 0, new Color(0x1A154A));


    // COMBAT

    public static Ability regen1 = new Ability()
        .initInfo(0, "Regen I", new String[] { "Heals 1 health every", "10 seconds" }, null, 1)
        .initGui(0, 0, 24 * 4, 0, 0, 0, new Color(0x7AE621));
    public static Ability regen2 = new Ability()
        .initInfo(1, "Regen II", new String[] { "Heals 2 health every", "10 seconds" }, regen1, 1)
        .initGui(60, 60, 24 * 5, 0, 0, 0, new Color(0x7AE621));
    public static Ability regen3 = new Ability()
        .initInfo(2, "Regen III", new String[] { "Heals 3 health every", "10 seconds" }, regen2, 1)
        .initGui(120, 120, 24 * 6, 0, 0, 0, new Color(0x7AE621));


    // WOODCUTTING

    public static Ability forestFire = new Ability()
        .initInfo(0, "Forest Fire", new String[] { "Convert all logs", "chopped to charcoal" }, null, 1)
        .initGui(0, 0, 24 * 1, 24 * 1, 0, 0, new Color(0x1A0C05));


    // FARMING

    public static Ability cropDrop = new Ability()
        .initInfo(0, "Crop Drop", new String[] { "50% chance to drop", "double crops" }, null, 1)
        .initGui(0, 0, 24 * 0, 24 * 1, 0, 0, new Color(0xD5DA45));

    public static List<Ability> generalAbilities = new ArrayList<Ability>();
    public static List<Ability> combatAbilities = new ArrayList<Ability>();
    public static List<Ability> miningAbilities = new ArrayList<Ability>();
    public static List<Ability> woodcuttingAbilities = new ArrayList<Ability>();
    public static List<Ability> farmingAbilities = new ArrayList<Ability>();

    static
    {
        flowerPower.initConflictingAbilities(new Ability[] {mule1, mule2});

        generalAbilities.add(mule1);
        generalAbilities.add(mule2);
        generalAbilities.add(flowerPower);
        generalAbilities.add(packling);
        generalAbilities.add(armadillo);
        generalAbilities.add(enderBoye);
    }
    static
    {
        regen1.initLevelRequirements(new HashMap<String, Integer>() {{
            put("Combat", 5);
        }});
        regen2.initLevelRequirements(new HashMap<String, Integer>() {{
            put("Combat", 10);
        }});
        regen3.initLevelRequirements(new HashMap<String, Integer>() {{
            put("Combat", 15);
        }});

        combatAbilities.add(regen1);
        combatAbilities.add(regen2);
        combatAbilities.add(regen3);
    }
    static
    {

    }
    static
    {
        woodcuttingAbilities.add(forestFire);
        woodcuttingAbilities.add(regen3);
    }
    static
    {
        farmingAbilities.add(cropDrop);
        farmingAbilities.add(regen3);
    }

    public static void readAbilityGroupFromBuf(ByteBuf buf, List<Ability> abilities, int length)
    {
        for (int i = 0; i < length; i++)
        {
            Ability ability = new Ability();
            ability.id = buf.readInt();
            ability.parentId = buf.readInt();
            ability.state = Ability.State.values()[buf.readInt()];
            ability.highlightColour = new Color(buf.readInt());
            ability.iconX = buf.readInt();
            ability.iconY = buf.readInt();
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
            ability.initFromDefaults();
            abilities.add(ability);
        }
        for (Ability ability : abilities)
        {
            for (Ability ability2 : abilities)
            {
                if (ability.parentId == -1) continue;

                if (ability.parentId == ability2.id)
                {
                    ability.parentAbility = ability2;
                }
            }
        }
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

        readAbilityGroupFromBuf(buf, generalAbilitiesList, g);
        readAbilityGroupFromBuf(buf, combatAbilitiesList, c);
        readAbilityGroupFromBuf(buf, miningAbilitiesList, m);
        readAbilityGroupFromBuf(buf, woodcuttingAbilitiesList, w);
        readAbilityGroupFromBuf(buf, farmingAbilitiesList, f);

        blockling.generalAbilities.abilities = generalAbilitiesList;
        blockling.combatAbilities.abilities = combatAbilitiesList;
        blockling.miningAbilities.abilities = miningAbilitiesList;
        blockling.woodcuttingAbilities.abilities = woodcuttingAbilitiesList;
        blockling.farmingAbilities.abilities = farmingAbilitiesList;
    }

    public static void writeAbilityGroupToBuf(ByteBuf buf, List<Ability> abilities)
    {
        for (Ability ability : abilities)
        {
            buf.writeInt(ability.id);
            if (ability.parentAbility != null) buf.writeInt(ability.parentAbility.id);
            else buf.writeInt(-1);
            buf.writeInt(ability.state.ordinal());
            buf.writeInt(ability.highlightColour.getRGB());
            buf.writeInt(ability.iconX);
            buf.writeInt(ability.iconY);
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

        writeAbilityGroupToBuf(buf, blockling.generalAbilities.abilities);
        writeAbilityGroupToBuf(buf, blockling.combatAbilities.abilities);
        writeAbilityGroupToBuf(buf, blockling.miningAbilities.abilities);
        writeAbilityGroupToBuf(buf, blockling.woodcuttingAbilities.abilities);
        writeAbilityGroupToBuf(buf, blockling.farmingAbilities.abilities);
    }
}
