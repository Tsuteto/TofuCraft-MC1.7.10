package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.gui.guiparts.GuiPart;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeRevH;
import tsuteto.tofu.gui.guiparts.GuiPartTfCharge;
import tsuteto.tofu.tileentity.ContainerTfReformerBase;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

@SideOnly(Side.CLIENT)
abstract public class GuiTfReformerBase extends GuiTfMachineBase
{
    private final TileEntityTfReformer machineInventory;

    protected GuiPartGaugeRevH progress;
    protected GuiPart smallArrow;
    protected GuiPartTfCharge tfCharged;

    public GuiTfReformerBase(ContainerTfReformerBase container, TileEntityTfReformer machine)
    {
        super(container);
        this.ySize = 180;
        this.machineInventory = machine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.machineInventory.hasCustomInventoryName() ? this.machineInventory.getInventoryName() : StatCollector.translateToLocal(this.machineInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x5c5e54);

        this.tfCharged.showInfoTip(this, par1, par2);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawStandardBasePanel();
        this.drawTfMachineSlot();

        smallArrow.draw(this);

        progress
                .setPercentage(this.machineInventory.getProgressScaledOutput())
                .draw(this);

        tfCharged
                .setPercentage(this.machineInventory.tfAmount / this.machineInventory.tfCapacity)
                .draw(this);
    }
}
