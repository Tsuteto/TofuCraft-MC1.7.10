package tsuteto.tofu.recipe.craftguide;

import com.google.common.collect.Lists;
import net.minecraft.util.StatCollector;
import uristqwerty.CraftGuide.api.ItemFilter;
import uristqwerty.CraftGuide.api.Renderer;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

import java.util.ArrayList;
import java.util.List;

public class TfAmountSlot implements Slot
{
    public static final double VALUE_UNSPECIFIED = Double.MIN_VALUE;
    protected double tfAmount = VALUE_UNSPECIFIED;
    protected final int x, y;
    protected SlotType slotType = SlotType.INPUT_SLOT;

    public TfAmountSlot(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getHeight()
    {
        return 15;
    }

    public int getWidth()
    {
        return 15;
    }

    @Override
    public void draw(Renderer renderer, int recipeX, int recipeY, Object[] data, int dataIndex, boolean isMouseOver)
    {
        //renderer.renderRect(recipeX + x, recipeY + y, width, height, image);
    }

    @Override
    public ItemFilter getClickedFilter(int x, int y, Object[] data, int dataIndex)
    {
        return null;
    }

    @Override
    public boolean isPointInBounds(int x, int y, Object[] data, int dataIndex)
    {
        return x >= this.x && x < this.x + getWidth()
                && y >= this.y && y < this.y + getHeight();
    }

    @Override
    public List<String> getTooltip(int x, int y, Object[] data, int dataIndex)
    {
        double value = getTfAmount(data[dataIndex]);

        ArrayList<String> tooltip = Lists.newArrayList();

        String msgKey = "";
        if (this.slotType == SlotType.INPUT_SLOT)
        {
            msgKey = "tofucraft.tfInput";
        }
        else if (this.slotType == SlotType.OUTPUT_SLOT)
        {
            msgKey = "tofucraft.tfProduced";
        }
        tooltip.add(StatCollector.translateToLocal(msgKey));

        if(value != VALUE_UNSPECIFIED)
        {
            tooltip.add(String.format("%.0f tf", value));
        }

        return tooltip;
    }

    @Override
    public boolean matches(ItemFilter filter, Object[] data, int dataIndex, SlotType type)
    {
        return false;
    }

    public double getTfAmount(Object data)
    {
        if(data instanceof Object[] && ((Object[])data)[0] instanceof Double)
        {
            return (Integer)((Object[])data)[0];
        }
        else if(tfAmount != VALUE_UNSPECIFIED)
        {
            return tfAmount;
        }
        else if(data instanceof Double)
        {
            return (Double)data;
        }
        else
        {
            return VALUE_UNSPECIFIED;
        }
    }

    public TfAmountSlot setTfAmount(double amount)
    {
        tfAmount = amount;
        return this;
    }

    public TfAmountSlot setSlotType(SlotType slotType)
    {
        this.slotType = slotType;
        return this;
    }
}
