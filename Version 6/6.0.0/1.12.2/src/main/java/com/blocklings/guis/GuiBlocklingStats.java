package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper.Tab;
import javafx.scene.paint.Color;
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
        nameTextField = new GuiTextField2(3, fontRenderer, width / 2 - 80, height / 2 - 85, 160, 20)
        {
            public void setFocused(boolean isFocusedIn)
            {
                blockling.setName(nameTextField.getText());
                nameTextField.setText(blockling.getCustomNameTag());
                super.setFocused(isFocusedIn);
            }
        };
        nameTextField.setText(blockling.getCustomNameTag());
        nameTextField.setFocused(true);
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
        drawEntityOnScreen(width / 2, height / 2 + 16, 45, width / 2 - mouseX,  height / 2 - mouseY - 16, blockling);

        setDefaultRenderSettings();
        nameTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawInfo()
    {
        int size = 11;
        int i = -50, j = -22, k = -72;
        int xx = width / 2 + k;
        int yy = height / 2 + i;
        drawTexturedModalRect(xx, yy - (j * 0), 0, TEXTURE_HEIGHT, size, size);
        drawTexturedModalRect(xx, yy - (j * 1), 11, TEXTURE_HEIGHT, size, size);
        drawTexturedModalRect(xx, yy - (j * 2), 22, TEXTURE_HEIGHT, size, size);
        drawTexturedModalRect(xx, yy - (j * 3), 33, TEXTURE_HEIGHT, size, size);

        xx += 15;
        yy += 2;
        int colour = 0xd1d1d1;
        String totalLevelString = Integer.toString(blockling.getGeneralLevel());
        fontRenderer.drawString(totalLevelString, xx, yy - (j * 0), colour, true);
        colour = 0xb30000;
        colour = 0xff4d4d;
        String combatLevelString = Integer.toString(blockling.getCombatLevel());
        fontRenderer.drawString(combatLevelString, xx, yy - (j * 1), colour, true);
        colour = 0x2952a3;
        colour = 0x7094db;
        String miningLevelString = Integer.toString(blockling.getMiningLevel());
        fontRenderer.drawString(miningLevelString, xx, yy - (j * 2), colour, true);
        colour = 0x0a9306;
        colour = 0x57a65b;
        String woodcuttingLevelString = Integer.toString(blockling.getWoodcuttingLevel());
        fontRenderer.drawString(woodcuttingLevelString, xx, yy - (j * 3), colour, true);
        colour = 0x894d10;
        colour = 0x9d6d4a;

        setDefaultRenderSettings();
        mc.getTextureManager().bindTexture(WINDOW);

        xx = width / 2 - k - size;
        yy -= 2;
        drawTexturedModalRect(xx, yy - (j * 0), 0, TEXTURE_HEIGHT + size, size, size);
        drawTexturedModalRect(xx, yy - (j * 1), 11, TEXTURE_HEIGHT + size, size, size);
        drawTexturedModalRect(xx, yy - (j * 2), 22, TEXTURE_HEIGHT + size, size, size);
        drawTexturedModalRect(xx, yy - (j * 3), 33, TEXTURE_HEIGHT + size, size, size);

        xx -= 15 - size;
        yy += 2;
        double health = blockling.getHealth();
        double maxHealth = blockling.getMaxHealth();
        double r = 163 - 92 * (health / maxHealth), g = 0 + 171 * (health / maxHealth), b = 0 + 3 * (health / maxHealth);
        colour = (int) r;
        colour = (colour << 8) + (int) g;
        colour = (colour << 8) + (int) b;
        String healthString = Integer.toString((int) health);
        fontRenderer.drawString(healthString, xx - fontRenderer.getStringWidth(healthString), yy - (j * 0), colour, true);
        colour = 0xfbba20;
        String damageString = "5";
        fontRenderer.drawString(damageString, xx - fontRenderer.getStringWidth(damageString), yy - (j * 1), colour, true);
        String attackSpeedString = "30";
        fontRenderer.drawString(attackSpeedString, xx - fontRenderer.getStringWidth(attackSpeedString), yy - (j * 2), colour, true);
        String speedString = "13";
        fontRenderer.drawString(speedString, xx - fontRenderer.getStringWidth(speedString), yy - (j * 3), colour, true);
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
                nameTextField.setFocused(false);
            default:
                nameTextField.textboxKeyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed()
    {
        if (nameTextField != null) nameTextField.setFocused(false);
        super.onGuiClosed();
    }
}
