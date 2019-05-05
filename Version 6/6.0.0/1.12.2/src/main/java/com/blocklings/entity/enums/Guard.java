package com.blocklings.entity.enums;

import com.blocklings.entity.entities.EntityBlockling;

public enum Guard
{
    NOGUARD("Ignore", 1),
    GUARD("Guard", 2);

    public String name;
    public int id;

    Guard(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public static Guard getFromID(int id)
    {
        for (Guard guard : Guard.values())
        {
            if (guard.id == id)
            {
                return guard;
            }
        }

        return Guard.NOGUARD;
    }
}
