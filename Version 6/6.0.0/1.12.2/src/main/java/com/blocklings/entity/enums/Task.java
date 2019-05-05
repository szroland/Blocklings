package com.blocklings.entity.enums;

import com.blocklings.entity.entities.EntityBlockling;

public enum Task
{
    IDLE("Idle", 1),
    HUNT("Hunt", 2),
    MINE("Mine", 3),
    CHOP("Chop", 4),
    FARM("Farm", 5);

    public String name;
    public int id;

    Task(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public static Task getFromID(int id)
    {
        for (Task task : Task.values())
        {
            if (task.id == id)
            {
                return task;
            }
        }

        return Task.IDLE;
    }
}
