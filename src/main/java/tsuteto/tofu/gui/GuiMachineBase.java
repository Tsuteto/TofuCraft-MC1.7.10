package tsuteto.tofu.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

abstract public class GuiMachineBase extends GuiContainer
{
    public GuiMachineBase(Container p_i1072_1_)
    {
        super(p_i1072_1_);
    }

    public void drawString(String s, int x, int y, int color)
    {
        super.fontRendererObj.drawString(s, x, y, color);
    }

    public void drawStringWithShadow(String s, int x, int y, int color)
    {
        super.drawString(this.fontRendererObj, s, x, y, color);
    }

    public int getStringWidth(String s)
    {
        return this.fontRendererObj.getStringWidth(s);
    }

    public FontRenderer getFontRenderer()
    {
        return this.fontRendererObj;
    }

    public boolean isPointInRegion(int ox, int oy, int w, int h, int x, int y)
    {
        return this.func_146978_c(ox, oy, w, h, x, y);
    }

    public int getGuiLeft()
    {
        return this.guiLeft;
    }

    public int getGuiTop()
    {
        return this.guiTop;
    }
}
