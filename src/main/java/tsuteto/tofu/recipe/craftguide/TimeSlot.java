package tsuteto.tofu.recipe.craftguide;

import com.google.common.collect.Lists;
import net.minecraft.util.StatCollector;
import uristqwerty.CraftGuide.api.ItemFilter;
import uristqwerty.CraftGuide.api.Renderer;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

import java.util.ArrayList;
import java.util.List;

public class TimeSlot implements Slot
{
    public static final int VALUE_UNSPECIFIED = Integer.MIN_VALUE;
    protected int ticks = VALUE_UNSPECIFIED;
    protected final int x, y;
    protected final int width, height;

    public TimeSlot(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getWidth()
    {
        return this.width;
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
        int value = getValue(data[dataIndex]);

        ArrayList<String> tooltip = Lists.newArrayList();
        tooltip.add(StatCollector.translateToLocalFormatted("tofucraft.seconds", value / 20.0D));

        return tooltip;
    }

    @Override
    public boolean matches(ItemFilter filter, Object[] data, int dataIndex, SlotType type)
    {
        return false;
    }

    public int getValue(Object data)
    {
        if(data instanceof Object[] && ((Object[])data)[0] instanceof Integer)
        {
            return (Integer)((Object[])data)[0];
        }
        else if(ticks != VALUE_UNSPECIFIED)
        {
            return ticks;
        }
        else if(data instanceof Integer)
        {
            return (Integer)data;
        }
        else
        {
            return VALUE_UNSPECIFIED;
        }
    }

    public TimeSlot setTicks(int ticks)
    {
        this.ticks = ticks;
        return this;
    }
}
