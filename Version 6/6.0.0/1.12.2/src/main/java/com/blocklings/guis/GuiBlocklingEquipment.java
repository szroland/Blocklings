package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.inventories.InventoryBlockling;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper.Tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.jline.utils.Log;

class GuiBlocklingEquipment extends GuiContainer
{
    private static final ResourceLocation WINDOW = new ResourceLocationBlocklings("textures/guis/inventory" + Tab.EQUIPMENT.id + ".png");

    private EntityBlockling blockling;
    private EntityPlayer player;

    private int textureWidth = 232;
    private int textureHeight = 166;

    private int left, top;

    private GuiButton lockButtonLeft, lockButtonRight;

    GuiBlocklingEquipment(InventoryPlayer playerInv, InventoryBlockling blocklingInv, EntityBlockling blockling, EntityPlayer player)
    {
        super(new ContainerEquipmentBlockling(playerInv, blocklingInv));

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

        blockling.isInGui = true;
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

        drawEntityOnScreen(width / 2, height / 2 - 28, 38, width / 2 - mouseX,  height / 2 - mouseY - 28, blockling);

        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null)
        {
            drawHoveringText(tab.name, mouseX, mouseY);
        }
    }

    private static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityBlockling ent)
    {
        float scale2 = ent.getBlocklingScale();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale) / scale2, (float)scale / scale2, (float)scale / scale2);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(WINDOW);
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

    @Override
    public void onGuiClosed()
    {
        blockling.isInGui = false;
        super.onGuiClosed();
    }
}
