package tsuteto.tofu.gui.guiparts;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import tsuteto.tofu.gui.GuiTfMachineBase;

abstract public class GuiPartGaugeBase<T extends GuiPartGaugeBase> extends GuiPart<T>
{
    public TfMachineGuiParts indicator;
    public TfMachineGuiParts itemDisplay = null;

    public int xIndicatorOffset;
    public int yIndicatorOffset;

    public int frameColor = 0xffffff;
    public int indicatorColor = 0xffffff;

    public double percentage = 0D;

    public ItemStack itemStack = null;
    public FluidStack fluidStack = null;
    public HoverTextPosition itemTipPosition = HoverTextPosition.UPPER_CENTER;

    public GuiPartGaugeBase(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator)
    {
        this(x, y, frame, indicator, (frame.xSize - indicator.xSize) / 2, (frame.ySize - indicator.ySize) / 2);
    }

    public GuiPartGaugeBase(int x, int y, TfMachineGuiParts frame, TfMachineGuiParts indicator, int xIndicatorOffset, int yIndicatorOffset)
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
        this.percentage = Math.min(1.0D, Math.max(0.0D, val));
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
                    gui.drawTfHoveringText(fluidStack.getFluid().getLocalizedName(fluidStack), px, py, itemTipPosition);
                }
                else
                {
                    gui.drawTfHoveringText(StatCollector.translateToLocal("tofucraft.empty"), px, py, itemTipPosition);
                }
            }
        }
    }

    abstract protected void drawFrame(GuiTfMachineBase gui);
    abstract protected void drawIndicator(GuiTfMachineBase gui);
}
