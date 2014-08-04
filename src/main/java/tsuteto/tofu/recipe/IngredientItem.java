package tsuteto.tofu.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class IngredientItem extends Ingredient<ItemStack>
{
    public IngredientItem(ItemStack item)
    {
        super(item);
    }

    @Override
    public boolean matchesWithItemStack(ItemStack input)
    {
        return OreDictionary.itemMatches(this.itemObj, input, false);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof IngredientItem)) return false;
        IngredientItem other = (IngredientItem)obj;
        return itemObj.isItemEqual(other.itemObj);
    }

    public int hashCode()
    {
        return Item.getIdFromItem(itemObj.getItem()) + (itemObj.getItemDamage() << 15);
    }
}
