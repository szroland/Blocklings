package com.blocklings.guis;

import com.blocklings.abilities.Ability;
import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jline.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class GuiBlocklingBase extends GuiScreen
{
    protected static final int SCREEN_WIDTH = 160;
    protected static final int SCREEN_HEIGHT = 150;

    protected static final int TEXTURE_WIDTH = 232;
    protected static final int TEXTURE_HEIGHT = 166;

    protected int prevMouseX, prevMouseY;
    protected boolean isClicking = false;

    protected EntityBlockling blockling;
    protected EntityPlayer player;

    protected int xSize, ySize, left, top;
    protected int screenLeft, screenTop;

    GuiBlocklingBase(EntityBlockling blockling, EntityPlayer player)
    {
        super();

        this.blockling = blockling;
        this.player = player;
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void initGui()
    {
        xSize = 232;
        ySize = 166;

        left = (width - xSize) / 2;
        top = (height - ySize) / 2 + GuiHelper.YOFFSET;

        screenLeft = (width - SCREEN_WIDTH) / 2;
        screenTop = (height - SCREEN_HEIGHT) / 2 + GuiHelper.YOFFSET;
    }

    @Override
    public void updateScreen()
    {
        left = (width - xSize) / 2;
        top = (height - ySize) / 2 + GuiHelper.YOFFSET;

        screenLeft = (width - SCREEN_WIDTH) / 2;
        screenTop = (height - SCREEN_HEIGHT) / 2 + GuiHelper.YOFFSET;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        prevMouseX = mouseX;
        prevMouseY = mouseY;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null && blockling.getGuiID() != tab.id)
        {
            blockling.openGui(tab.id, player);
        }

        isClicking = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (isMouseOverScreen(mouseX, mouseY))
        {
            isClicking = true;
        }
    }

    /**
     * Draw tooltip on tab when if mouse is over one
     */
    protected void drawTabTooltip(int mouseX, int mouseY)
    {
        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null)
        {
            drawHoveringText(tab.name, mouseX, mouseY);
        }
    }

    /**
     * Check if mouse position is within a certain area
     */
    protected boolean isMouseOver(int mouseX, int mouseY, int left, int top, int width, int height)
    {
        return mouseX >= left && mouseX < left + width && mouseY >= top && mouseY <= top + height;
    }

    /**
     * Check if mouse position is currently over the central ability window
     */
    protected boolean isMouseOverScreen(int mouseX, int mouseY)
    {
        return isMouseOver(mouseX, mouseY, screenLeft, screenTop, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
