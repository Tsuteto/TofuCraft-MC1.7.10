package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.tileentity.ContainerTfReformer;
import tsuteto.tofu.tileentity.ContainerTfReformer2;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

@SideOnly(Side.CLIENT)
public class GuiTfReformer extends GuiTfReformerBase
{
    public GuiTfReformer(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer(par1InventoryPlayer, machine), machine);
        progress = new GuiPartGaugeRevH(85, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);
        smallArrow = new GuiPart(65, 44, TfMachineGuiParts.smallArrowDown);
    }
}
