package com.blocklings.guis;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import net.minecraft.entity.player.EntityPlayer;
import org.jline.utils.Log;

import java.awt.*;

class GuiBlocklingMining extends GuiBlocklingAbility
{
    GuiBlocklingMining(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
        if (init)
        {
            Ability ability0 = new Ability(0, null, -2, -3, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 45, 45, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 150, 90, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -20, 95, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -56, 99, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 100, 150, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 34, 120, 24, 0, "Ability 4", "Ability 4 description 123456");

            ability0.colour = new Color(0xaa55aa);
            ability1.colour = new Color(0x500F89);
            ability0.colour = new Color(0xB98F2C);
            ability2.colour = new Color(0x920C07);
            ability5.colour = new Color(0x0A8C2E);

            abilities.add(ability0);
            abilities.add(ability1);
            abilities.add(ability2);
            abilities.add(ability3);
            abilities.add(ability4);
            abilities.add(ability5);
            abilities.add(ability6);

            WINDOW = new ResourceLocationBlocklings("textures/guis/inventory" + GuiHelper.Tab.MINING.id + ".png");
        }

        super.initGui();
    }
}
