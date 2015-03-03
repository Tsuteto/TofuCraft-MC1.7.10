package tsuteto.tofu.gui.guiparts;

import com.google.common.collect.Lists;
import net.minecraft.util.StatCollector;
import tsuteto.tofu.gui.GuiTfMachineBase;

import java.util.List;

public class GuiPartTfCharge extends GuiPartGaugeV
{
    protected double tfThreshold;

    public GuiPartTfCharge(int x, int y, double tfThreshold)
    {
        super(x, y, TfMachineGuiParts.gaugeTfChargedBg, TfMachineGuiParts.gaugeTfCharged);
        this.setInfoTip(HoverTextPosition.LOWER_CENTER);
        this.tfThreshold = tfThreshold;
    }

    public GuiPartTfCharge setTfThreshold(double tfThreshold)
    {
        this.tfThreshold = tfThreshold;
        return this;
    }

    public void showInfoTip(GuiTfMachineBase gui, int px, int py)
    {
        List<String> list = Lists.newArrayList();
        list.add(StatCollector.translateToLocal("tofucraft.tfPowered"));
        list.add(StatCollector.translateToLocal("tofucraft.tfPowered." + (this.percentage >= tfThreshold ? "true" : "false")));
        super.showInfoTip(gui, px, py, list);
    }
}
