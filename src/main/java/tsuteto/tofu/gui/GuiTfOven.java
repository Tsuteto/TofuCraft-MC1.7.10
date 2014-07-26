package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerTfOven;
import tsuteto.tofu.tileentity.TileEntityTfOven;

public class GuiTfOven extends GuiTfMachineBase
{
    private final TileEntityTfOven machineInventory;

    private GuiPartGaugeH progress = new GuiPartGaugeH(83, 31, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    private GuiPartGaugeH heaterLeft  = new GuiPartGaugeH(31, 32, TfMachineGuiParts.heaterBgLeft, TfMachineGuiParts.heaterLeft);
    private GuiPartGaugeRevH heaterRight = new GuiPartGaugeRevH(67, 32, TfMachineGuiParts.heaterBgRight, TfMachineGuiParts.heaterRight);

    public GuiTfOven(InventoryPlayer par1InventoryPlayer, TileEntityTfOven machine)
    {
        super(new ContainerTfOven(par1InventoryPlayer, machine));
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

        progress.setPercentage(machineInventory.getCookProgressScaled())
                .draw(this);

        double heaterScaled = machineInventory.getHeaterScaled();
        heaterLeft.setPercentage(heaterScaled)
                .draw(this);
        heaterRight.setPercentage(heaterScaled)
                .draw(this);
    }

}
