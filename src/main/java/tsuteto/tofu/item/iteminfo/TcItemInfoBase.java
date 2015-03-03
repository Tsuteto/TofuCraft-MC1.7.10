package tsuteto.tofu.item.iteminfo;

import net.minecraft.item.ItemStack;

public class TcItemInfoBase<T extends TcItemInfoBase>
{
    public TcItemType type;
    public ItemStack container = null;
    public int liquidColor = 0xffffff;
    public int itemUseDuration = 32;
    public boolean hasEnchantEffect = false;
    public boolean isNonDurabilityTool = false;
    public boolean isCraftingDurabilityTool = false;


    public TcItemInfoBase(TcItemType type)
    {
        this.type = type;
    }

    public void setLiquidColor(int color)
    {
        this.liquidColor = color;
    }

    public T setContainerItem(ItemStack stack)
    {
        this.container = stack;
        return (T)this;
    }

    public ItemStack getContainerItem()
    {
        if (container != null)
        {
            return container.copy();
        }
        else
        {
            return null;
        }
    }

    public boolean hasContainerItem()
    {
        return container != null;
    }

    public T asGlassBottle(int liquidColor)
    {
        this.type = TcItemType.BOTTLE;
        this.liquidColor = liquidColor;
        return (T)this;
    }

    public T setMaxItemUseDuration(int itemUseDuration)
    {
        this.itemUseDuration = itemUseDuration;
        return (T)this;
    }

    public T setEnchantEffect()
    {
        this.hasEnchantEffect = true;
        return (T)this;
    }

    public T setNonDurabilityTool()
    {
        this.isNonDurabilityTool = true;
        return (T)this;
    }

    public T setCraftingDurabilityTool()
    {
        this.isCraftingDurabilityTool = true;
        return (T)this;
    }

    public boolean isCraftingDurabilityTool()
    {
        return this.isCraftingDurabilityTool;
    }
}
