package com.blocklings.guis;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.io.IOException;

class GuiBlocklingOverall extends GuiBlocklingAbility
{
    GuiBlocklingOverall(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
        if (init)
        {
            Ability ability0 = new Ability(0, null, -10, -20, 24, 0, "Ability 0 Super Long Ability Name", "Ability 0 description");
            Ability ability1 = new Ability(1, ability0, 30, 30, 0, 0, "Ability 1", "Ability 1 description 1");
            Ability ability6 = new Ability(1, ability0, 110, 30, 0, 0, "Ability 6", "Ability 6 description 12");
            Ability ability2 = new Ability(2, ability0, -40, 90, 0, 0, "Ability 2", "Ability 2 description 123");
            Ability ability5 = new Ability(2, ability0, -76, 90, 0, 0, "Ability 5", "Ability 5 description 1234");
            Ability ability3 = new Ability(3, ability1, 90, 140, 24, 0, "Ability 3", "Ability 3 description 12345");
            Ability ability4 = new Ability(4, ability2, 20, 130, 24, 0, "Ability 4", "Ability 4 description 123456");

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
        }

        super.initGui();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
