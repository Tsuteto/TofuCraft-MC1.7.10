package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerTfReformer;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

@SideOnly(Side.CLIENT)
public class GuiTfReformer extends GuiTfMachineBase
{
    private final TileEntityTfReformer machineInventory;

    private GuiPartGaugeRevH progress = new GuiPartGaugeRevH(90, 36, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);

    public GuiTfReformer(InventoryPlayer par1InventoryPlayer, TileEntityTfReformer machine)
    {
        super(new ContainerTfReformer(par1InventoryPlayer, machine));
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
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawStandardBasePanel();
        this.drawTfMachineSlot();

        this.drawGuiPart(65, 44, TfMachineGuiParts.smallArrowDown);

        progress
                .setPercentage(this.machineInventory.getProgressScaledOutput())
                .draw(this);
    }
}
