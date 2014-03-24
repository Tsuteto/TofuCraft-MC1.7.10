package tsuteto.tofu.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import tsuteto.tofu.block.tileentity.ContainerSaltFurnace;
import tsuteto.tofu.block.tileentity.TileEntitySaltFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSaltFurnace extends GuiContainer
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
        //String s = this.furnaceInventory.func_94042_c() ? this.furnaceInventory.getInvName() : StatCollector.translateToLocal(this.furnaceInventory.getInvName());
        String s = StatCollector.translateToLocal(this.furnaceInventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
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

        if (this.furnaceInventory.isBurning())
        {
            var7 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.furnaceInventory.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);

        var7 = this.furnaceInventory.getCauldronStatus();
        if (var7 == 0)
        {
            this.drawTexturedModalRect(var5 + 56, var6 + 17, 176, 31, 16, 16);
        }
        else if (var7 == 1)
        {
            this.drawTexturedModalRect(var5 + 56, var6 + 17, 192, 31, 16, 16);
        }
        else if (var7 == 2)
        {
            this.drawTexturedModalRect(var5 + 56, var6 + 17, 208, 31, 16, 16);
        }
    }
}
