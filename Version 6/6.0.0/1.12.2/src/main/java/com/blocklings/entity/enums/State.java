package com.blocklings.entity.enums;

import com.blocklings.entity.entities.EntityBlockling;

public enum State
{
    STAY("Stay", 1),
    FOLLOW("Follow", 2),
    WANDER("Wander", 3);

    public String name;
    public int id;

    State(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public static State getFromID(int id)
    {
        for (State state : State.values())
        {
            if (state.id == id)
            {
                return state;
            }
        }

        return State.WANDER;
    }
}
