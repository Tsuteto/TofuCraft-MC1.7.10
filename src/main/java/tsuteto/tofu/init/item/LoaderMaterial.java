package tsuteto.tofu.init.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.dispanse.DispenserBehaviorNigari;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.*;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderMaterial extends TcItemLoader
{
    public void load()
    {
        soybeans = $("seeds_soybeans", new ItemSoybeans())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);
        MinecraftForge.addGrassSeed(new ItemStack(soybeans), 2);

        soybeansHell = $("seeds_soybeansHell", new ItemSoybeansHell())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        /*
         * === Fluid Buckets ===
         */
        bucketSoymilk = $("bucketSoymilk", new ItemDrinkBucket(TcBlocks.soymilkStill, 1, 0.1F))
                .register()
                .setAlwaysEdible()
                .setCreativeTab(TcCreativeTabs.MATERIALS)
                .setContainerItem(Items.bucket);

        bucketSoymilkHell = $("bucketSoymilkHell", new ItemDrinkBucket(TcBlocks.soymilkHellStill, 2, 0.2F))
                .register()
                .setPotionEffect(Potion.fireResistance.id, 60, 0, 1.0F)
                .setAlwaysEdible()
                .setCreativeTab(TcCreativeTabs.MATERIALS)
                .setContainerItem(Items.bucket);

        bucketSoySauce = $("bucketSoySauce", new ItemTcBucket(TcBlocks.soySauceStill))
                .register()
                .setContainerItem(Items.bucket)
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        Object dispenserbehaviorfilledbucket = BlockDispenser.dispenseBehaviorRegistry.getObject(Items.water_bucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoymilk, dispenserbehaviorfilledbucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoymilkHell, dispenserbehaviorfilledbucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoySauce, dispenserbehaviorfilledbucket);

        // Salt
        salt = $("salt", new ItemAltIcon())
                .register()
                .setIconName("sugar")
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        // Nigari
        nigari = $("nigari", new ItemNigari())
                .register()
                .setContainerItem(Items.glass_bottle)
                .setCreativeTab(TcCreativeTabs.MATERIALS);
        BlockDispenser.dispenseBehaviorRegistry.putObject(nigari, new DispenserBehaviorNigari());

        // Kouji
        koujiBase = $("koujiBase", new ItemKoujiBase())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        kouji = $("kouji", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        miso = $("miso", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        edamame = $("edamame", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        zunda = $("zunda", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        barrelEmpty = $("barrelEmpty", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

//        phialEmpty = $("", new ItemPhial())
//                .setCreativeTab(TofuCraftCore.tabTofuCraft)
//                .setUnlocalizedName("tofucraft:phialEmpty");

        bottleSoySauce = $("bottleSoySause", new ItemSeasoningBottle(0x432709, 19))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        soybeansParched = $("soybeansParched", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        kinako = $("kinako", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        defattingPotion = $("defattingPotion", new ItemDefattingPotion(0xada1cc))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        dashi = $("dashi", new ItemSeasoningBottle(0xfcf6ac, 9))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        soyOil = $("soyOil", new ItemSeasoningBottle(0xfeff82, 19))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        natto = $("natto", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        nattoHiyayakko = $("nattoHiyayakko", new ItemSoupBase(8, 0.8f, false))
                .register();

        apricotSeed = $("apricotSeed", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        filterCloth = $("filterCloth", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        okara = $("okara", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        mincedPotato = $("mincedPotato", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        starchRaw = $("starchRaw", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        starch = $("starch", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        kyoninso = $("kyoninso", new ItemAltIcon())
                .register()
                .setIconName("tofucraft:starch")
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        leek = $("leek", new ItemLeek())
                .register()
                .setFull3D()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        sesame = $("sesame", new ItemTcSeeds(TcBlocks.sesame, Blocks.farmland))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        zundama = $("zundama", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        // Gelatin and Gelatin Base
        gelatin = $("gelatin", new ItemGelatin())
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        doubanjiang = $("doubanjiang", new ItemSeasoningBottleAltIcon(57))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        strawberryJam = $("strawberryJam", new ItemSeasoningBottleAltIcon(99))
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        somenTsuyuBowl = $("somenTsuyuBowlGl", new ItemSomenTsuyuBowl(2, 0.1F, false))
                .withResource("tsuyuBowl_glass")
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        /*
         * === Material Items ===
         */
        (materials = $("materials", new ItemTcMaterials()).register()).setCreativeTab(TcCreativeTabs.MATERIALS);

        TcBlocks.oreTofu.setItemContained(
                ItemTcMaterials.tofuGem.getStack());
        TcBlocks.oreTofuDiamond.setItemContained(
                ItemTcMaterials.tofuDiamondNugget.getStack());


    }

}
