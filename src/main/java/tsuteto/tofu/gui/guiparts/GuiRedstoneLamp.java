package tsuteto.tofu.gui.guiparts;

import com.google.common.collect.Lists;
import net.minecraft.util.StatCollector;

public class GuiRedstoneLamp extends GuiLamp
{
    public GuiRedstoneLamp(int x, int y)
    {
        super(x, y, TfMachineGuiParts.redstoneOff, TfMachineGuiParts.redstoneOn);

        this.setInfoTip(HoverTextPosition.LOWER_CENTER);
        this.setTipMessage(
                Lists.newArrayList(
                        StatCollector.translateToLocal("tofucraft.redstone"),
                        StatCollector.translateToLocal("tofucraft.redstone.off")),
                Lists.newArrayList(
                        StatCollector.translateToLocal("tofucraft.redstone"),
                        StatCollector.translateToLocal("tofucraft.redstone.on")));
    }
}
