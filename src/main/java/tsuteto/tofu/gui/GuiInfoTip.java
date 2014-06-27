package tsuteto.tofu.gui;

public class GuiInfoTip
{
    public int wArea;
    public int hArea;
    public int wTip = 0;
    public int hTip = 0;
    GuiTfMachineBase.HoverTextPosition pos;

    public GuiInfoTip(int w, int h, int tw, int th, GuiTfMachineBase.HoverTextPosition pos)
    {
        this.wArea = w;
        this.hArea = h;
        this.wTip = tw;
        this.hTip = th;
        this.pos = pos;
    }

    public GuiInfoTip(int w, int h, GuiTfMachineBase.HoverTextPosition pos)
    {
        this.wArea = w;
        this.hArea = h;
        this.pos = pos;
    }

    public GuiInfoTip setTipSize(int tw, int th)
    {
        this.wTip = tw;
        this.hTip = th;
        return this;
    }
}
