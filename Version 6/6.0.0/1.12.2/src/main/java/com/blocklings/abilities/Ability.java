package com.blocklings.abilities;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ability implements Serializable
{
    public enum State { LOCKED, UNLOCKED, ACQUIRED }

    public int id, x, y, width = 24, height = 24, textureX, textureY, parentId = -1;
    public String name = "Michael", description = "Renshaw";
    public State state = State.LOCKED;
    public Ability parentAbility;
    public Color colour = new Color(0x036A96);

    public Ability()
    {

    }

    public Ability(int id, Ability parentAbility, int x, int y, int textureX, int textureY, String text1, String text2)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        if (parentAbility != null) this.parentId = parentAbility.id;
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.name = text1;
        this.description = text2;
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
