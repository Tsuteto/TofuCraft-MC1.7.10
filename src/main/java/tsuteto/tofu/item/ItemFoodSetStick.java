package tsuteto.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.item.ItemFoodSet.Food;

public class ItemFoodSetStick extends ItemFoodSetBase
{
    public static Food[] foodList = new Food[1];
    
    public static TcFoodBase goheimochi
        = new Food(0,  6, 0.8f, false, "goheimochi").setContainer(new ItemStack(Items.stick));

    public ItemFoodSetStick()
    {
        super();
        this.bFull3D = true;
    }
    
    public static class Food extends TcFoodBase
    {

        Food(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
        {
            this(id, healAmount, saturationModifier, alwaysEdible, 32, name);
        }
        
        Food(int id, int healAmount, float saturationModifier, boolean alwaysEdible, int itemUseDuration, String name)
        {
            super(id, healAmount, saturationModifier, alwaysEdible, itemUseDuration, name);
            ItemFoodSetStick.foodList[id] = this;
        }
    }

    @Override
    public TcFoodBase getFood(int dmg)
    {
        return foodList[foodList.length < dmg ? dmg : 0];
    }

    @Override
    public TcFoodBase[] getFoodList()
    {
        return foodList;
    }
}
