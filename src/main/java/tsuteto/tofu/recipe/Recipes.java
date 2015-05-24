package tsuteto.tofu.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tsuteto.tofu.api.recipe.TcOreDic;
import tsuteto.tofu.api.recipe.TfReformerRecipe;
import tsuteto.tofu.api.recipe.TfReformerRecipeRegistry;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcEntity;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.*;
import tsuteto.tofu.tileentity.TileEntityTfReformer;
import tsuteto.tofu.util.ItemUtils;

import java.util.Map;

public class Recipes
{
    private static final int DMG_WILDCARD = OreDictionary.WILDCARD_VALUE;
    private static final String oredic_cookingRice = "cookingRice";
    private static final String oredic_cookedMochi = "cookedMochi";
    private static final String oredic_cropStraw = "cropStraw";
    private static final String oredic_foodTea = "foodTea";

    public static void unifyOreDicItems()
    {
        ItemUtils.integrateOreItems("salt", "itemSalt", "Salt");
        ItemUtils.integrateOreItems("tofuMomen", "tofu", "Tofu", "itemTofu");
        ItemUtils.integrateOreItems("blockTofuMomen", "blockTofu");
        ItemUtils.integrateOreItems("rollingPin", "itemRollingPin");
        ItemUtils.integrateOreItems("cookingRice", "cookedRice", "riceCooked", "itemRiceCooked", "wheatRice");
    }

    public static void register()
    {
        /*
         * Smelting
         */
        GameRegistry.addSmelting(TcItems.tofuKinu, new ItemStack(TcItems.tofuGrilled), 0.2f);
        GameRegistry.addSmelting(TcItems.tofuMomen, new ItemStack(TcItems.tofuGrilled), 0.2f);
        GameRegistry.addSmelting(TcBlocks.tofuKinu, new ItemStack(TcBlocks.tofuGrilled), 0.8f);
        GameRegistry.addSmelting(TcBlocks.tofuMomen, new ItemStack(TcBlocks.tofuGrilled), 0.8f);
        GameRegistry.addSmelting(TcItems.edamame, new ItemStack(TcItems.edamameBoiled, 12), 0.5f);
        GameRegistry.addSmelting(TcItems.soybeans, new ItemStack(TcItems.soybeansParched), 0.2f);
        GameRegistry.addSmelting(TcItems.starchRaw, new ItemStack(TcItems.starch), 0.5f);
        GameRegistry.addSmelting(TcBlocks.tcLog, new ItemStack(Items.coal, 1, 1), 0.5f);
        GameRegistry.addSmelting(ItemGelatin.gelatinRaw.getStack(), ItemGelatin.gelatin.getStack(), 0.5f);
        GameRegistry.addSmelting(ItemTcMaterials.tofuHamburgRaw.getStack(), ItemFoodSet.tofuHamburg.getStack(), 0.8f);
        GameRegistry.addSmelting(ItemFoodSet.tofufishRow.getStack(), ItemFoodSet.tofufishCooked.getStack(), 1.5f);
        GameRegistry.addSmelting(TcItems.tofuIshi, ItemFoodSet.tofuSteak.getStack(), 0.5f);

        /*
         * Crafting
         */

        // Soymilk

        addSharedRecipe(new ItemStack(TcItems.bucketSoymilk),
                "S",
                "B",
                'S', TcOreDic.soybeans,
                'B', Items.bucket
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                new ItemStack(TcItems.bucketSoymilk),
                40)
        );

        // Soymilk and Okara
        addSharedRecipe(new ItemStack(TcItems.bucketSoymilk),
                "S",
                "F",
                "B",
                'S', TcOreDic.soybeans,
                'F', TcOreDic.filterCloth,
                'B', Items.bucket
        );

