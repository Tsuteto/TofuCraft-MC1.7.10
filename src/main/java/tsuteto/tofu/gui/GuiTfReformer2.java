package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.tileentity.ContainerTfReformer2;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

@SideOnly(Side.CLIENT)
public class GuiTfReformer2 extends GuiTfReformerBase
{
    public GuiTfReformer2(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer2(par1InventoryPlayer, machine), machine);
        progress = new GuiPartGaugeRevH(72, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);
        smallArrow = new GuiPart(55, 44, TfMachineGuiParts.smallArrowDown);
    }
}
