package com.blocklings.abilities;

import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AbilityGroup
{
    public int id = 0;
    public String groupName = "";
    public List<Ability> abilities = new ArrayList<Ability>();

    public AbilityGroup()
    {
        this.id = -1;
        this.abilities = new ArrayList<Ability>();
    }

    public AbilityGroup(int id, String name, List<Ability> abilities)
    {
        this.id = id;
        this.groupName = name;
        this.abilities = abilities;
    }

    public boolean contains(Ability ability)
    {
        for (Ability testAbility : abilities)
        {
            if (testAbility.equals(ability))
            {
                return true;
            }
        }

        return false;
    }

    private Ability getMatchingAbility(Ability ability)
    {
        for (Ability testAbility : abilities)
        {
            if (testAbility.equals(ability))
            {
                return testAbility;
            }
        }

        return null;
    }

    public boolean isAbilityAcquired(Ability ability)
    {
        Ability matchingAbility = getMatchingAbility(ability);
        return matchingAbility != null && matchingAbility.state == Ability.State.ACQUIRED;
    }

    public void writeToNBT(NBTTagCompound c)
    {
        c.setString("GroupName" + id, groupName);
        c.setInteger(groupName + "Length", abilities.size());

        for (Ability ability : abilities)
        {
            if (ability.parentAbility != null) c.setInteger(groupName + ability.id + "ParentId", ability.parentAbility.id);
            else c.setInteger(groupName + ability.id + "ParentId", -1);
            c.setInteger(groupName + ability.id + "StateId", ability.state.ordinal());
            c.setInteger(groupName + ability.id + "Colour", ability.highlightColour.getRGB());
            c.setInteger(groupName + ability.id + "TextureX", ability.iconX);
            c.setInteger(groupName + ability.id + "TextureY", ability.iconY);
            c.setInteger(groupName + ability.id + "Width", ability.width);
            c.setInteger(groupName + ability.id + "Height", ability.height);
            c.setInteger(groupName + ability.id + "X", ability.x);
            c.setInteger(groupName + ability.id + "Y", ability.y);
            c.setString(groupName + ability.id + "Name", ability.name);
            c.setInteger(groupName + ability.id + "DescriptionSize", ability.description.size());
            for (int i = 0; i < ability.description.size(); i++)
            {
                c.setString(groupName + ability.id + "DescriptionLine" + i, ability.description.get(i));
            }
        }

        int i = c.getId();
    }

    public static AbilityGroup createFromNBTAndId(NBTTagCompound c, int id)
    {
        List<Ability> abilities= new ArrayList<Ability>();
        String groupName = c.getString("GroupName" + id);
        int length = c.getInteger(groupName + "Length");

        for (int i = 0; i < length; i++)
        {
            Ability ability = new Ability();
            ability.id = i;
            ability.parentId = c.getInteger(groupName + i + "ParentId");
            ability.state = Ability.State.values()[c.getInteger(groupName + i + "StateId")];
            ability.highlightColour = new Color(c.getInteger(groupName + i + "Colour"));
            ability.iconX = c.getInteger(groupName + i + "TextureX");
            ability.iconY = c.getInteger(groupName + i + "TextureY");
            ability.width = c.getInteger(groupName + i + "Width");
            ability.height = c.getInteger(groupName + i + "Height");
            ability.x = c.getInteger(groupName + i + "X");
            ability.y = c.getInteger(groupName + i + "Y");
            ability.name = c.getString(groupName + i + "Name");
            int descriptionSize = c.getInteger(groupName + i + "DescriptionSize");
            ability.description = new ArrayList<String>();
            for (int j = 0; j < descriptionSize; j++)
            {
                ability.description.add(c.getString(groupName + i + "DescriptionLine" + j));
            }
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

        return new AbilityGroup(id, groupName, abilities);
    }
}
