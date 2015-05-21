package tsuteto.tofu.village;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import tsuteto.tofu.init.TcItems;

import java.util.Random;

public class TradeHandlerFarmer implements IVillageTradeHandler
{
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.sesame, 4 + random.nextInt(4)))
        );
        
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.strawberryJam, 1))
        );

    }

}
