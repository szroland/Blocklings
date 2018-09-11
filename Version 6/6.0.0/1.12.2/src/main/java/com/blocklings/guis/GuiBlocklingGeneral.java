package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.abilities.Ability;
import com.blocklings.util.helpers.GuiHelper;
import com.blocklings.util.helpers.GuiHelper.Tab;
import com.blocklings.util.ResourceLocationBlocklings;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.jline.utils.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GuiBlocklingGeneral extends GuiScreen
{
    private static final ResourceLocation BACKGROUND = new ResourceLocationBlocklings("textures/gui/inventory2_overlay.png");
    private static final ResourceLocation ABILITIES = new ResourceLocationBlocklings("textures/gui/inventory2_abilities.png");
    private static final ResourceLocation WINDOW = new ResourceLocationBlocklings("textures/gui/inventory2.png");

    private static final int TEXTURE_WIDTH = 232;
    private static final int TEXTURE_HEIGHT = 166;

    private static final int SCREEN_WIDTH = 160;
    private static final int SCREEN_HEIGHT = 150;

    private List<Ability> abilities = new ArrayList<Ability>();

    private EntityBlockling blockling;
    private EntityPlayer player;

    private int xSize, ySize, left, top;
    private int screenLeft, screenTop;

    private int minScreenX = 0;
    private int minScreenY = 0;
    private int maxScreenX = 0;
    private int maxScreenY = 0;

    /** Relative x position for ability screen */
    private int x;
    /** Relative y position for ability screen */
    private int y;

    private int prevMouseX, prevMouseY;
    private boolean isClicking = false;

    GuiBlocklingGeneral(EntityBlockling blockling, EntityPlayer player)
    {
        super();

        this.blockling = blockling;
        this.player = player;

        Ability ability0 = new Ability(0, null, -10, -20, 24, 0);
        Ability ability1 = new Ability(1, ability0, 30, 30, 0, 0);
        Ability ability2 = new Ability(2, ability0, -40, 90, 0, 0);
        Ability ability3 = new Ability(3, ability1, 90, 140, 24, 0);
        Ability ability4 = new Ability(3, ability2, 20, 130, 24, 0);

        abilities.add(ability0);
        abilities.add(ability1);
        abilities.add(ability2);
        abilities.add(ability3);
        abilities.add(ability4);
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

        int minX = -10000, minY = -10000;
        int maxX = 10000, maxY = 10000;

        for (Ability ability : abilities)
        {
            minX = ability.x + ability.width > minX ? ability.x + ability.width : minX;
            minY = ability.y + ability.height > minY ? ability.y + ability.height : minY;

            maxX = ability.x < maxX ? ability.x : maxX;
            maxY = ability.y < maxY ? ability.y : maxY;
        }

        minScreenX = SCREEN_WIDTH - minX - 50;
        minScreenY = SCREEN_HEIGHT - minY - 50;
        maxScreenX = -maxX + 50;
        maxScreenY = -maxY + 50;

        x = minScreenX + ((maxScreenX - minScreenX) / 2);
        y = minScreenY + ((maxScreenY - minScreenY) / 2);
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
        if (isClicking)
        {
            x += mouseX - prevMouseX;
            y += mouseY - prevMouseY;
        }

        if (x < minScreenX) x = minScreenX;
        else if (x > maxScreenX) x = maxScreenX;
        if (y < minScreenY) y = minScreenY;
        else if (y > maxScreenY) y = maxScreenY;

        drawDefaultBackground();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        RenderHelper.disableStandardItemLighting();

        // Draw background
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(screenLeft, screenTop, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        drawLines();
        drawAbilities();

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        RenderHelper.disableStandardItemLighting();

        // Draw main window
        mc.getTextureManager().bindTexture(WINDOW);
        drawTexturedModalRect(left, top, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        drawTabTooltip(mouseX, mouseY);

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

    private void drawLines()
    {
        for (Ability ability : abilities)
        {
            for (Ability child : ability.getChildren(abilities))
            {
                int abilityX = ability.x + (ability.width / 2), abilityY = ability.y + (ability.height / 2);
                int childX = child.x + (child.width / 2), childY = child.y + (child.height / 2);

                int difX = abilityX - childX, difY = abilityY - childY;
                int cornerX = abilityX - difX, cornerY = abilityY - difY;

                int startX = screenLeft + x + abilityX, endX = screenLeft + x + cornerX;
                int startY = screenTop + y + abilityY, endY = screenTop + y + cornerY;

                if (difX > 0)
                {
                    int i = startX;
                    startX = endX;
                    endX = i;
                }
                if (difY > 0)
                {
                    int i = startY;
                    startY = endY;
                    endY = i;
                }

                if (startX < screenLeft) startX = screenLeft;
                else if (startX > screenLeft + SCREEN_WIDTH) startX = screenLeft + SCREEN_WIDTH;
                if (startY < screenTop) startY = screenTop;
                else if (startY > screenTop + SCREEN_HEIGHT) startY = screenTop + SCREEN_HEIGHT;
                if (endX < screenLeft) endX = screenLeft;
                else if (endX > screenLeft + SCREEN_WIDTH) endX = screenLeft + SCREEN_WIDTH;
                if (endY < screenTop) endY = screenTop;
                else if (endY > screenTop + SCREEN_HEIGHT) endY = screenTop + SCREEN_HEIGHT;

                int changeX = 0;
                int changeY = 0;
                if (difX < 0) changeX = -1;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, 0xff333333);
                if (difX > 0) drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, 0xff333333);
                else drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, 0xff333333);

                changeX = -1;
                changeY = -1;
                if (difX < 0) changeX = 0;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, 0xff333333);
                if (difX > 0) drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, 0xff333333);
                else drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, 0xff333333);

                changeX = -2;
                changeY = -2;
                if (difX < 0) changeX = 1;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, 0xffffffff);
                if (difX > 0) drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, 0xffffffff);
                else drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, 0xffffffff);

                changeX = 1;
                changeY = 1;
                if (difX < 0) changeX = -2;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, 0xffffffff);
                if (difX > 0) drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, 0xffffffff);
                else drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, 0xffffffff);
            }
        }
    }

    private void drawAbilities()
    {
        mc.getTextureManager().bindTexture(ABILITIES);

        for (Ability ability : abilities)
        {
            drawAbility(ability);
        }
    }

    private void drawTabTooltip(int mouseX, int mouseY)
    {
        Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

        if (tab != null)
        {
            drawHoveringText(tab.name, mouseX, mouseY);
        }
    }

    /** Draw an ability while taking into account the relative position of it */
    private void drawAbility(Ability ability)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        RenderHelper.disableStandardItemLighting();

        int startX = 0, startY = 0;
        int startDrawX = 0;
        int startDrawY = 0;
        int difX = 0, difY = 0;

        if (x + ability.x < 0)
        {
            difX = -(x + ability.x);
            startX = difX;
            startDrawX = difX;
        }

        if (x + ability.x + ability.width > SCREEN_WIDTH)
        {
            difX = -(SCREEN_WIDTH - (x + ability.x + ability.width));
            startX = 0;
            startDrawX = 0;
        }

        if (y + ability.y < 0)
        {
            difY = -(y + ability.y);
            startY = difY;
            startDrawY = difY;
        }

        if (y + ability.y + ability.height > SCREEN_HEIGHT)
        {
            difY = -(SCREEN_HEIGHT - (y + ability.y + ability.height));
            startY = 0;
            startDrawY = 0;
        }

        if (difX <= ability.width && difY <= ability.height) drawTexturedModalRect(screenLeft + x + ability.x + startX, screenTop + y + ability.y + startY, ability.textureX + startDrawX, ability.textureY + startDrawY, ability.width - difX, ability.height - difY);
    }

    /** Check if mouse position is within a certain area */
    private boolean isMouseOver(int mouseX, int mouseY, int left, int top, int width, int height)
    {
        return mouseX >= left && mouseX < left + width && mouseY >= top && mouseY <= top + height;
    }

    /** Check if mouse position is currently over the central ability window */
    private boolean isMouseOverScreen(int mouseX, int mouseY)
    {
        return isMouseOver(mouseX, mouseY, screenLeft, screenTop, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /** Gets the ability currently underneath given mouse position */
    private Ability getAbilityAtMouseLocation(int mouseX, int mouseY)
    {
        if (isMouseOverScreen(mouseX, mouseY))
        {
            for (Ability ability : abilities)
            {
                if (mouseX >= actualAbilityX(ability) && mouseX < actualAbilityX(ability) + ability.width)
                {
                    if (mouseY >= actualAbilityY(ability) && mouseY < actualAbilityY(ability) + ability.height)
                    {
                        Log.info(ability.id);
                    }
                }
            }
        }

        return null;
    }

    /** Gets the actual x position of the ability on screen */
    private int actualAbilityX(Ability ability)
    {
        return screenLeft + x + ability.x;
    }

    /** Gets the actual y position of the ability on screen */
    private int actualAbilityY(Ability ability)
    {
        return screenTop + y + ability.y;
    }
}
