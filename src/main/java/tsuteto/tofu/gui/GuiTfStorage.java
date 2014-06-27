package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import tsuteto.tofu.tileentity.ContainerTfStorage;
import tsuteto.tofu.tileentity.TileEntityTfStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTfStorage extends GuiTfMachineBase
{
    private final TileEntityTfStorage machineInventory;

    private GuiPartGuageH progress = new GuiPartGuageH(69, 36, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

    private GuiPartGuageBase tfGuage = (GuiPartGuageBase)new GuiPartGuageH(100, 39, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
            .setInfoTip(57, 22, HoverTextPosition.LOWER_CENTER);

    public GuiTfStorage(InventoryPlayer par1InventoryPlayer, TileEntityTfStorage tfstorage)
    {
        super(new ContainerTfStorage(par1InventoryPlayer, tfstorage));
        this.ySize = 180;
        this.machineInventory = tfstorage;
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

        tfGuage.showInfoTip(this, par1, par2, new IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.0f", machineInventory.tfAmount);
                fontRendererObj.drawString(s, ox + 33 - fontRendererObj.getStringWidth(s), oy + 2, 0xf1f2e6);

                s = String.format("/%.0f", machineInventory.tfCapacity);
                fontRendererObj.drawString(s, ox + 41 - fontRendererObj.getStringWidth(s), oy + 12, 0xb4b5aa);

                printTfSign(ox + 33, oy + 2, 0xf1f2e6);
                printTfSign(ox + 41, oy + 12, 0xb4b5aa);
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

        this.drawStandardBasePanel();
        this.drawTfMachineSlot();

        this.drawGuiPart(50, 44, TfMachineGuiParts.smallArrowDown);

        progress
                .setPercentage(this.machineInventory.getProgressScaledInput())
                .draw(this);

        tfGuage
                .setPercentage(this.machineInventory.getProgressScaledTfAmount())
                .draw(this);
    }
}
