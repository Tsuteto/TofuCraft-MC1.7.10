package tsuteto.tofu.gui.guiparts;

import tsuteto.tofu.gui.GuiTfMachineBase;

public class GuiPartGaugeV extends GuiPartGaugeBase<GuiPartGaugeV>
{
    public GuiPartGaugeV(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator)
    {
        super(x, y, frame, indicator);
    }

    public GuiPartGaugeV(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator, int xIndicatorOffset, int yIndicatorOffset)
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
        int yIndicatorSize = (int)(indicator.ySize * percentage);
        gui.drawTexturedModalRect(
                gui.getGuiLeft() + x + xIndicatorOffset, gui.getGuiTop() + y + yIndicatorOffset + indicator.ySize - yIndicatorSize,
                indicator.ox, indicator.oy + indicator.ySize - yIndicatorSize,
                indicator.xSize, yIndicatorSize,
                indicatorColor);
    }
}
