package com.blocklings.guis;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;
import java.io.IOException;

class GuiBlocklingGeneral extends GuiBlocklingAbility
{
    GuiBlocklingGeneral(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);

        abilities = blockling.generalAbilities;
    }

    @Override
    public void initGui()
    {
        if (init)
        {
            WINDOW = new ResourceLocationBlocklings("textures/guis/inventory" + GuiHelper.Tab.GENERAL.id + ".png");
        }

        super.initGui();
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();

        abilities = blockling.generalAbilities;
    }
}