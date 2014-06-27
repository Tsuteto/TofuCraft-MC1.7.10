package tsuteto.tofu.gui;

import net.minecraft.client.gui.Gui;

import java.util.List;

abstract public class GuiPart<T extends GuiPart> extends Gui
{
    public int x;
    public int y;
    public TfMachineGuiParts part;
    public GuiInfoTip infoTip = null;

    public GuiPart(int x, int y, TfMachineGuiParts part)
    {
        this.x = x;
        this.y = y;
        this.part = part;
    }

    public T setInfoTip(int tipW, int tipH, GuiTfMachineBase.HoverTextPosition pos)
    {
        this.infoTip = new GuiInfoTip(part.xSize, part.ySize, tipW, tipH, pos);
        return (T)this;
    }

    public T setInfoTip(GuiTfMachineBase.HoverTextPosition pos)
    {
        this.infoTip = new GuiInfoTip(part.xSize, part.ySize, pos);
        return (T)this;
    }

    abstract public void draw(GuiTfMachineBase gui);

    public void showInfoTip(GuiTfMachineBase gui, int px, int py, GuiTfMachineBase.IHoverDrawingHandler handler)
    {
        if (infoTip != null && gui.isPointInRegion(x, y, part.xSize, part.ySize, px, py)) // isPointInRegion
        {
            gui.drawTfHoveringTipFixedSize(px, py, infoTip.wTip, infoTip.hTip, infoTip.pos, handler);
        }
    }
    public void showInfoTip(GuiTfMachineBase gui, int px, int py, List text)
    {
        if (infoTip != null && gui.isPointInRegion(x, y, part.xSize, part.ySize, px, py)) // isPointInRegion
        {
            gui.drawTfHoveringText(text, px, py, infoTip.pos);
        }
    }
}
