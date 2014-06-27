package tsuteto.tofu.gui;

public class GuiPartGuageH<T extends GuiPartGuageH> extends GuiPartGuageBase<T>
{
    public GuiPartGuageH(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator)
    {
        super(x, y, frame, indicator);
    }

    public GuiPartGuageH(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator, int xIndicatorOffset, int yIndicatorOffset)
    {
        super(x, y, frame, indicator, xIndicatorOffset, yIndicatorOffset);
    }

    @Override
    protected void drawFrame(GuiTfMachineBase gui)
    {
        gui.drawTexturedModalRect(gui.getGuiLeft() + x, gui.getGuiTop() + y, part.ox, part.oy, part.xSize, part.ySize, frameColor);
    }

    @Override
    protected void drawIndicator(GuiTfMachineBase gui)
    {
        int xIndicatorSize = (int)(indicator.xSize * percentage);
        gui.drawTexturedModalRect(
                gui.getGuiLeft() + x + xIndicatorOffset, gui.getGuiTop() + y + yIndicatorOffset,
                indicator.ox, indicator.oy,
                xIndicatorSize, indicator.ySize,
                indicatorColor);
    }
}
