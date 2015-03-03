package tsuteto.tofu.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class IngredientDic extends Ingredient<String>
{
    public IngredientDic(String oreName)
    {
        super(oreName);
    }

    @Override
    public boolean matches(ItemStack input)
    {
        List<ItemStack> dicItems = OreDictionary.getOres(itemObj);
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
    public List<ItemStack> getApplicableItems()
    {
        List<ItemStack> ores = OreDictionary.getOres(this.itemObj);
        List<ItemStack> ret = Lists.newArrayList();

        for (ItemStack stack : ores)
        {
            if (stack.getItem() != null) ret.add(stack); // Ignore invalid entries
        }

        return ret;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof IngredientDic)) return false;

        IngredientDic other = (IngredientDic)obj;
        return itemObj.equals(other.itemObj);
    }

    @Override
    public int hashCode()
    {
        return itemObj.hashCode();
    }
}
