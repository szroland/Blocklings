package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBlocklingStats extends GuiBlocklingBase
{
    private static final ResourceLocation WINDOW = new ResourceLocationBlocklings("textures/guis/inventory" + Tab.STATS.id + ".png");

    public GuiBlocklingStats(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
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
        drawDefaultBackground();

        setDefaultRenderSettings();

        // Draw background
        mc.getTextureManager().bindTexture(WINDOW);
        drawTexturedModalRect(left, top, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
