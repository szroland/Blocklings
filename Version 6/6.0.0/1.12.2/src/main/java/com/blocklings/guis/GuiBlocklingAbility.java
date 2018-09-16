package com.blocklings.guis;

import com.blocklings.entities.EntityBlockling;
import com.blocklings.abilities.Ability;
import com.blocklings.util.ResourceLocationBlocklings;
import com.blocklings.util.helpers.GuiHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

abstract class GuiBlocklingAbility extends GuiBlocklingBase
{
    protected static final ResourceLocation BACKGROUND = new ResourceLocationBlocklings("textures/guis/inventory_overlay.png");
    protected static final ResourceLocation ABILITIES = new ResourceLocationBlocklings("textures/guis/inventory_abilities.png");
    protected static final ResourceLocation ABILITIES2 = new ResourceLocationBlocklings("textures/guis/inventory_abilities2.png");
    protected static final ResourceLocation ABILITIES3 = new ResourceLocationBlocklings("textures/guis/inventory_abilities3.png");
    protected static ResourceLocation WINDOW = new ResourceLocationBlocklings("textures/guis/inventory3.png");

    protected int minScreenX = 0;
    protected int minScreenY = 0;
    protected int maxScreenX = 0;
    protected int maxScreenY = 0;

    protected List<Ability> abilities = new ArrayList<Ability>();

    /**
     * Relative x position for ability screen
     */
    protected int x;
    /**
     * Relative y position for ability screen
     */
    protected int y;

    /**
     * Current ability the mouse is over
     * Will return null if no ability
     */
    protected Ability hoveredAbility;

    /**
     * Needed to ensure screen isn't reset when resizing window/maximising
     * This is because initGui is called again on any screen size change
     */
    protected boolean init = true;

    private int beforeReleaseX, beforeReleaseY;

    GuiBlocklingAbility(EntityBlockling blockling, EntityPlayer player)
    {
        super(blockling, player);
    }

    @Override
    public void initGui()
    {
        super.initGui();

        if (init)
        {
            int minX = -10000, minY = -10000;
            int maxX = 10000, maxY = 10000;

            for (Ability ability : abilities)
            {
                minX = ability.x + ability.width > minX ? ability.x + ability.width : minX;
                minY = ability.y + ability.height > minY ? ability.y + ability.height : minY;

                maxX = ability.x < maxX ? ability.x : maxX;
                maxY = ability.y < maxY ? ability.y : maxY;
            }

            minScreenX = SCREEN_WIDTH - minX - 35;
            minScreenY = SCREEN_HEIGHT - minY - 35;
            maxScreenX = -maxX + 35;
            maxScreenY = -maxY + 35;

            x = minScreenX + ((maxScreenX - minScreenX) / 2);
            y = minScreenY + ((maxScreenY - minScreenY) / 2);

            init = false;
        }
    }

    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        hoveredAbility = getAbilityAtMouseLocation(mouseX, mouseY);

        updateXY(mouseX, mouseY);

        drawDefaultBackground();
        setDefaultRenderSettings();

        // Draw background
        mc.getTextureManager().bindTexture(BACKGROUND);
        drawTexturedModalRect(screenLeft, screenTop, 16 - Math.abs((x + 10000) % 16), 16 - Math.abs((y + 10000) % 16), SCREEN_WIDTH, SCREEN_HEIGHT);

        drawLines();
        drawAbilities();

        setDefaultRenderSettings();

        // Darken ability area when hovering over ability
        int colour = 0x00ffffff;
        if (getAbilityAtMouseLocation(mouseX, mouseY) != null) colour = 0x6a000000;
        drawRect(screenLeft, screenTop, screenLeft + SCREEN_WIDTH, screenTop + SCREEN_HEIGHT, colour);

        setDefaultRenderSettings();

        // Draw main window
        mc.getTextureManager().bindTexture(WINDOW);

        drawWindow();

        drawHover();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (beforeReleaseX == x && beforeReleaseY == y)
        {
            Ability ability = getAbilityAtMouseLocation(mouseX, mouseY);

            if (ability != null)
            {
                if (ability.state == Ability.State.UNLOCKED) ability.state = Ability.State.ACQUIRED;
                else if (ability.state == Ability.State.ACQUIRED) ability.state = Ability.State.LOCKED;
                else if (ability.state == Ability.State.LOCKED) ability.state = Ability.State.UNLOCKED;
            }

            GuiHelper.Tab tab = GuiHelper.getTabAt(mouseX, mouseY, width, height);

            if (tab != null && blockling.getGuiID() != tab.id)
            {
                blockling.openGui(tab.id, player);
            }
        }