        // Hell Soymilk
        addSharedRecipe(new ItemStack(TcItems.bucketSoymilkHell),
                "S",
                "B",
                'S', TcOreDic.soybeansHell,
                'B', Items.bucket
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                new ItemStack(TcItems.bucketSoymilkHell),
                600)
                .addIngredients(TcOreDic.activatedHellTofu, true)
        );

        // Tofu Blocks
        addSharedRecipe(new ItemStack(TcBlocks.tofuMomen, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuKinu
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuMomen, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMomen
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuIshi, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuIshi
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuMetal, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMetal
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuGrilled, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuGrilled
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuDried, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuDried
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuFriedPouch, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuFriedPouch
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuFried, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuFried
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuEgg, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuEgg
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuAnnin, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuAnnin
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuSesame, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuSesame
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuZunda, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuZunda
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuStrawberry, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuStrawberry
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuHell, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuHell
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuGlow, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuGlow
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuMiso, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMiso
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuDiamond, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuDiamond
        );

        addSharedRecipe(new ItemStack(TcBlocks.tofuMinced, 1),
                "TT",
                "TT",
                'T', TcOreDic.tofuMinced
        );

        // Minced Tofu
        addShapelessSharedRecipe(ItemFoodSet.tofuMinced.getStack(4),
                TcOreDic.blockTofuMomen,
                TcOreDic.rollingPin);
        addShapelessSharedRecipe(ItemFoodSet.tofuMinced.getStack(1),
                TcOreDic.tofuMomen,
                TcOreDic.rollingPin);

        // Fried Tofu Pouch
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFriedPouch),
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                TcOreDic.soyOil
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFriedPouch),
                TcOreDic.tofuMomen,
                TcOreDic.starch,
                TcOreDic.soyOil
        );

        // Fried Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFried),
                TcOreDic.tofuKinu,
                TcOreDic.soyOil
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.tofuFried),
                TcOreDic.tofuMomen,
                TcOreDic.soyOil
        );

        // Egg Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuEgg, 4),
                Items.egg,
                TcOreDic.dashi
        );

        // Salt Furnace
        GameRegistry.addRecipe(new ItemStack(TcBlocks.saltFurnaceIdle),
                "@ @",
                "@ @",
                "@@@",
                '@', Blocks.cobblestone
        );

        // Golden Salt
        addShapelessSharedRecipe(new ItemStack(TcItems.goldenSalt),
                TcOreDic.salt,
                Items.gold_nugget,
                Items.gold_nugget,
                Items.gold_nugget
        );

        // Salty Melon
        addShapelessSharedRecipe(new ItemStack(TcItems.saltyMelon),
                TcOreDic.salt,
                Items.melon
        );

        // Tasty Chicken Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyStew),
                Blocks.brown_mushroom,
                Blocks.red_mushroom,
                Items.cooked_chicken,
                TcOreDic.salt,
                Items.milk_bucket,
                Items.wheat,
                Items.bowl
        );

        // Tasty Pork Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyStew),
                Blocks.brown_mushroom,
                Blocks.red_mushroom,
                Items.cooked_porkchop,
                TcOreDic.salt,
                Items.milk_bucket,
                Items.wheat,
                Items.bowl
        );

        // Tasty Beef Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.tastyBeefStew),
                Blocks.brown_mushroom,
                Blocks.red_mushroom,
                Items.cooked_beef,
                TcOreDic.salt,
                Items.milk_bucket,
                Items.wheat,
                Items.bowl
        );

        // Tofu Cake
        addSharedRecipe(new ItemStack(TcItems.tofuCake),
                "TTT",
                "SES",
                "WWW",
                'T', TcOreDic.tofuKinu,
                'S', Items.sugar,
                'E', Items.egg,
                'W', Items.wheat);

        // Yudofu
        addShapelessSharedRecipe(new ItemStack(TcItems.yudofu),
                TcOreDic.tofuKinu,
                Items.potionitem, // Water bottle
                Items.bowl
        );
        addShapelessSharedRecipe(new ItemStack(TcItems.yudofu),
                TcOreDic.tofuMomen,
                Items.potionitem, // Water bottle
                Items.bowl
        );

        // TTT Burger
        addSharedRecipe(new ItemStack(TcItems.tttBurger),
                " B ",
                "TTT",
                " B ",
                'B', Items.bread,
                'T', TcOreDic.tofuFriedPouch
        );

        // Kouji Base
        addShapelessSharedRecipe(new ItemStack(TcItems.koujiBase),
                Items.wheat,
                TcOreDic.soybeans
        );

        // Morijio
        addSharedRecipe(new ItemStack(TcItems.morijio, 4),
                "D",
                "S",
                "B",
                'D', Items.diamond,
                'S', TcOreDic.salt,
                'B', Items.bowl
        );

        // Rappa
        GameRegistry.addRecipe(new ItemStack(TcItems.bugle),
                "I  ",
                "III",
                'I', Items.iron_ingot
        );

        // Miso Soup
        addShapelessSharedRecipe(new ItemStack(TcItems.misoSoup),
                TcOreDic.miso,
                TcOreDic.tofuKinu,
                TcOreDic.dashi,
                Items.bowl
        );

        // Miso Soup
        addShapelessSharedRecipe(new ItemStack(TcItems.misoSoup),
                TcOreDic.miso,
                TcOreDic.tofuMomen,
                TcOreDic.dashi,
                Items.bowl
        );

        // Miso Dengaku
        addSharedRecipe(new ItemStack(TcItems.misoDengaku),
                "M",
                "T",
                "|",
                'M', TcOreDic.miso,
                'T', TcOreDic.tofuMomen,
                '|', Items.stick
        );

        // Zunda
        addSharedRecipe(new ItemStack(TcItems.zunda),
                "EEE",
                "ESE",
                "EEE",
                'E', TcOreDic.edamameBoiled,
                'S', Items.sugar
        );

        // Zunda Manju
        addSharedRecipe(new ItemStack(TcItems.zundaManju, 2),
                " Z ",
                "WWW",
                'Z', TcOreDic.zunda,
                'W', Items.wheat
        );

        // Kinako Manju
        addSharedRecipe(new ItemStack(TcItems.kinakoManju, 2),
                " K ",
                "WWW",
                'K', TcOreDic.kinako,
                'W', Items.wheat
        );

        // Barrel
        GameRegistry.addRecipe(new ItemStack(TcItems.barrelEmpty),
                "W W",
                "===",
                "WWW",
                'W', Blocks.planks,
                '=', Items.reeds);

        // Miso Barrel
        addSharedRecipe(new ItemStack(TcItems.barrelMiso),
                "SSS",
                "MMM",
                " B ",
                'S', TcOreDic.salt,
                'M', TcOreDic.kouji,
                'B', TcItems.barrelEmpty);

        // Nikujaga
        addShapelessSharedRecipe(new ItemStack(TcItems.nikujaga),
                Items.bowl,
                Items.cooked_beef,
                Items.potato,
                Items.carrot,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                Items.sugar
        );

        addShapelessSharedRecipe(new ItemStack(TcItems.nikujaga),
                Items.bowl,
                Items.cooked_porkchop,
                Items.potato,
                Items.carrot,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                Items.sugar
        );

        // Soy Sauce Bucket
        // This is impossible because a soy sauce bottle always takes 1 point of damage when crafting

        // Soy Sauce Bottle
        addShapelessSharedRecipe(new ItemStack(TcItems.bottleSoySauce, 1, 0),
                Items.glass_bottle,
                TcOreDic.bucketSoySauce
        );

        // Kinako
        addShapelessSharedRecipe(new ItemStack(TcItems.kinako),
                TcOreDic.soybeansParched,
                Items.sugar
        );

        // Kinako manju
        addSharedRecipe(new ItemStack(TcItems.kinakoManju, 2),
                " K ",
                "BBB",
                'K', TcOreDic.kinako,
                'B', Items.wheat
        );

        // Agedashi Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.agedashiTofu),
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.tofuFriedPouch,
                Items.bowl
        );

        // Soy Milk Bottle (Plain)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPlain),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPlain),
                20)
        );

        // Soy Milk Bottle (Kinako)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvKinako),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                TcOreDic.kinako
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvKinako),
                20)
                .addIngredients(TcOreDic.kinako, false)
        );

        // Soy Milk Bottle (Cocoa)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvCocoa),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                new ItemStack(Items.dye, 1, 3),
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvCocoa),
                20)
                .addIngredients(new ItemStack(Items.dye, 1, 3), false)
                .addIngredients(new ItemStack(Items.sugar), false)
        );

        // Soy Milk Bottle (Zunda)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvZunda),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                TcOreDic.zunda
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvZunda),
                20)
                .addIngredients(TcOreDic.zunda, false)
        );

        // Soy Milk Bottle (Apple)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvApple),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                Items.apple,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvApple),
                        20)
                        .addIngredients(Items.apple, false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Pumpkin)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPumpkin),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                Blocks.pumpkin,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPumpkin),
                        20)
                        .addIngredients(Blocks.pumpkin, false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Ramune)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvRamune),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                new ItemStack(Items.dye, 1, 12),
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvRamune),
                        20)
                        .addIngredients(new ItemStack(Items.dye, 1, 12), false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Strawberry)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvStrawberry),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                TcOreDic.strawberryJam,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvStrawberry),
                        20)
                        .addIngredients(TcOreDic.strawberryJam, false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Annin)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvAnnin),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                TcOreDic.kyoninso,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvAnnin),
                        20)
                        .addIngredients(TcOreDic.kyoninso, false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Pudding)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPudding),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                Items.egg,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPudding),
                        20)
                        .addIngredients(Items.egg, false)
                        .addIngredients(Items.sugar, false)
        );

        // Soy Milk Bottle (Tea)
        addShapelessSharedRecipe(TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvTea),
                Items.glass_bottle,
                TcOreDic.bucketSoymilk,
                oredic_foodTea,
                Items.sugar
        );

        TfReformerRecipeRegistry.register(new TfReformerRecipe(
                        TcItems.bottleSoymilk.getItemStack(ItemBottleSoyMilk.flvPudding),
                        20)
                        .addIngredients(Items.egg, false)
                        .addIngredients(Items.sugar, false)
        );

        // Dashi
        GameRegistry.addShapelessRecipe(new ItemStack(TcItems.dashi, 1, 0),
                Items.glass_bottle,
                Items.water_bucket,
                Items.cooked_fished
        );

        // Soy Oil
        addShapelessSharedRecipe(new ItemStack(TcItems.soyOil),
                TcOreDic.defattingPotion,
                Items.glass_bottle,
                TcOreDic.soybeans
        );

        // Koya Tofu fukumeni
        addShapelessSharedRecipe(new ItemStack(TcItems.fukumeni, 8),
                TcOreDic.tofuDried,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        // Koya Tofu Stew
        addShapelessSharedRecipe(new ItemStack(TcItems.koyadofuStew),
                TcOreDic.tofuDried,
                TcOreDic.dashi,
                Blocks.brown_mushroom,
                TcOreDic.bottleSoySauce,
                Items.bowl
        );

        // Natto Bed Blocks
        addSharedRecipe(new ItemStack(TcBlocks.nattoBed),
                "BBB",
                "BBB",
                "WWW",
                'B', TcOreDic.soybeans,
                'W', Items.wheat
        );

        addSharedRecipe(new ItemStack(TcBlocks.nattoBed),
                "BBB",
                "BBB",
                "WWW",
                'B', TcOreDic.soybeans,
                'W', oredic_cropStraw
        );

        // Natto Hiyayakko
        addShapelessSharedRecipe(new ItemStack(TcItems.nattoHiyayakko),
                TcOreDic.tofuKinu,
                TcOreDic.natto,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                Items.bowl
        );

        addShapelessSharedRecipe(ItemFoodSet.nattoHiyayakkoGl.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.natto,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Minced Potato
        GameRegistry.addShapelessRecipe(new ItemStack(TcItems.mincedPotato),
                Items.potato
        );

        // Raw Starch
        addShapelessSharedRecipe(new ItemStack(TcItems.starchRaw),
                TcOreDic.mincedPotato,
                TcOreDic.filterCloth
        );

        // Apricot Seed
        addShapelessSharedRecipe(new ItemStack(TcItems.apricotSeed),
                TcOreDic.apricot
        );

        // Kyoninso
        addShapelessSharedRecipe(new ItemStack(TcItems.kyoninso),
                TcOreDic.apricotSeed
        );

        // Annin Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuAnnin, 4),
                TcOreDic.gelatin,
                Items.sugar,
                Items.milk_bucket,
                TcOreDic.kyoninso
        );

        // Sesame Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuSesame, 4),
                TcOreDic.starch,
                TcOreDic.sesame,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        // Zunda Tofu
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuZunda, 4),
                TcOreDic.starch,
                TcOreDic.zunda,
                TcOreDic.dashi,
                TcOreDic.salt
        );

        // Log -> planks
