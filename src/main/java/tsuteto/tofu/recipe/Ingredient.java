package tsuteto.tofu.recipe;

import net.minecraft.item.ItemStack;

abstract public class Ingredient<T>
{
    public final T itemObj;

    public Ingredient(T item)
    {
        this.itemObj = item;
    }

    abstract public boolean matchesWithItemStack(ItemStack input);

    @Override
    abstract public boolean equals(Object obj);

    @Override
    public String toString()
    {
        return String.format("Ingredient(%s)", itemObj.toString());
    }
}
