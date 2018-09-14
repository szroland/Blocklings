package com.blocklings.abilities;

import java.util.ArrayList;
import java.util.List;

public class Ability
{
    public enum State { LOCKED, UNLOCKED, ACQUIRED }

    public int id, x, y, width = 24, height = 24, textureX, textureY;
    public State state = State.LOCKED;
    public Ability parentAbility;

    public Ability(int id, Ability parentAbility, int textureX, int textureY)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        this.textureX = textureX;
        this.textureY = textureY;
    }
    
    public Ability(int id, Ability parentAbility, int x, int y, int textureX, int textureY)
    {
        this.id = id;
        this.parentAbility = parentAbility;
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
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
