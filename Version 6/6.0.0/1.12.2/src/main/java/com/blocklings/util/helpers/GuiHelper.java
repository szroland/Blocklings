package com.blocklings.util.helpers;

public class GuiHelper
{

    /** * Y offset for all GUI drawing/containers */
    public static final int YOFFSET = -10;
    
    public enum Tab
    {
        INVENTORY("Inventory", 0, -111, -81, 22, 22), 
        GENERAL("General", 1, 87, -81, 22, 22), 
        COMBAT("Combat", 2, 87, -52, 22, 22), 
        MINING("Mining", 3, 87, -23, 22, 22), 
        WOODCUTTING("Woodcutting", 4, 87, 6, 22, 22);

        public String name;
        public int id, x, y, width, height;

        Tab(String name, int id, int guiX, int guiY, int width, int height)
        {
            this.name = name;
            this.id = id;
            this.x = guiX;
            this.y = guiY;
            this.width = width;
            this.height = height;
        }
    }
    
    public static Tab getTabAt(int mouseX, int mouseY, int width, int height)
    {
        Tab tab = null;

        for (Tab t : Tab.values())
        {
            if (mouseX > (width / 2) + t.x && mouseX <= (width / 2) + t.x + t.width && mouseY > (height / 2) + t.y + YOFFSET && mouseY <= (height / 2) + t.y + t.height + YOFFSET)
            {
                return t;
            }
        }

        return tab;
    }
}
