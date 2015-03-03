package tsuteto.tofu.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class IngredientItem extends Ingredient<ItemStack>
{
    public IngredientItem(ItemStack item)
    {
        super(item);
    }

    @Override
    public boolean matches(ItemStack input)
    {
        return OreDictionary.itemMatches(this.itemObj, input, false);
    }

    @Override
    public List<ItemStack> getApplicableItems()
    {
        return Lists.newArrayList(this.itemObj);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof IngredientItem)) return false;
        IngredientItem other = (IngredientItem)obj;
        return this.matches(other.itemObj);
    }

    @Override
    public int hashCode()
    {
        return itemObj.getItem().hashCode();
    }
}
