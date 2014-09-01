package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.iteminfo.TcEffectFoodBase;

public class ItemFoodSetStick extends ItemFoodSetBase<ItemFoodSetStick.Food>
{
    public static Food[] foodList = new Food[1];
    
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
            foodList[id] = this;
        }
    }

    @Override
    public Food[] getItemList()
    {
        return foodList;
    }
}
