package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper.Tab;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiBlocklingInventory extends GuiContainer
{
    public static final Tab INVENTORY = Tab.INVENTORY;

    private static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocationBlocklings("textures/guis/inventory.png");

    private EntityBlockling blockling;
    private EntityPlayer player;

    private int textureWidth = 232;
    private int textureHeight = 166;

    private int left, top;

    public GuiBlocklingInventory(InventoryPlayer playerInv, InventoryBlockling blocklingInv, EntityBlockling blockling, EntityPlayer player)
    {
        super(new ContainerInventoryBlockling(playerInv, blocklingInv));

        this.blockling = blockling;
        this.player = player;

        xSize = 232;
        ySize = 166;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        xSize = 232;
        ySize = 166;

        left = guiLeft;
        top = guiTop + GuiHelper.YOFFSET;
    }

    @Override
    public void updateScreen()
    {
        left = guiLeft;
        top = guiTop + GuiHelper.YOFFSET;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null)
        {
            drawHoveringText(tab.name, mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        this.drawTexturedModalRect(left, top, 0, 0, textureWidth, textureHeight);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null && blockling.getGuiID() != tab.id)
        {
            blockling.openGui(tab.id, player);
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
