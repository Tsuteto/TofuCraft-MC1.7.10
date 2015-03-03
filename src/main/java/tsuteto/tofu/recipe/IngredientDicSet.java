package tsuteto.tofu.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;

public class IngredientDicSet extends Ingredient<String[]>
{
    public IngredientDicSet(String[] oreName)
    {
        super(oreName);
    }

    @Override
    public boolean matches(ItemStack input)
    {
        for (String oreName : this.itemObj)
        {
            List<ItemStack> dicItems = OreDictionary.getOres(oreName);
            for (ItemStack is : dicItems)
            {
                if (OreDictionary.itemMatches(is, input, false))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> getApplicableItems()
    {
        List<ItemStack> ret = Lists.newArrayList();

        for (String oreName : this.itemObj)
        {
            List<ItemStack> ores = OreDictionary.getOres(oreName);

            for (ItemStack stack : ores)
            {
                if (stack.getItem() != null) ret.add(stack); // Ignore invalid entries
            }
        }
        return ret;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof IngredientDicSet)) return false;

        IngredientDicSet other = (IngredientDicSet)obj;
        return Arrays.equals(itemObj, other.itemObj);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(itemObj);
    }
}
