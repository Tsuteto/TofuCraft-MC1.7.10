package tsuteto.tofu.recipe;

import net.minecraft.item.ItemStack;

import java.util.List;

abstract public class Ingredient<T>
{
    public final T itemObj;

    public Ingredient(T item)
    {
        this.itemObj = item;
    }

    abstract public boolean matches(ItemStack input);

    abstract public List<ItemStack> getApplicableItems();

    @Override
    abstract public boolean equals(Object obj);

    abstract public int hashCode();

    @Override
    public String toString()
    {
        return String.format("Ingredient(%s)", itemObj.toString());
    }

}
