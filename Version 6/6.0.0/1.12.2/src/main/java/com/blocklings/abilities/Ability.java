package com.blocklings.abilities;

import scala.actors.threadpool.Arrays;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ability implements Serializable
{
    public enum State { LOCKED, UNLOCKED, ACQUIRED }

    public int id, x, y, width = 24, height = 24, textureX, textureY, parentId = -1;
    public String name = "Name";
    public List<String> description = new ArrayList<>();
    public State state = State.LOCKED;
    public Ability parentAbility;
    public Color colour = new Color(0x036A96);

    public Ability()
    {

    }

    public Ability(int id, Ability parentAbility, int x, int y, int textureX, int textureY, String name, String description)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        if (parentAbility != null) this.parentId = parentAbility.id;
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.name = name;
        List<String> desc = new ArrayList<>();
        desc.add(description);
        this.description = desc;
    }

    public Ability(int id, Ability parentAbility, int x, int y, int textureX, int textureY, String name, String[] description)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        if (parentAbility != null) this.parentId = parentAbility.id;
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.name = name;
        this.description = Arrays.asList(description);
    }

    public Ability(int id, Ability parentAbility, int x, int y, int textureX, int textureY, String name, List<String> description)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        if (parentAbility != null) this.parentId = parentAbility.id;
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.name = name;
        this.description = description;
    }

    public List<Ability> getChildren(List<Ability> baseList)
    {
        List<Ability> list = new ArrayList<Ability>();

        for (Ability ability : baseList)
        {
            if (this == ability.parentAbility)
            {
                list.add(ability);
            }
        }

        return list;
    }
}
