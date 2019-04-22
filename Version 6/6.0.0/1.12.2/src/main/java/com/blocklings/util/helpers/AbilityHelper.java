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
        .initGui(0, 50, 24, 0, 0, 0, new Color(0xFFBF79));

    public static Ability botanist = new Ability()
        .initInfo(7, "Botanist", new String[] { "Blockling can be healed", "using flowers for 1", "health each" }, null, 1)
        .initGui(50, -50, 24 * 3, 24 * 3, 0, 0, new Color(0xCC0E26));

    public static Ability flowerPower = new Ability()
        .initInfo(2, "Flower Power", new String[] { "Drop XP when healed" }, botanist, 1)
        .initGui(50, 0, 24 * 2, 0, 0, 0, new Color(0xAFFF48));

    public static Ability packling = new Ability()
        .initInfo(5, "Packling", new String[] { "Pick blockling up", "by using a flower" }, botanist, 1)
        .initGui(100, 0, 24 * 8, 0, 0, 0, new Color(0xBEA60C));
    public static Ability armadillo = new Ability()
        .initInfo(3, "Armadillo", new String[] { "Blockling drops as", "item instead of dying" }, packling, 1)
        .initGui(100, 100, 24 * 3, 0, 0, 0, new Color(0x945A51));

    public static Ability enderBoye1 = new Ability()
        .initInfo(4, "Ender Boye I", new String[] { "Chance to teleport", "to destination" }, null, 1)
        .initGui(150, 0, 24 * 7, 0, 0, 0, new Color(0x1A154A));
    public static Ability enderBoye2 = new Ability()
        .initInfo(8, "Ender Boye II", new String[] { "Higher chance to teleport", "to destination" }, enderBoye1, 1)
        .initGui(150, 50, 24 * 4, 24 * 3, 0, 0, new Color(0x0A0539));

    public static Ability outline = new Ability()
        .initInfo(6, "Outline", new String[] { "Outlines the blockling", "with glowing effect" }, null, 1)
        .initGui(-50, 0, 24 * 5, 24 * 1, 0, 0, new Color(0xFFE857));


    // COMBAT

    public static Ability regen1 = new Ability()
        .initInfo(0, "Regen I", new String[] { "Heals 1 health every", "10 seconds" }, null, 1)
        .initGui(0, 0, 24 * 4, 0, 0, 0, new Color(0x7AE621));
    public static Ability regen2 = new Ability()
        .initInfo(1, "Regen II", new String[] { "Heals 2 health every", "10 seconds" }, regen1, 1)
        .initGui(0, 50, 24 * 5, 0, 0, 0, new Color(0x7AE621));
    public static Ability regen3 = new Ability()
        .initInfo(2, "Regen III", new String[] { "Heals 3 health every", "10 seconds" }, regen2, 1)
        .initGui(0, 100, 24 * 6, 0, 0, 0, new Color(0x7AE621));

    public static Ability shinobi1 = new Ability()
        .initInfo(3, "Shinobi I", new String[] { "Double damage from", "backstabs" }, null, 1)
        .initGui(100, 0, 24 * 4, 24 * 1, 0, 0, new Color(0xBC1A2F));
    public static Ability shinobi2 = new Ability()
        .initInfo(4, "Shinobi II", new String[] { "Triple damage from", "backstabs" }, shinobi1, 1)
        .initGui(100, 100, 24 * 0, 24 * 3, 0, 0, new Color(0x8B001A));


    // MINING

    public static Ability hasteMining = new Ability()
        .initInfo(0, "Haste", new String[] { "Decreases mining interval", "by 10" }, null, 1)
        .initGui(50, 0, 24 * 3, 24 * 1, 0, 0, new Color(0xE5D600));
    public static Ability brittleBlock = new Ability()
    .initInfo(1, "Brittle Block", new String[] { "10% chance to instantly", "mine a block" }, hasteMining, 1)
    .initGui(50, 50, 24 * 1, 24 * 2, 0, 0, new Color(0x828F7F));

    public static Ability blocksmith = new Ability()
        .initInfo(2, "Blocksmith", new String[] { "Automatically smelts", "ores mined" }, hasteMining, 1)
        .initGui(0, 50, 24 * 8, 24 * 1, 0, 0, new Color(0xFF8200));
    public static Ability metallurgy1 = new Ability()
        .initInfo(3, "Metallurgy I", new String[] { "25% chance for", "double smelt" }, blocksmith, 1)
        .initGui(0, 100, 24 * 9, 24 * 1, 0, 0, new Color(0xDCDACE));
    public static Ability metallurgy2 = new Ability()
        .initInfo(4, "Metallurgy II", new String[] { "25% chance for", "triple smelt" }, metallurgy1, 1)
        .initGui(0, 150, 24 * 0, 24 * 2, 0, 0, new Color(0xFFBE0E));

    public static Ability dwarvenSenses1 = new Ability()
        .initInfo(5, "Dwarven Senses I", new String[] { "Bigger search radius" }, null, 1)
        .initGui(100, 50, 24 * 2, 24 * 2, 0, 0, new Color(0xB2FFEB));
    public static Ability dwarvenSenses2 = new Ability()
        .initInfo(6, "Dwarven Senses II", new String[] { "Blockling can path to", "blocks they can't even", "see" }, dwarvenSenses1, 1)
        .initGui(100, 100, 24 * 3, 24 * 2, 0, 0, new Color(0x41FFFA));


    // WOODCUTTING

    public static Ability hasteWoodcutting = new Ability()
        .initInfo(0, "Haste", new String[] { "Decreases chopping interval", "by 10" }, null, 1)
        .initGui(50, 0, 24 * 3, 24 * 1, 0, 0, new Color(0xE5D600));
    public static Ability sawmill = new Ability()
        .initInfo(2, "Sawmill", new String[] { "10% chance to cut an", "extra log from the tree" }, hasteWoodcutting, 1)
        .initGui(50, 50, 24 * 4, 24 * 2, 0, 0, new Color(0x853D25));

    public static Ability forestFire = new Ability()
        .initInfo(1, "Forest Fire", new String[] { "Convert all logs", "chopped to charcoal" }, null, 1)
        .initGui(100, 50, 24 * 1, 24 * 1, 0, 0, new Color(0x1A0C05));

    public static Ability leafBlower = new Ability()
        .initInfo(3, "Leaf Blower", new String[] { "Break the leaves on", "adjacent to logs" }, hasteWoodcutting, 1)
        .initGui(0, 50, 24 * 5, 24 * 2, 0, 0, new Color(0x397129));
    public static Ability treeSurgeon = new Ability()
        .initInfo(4, "Tree Surgeon", new String[] { "Collect the drops from", "leaves" }, leafBlower, 1)
        .initGui(0, 100, 24 * 6, 24 * 2, 0, 0, new Color(0x30A502));

    public static Ability treeHugger = new Ability()
        .initInfo(5, "Tree Hugger", new String[] { "Plant a sapling after", "chopping a tree" }, null, 1)
        .initGui(150, 50, 24 * 1, 24 * 3, 0, 0, new Color(0xA7662C));
    public static Ability fertilisationWoodcutting = new Ability()
        .initInfo(6, "Fertilisation", new String[] { "Fertilise any sapling", "planted using bonemeal", "in inventory" }, treeHugger, 1)
        .initGui(150, 100, 24 * 9, 24 * 2, 0, 0, new Color(0xEBEBEB));


    // FARMING

    public static Ability hasteFarming = new Ability()
        .initInfo(0, "Haste", new String[] { "Decreases farming interval", "by 10" }, null, 1)
        .initGui(0, 0, 24 * 3, 24 * 1, 0, 0, new Color(0xE5D600));
    public static Ability scythe = new Ability()
        .initInfo(2, "Scythe", new String[] { "10% chance to harvest", "crops in a 3x3 area" }, hasteFarming, 1)
        .initGui(0, 100, 24 * 2, 24 * 1, 0, 0, new Color(0xC7A600));

    public static Ability plentifulHarvest = new Ability()
        .initInfo(1, "Plentiful Harvest", new String[] { "50% chance to drop", "double crops" }, null, 1)
        .initGui(100, 0, 24 * 0, 24 * 1, 0, 0, new Color(0xD5DA45));

    public static Ability replanter = new Ability()
        .initInfo(3, "Replanter", new String[] { "Can replant seeds after", "harvest" }, null, 1)
        .initGui(50, -50, 24 * 2, 24 * 3, 0, 0, new Color(0x92C62F));
    public static Ability clinicalDibber = new Ability()
        .initInfo(4, "Clinical Dibber", new String[] { "50% chance not to use", "seed on plant" }, replanter, 1)
        .initGui(50, 0, 24 * 7, 24 * 2, 0, 0, new Color(0xD6BF97));
    public static Ability fertilisationFarming = new Ability()
        .initInfo(5, "Fertilisation", new String[] { "Fertilise any crop", "planted using bonemeal", "in inventory" }, clinicalDibber, 1)
        .initGui(50, 50, 24 * 9, 24 * 2, 0, 0, new Color(0xEBEBEB));
    public static Ability natureAura = new Ability()
        .initInfo(6, "Nature Aura", new String[] { "Chance to fertilise nearby", "crops" }, fertilisationFarming, 1)
        .initGui(50, 100, 24 * 8, 24 * 2, 0, 0, new Color(0x0B9F00));

    public static List<Ability> generalAbilities = new ArrayList<Ability>();
    public static List<Ability> combatAbilities = new ArrayList<Ability>();
    public static List<Ability> miningAbilities = new ArrayList<Ability>();
    public static List<Ability> woodcuttingAbilities = new ArrayList<Ability>();
    public static List<Ability> farmingAbilities = new ArrayList<Ability>();

    static
    {
        flowerPower.initConflictingAbilities(new Ability[] {mule1, mule2});

        generalAbilities.add(botanist);
        generalAbilities.add(mule1);
        generalAbilities.add(mule2);
        generalAbilities.add(flowerPower);
        generalAbilities.add(packling);
        generalAbilities.add(armadillo);
        generalAbilities.add(enderBoye1);
        generalAbilities.add(enderBoye2);
        generalAbilities.add(outline);
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
        combatAbilities.add(shinobi1);
        combatAbilities.add(shinobi2);
    }
    static
    {
        miningAbilities.add(hasteMining);
        miningAbilities.add(brittleBlock);
        miningAbilities.add(blocksmith);
        miningAbilities.add(metallurgy1);
        miningAbilities.add(metallurgy2);
        miningAbilities.add(dwarvenSenses1);
        miningAbilities.add(dwarvenSenses2);
    }
    static
    {
        woodcuttingAbilities.add(hasteWoodcutting);
        woodcuttingAbilities.add(sawmill);
        woodcuttingAbilities.add(forestFire);
        woodcuttingAbilities.add(leafBlower);
        woodcuttingAbilities.add(treeSurgeon);
        woodcuttingAbilities.add(treeHugger);
        woodcuttingAbilities.add(fertilisationWoodcutting);
    }
    static
    {
        farmingAbilities.add(hasteFarming);
        farmingAbilities.add(plentifulHarvest);
        farmingAbilities.add(scythe);
        farmingAbilities.add(replanter);
        farmingAbilities.add(clinicalDibber);
        farmingAbilities.add(fertilisationFarming);
        farmingAbilities.add(natureAura);
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
