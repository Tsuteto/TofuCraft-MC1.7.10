package tsuteto.tofu.village;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemBottleSoyMilk;
import tsuteto.tofu.item.ItemTcMaterials;

import java.util.Random;

public class TradeHandlerTofuCook implements IVillageTradeHandler
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
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.soybeans, 18 + random.nextInt(4)),
                new ItemStack(Items.emerald, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.tofuMomen, 14 + random.nextInt(4)),
                new ItemStack(Items.emerald, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.barrelEmpty, 6 + random.nextInt(4)),
                new ItemStack(Items.emerald, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.edamame, 14 + random.nextInt(4)),
                new ItemStack(Items.emerald, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.miso, 9 + random.nextInt(4)),
                new ItemStack(Items.emerald, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.materials, 2 + random.nextInt(3), ItemTcMaterials.tofuGem.id),
                new ItemStack(Items.emerald, 1))
        );

        // Sell
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.tofuIshi, 17 + random.nextInt(8)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.tofuFriedPouch, 10 + random.nextInt(4)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.tofuFried, 12 + random.nextInt(4)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.tofuEgg, 26 + random.nextInt(8)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.bottleSoymilk, 8 + random.nextInt(4), ItemBottleSoyMilk.flvApple.id))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.bottleSoymilk, 8 + random.nextInt(4), ItemBottleSoyMilk.flvPumpkin.id))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.bottleSoymilk, 8 + random.nextInt(4), ItemBottleSoyMilk.flvKinako.id))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 3),
                new ItemStack(TcItems.tofuMetal, 2 + random.nextInt(2)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 3),
                new ItemStack(TcItems.defattingPotion, 1))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.tofuDried, 8 + random.nextInt(4)))
        );
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(Items.emerald, 1),
                new ItemStack(TcItems.zundaManju, 9 + random.nextInt(4)))
        );

        // Exchange
        recipeList.addToListWithCheck(new MerchantRecipe(
                new ItemStack(TcItems.materials, 6, ItemTcMaterials.tofuGem.id),
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuDiamondNugget.id))
        );


    }

}