//        addSharedRecipe(new ItemStack(Blocks.planks, 4, 0),
//                "L",
//                Character.valueOf('L'), TcOreDic.logApricot
//        );

        GameRegistry.addRecipe(new ItemStack(Blocks.planks, 4, 0),
                "L",
                'L', new ItemStack(TcBlocks.tcLog, 1, 0)
        );

        // Filter Cloth
        GameRegistry.addRecipe(new ItemStack(TcItems.filterCloth, 32),
                "WWW",
                'W', new ItemStack(Blocks.wool, 1, DMG_WILDCARD)
        );

        // Okara Stick
        addSharedRecipe(new ItemStack(TcItems.okaraStick, 3),
                "O",
                "E",
                "W",
                'O', TcOreDic.okara,
                'E', Items.egg,
                'W', Items.wheat
        );

        // Zundama
        addSharedRecipe(new ItemStack(TcItems.zundama),
                " Z ",
                "ZGZ",
                " Z ",
                'Z', TcOreDic.zunda,
                'G', Items.glowstone_dust
        );

        // Zunda Bow
        GameRegistry.addRecipe(new ItemStack(TcItems.zundaBow),
                "O O",
                " B ",
                "O O",
                'O', TcItems.zundama,
                'B', Items.bow
        );

        // Zunda Arrow
        addShapelessSharedRecipe(new ItemStack(TcItems.zundaArrow),
                TcOreDic.zunda,
                Items.arrow
        );

        // Gelatin Base
        GameRegistry.addShapelessRecipe(ItemGelatin.gelatinRaw.getStack(),
                Items.leather,
                Items.bone
        );

        // Fukumame (Initial)
        addSharedRecipe(new ItemStack(TcItems.fukumame),
                "sss",
                "sss",
                " B ",
                's', TcOreDic.soybeansParched,
                'B', Items.bowl
        );

        // Fukumame (Refill)
        addSharedRecipe(new ItemStack(TcItems.fukumame),
                "sss",
                "sss",
                " M ",
                's', TcOreDic.soybeansParched,
                'M', new ItemStack(TcItems.fukumame, 1, DMG_WILDCARD)
        );

        // Tofu Chikuwa
        addShapelessSharedRecipe(ItemFoodSet.tofuChikuwa.getStack(),
                TcOreDic.tofuMomen,
                Items.cooked_fished
        );
        addShapelessSharedRecipe(ItemFoodSet.tofuChikuwa.getStack(),
                TcOreDic.tofufishCooked
        );

        // Oage
        addShapelessSharedRecipe(ItemFoodSet.oage.getStack(4),
                new ItemStack(TcBlocks.tofuSingleSlab1, 1, 1),
                TcOreDic.soyOil
        );

        // Natto -> Natto Block
        addSharedRecipe(new ItemStack(TcBlocks.natto, 1),
                "NNN",
                "NNN",
                "NNN",
                'N', TcOreDic.natto
        );

        // Natto Block -> Items
        addSharedRecipe(new ItemStack(TcItems.natto, 9),
                "N",
                'N', TcOreDic.blockNatto
        );

        // Salt -> Salt Block
        addSharedRecipe(new ItemStack(TcBlocks.salt, 1),
                "SSS",
                "SSS",
                "SSS",
                'S', TcOreDic.salt
        );

        // Salt Block -> Items
        addSharedRecipe(new ItemStack(TcItems.salt, 9),
                "S",
                'S', TcOreDic.blockSalt
        );

        // Moyashiitame
        addShapelessSharedRecipe(ItemFoodSet.sproutSaute.getStack(),
                TcOreDic.soyOil,
                TcOreDic.bottleSoySauce,
                TcOreDic.salt,
                TcOreDic.sprouts,
                Items.bowl
        );

        // Moyashi no ohitashi
        addShapelessSharedRecipe(ItemFoodSet.moyashiOhitashi.getStack(),
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                TcOreDic.sprouts,
                Items.bowl
        );

        // Goheimochi
        addSharedRecipe(ItemFoodSetStick.goheimochi.getStack(),
                "M",
                "O",
                "S",
                'M', TcOreDic.miso,
                'O', TcOreDic.onigiri,
                'S', Items.stick
        );

        // Tofu Scoop
        GameRegistry.addRecipe(new ItemStack(TcItems.tofuScoop),
                "N",
                "S",
                "S",
                'N', Blocks.iron_bars,
                'S', Items.stick
        );

        // Onigiri
        GameRegistry.addRecipe(ItemFoodSet.onigiri.getStack(2),
                " W ",
                "WWW",
                'W', new ItemStack(Items.wheat)
        );

        // Salty Onigiri
        addShapelessSharedRecipe(ItemFoodSet.onigiriSalt.getStack(),
                TcOreDic.salt,
                TcOreDic.onigiri
        );

        // Miso yakionigiri
        addShapelessSharedRecipe(ItemFoodSet.yakionigiriMiso.getStack(),
                TcOreDic.miso,
                TcOreDic.onigiri
        );

        // Shoyu yakionigiri
        addShapelessSharedRecipe(ItemFoodSet.yakionigiriShoyu.getStack(),
                TcOreDic.bottleSoySauce,
                TcOreDic.onigiri
        );

        // Mabodofu (momen)
        addShapelessSharedRecipe(ItemFoodSet.mabodofu.getStack(),
                TcOreDic.tofuMomen,
                TcOreDic.starch,
                Items.porkchop,
                TcOreDic.doubanjiang,
                TcOreDic.bottleSoySauce,
                Items.bowl
        );

        // Mabodofu (kinu)
        addShapelessSharedRecipe(ItemFoodSet.mabodofu.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                Items.porkchop,
                TcOreDic.doubanjiang,
                TcOreDic.bottleSoySauce,
                Items.bowl
        );

        // Tofu Creeper Egg
        addSharedRecipe(new ItemStack(Items.spawn_egg, 1, TcEntity.entityIdTofuCreeper),
                " G ",
                "GTG",
                " G ",
                'G', TcOreDic.tofuGem,
                'T', Blocks.tnt
        );

        // Tofu Diamond (Nuggets <-> piece)
        addSharedRecipe(new ItemStack(TcItems.tofuDiamond),
                "NNN",
                "NNN",
                "NNN",
                'N', TcOreDic.tofuDiamondNugget);

        addSharedRecipe(ItemTcMaterials.tofuDiamondNugget.getStack(9),
                "D",
                'D', TcOreDic.tofuDiamond);

        // Tofu Slime Radar
        addSharedRecipe(new ItemStack(TcItems.tofuRadar, 1, TcItems.tofuRadar.getMaxDamage() + 1),
                "SR",
                "TT",
                'T', TcOreDic.tofuMetal,
                'S', Items.slime_ball,
                'R', Items.redstone
        );

        // Tofu Slime Radar (Charge)
        addShapelessSharedRecipe(new ItemStack(TcItems.tofuRadar, 1, 0),
                new ItemStack(TcItems.tofuRadar, 1, DMG_WILDCARD),
                TcOreDic.tofuGem
        );

        // Negi Hiyayakko
        addShapelessSharedRecipe(ItemFoodSet.hiyayakko.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                Items.bowl
        );

        addShapelessSharedRecipe(ItemFoodSet.hiyayakkoGl.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Natto rice
        addShapelessSharedRecipe(new ItemStack(TcItems.riceNatto),
                TcOreDic.bottleSoySauce,
                TcOreDic.natto,
                oredic_cookingRice
        );

        // Natto rice with leek
        addShapelessSharedRecipe(new ItemStack(TcItems.riceNattoLeek),
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                TcOreDic.natto,
                oredic_cookingRice
        );

        // Tofu Rice
        addShapelessSharedRecipe(ItemFoodSet.riceTofu.getStack(),
                TcOreDic.tofuKinu,
                TcOreDic.leek,
                TcOreDic.bottleSoySauce,
                oredic_cookingRice
        );

        // Tofu Hamburg (Raw)
        addSharedRecipe(ItemTcMaterials.tofuHamburgRaw.getStack(3),
                "TTT",
                "MPB",
                "TTT",
                'T', TcOreDic.tofuMomen,
                'P', Items.porkchop,
                'M', TcOreDic.miso,
                'B', Items.bread
        );

        // Tofu Hamburg Tempra
        addShapelessSharedRecipe(ItemFoodSet.tofuHamburgTempra.getStack(),
                TcOreDic.tofuHamburgRaw,
                TcOreDic.soyOil,
                Items.wheat);

        // Tofu Hamburg Ankake Tempra
        addShapelessSharedRecipe(ItemFoodSet.tofuHamburgTempraAnkake.getStack(),
                ItemFoodSet.tofuHamburgTempra.getStack(),
                TcOreDic.starch,
                TcOreDic.bottleSoySauce,
                TcOreDic.soupStock);

        // Tofu Cookie
        addSharedRecipe(ItemFoodSet.tofuCookie.getStack(8),
                "WTW",
                'T', TcOreDic.tofuMomen,
                'W', Items.wheat
        );
        
        // Miso Barrel
        addSharedRecipe(TcItems.barrelMisoTofu,
                "MMM",
                "TTT",
                " B ",
                'M', TcOreDic.miso,
                'T', TcOreDic.tofuMomen,
                'B', TcOreDic.barrel);

        // Inari
        addSharedRecipe(ItemFoodSet.inari.getStack(2),
                "#O*",
                " R ",
                '#', TcOreDic.bottleSoySauce,
                '*', Items.sugar,
                'O', TcOreDic.oage,
                'R', TcOreDic.onigiri
        );
        
        // Glowtofu Barrel
        addSharedRecipe(TcItems.barrelGlowtofu,
                "GGG",
                "TTT",
                " B ",
                'G', Items.glowstone_dust,
                'T', TcOreDic.tofuMomen,
                'B', TcOreDic.barrel
        );

        // Glass bowl
        addSharedRecipe(ItemTcMaterials.glassBowl.getStack(2),
                "P P",
                " P ",
                'P', Blocks.glass_pane
        );

        // Tofu somen
        addShapelessSharedRecipe(ItemTcMaterials.tofuSomen.getStack(4),
                TcOreDic.rollingPin,
                TcOreDic.tofuKinu,
                TcOreDic.starch,
                TcOreDic.salt
        );

        // Tofu somen bowl
        addShapelessSharedRecipe(ItemFoodSet.tofuSomen.getStack(),
                TcOreDic.tofuSomen,
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        addShapelessSharedRecipe(ItemFoodSet.tofuSomen.getStack(),
                TcOreDic.tofuSomen,
                TcOreDic.somenTsuyuBowl
        );

        // Somen tsuyu bowl
        addShapelessSharedRecipe(TcItems.somenTsuyuBowl,
                TcOreDic.dashi,
                TcOreDic.bottleSoySauce,
                TcOreDic.glassBowl
        );

        // Zunda mochi
        addShapelessSharedRecipe(ItemFoodSet.zundaMochi.getStack(3),
                TcOreDic.zunda,
                oredic_cookedMochi);

        // Kinako mochi
        addShapelessSharedRecipe(ItemFoodSet.kinakoMochi.getStack(3),
                TcOreDic.kinako,
                oredic_cookedMochi);

        addSharedRecipe(ItemFoodSet.chikuwa.getStack(4),
                " F ",
                "SET",
                " F ",
                'F', Items.cooked_fished,
                'S', TcOreDic.salt,
                'E', Items.egg,
                'T', TcOreDic.starch
        );

        addSharedRecipe(ItemFoodSet.chikuwa.getStack(4),
                " F ",
                "TES",
                " F ",
                'F', Items.cooked_fished,
                'S', TcOreDic.salt,
                'E', Items.egg,
                'T', TcOreDic.starch
        );

        // Chikuwa Platform
        addSharedRecipe(TcBlocks.chikuwaPlatformTofu,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.tofuChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformTofu,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.foodTofuChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformPlain,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.foodChikuwa);

        addSharedRecipe(TcBlocks.chikuwaPlatformPlain,
                " C ",
                "C C",
                " C ",
                'C', TcOreDic.chikuwa);

        // Soboro tofu rice
        addSharedRecipe(ItemFoodSet.riceSoboroTofu.getStack(),
                " Y ",
                "STs",
                " R ",
                'Y', TcOreDic.leek,
                'S', TcOreDic.bottleSoySauce,
                'T', TcOreDic.tofuMinced,
                's', TcOreDic.salt,
                'R', oredic_cookingRice);

        // Soboro tofu saute
        addShapelessSharedRecipe(ItemFoodSet.soboroTofuSaute.getStack(),
                TcOreDic.tofuMinced,
                Items.cooked_porkchop,
                Items.carrot,
                TcOreDic.bottleSoySauce,
                TcOreDic.dashi,
                TcOreDic.soyOil,
                Items.bowl);

        // Salt pan
        addSharedRecipe(TcBlocks.saltPan,
                "/ /",
                " _ ",
                '/', Items.stick,
                '_', new ItemStack(Blocks.stone_slab, 1, 3)
                );
        /*
         * Tofu force series
         */

        // Tofu Gem Barrel
        addSharedRecipe(TcItems.barrelAdvTofuGem,
                "RRR",
                "GGG",
                " B ",
                'R', Items.redstone,
                'G', TcOreDic.tofuGem,
                'B', TcOreDic.barrel
        );

        // TF Machine Case
        addSharedRecipe(TcBlocks.tfMachineCase,
                "TTT",
                "T T",
                "TTT",
                'T', TcOreDic.blockTofuMetal
        );
        
        addSharedRecipe(new ItemStack(TcBlocks.tofuMetal, 8),
                "C",
                'C', TcOreDic.blockTfMachineCase
        );
        
        // TF Capacitor
        addSharedRecipe(ItemTcMaterials.tfCapacitor.getStack(1),
                " T ",
                "RGR",
                " T ",
                'T', TcOreDic.tofuMetal,
                'G', TcOreDic.tofuGem,
                'R', Items.redstone
        );
        
        // TF Storage
        addSharedRecipe(TcBlocks.tfStorageIdle,
                "CCC",
                "GMG",
                'C', TcOreDic.tfCapacitor,
                'M', TcOreDic.blockTfMachineCase,
                'G', Blocks.glass
        );

        // Mineral soymilk
        addShapelessSharedRecipe(ItemTcMaterials.mineralSoymilk.getStack(1),
                TcOreDic.tofuGem,
                TcOreDic.tofuGem,
                TcOreDic.tofuGem,
                Items.redstone,
                Items.redstone,
                Items.redstone,
                Items.glass_bottle
        );

        // Rolling Pin
        addSharedRecipe(ItemTcMaterials.rollingPin.getStack(1),
                "  /",
                " P ",
                "/  ",
                '/', Items.stick,
                'P', Blocks.planks
        );

        // TF Circuit Board
        addSharedRecipe(ItemTcMaterials.tfCircuit.getStack(1),
                "RTR",
                "___",
                'R', Items.redstone,
                'T', TcOreDic.tofuKinu,
                '_', TcOreDic.blockTofuIshi
        );

        // TF Coil
        addSharedRecipe(ItemTcMaterials.tfCoil.getStack(1),
                "SSS",
                "TTT",
                "SSS",
                'S', TcOreDic.tofuSomen,
                'T', TcOreDic.tofuIshi
        );

        // TF Oscillator
        addSharedRecipe(ItemTcMaterials.tfOscillator.getStack(1),
                "TQT",
                "M M",
                'T', TcItems.tofuKinu,
                'Q', Items.quartz,
                'M', TcItems.tofuMetal
        );

        // Adv Tofu Gem Block
        addSharedRecipe(TcBlocks.advTofuGem,
                "GGG",
                "GGG",
                "GGG",
                'G', TcOreDic.advTofuGem
        );

        // TF Antenna
        addSharedRecipe(TcBlocks.tfAntennaMedium,
                " Y ",
                "CXA",
                "_B_",
                'Y', TcOreDic.leek,
                'C', TcOreDic.tfCoil,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'B', TcOreDic.tfCircuitBoard,
                '_', TcOreDic.tofuMetal
        );

        // TF Ultra Antenna
        addSharedRecipe(TcBlocks.tfAntennaUltra,
                " D ",
                "GYG",
                'Y', TcBlocks.tfAntennaMedium,
                'G', TcOreDic.advTofuGem,
                'D', TcOreDic.tofuDiamond
        );

        // TF Reformer
        addSharedRecipe(new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.Model.simple.id),
                "CXC",
                "TBT",
                " M ",
                'C', TcOreDic.tfCoil,
                'X', TcOreDic.tfOscillator,
                'B', TcOreDic.tfCircuitBoard,
                'T', TcOreDic.blockTofuDried,
                'M', TcOreDic.blockTfMachineCase
                );

        // TF Mix Reformer
        addSharedRecipe(new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.Model.mix.id),
                " H ",
                "GTG",
                " M ",
                'H', Blocks.hopper,
                'T', Blocks.crafting_table,
                'G', TcOreDic.advTofuGem,
                'M', new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.Model.simple.id)
        );

        // TF Condenser
        addSharedRecipe(TcBlocks.tfCondenserIdle,
                "PHP",
                "SDR",
                'H', Blocks.hopper,
                'P', Blocks.piston,
                'S', TcBlocks.tfStorageIdle,
                'D', TcOreDic.blockAdvTofuGem,
                'R', new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.Model.simple.id)
        );

        // TF Oven
        addSharedRecipe(TcBlocks.tfOvenIdle,
                "XHA",
                "CGC",
                " M ",
                'H', TcOreDic.activatedHellTofu,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'C', Blocks.hardened_clay,
                'G', Blocks.glass,
                'M', TcOreDic.blockTfMachineCase
        );

        addSharedRecipe(TcBlocks.tfOvenIdle,
                "XHA",
                "CGC",
                " M ",
                'H', TcOreDic.activatedHellTofu,
                'X', TcOreDic.tfOscillator,
                'A', TcOreDic.tfCapacitor,
                'C', new ItemStack(Blocks.stained_hardened_clay, 1, DMG_WILDCARD),
                'G', Blocks.glass,
                'M', TcOreDic.blockTfMachineCase
        );

        // TF Collector
        addSharedRecipe(TcBlocks.tfCollector,
                "H H",
                "GBG",
                " M ",
                'H', Blocks.hopper,
                'G', TcOreDic.activatedTofuGem,
                'B', TcOreDic.tfCircuitBoard,
                'M', TcOreDic.blockTfMachineCase
        );

        // TF Saturator
        addSharedRecipe(TcBlocks.tfSaturatorIdle,
                "TTT",
                "TGT",
                "TMT",
                'T', TcOreDic.blockTofuDried,
                'G', TcOreDic.advTofuGem,
                'M', TcOreDic.blockTfMachineCase
        );


        /*
         * Stairs Blocks
         */
        addStairsRecipes(TcOreDic.tofuKinu, TcBlocks.tofuStairsKinu);
        addStairsRecipes(TcOreDic.tofuMomen, TcBlocks.tofuStairsMomen);
        addStairsRecipes(TcOreDic.tofuIshi, TcBlocks.tofuStairsIshi);
        addStairsRecipes(TcOreDic.tofuMetal, TcBlocks.tofuStairsMetal);
        addStairsRecipes(TcOreDic.tofuGrilled, TcBlocks.tofuStairsGrilled);
        addStairsRecipes(TcOreDic.tofuDried, TcBlocks.tofuStairsDried);
        addStairsRecipes(TcOreDic.tofuFriedPouch, TcBlocks.tofuStairsFriedPouch);
        addStairsRecipes(TcOreDic.tofuFried, TcBlocks.tofuStairsFried);
        addStairsRecipes(TcOreDic.tofuEgg, TcBlocks.tofuStairsEgg);
        addStairsRecipes(TcOreDic.tofuAnnin, TcBlocks.tofuStairsAnnin);
        addStairsRecipes(TcOreDic.tofuSesame, TcBlocks.tofuStairsSesame);
        addStairsRecipes(TcOreDic.tofuZunda, TcBlocks.tofuStairsZunda);
        addStairsRecipes(TcOreDic.tofuStrawberry, TcBlocks.tofuStairsStrawberry);
        addStairsRecipes(TcOreDic.tofuHell, TcBlocks.tofuStairsHell);
        addStairsRecipes(TcOreDic.tofuGlow, TcBlocks.tofuStairsGlow);
        addStairsRecipes(TcOreDic.tofuDiamond, TcBlocks.tofuStairsDiamond);
        addStairsRecipes(TcOreDic.tofuMiso, TcBlocks.tofuStairsMiso);
        
        /*
         * Half Slabs
         */
        addSlabRecipe(TcOreDic.tofuKinu, TcBlocks.tofuSingleSlab1, 0);
        addSlabRecipe(TcOreDic.tofuMomen, TcBlocks.tofuSingleSlab1, 1);
        addSlabRecipe(TcOreDic.tofuIshi, TcBlocks.tofuSingleSlab1, 2);
        addSlabRecipe(TcOreDic.tofuMetal, TcBlocks.tofuSingleSlab1, 3);
        addSlabRecipe(TcOreDic.tofuGrilled, TcBlocks.tofuSingleSlabFaces, 0);
        addSlabRecipe(TcOreDic.tofuDried, TcBlocks.tofuSingleSlab1, 5);
        addSlabRecipe(TcOreDic.tofuFriedPouch, TcBlocks.tofuSingleSlab1, 6);
        addSlabRecipe(TcOreDic.tofuFried, TcBlocks.tofuSingleSlab1, 7);
        addSlabRecipe(TcOreDic.tofuEgg, TcBlocks.tofuSingleSlab2, 0);
        addSlabRecipe(TcOreDic.tofuAnnin, TcBlocks.tofuSingleSlab2, 1);
        addSlabRecipe(TcOreDic.tofuSesame, TcBlocks.tofuSingleSlab2, 2);
        addSlabRecipe(TcOreDic.tofuZunda, TcBlocks.tofuSingleSlab2, 3);
        addSlabRecipe(TcOreDic.tofuStrawberry, TcBlocks.tofuSingleSlab2, 4);
        addSlabRecipe(TcOreDic.tofuHell, TcBlocks.tofuSingleSlab2, 5);
        addSlabRecipe(TcOreDic.tofuGlow, TcBlocks.tofuSingleSlabGlow, 0);
        addSlabRecipe(TcOreDic.tofuDiamond, TcBlocks.tofuSingleSlab2, 7);
        addSlabRecipe(TcOreDic.tofuMiso, TcBlocks.tofuSingleSlab3, 0);
        
        // Converting recipes
        addSharedRecipe(TcBlocks.tofuSingleSlabFaces,
                "S",
                'S', new ItemStack(TcBlocks.tofuSingleSlab1, 1, 4)
        );
        addSharedRecipe(TcBlocks.tofuSingleSlabGlow,
                "S",
                'S', new ItemStack(TcBlocks.tofuSingleSlab2, 1, 6)
        );
        

        // Armors
        addCombatItemRecipes(TcOreDic.blockTofuKinu, TcItems.armorKinu, TcItems.swordKinu);
        addCombatItemRecipes(TcOreDic.blockTofuMomen, TcItems.armorMomen, TcItems.swordMomen);
        addCombatItemRecipes(TcOreDic.blockTofuIshi, TcItems.armorSolid, TcItems.swordSolid);
        addCombatItemRecipes(TcOreDic.blockTofuMetal, TcItems.armorMetal, TcItems.swordMetal);
        addCombatItemRecipes(TcOreDic.blockTofuDiamond, TcItems.armorDiamond, TcItems.swordDiamond);

        // Tools
        addToolItemRecipes(TcOreDic.blockTofuKinu, TcItems.toolKinu);
        addToolItemRecipes(TcOreDic.blockTofuMomen, TcItems.toolMomen);
        addToolItemRecipes(TcOreDic.blockTofuIshi, TcItems.toolSolid);
        addToolItemRecipes(TcOreDic.blockTofuMetal, TcItems.toolMetal);
        addToolItemRecipes(TcOreDic.blockTofuDiamond, TcItems.toolDiamond);

        // Doors
        for (TofuMaterial tofuMaterial: TofuMaterial.values())
        {
            addSharedRecipe(new ItemStack(TcItems.tofuDoor, 1, tofuMaterial.id()),
                    "TT",
                    "TT",
                    "TT",
                    'T', tofuMaterial.getBlock());
        }

        // Walls
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuWalls.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 6),
                    "TTT",
                    "TTT",
                    'T', entry.getKey().getBlock());
        }

        // Fence gates
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuFenceGates.entrySet())
        {
            addSharedRecipe(entry.getValue(),
                    "BIB",
                    "BIB",
                    'B', entry.getKey().getBlock(),
                    'I', entry.getKey().getItem());
        }

        // Torches
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuTorches.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 4),
                    "C",
                    "T",
                    "T",
                    'T', entry.getKey().getItem(),
                    'C', new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE));
            addSharedRecipe(new ItemStack(entry.getValue(), 4),
                    "O",
                    "T",
                    "T",
                    'T', entry.getKey().getItem(),
                    'O', TcOreDic.soyOil);
        }

        // Ladders
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuLadders.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 3),
                    "T T",
                    "TTT",
                    "T T",
                    'T', entry.getKey().getItem());
        }

        // Trapdoors
        for (Map.Entry<TofuMaterial, Block> entry: TcBlocks.tofuTrapdoors.entrySet())
        {
            addSharedRecipe(new ItemStack(entry.getValue(), 2),
                    "TTT",
                    "TTT",
                    'T', entry.getKey().getBlock());
        }

    }

    private static void addCombatItemRecipes(TcOreDic tofu, Item[] armors, Item sword)
    {
        addSharedRecipe(new ItemStack(armors[0]),
            "TTT",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[1]),
            "T T",
            "TTT",
            "TTT",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[2]),
            "TTT",
            "T T",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(armors[3]),
            "T T",
            "T T",
                'T', tofu
        );
        addSharedRecipe(new ItemStack(sword),
            "T",
            "T",
            "|",
                'T', tofu,
                '|', Items.stick
        );
    }

    private static void addToolItemRecipes(TcOreDic tofu, Item[] tools)
    {
        addSharedRecipe(new ItemStack(tools[0]),
            "T",
            "|",
            "|",
                'T', tofu,
                '|', Items.stick
        );
        addSharedRecipe(new ItemStack(tools[1]),
            "TTT",
            " | ",
            " | ",
                'T', tofu,
                '|', Items.stick
        );
        addSharedRecipe(new ItemStack(tools[2]),
            "TT",
            "T|",
            " |",
                'T', tofu,
                '|', Items.stick
        );
    }

    private static void addStairsRecipes(TcOreDic tofu, Block stairs)
    {
        // Stairs Blocks
        addSharedRecipe(new ItemStack(stairs),
                "  T",
                " TT",
                "TTT",
                'T', tofu
        );
        // Stairs Blocks
        addSharedRecipe(new ItemStack(stairs),
                "T  ",
                "TT ",
                "TTT",
                'T', tofu
        );
    }

    private static void addSlabRecipe(TcOreDic tofu, Block slab, int meta)
    {
        addSharedRecipe(new ItemStack(slab, 1, meta),
                "TT",
                'T', tofu
        );
    }

    /**
     * === External Mod Recipes ===
     */
    public static void registerExternalModRecipes()
    {
        if (TcItems.plantBall != null)
        {
            addShapelessSharedRecipe(new ItemStack(TcItems.plantBall, 1),
                    TcOreDic.okara,
                    TcOreDic.okara,
                    TcOreDic.okara,
                    TcOreDic.mincedPotato
            );
        }

        if (TcBlocks.sakuraLeaves != null)
        {
            // Soy Milk Bottle (Sakura)
            addShapelessSharedRecipe(new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvSakura.id),
                    Items.glass_bottle,
                    TcOreDic.bucketSoymilk,
                    new ItemStack(TcBlocks.sakuraLeaves, 1, 15)
            );
        }

        if (TcItems.bambooFood != null)
        {
            if (OreDictionary.getOres(oredic_cookedMochi).size() == 0)
            {
                // Zunda mochi
                addShapelessSharedRecipe(ItemFoodSet.zundaMochi.getStack(3),
                        TcOreDic.zunda,
                        new ItemStack(TcItems.bambooFood, 1, 23));

                // Kinako mochi
                addShapelessSharedRecipe(ItemFoodSet.kinakoMochi.getStack(3),
                        TcOreDic.kinako,
                        new ItemStack(TcItems.bambooFood, 1, 23));
            }
        }
    }

    /*
     * === Facade methods for Forge Ore Dictionary ===
     */
    private static void addSharedRecipe(Block block, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(block, recipe));
    }

    private static void addSharedRecipe(Item item, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(item, recipe));
    }

    private static void addSharedRecipe(ItemStack is, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapedOreRecipe(is, recipe));
    }

    private static void addShapelessSharedRecipe(Block block, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(block, recipe));
    }

    private static void addShapelessSharedRecipe(Item item, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(item, recipe));
    }

    private static void addShapelessSharedRecipe(ItemStack is, Object... recipe)
    {
        decodeDicRecipe(recipe);
        GameRegistry.addRecipe(new ShapelessOreRecipe(is, recipe));
    }

    private static void decodeDicRecipe(Object[] list)
    {
        for (int i = 0; i < list.length; i++)
        {
            Object Items = list[i];
            if (Items instanceof TcOreDic)
            {
                list[i] = ((TcOreDic)Items).name();
            }
        }
    }
}
