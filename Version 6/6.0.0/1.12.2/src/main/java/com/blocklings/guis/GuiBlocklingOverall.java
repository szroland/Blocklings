package com.blocklings.guis;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jline.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GuiBlocklingOverall extends GuiBlocklingAbility
{
    GuiBlocklingOverall(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
        Ability ability0 = new Ability(0, null, -10, -20, 24, 0);
        Ability ability1 = new Ability(1, ability0, 30, 30, 0, 0);
        Ability ability2 = new Ability(2, ability0, -40, 90, 0, 0);
        Ability ability3 = new Ability(3, ability1, 90, 140, 24, 0);
        Ability ability4 = new Ability(4, ability2, 20, 130, 24, 0);

        abilities.add(ability0);
        abilities.add(ability1);
        abilities.add(ability2);
        abilities.add(ability3);
        abilities.add(ability4);

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
