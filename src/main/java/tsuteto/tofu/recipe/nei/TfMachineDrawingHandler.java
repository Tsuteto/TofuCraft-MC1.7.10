package tsuteto.tofu.recipe.nei;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.gui.GuiScreen;
import tsuteto.tofu.gui.GuiTfMachineBase;
import tsuteto.tofu.tileentity.ContainerDummy;

public class TfMachineDrawingHandler extends GuiTfMachineBase
{
    public int toolTipOffsetX;
    public int toolTipOffsetY;

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {}

    public TfMachineDrawingHandler()
    {
        super(new ContainerDummy());
        this.mc = FMLClientHandler.instance().getClient();
        this.fontRendererObj = this.mc.fontRenderer;
    }

    public void setGuiPosition(int guiLeft, int guiTop)
    {
        this.guiLeft = guiLeft;
        this.guiTop = guiTop;
    }

    public void setParentGui(GuiScreen gui)
    {
        this.width = gui.width;
        this.height = gui.height;
    }

    @Override
    public int getToolTipOffsetX()
    {
        return toolTipOffsetX;
    }

    @Override
    public int getToolTipOffsetY()
    {
        return toolTipOffsetY;
    }
}
