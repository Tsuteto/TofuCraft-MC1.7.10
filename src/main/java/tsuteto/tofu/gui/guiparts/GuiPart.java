package tsuteto.tofu.gui.guiparts;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.Gui;
import tsuteto.tofu.gui.GuiTfMachineBase;

import java.awt.*;
import java.util.List;

public class GuiPart<T extends GuiPart> extends Gui
{
    public int x;
    public int y;
    public TfMachineGuiParts part = null;
    public GuiInfoTip infoTip = null;

    public GuiPart(int x, int y, TfMachineGuiParts part)
    {
        this.x = x;
        this.y = y;
        this.part = part;
    }

    public T setInfoTip(int tipW, int tipH, HoverTextPosition pos)
    {
        if (this.infoTip == null)
        {
            this.infoTip = new GuiInfoTip(part.xSize, part.ySize, tipW, tipH, pos);
        }
        else
        {
            this.infoTip.wTip = tipW;
            this.infoTip.hTip = tipH;
            this.infoTip.pos = pos;
        }
        return (T)this;
    }

    public T setInfoTip(HoverTextPosition pos)
    {
        if (this.infoTip == null)
        {
            this.infoTip = new GuiInfoTip(part.xSize, part.ySize, pos);
        }
        else
        {
            this.infoTip.pos = pos;
        }
        return (T)this;
    }

    public void draw(GuiTfMachineBase gui)
    {
        if (part != null) gui.drawGuiPart(x, y, part);
    }

    public void showInfoTip(GuiTfMachineBase gui, int px, int py, GuiTfMachineBase.IHoverDrawingHandler handler)
    {
        if (infoTip != null && gui.isPointInRegion(x, y, part.xSize, part.ySize, px, py)) // isPointInRegion
        {
            gui.drawTfHoveringTipFixedSize(px, py, infoTip.wTip, infoTip.hTip, infoTip.pos, handler);
        }
    }

    public void showInfoTip(GuiTfMachineBase gui, int px, int py, String text)
    {
        this.showInfoTip(gui, px, py, Lists.newArrayList(text));
    }

    public void showInfoTip(GuiTfMachineBase gui, int px, int py, List text)
    {
        if (infoTip != null && gui.isPointInRegion(x, y, part.xSize, part.ySize, px, py)) // isPointInRegion
        {
            gui.drawTfHoveringText(text, px, py, infoTip.pos);
        }
    }

    public Rectangle getBoundingBox()
    {
        return new Rectangle(x, y, part.xSize, part.ySize);
    }
}
