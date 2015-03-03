package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeH;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeRevH;
import tsuteto.tofu.gui.guiparts.GuiPartTfCharge;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.tileentity.ContainerTfOven;
import tsuteto.tofu.tileentity.TileEntityTfOven;

public class GuiTfOven extends GuiTfMachineBase
{
    private final TileEntityTfOven machineInventory;

    private GuiPartGaugeH progress = new GuiPartGaugeH(83, 31, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    private GuiPartGaugeH heaterLeft  = new GuiPartGaugeH(31, 32, TfMachineGuiParts.heaterBgLeft, TfMachineGuiParts.heaterLeft);
    private GuiPartGaugeRevH heaterRight = new GuiPartGaugeRevH(67, 32, TfMachineGuiParts.heaterBgRight, TfMachineGuiParts.heaterRight);

    private GuiPartTfCharge tfCharge = new GuiPartTfCharge(49, 54, TileEntityTfOven.COST_TF_PER_TICK / TileEntityTfOven.TF_CAPACITY);

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

        tfCharge.showInfoTip(this, par1, par2);
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

        progress.setPercentage(machineInventory.getCookProgressScaled())
                .draw(this);

        double heaterScaled = machineInventory.isWorking ? 1.0D : 0.0D;
        heaterLeft.setPercentage(heaterScaled)
                .draw(this);
        heaterRight.setPercentage(heaterScaled)
                .draw(this);

        tfCharge.setTfThreshold(machineInventory.getTfAmountNeeded() / TileEntityTfOven.TF_CAPACITY)
                .setPercentage(machineInventory.getTfCharged())
                .draw(this);
    }

}
