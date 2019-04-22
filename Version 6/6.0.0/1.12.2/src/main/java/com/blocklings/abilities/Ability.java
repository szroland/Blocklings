package com.blocklings.abilities;

import com.blocklings.util.helpers.AbilityHelper;
import javafx.util.Pair;
import scala.actors.threadpool.Arrays;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ability implements Serializable
{
    public enum State
    {
        LOCKED(new Color(0x444444)),
        UNLOCKED(new Color(0xF4F4F4)),
        ACQUIRED(new Color(0xFFC409));

        public Color colour;

        State(Color colour)
        {
            this.colour = colour;
        }
    }

    public int id, x, y, width = 24, height = 24, iconX, iconY, shapeX, shapeY, parentId = -1, skillPointCost;
    public String name = "Name";
    public List<Ability> conflictingAbilities = new ArrayList<>();
    public List<String> description = new ArrayList<>();
    public HashMap<String, Integer> levelRequirements = new HashMap<>();
    public State state = State.LOCKED;
    public Ability parentAbility;
    public Color highlightColour = new Color(0x036A96);

    public Ability()
    {

    }

    public Ability initInfo(int id, String name, String[] description, Ability parentAbility, int skillPointCost)
    {
        this.id = id;
        this.name = name;
        this.description = Arrays.asList(description);
        this.parentAbility = parentAbility;
        if (parentAbility != null) this.parentId = parentAbility.id;
        this.skillPointCost = skillPointCost;
        return this;
    }

    public Ability initGui(int x, int y, int iconX, int iconY, int shapeX, int shapeY, Color highlightColour)
    {
        this.x = x;
        this.y = y;
        this.iconX = iconX;
        this.iconY = iconY;
        this.shapeX = shapeX;
        this.shapeY = shapeY;
        this.highlightColour = highlightColour;
        return this;
    }

    public Ability initConflictingAbilities(Ability[] conflictingAbilities)
    {
        this.conflictingAbilities = Arrays.asList(conflictingAbilities);
        return this;
    }

    public Ability initLevelRequirements(HashMap<String, Integer> levelRequirements)
    {
        this.levelRequirements = levelRequirements;
        return this;
    }

    public void initFromDefaults()
    {
        checkList(AbilityHelper.generalAbilities);
        checkList(AbilityHelper.combatAbilities);
        checkList(AbilityHelper.miningAbilities);
        checkList(AbilityHelper.woodcuttingAbilities);
        checkList(AbilityHelper.farmingAbilities);
    }

    private void checkList(List<Ability> abilities)
    {
        if (abilities.contains(this))
        {
            for (Ability ability : abilities)
            {
                if (this.equals(ability))
                {
                    this.conflictingAbilities = ability.conflictingAbilities;
                    this.skillPointCost = ability.skillPointCost;
                    this.levelRequirements = ability.levelRequirements;
                }
            }
        }
    }


    public boolean tryCycleState(List<Ability> baseList)
    {
        if (!hasConflictingAbility(baseList))
        {
            if (state.ordinal() == State.values().length - 1)
            {
                for (Ability child : getChildren(baseList))
                {
                    if (child.state.ordinal() >= state.ordinal())
                    {
                        return false;
                    }
                }

                state = State.UNLOCKED;
                return true;
            }
            else if (parentAbility == null)
            {
                state = State.values()[state.ordinal() + 1];
                return true;
            }
            else if (parentAbility.state.ordinal() > state.ordinal())
            {
                state = State.values()[state.ordinal() + 1];
                return true;
            }
        }

        return false;
    }

    public boolean hasConflictingAbility(List<Ability> baseList)
    {
        if (state == State.UNLOCKED)
        {
            for (Ability ability : baseList)
            {
                if (conflictingAbilities.contains(ability))
                {
                    if (ability.state == State.ACQUIRED)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
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

    private static HashMap<Integer, Pair<Integer, Integer>> mutexTextures = new HashMap<Integer, Pair<Integer, Integer>>()
    {{
        put(-1, new Pair<>(0, 0));
        put(0, new Pair<>(24, 0));
    }};

    @Override
    public boolean equals(Object o)
    {
        return ((Ability)o).name.equals(name);
    }
}
