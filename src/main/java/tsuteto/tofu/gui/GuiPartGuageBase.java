package tsuteto.tofu.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

abstract public class GuiPartGuageBase<T extends GuiPartGuageBase> extends GuiPart<T>
{
    public TfMachineGuiParts indicator;
    public TfMachineGuiParts itemDisplay = null;

    public int xIndicatorOffset;
    public int yIndicatorOffset;

    public int frameColor = 0xffffffff;
    public int indicatorColor = 0xffffffff;

    public double percentage = 0D;

    public ItemStack itemStack = null;
    public FluidStack fluidStack = null;

    public GuiPartGuageBase(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator)
    {
        this(x, y, frame, indicator, (frame.xSize - indicator.xSize) / 2, (frame.ySize - indicator.ySize) / 2);
    }

    public GuiPartGuageBase(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator, int xIndicatorOffset, int yIndicatorOffset)
    {
        super(x, y, frame);
        this.indicator = indicator;
        this.xIndicatorOffset = xIndicatorOffset;
        this.yIndicatorOffset = yIndicatorOffset;
    }

    public T setIndicatorColor(int color)
    {
        this.indicatorColor = color;
        return (T)this;
    }

    public T setFrameColor(int color)
    {
        this.frameColor = color;
        return (T)this;
    }

    public T setPercentage(double val)
    {
        this.percentage = val;
        return (T)this;
    }

    public T setItemDisplay(TfMachineGuiParts part)
    {
        this.itemDisplay = part;
        return (T)this;
    }

    public T setItemStack(ItemStack itemstack)
    {
        this.itemStack = itemstack;
        return (T)this;
    }

    public T setFluidStack(FluidStack fluidstack)
    {
        this.fluidStack = fluidstack;
        return (T)this;
    }

    @Override
    public void draw(GuiTfMachineBase gui)
    {
        drawFrame(gui);
        drawIndicator(gui);

        if (itemDisplay != null)
        {
            int ix = x + (part.xSize - itemDisplay.xSize) / 2;
            int iy = y + part.ySize - 3;
            gui.drawGuiPart(ix, iy, itemDisplay);
        }
    }

    public void drawItem(GuiTfMachineBase gui)
    {
        if (itemDisplay != null && itemStack != null)
        {
            int ix = gui.getGuiLeft() + x + (part.xSize - itemDisplay.xSize) / 2;
            int iy = gui.getGuiTop() + y + part.ySize - 3 + 1;
            gui.drawItemStack(itemStack, ix, iy);
        }
    }

    @Override
    public void showInfoTip(GuiTfMachineBase gui, int px, int py, GuiTfMachineBase.IHoverDrawingHandler handler)
    {
        super.showInfoTip(gui, px, py, handler);

        if (itemDisplay != null)
        {
            int ix = x + (part.xSize - itemDisplay.xSize) / 2;
            int iy = y + part.ySize - 3 + 1;

            if (gui.isPointInRegion(ix, iy, 16, 16, px, py))
            {
                if (fluidStack != null)
                {
                    gui.drawTfHoveringText(fluidStack.getFluid().getLocalizedName(), px, py);
                }
                else
                {
                    gui.drawTfHoveringText(StatCollector.translateToLocal("tofucraft.empty"), px, py);
                }
            }
        }
    }

    abstract protected void drawFrame(GuiTfMachineBase gui);
    abstract protected void drawIndicator(GuiTfMachineBase gui);
}
