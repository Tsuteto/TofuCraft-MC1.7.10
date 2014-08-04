package tsuteto.tofu.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class IngredientDic extends Ingredient<String>
{
    public IngredientDic(String item)
    {
        super(item);
    }

    @Override
    public boolean matchesWithItemStack(ItemStack input)
    {
        List<ItemStack> dicItems = OreDictionary.getOres(this.itemObj);
        for (ItemStack is : dicItems)
        {
            if (OreDictionary.itemMatches(is, input, false))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof IngredientDic)) return false;

        IngredientDic other = (IngredientDic)obj;
        return itemObj.equals(other.itemObj);
    }

    public int hashCode()
    {
        return itemObj.hashCode();
    }
}
