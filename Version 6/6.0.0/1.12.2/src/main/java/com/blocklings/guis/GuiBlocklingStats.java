package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper.Tab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiBlocklingStats extends GuiBlocklingBase
{
    private static final ResourceLocation WINDOW = new ResourceLocationBlocklings("textures/guis/inventory" + Tab.STATS.id + ".png");

    private GuiButton taskButton;
    private GuiButton guardButton;
    private GuiButton stateButton;
    private GuiTextField2 nameTextField;

    GuiBlocklingStats(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        buttonList.add(taskButton = new GuiButton(0, width / 2 - 81, height / 2 + 46, 52, 20, "asdsad"));
        buttonList.add(guardButton = new GuiButton(1, width / 2 - 25, height / 2 + 46, 51, 20, "asdsad"));
        buttonList.add(stateButton = new GuiButton(2, width / 2 + 29, height / 2 + 46, 52, 20, "asdsad"));
        nameTextField = new GuiTextField2(3, fontRenderer, width / 2 - 80, height / 2 - 85, 160, 20);
        nameTextField.setFocused(true);
        nameTextField.setText(blockling.getCustomNameTag());
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        nameTextField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();

        setDefaultRenderSettings();

        // Draw background
        mc.getTextureManager().bindTexture(WINDOW);
        drawTexturedModalRect(left, top, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        drawInfo();

        setDefaultRenderSettings();
        drawEntityOnScreen(width / 2, height / 2 + 10, 55, width / 2 - mouseX,  height / 2 - mouseY, blockling);

        nameTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawInfo()
    {
        int i = -10, j = 18;
        String totalLevelString = Integer.toString(blockling.getGeneralLevel());
        fontRenderer.drawString(totalLevelString, width / 2 - 72, height / 2 - (i + j * 0), 0xffd700, false);
        String combatLevelString = Integer.toString(blockling.getCombatLevel());
        fontRenderer.drawString(combatLevelString, width / 2 - 72, height / 2 - (i + j * 1), 0xffd700, false);
        String miningLevelString = Integer.toString(blockling.getMiningLevel());
        fontRenderer.drawString(miningLevelString, width / 2 - 72, height / 2 - (i + j * 2), 0xffd700, false);
        String woodcuttingLevelString = Integer.toString(blockling.getCombatLevel());
        fontRenderer.drawString(woodcuttingLevelString, width / 2 - 72, height / 2 - (i + j * 3), 0xffd700, false);
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
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        nameTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);

        switch (keyCode)
        {
            case 28:
                blockling.setName(nameTextField.getText());
                nameTextField.setFocused(false);
            default:
                nameTextField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }
}
