package tsuteto.tofu.gui;

import com.google.common.collect.Lists;

import java.util.List;

public class GuiLamp extends GuiPart<GuiLamp>
{
    protected static final int STAT_OFF = 0;
    protected static final int STAT_ON = 1;

    protected boolean stat = false;
    protected TfMachineGuiParts[] guiParts = new TfMachineGuiParts[2];
    protected List[] tipMessages = null;

    public GuiLamp(int x, int y, TfMachineGuiParts partOff, TfMachineGuiParts partOn)
    {
        super(x, y, partOff);
        this.guiParts[STAT_OFF] = partOff;
        this.guiParts[STAT_ON] = partOn;
    }

    public GuiLamp setSwitch(boolean bool)
    {
        this.stat = bool;
        return this;
    }

    public GuiLamp setTipMessage(String textOff, String textOn)
    {
        return this.setTipMessage(Lists.newArrayList(textOff), Lists.newArrayList(textOn));
    }

    public GuiLamp setTipMessage(List<String> textOff, List<String> textOn)
    {
        this.tipMessages = new List[2];
        this.tipMessages[STAT_OFF] = textOff;
        this.tipMessages[STAT_ON] = textOn;

        return this;
    }

    @Override
    public void draw(GuiTfMachineBase gui)
    {
        if (this.stat)
        {
            gui.drawGuiPart(x, y, guiParts[STAT_ON]);
        }
        else
        {
            gui.drawGuiPart(x, y, guiParts[STAT_OFF]);
        }
    }

    public void showInfoTip(GuiTfMachineBase gui, int px, int py)
    {
        if (infoTip != null && this.tipMessages != null && gui.isPointInRegion(x, y, part.xSize, part.ySize, px, py)) // isPointInRegion
        {
            List text = this.stat ? this.tipMessages[STAT_ON] : this.tipMessages[STAT_OFF];
            gui.drawTfHoveringText(text, px, py, infoTip.pos);
        }
    }
}
