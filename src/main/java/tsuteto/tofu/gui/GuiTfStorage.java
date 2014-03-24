package tsuteto.tofu.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import tsuteto.tofu.block.tileentity.ContainerTfStorage;
import tsuteto.tofu.block.tileentity.TileEntityTfStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTfStorage extends GuiTfMachineBase
{
    private static final ResourceLocation texture = new ResourceLocation("tofucraft", "textures/gui/tfstorage.png");

    private final TileEntityTfStorage machineInventory;

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
        //String s = this.furnaceInventory.func_94042_c() ? this.furnaceInventory.getInvName() : StatCollector.translateToLocal(this.furnaceInventory.getInvName());
        String s = StatCollector.translateToLocal(this.machineInventory.getInventoryName());
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
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;

        var7 = this.machineInventory.getProgressScaledInput(24);
        this.drawTexturedModalRect(var5 + 69, var6 + 21, 176, 0, var7 + 1, 16);

        var7 = this.machineInventory.getProgressScaledOutput(24);
        this.drawTexturedModalRect(var5 + 69, var6 + 50, 176, 17, var7 + 1, 16);
        
        if (this.machineInventory.tfAmount > 0)
        {
            var7 = this.machineInventory.getProgressScaledTfAmount(55);
            this.drawTexturedModalRect(var5 + 103, var6 + 38, 176, 34, var7 + 1, 8);
        }
        
        if (this.func_146978_c(101, 36, 59, 12, par2, par3)) // isPointInRegion
        {
            this.drawTfHoveringTipFixedSize(par2, par3, 51, 22, HoverTextPosition.LOWER_CENTER, new IHoverDrawingHandler()
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
    }
}
