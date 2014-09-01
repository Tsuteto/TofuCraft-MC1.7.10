package tsuteto.tofu.item.iteminfo;

import net.minecraft.item.ItemStack;

public class TcFoodBase<T extends TcFoodBase> extends TcItemSetInfo<T>
{
    public int healAmount;
    public float saturationModifier;
    public boolean alwaysEdible;

    public TcFoodBase(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
    {
        super(id, TcItemType.NORMAL, name);
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
        this.alwaysEdible = alwaysEdible;
    }
    
    public T setContainer(ItemStack item)
    {
        this.container = item;
        return (T)this;
    }
}
