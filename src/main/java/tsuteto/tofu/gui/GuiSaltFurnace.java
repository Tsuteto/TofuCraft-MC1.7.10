package tsuteto.tofu.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.tileentity.ContainerSaltFurnace;
import tsuteto.tofu.tileentity.TileEntitySaltFurnace;
import tsuteto.tofu.util.GuiUtils;

import java.util.Collections;

@SideOnly(Side.CLIENT)
public class GuiSaltFurnace extends GuiMachineBase
{
    private static final ResourceLocation texture = new ResourceLocation("tofucraft", "textures/gui/saltfurnace.png");

    private final TileEntitySaltFurnace furnaceInventory;

    public GuiSaltFurnace(InventoryPlayer par1InventoryPlayer, TileEntitySaltFurnace saltFurnace)
    {
        super(new ContainerSaltFurnace(par1InventoryPlayer, saltFurnace));
        this.furnaceInventory = saltFurnace;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.furnaceInventory.hasCustomInventoryName() ? this.furnaceInventory.getInventoryName() : StatCollector.translateToLocal(this.furnaceInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);

        if (this.isPointInRegion(114, 29, 6, 35, p_73863_1_, p_73863_2_))
        {
            this.drawHoveringText(Collections.singletonList(I18n.format("fluid.tofucraft:nigari")), p_73863_1_, p_73863_2_, fontRendererObj);
        }
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

        // Flame
        if (this.furnaceInventory.isBurning())
        {
            var7 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 23, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        // Progress arrow
        var7 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 46, var6 + 34, 176, 14, var7 + 1, 16);

        // Cauldron
        var7 = this.furnaceInventory.getCauldronStatus();
        if (var7 == 0)
        {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 176, 31, 16, 16);
        }
        else if (var7 == 1)
        {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 192, 31, 16, 16);
        }
        else if (var7 == 2)
        {
            this.drawTexturedModalRect(var5 + 23, var6 + 17, 208, 31, 16, 16);
        }

        // Nigari Gauge
        if (this.furnaceInventory.getNigariTank().getFluid() != null)
        {
            FluidTank fluidTank = this.furnaceInventory.getNigariTank();
            int heightInd = (int) (35 * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
            if (heightInd > 0)
            {
                GuiUtils.drawFluidGuage(var5 + 114, var6 + 29, this.furnaceInventory.getNigariTank(), 6, 35, this);
                //GuiUtils.drawBorderedRect(var5 + 114, var6 + 29 + (35 - heightInd), 6, heightInd, 0xff75bcff, this.zLevel);
            }
        }
    }
}
