package tsuteto.tofu.gui;

import net.minecraft.util.StatCollector;

public class GuiRedstoneLamp extends GuiLamp
{
    public GuiRedstoneLamp(int x, int y)
    {
        super(x, y, TfMachineGuiParts.redstoneOff, TfMachineGuiParts.redstoneOn);

        this.setInfoTip(GuiTfMachineBase.HoverTextPosition.LOWER_CENTER);
        this.setTipMessage(
                    StatCollector.translateToLocal("tofucraft.redstone.off"),
                    StatCollector.translateToLocal("tofucraft.redstone.on"));

    }
}
