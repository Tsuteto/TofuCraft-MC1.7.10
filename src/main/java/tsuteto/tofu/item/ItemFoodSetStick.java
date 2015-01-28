package tsuteto.tofu.item;

import com.google.common.collect.Lists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.iteminfo.TcEffectFoodBase;

import java.util.List;

public class ItemFoodSetStick extends ItemFoodSetBase<ItemFoodSetStick.Food>
{
    public static List<Food> foodList = Lists.newArrayList();
    
    public static Food goheimochi
        = new Food(0,  6, 0.8f, false, "goheimochi").setContainer(new ItemStack(Items.stick));

    public ItemFoodSetStick()
    {
        super();
        this.bFull3D = true;
    }
    
    public static class Food extends TcEffectFoodBase<Food>
    {

        Food(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
        {
            super(id, healAmount, saturationModifier, alwaysEdible, name);
            foodList.add(id, this);
        }
    }

    @Override
    public List<Food> getItemList()
    {
        return foodList;
    }
}
