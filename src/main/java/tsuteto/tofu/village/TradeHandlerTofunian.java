package tsuteto.tofu.village;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import tsuteto.tofu.entity.EntityTofunian;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

import java.util.Random;

public class TradeHandlerTofunian implements IVillageTradeHandler
{
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        /*
         * MerchantRecipe(ItemStack, ItemStack, ItemStack) : want1, want2, sell
         * MerchantRecipe(ItemStack, ItemStack) : want, sell
         * MerchantRecipe(ItemStack, Item) : want, sell
         */

        // Buy
        int tofu = 3 + random.nextInt(18);

        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.tofuMomen, tofu),
                new ItemStack(TcItems.tofuKinu, tofu))
        );

        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.tofuKinu, tofu),
                new ItemStack(TcItems.tofuMomen, tofu))
        );

        if (villager instanceof EntityTofunian)
        {
            EntityTofunian tofunian = (EntityTofunian)villager;
            ModLog.debug("friendship=%d", tofunian.getFriendship());
            if (tofunian.getFriendship() > 64)
            {
                recipeList.addToListWithCheck(new MerchantRecipe(
                        ItemTcMaterials.tofuGem.getStack(3 + random.nextInt(3)),
                        new ItemStack(TcItems.tofuHoe, 1))
                );
            }
            if (tofunian.getFriendship() > 255)
            {
                recipeList.addToListWithCheck(new MerchantRecipe(
                        ItemTcMaterials.tofuGem.getStack(5 + random.nextInt(5)),
                        new ItemStack(TcItems.tofuDiamond, 1))
                );
            }
        }
    }

}