        isClicking = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        beforeReleaseX = x;
        beforeReleaseY = y;
    }

    protected void drawHover()
    {
        mc.getTextureManager().bindTexture(WINDOW);
        zLevel += 15;

        if (hoveredAbility != null)
        {
            String text1 = hoveredAbility.text1;
            String text2 = hoveredAbility.text2;
            int width1 = fontRenderer.getStringWidth(text1);
            int width2 = fontRenderer.getStringWidth(text2);

            int startX = actualAbilityX(hoveredAbility) - 5, startY = actualAbilityY(hoveredAbility) + 3;
            int width = 90;

            if (width1 > width2) width = width1 + 34;
            else width = width2;

            drawTexturedModalRect(startX, startY + 14, 0, TEXTURE_HEIGHT + 20, width, 20);
            drawTexturedModalRect(startX + width, startY + 14, 192, TEXTURE_HEIGHT + 20, 8, 20);
            GlStateManager.color(hoveredAbility.colour.getRed() / 255f, hoveredAbility.colour.getGreen() / 255f, hoveredAbility.colour.getBlue() / 255f);
            drawTexturedModalRect(startX, startY, 0, TEXTURE_HEIGHT, width, 20);
            drawTexturedModalRect(startX + width, startY, 192, TEXTURE_HEIGHT, 8, 20);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

            GlStateManager.translate(0, 0, 25);
            fontRenderer.drawString(text1, startX + 34, startY + 6, 0xffffff, true);
            fontRenderer.drawString(text2, startX + 4, startY + 22, 0xeeeeee, true);
            GlStateManager.translate(0, 0, -25);
        }

        zLevel -= 15;
    }

    protected void drawWindow()
    {
        zLevel += 10;
        drawTexturedModalRect(left, top, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        GlStateManager.translate(0, 0, 11);
        fontRenderer.drawString("30", screenLeft + 12, screenTop - 1, 0x333333);
        fontRenderer.drawString("30", screenLeft + 11, screenTop - 2, 0xffffff);
        GlStateManager.translate(0, 0, -11);
        zLevel -= 10;
    }

    /**
     * Draw lines between abilities
     * Won't work if parent abilities are lower than children
     * Is inefficient so could do with a rework
     */
    private void drawLines()
    {
        for (Ability ability : abilities)
        {
            for (Ability child : ability.getChildren(abilities))
            {
                int colour1 = 0xff6a6a6a;
                int colour2 = 0xff121212;

                if (child.state == Ability.State.LOCKED)
                {
                    colour1 = 0xff454545;
                    colour2 = 0xff121212;
                }
                else if (child.state == Ability.State.ACQUIRED)
                {
                    colour1 = 0xffdddddd;
                    colour2 = 0xff121212;
                }

                int abilityX = ability.x + (ability.width / 2), abilityY = ability.y + (ability.height / 2);
                int childX = child.x + (child.width / 2), childY = child.y + (child.height / 2);

                int difX = abilityX - childX, difY = abilityY - childY;
                int cornerX = abilityX - difX, cornerY = abilityY - difY;

                int startX = screenLeft + x + abilityX, endX = screenLeft + x + cornerX;
                int startY = screenTop + y + abilityY, endY = screenTop + y + cornerY;

                // Swap start and end x values so always draw left to right
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

                if (startX < screenLeft)
                    startX = screenLeft;
                else if (startX > screenLeft + SCREEN_WIDTH)
                    startX = screenLeft + SCREEN_WIDTH;
                if (startY < screenTop)
                    startY = screenTop;
                else if (startY > screenTop + SCREEN_HEIGHT + 4)
                    startY = screenTop + SCREEN_HEIGHT + 4;
                if (endX < screenLeft)
                    endX = screenLeft;
                else if (endX > screenLeft + SCREEN_WIDTH)
                    endX = screenLeft + SCREEN_WIDTH;
                if (endY < screenTop)
                    endY = screenTop;
                else if (endY > screenTop + SCREEN_HEIGHT + 4)
                    endY = screenTop + SCREEN_HEIGHT + 4;

                int changeX = 0;
                int changeY = 0;
                if (difX < 0)
                    changeX = -1;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, colour1);
                if (difX > 0)
                {
                    drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, colour1);
                    drawVerticalLine(startX + changeX, endY + changeY - 2, endY + changeY, colour2);
                }
                else
                {
                    drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, colour1);
                    drawVerticalLine(endX + changeX, endY + changeY - 2, endY + changeY, colour2);
                }

                changeX = -1;
                changeY = -1;
                if (difX < 0)
                    changeX = 0;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, colour1);
                if (difX > 0)
                {
                    drawVerticalLine(startX + changeX, endY + changeY, startY + changeY, colour1);
                    drawVerticalLine(startX + changeX, endY + changeY - 2, endY + changeY, colour2);
                }
                else
                {
                    drawVerticalLine(endX + changeX, endY + changeY, startY + changeY, colour1);
                    drawVerticalLine(endX + changeX, endY + changeY - 2, endY + changeY, colour2);
                }

                changeX = -2;
                changeY = -2;
                if (difX < 0)
                    changeX = 1;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, colour2);
                if (difX > 0)
                {
                    drawVerticalLine(startX + changeX, endY + changeY - 2, startY + changeY, colour2);
                }
                else
                {
                    drawVerticalLine(endX + changeX, endY + changeY - 2, startY + changeY, colour2);
                }

                changeX = 1;
                changeY = 1;
                if (difX < 0)
                    changeX = -2;

                drawHorizontalLine(startX + changeX, endX + changeX, startY + changeY, colour2);
                if (difX > 0)
                {
                    drawVerticalLine(startX + changeX, endY + changeY - 2, startY + changeY, colour2);
                }
                else
                {
                    drawVerticalLine(endX + changeX, endY + changeY - 2, startY + changeY, colour2);
                }
            }
        }
    }

    /**
     * Draw all abilities in list
     */
    private void drawAbilities()
    {
        for (Ability ability : abilities)
        {
            drawAbility(ability);
        }
    }

    /**
     * Draw an ability while taking into account the relative position of it
     */
    private void drawAbility(Ability ability)
    {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        RenderHelper.disableStandardItemLighting();

        mc.getTextureManager().bindTexture(ABILITIES);
        if (ability.state == Ability.State.ACQUIRED) mc.getTextureManager().bindTexture(ABILITIES2);
        else if (ability.state == Ability.State.LOCKED) mc.getTextureManager().bindTexture(ABILITIES3);

        int startX = 0, startY = 0;
        int startDrawX = 0;
        int startDrawY = 0;
        int difX = 0, difY = 0;

        if (hoveredAbility == null || hoveredAbility != ability)
        {
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
        }

        if (difX <= ability.width && difY <= ability.height)
        {
            if (hoveredAbility != null)
            {
                int i = hoveredAbility == ability ? 20 : 0;

                zLevel+=i;
                drawTexturedModalRect(screenLeft + x + ability.x + startX, screenTop + y + ability.y + startY, ability.textureX + startDrawX, ability.textureY + startDrawY, ability.width - difX, ability.height - difY);
                zLevel-=i;
            }
            else
            {
                drawTexturedModalRect(screenLeft + x + ability.x + startX, screenTop + y + ability.y + startY, ability.textureX + startDrawX, ability.textureY + startDrawY, ability.width - difX, ability.height - difY);
            }
        }
    }

    private void updateXY(int mouseX, int mouseY)
    {
        if (isClicking)
        {
            x += mouseX - prevMouseX;
            y += mouseY - prevMouseY;
        }

        if (x < minScreenX)
            x = minScreenX;
        else if (x > maxScreenX)
            x = maxScreenX;
        if (y < minScreenY)
            y = minScreenY;
        else if (y > maxScreenY)
            y = maxScreenY;
    }

    /**
     * Gets the ability currently underneath the given mouse position
     */
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
                        return ability;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the actual x position of the ability on screen
     */
    private int actualAbilityX(Ability ability)
    {
        return screenLeft + x + ability.x;
    }

    /**
     * Gets the actual y position of the ability on screen
     */
    private int actualAbilityY(Ability ability)
    {
        return screenTop + y + ability.y;
    }
}
