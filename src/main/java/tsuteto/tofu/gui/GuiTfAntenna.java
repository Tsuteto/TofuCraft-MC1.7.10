package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerTfAntenna;
import tsuteto.tofu.tileentity.TileEntityTfAntenna;

@SideOnly(Side.CLIENT)
public class GuiTfAntenna extends GuiTfMachineBase
{
    private final TileEntityTfAntenna machineInventory;

    private GuiPartGaugeBase supply = new GuiPartGaugeRevH(31, 35, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
            .setIndicatorColor(0xffad5d)
            .setInfoTip(57, 12, HoverTextPosition.LOWER_CENTER);
    private GuiPartGaugeBase demand = (GuiPartGaugeH)new GuiPartGaugeH(89, 35, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
            .setIndicatorColor(0x56e492)
            .setInfoTip(57, 12, HoverTextPosition.LOWER_CENTER);
//    private GuiPartGaugeH balance = new GuiPartGaugeH<GuiPartGaugeH>(50, 40, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
//            .setIndicatorColor(0x);

    public GuiTfAntenna(InventoryPlayer par1InventoryPlayer, TileEntityTfAntenna tfstorage)
    {
        super(new ContainerTfAntenna(par1InventoryPlayer, tfstorage));
        this.ySize = 80;
        this.machineInventory = tfstorage;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int px, int py)
    {
        String s = this.machineInventory.hasCustomInventoryName() ? this.machineInventory.getInventoryName() : StatCollector.translateToLocal(this.machineInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 0x5c5e54);
        //this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x5c5e54);

        supply.showInfoTip(this, px, py, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.2f", machineInventory.statTfSupply * 20);
                fontRendererObj.drawString(s, ox + 36 - fontRendererObj.getStringWidth(s), oy + 2, COLOR_TIP_TEXT);
                s = "/s";
                fontRendererObj.drawString(s, ox + 44, oy + 2, COLOR_TIP_TEXT);

                printTfSign(ox + 36, oy + 2, COLOR_TIP_TEXT);
            }
        });

        demand.showInfoTip(this, px, py, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.2f", machineInventory.statTfDemand * 20);
                fontRendererObj.drawString(s, ox + 36 - fontRendererObj.getStringWidth(s), oy + 2, COLOR_TIP_TEXT);
                s = "/s";
                fontRendererObj.drawString(s, ox + 44, oy + 2, COLOR_TIP_TEXT);

                printTfSign(ox + 36, oy + 2, COLOR_TIP_TEXT);
            }
        });
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        this.mc.getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawBasePanel(this.xSize, this.ySize);

        this.drawGuiPart(75, 50, TfMachineGuiParts.antennaGuide);

        double tfSupply = machineInventory.statTfSupply;
        double tfDemand = machineInventory.statTfDemand;
        double range = Math.max(tfSupply, tfDemand);

        supply.setPercentage(tfSupply / range)
                .draw(this);

        demand.setPercentage(tfDemand / range)
                .draw(this);
    }
}
