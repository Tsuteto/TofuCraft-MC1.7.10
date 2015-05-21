package tsuteto.tofu.init.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.*;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderFood extends TcItemLoader
{
    public void load()
    {
        // Salty Melon
        saltyMelon = $("saltyMelon", new ItemTcFood(3, 0.5F, false))
                .register();

        // Tasty Stew
        tastyStew = $("tastyStew", new ItemSoupBase(20, 1.0F, false))
                .register();

        // Tasty Beef Stew
        tastyBeefStew = $("tastyBeefStew", new ItemSoupBase(20, 1.0F, false))
                .register();

        // Tofu Cake
        tofuCake = $("tofuCake", new ItemTcReed(TcBlocks.tofuCake))
                .register()
        		.setMaxStackSize(1)
                .setCreativeTab(TcCreativeTabs.FOOD);

        yudofu = $("yudofu", new ItemSoupBase(3, 0.3F, false))
                .register();

        tttBurger = $("tttBurger", new ItemTcFood(8, 0.4F, false))
                .register();

        misoSoup = $("misoSoup", new ItemSoupBase(4, 0.6F, false))
                .register();

        misoDengaku = $("misoDengaku", new ItemFoodContainer(5, 0.6F, true))
                .register()
                .setContainedItem(new ItemStack(Items.stick))
                .setFull3D();

        edamameBoiled = $("edamameBoiled", new ItemTcFood(1, 0.25F, false))
                .register()
                .setAlwaysEdible();

        zundaManju = $("zundaManju", new ItemTcFood(6, 0.8F, true))
                .register();

        nikujaga = $("nikujaga", new ItemSoupBase(10, 0.7F, false))
                .register();

        agedashiTofu = $("agedashiTofu", new ItemSoupBase(6, 0.6F, false))
                .register();

        kinakoManju = $("kinakoManju", new ItemTcFood(4, 0.5F, true))
                .register();

        bottleSoymilk = $("bottleSoymilk", new ItemBottleSoyMilk())
                .register();

        fukumeni = $("fukumeni", new ItemTcFood(3, 1.0f, true))
                .register();

        koyadofuStew = $("koyadofuStew", new ItemSoupBase(8, 0.8f, false))
                .register();

        apricot = $("apricot", new ItemFoodContainer(3, 0.3f, false, new ItemStack(apricotSeed)))
                .register();

        okaraStick = $("okaraStick", new ItemTcFood(5, 0.6f, false))
                .register()
                .setEatingDuration(8);

        riceNatto = $("riceNatto", new ItemTcFood(8, 0.8f, false))
                .register();

        riceNattoLeek = $("riceNattoLeek", new ItemTcFood(9, 0.8f, false))
                .register();

        yuba = $("yuba", new ItemYuba(1, 1.0f, false))
                .register()
                .setAlwaysEdible();

        /*
         * === Food Set ===
         */
        foodSet = $("foodSet", new ItemFoodSet()).register();
        foodSetStick = $("foodSetStick", new ItemFoodSetStick()).register();
    }
}
