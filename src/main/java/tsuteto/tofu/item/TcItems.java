package tsuteto.tofu.item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.eventhandler.TcCraftingHandler;
import tsuteto.tofu.potion.TcPotion;
import tsuteto.tofu.util.Utils;

public class TcItems
{
    public static final String[] armorNameList = new String[]{"helmet", "chestplate", "leggings", "boots"};
    public static final String[] toolNameList = new String[]{"shovel", "pickaxe", "axe"};
    public static Item soybeans;
    public static Item nigari;
    public static Item tofuKinu;
    public static Item tofuMomen;
    public static Item tofuIshi;
    public static Item tofuMetal;
    public static Item tofuGrilled;
    public static Item tofuDried;
    public static Item tofuFriedPouch;
    public static Item tofuFried;
    public static Item tofuEgg;
    public static Item tofuCake;
    public static Item tofuStick;
    public static Item koujiBase;
    public static Item kouji;
    public static Item miso;
    public static Item yudofu;
    public static Item tttBurger;
    public static Item morijio;
    public static Item bugle;
    public static Item misoSoup;
    public static Item misoDengaku;
    public static Item edamame;
    public static Item zunda;
    public static Item bucketSoymilk;
    public static Item edamameBoiled;
    public static Item bottleSoymilk;
    public static Item barrelEmpty;
    public static Item barrelMiso;
    public static Item zundaManju;
    public static Item bucketSoySauce;
    public static Item phialEmpty;
    public static Item bottleSoySauce;
    public static Item soybeansParched;
    public static Item kinako;
    public static Item nikujaga;
    public static Item defattingPotion;
    public static Item dashi;
    public static Item soyOil;
    public static Item agedashiTofu;
    public static Item kinakoManju;
    public static Item fukumeni;
    public static Item koyadofuStew;
    public static Item natto;
    public static Item nattoHiyayakko;
    public static Item salt;
    public static Item saltyMelon;
    public static Item tastyStew;
    public static Item tastyBeefStew;
    public static Item goldenSalt;
    public static Item[] armorKinu;
    public static Item[] armorMomen;
    public static Item[] armorSolid;
    public static Item[] armorMetal;
    public static Item[] armorDiamond;
    public static Item swordKinu;
    public static Item swordMomen;
    public static Item swordSolid;
    public static Item swordMetal;
    public static Item swordDiamond;
    public static Item[] toolKinu;
    public static Item[] toolMomen;
    public static Item[] toolSolid;
    public static Item[] toolMetal;
    public static Item[] toolDiamond;
    public static Item zundaArrow;
    public static Item zundaBow;
    public static Item apricot;
    public static Item apricotSeed;
    public static Item filterCloth;
    public static Item okara;
    public static Item mincedPotato;
    public static Item starchRaw;
    public static Item starch;
    public static Item tofuAnnin;
    public static Item tofuSesame;
    public static Item tofuZunda;
    public static Item kyoninso;
    public static Item leek;
    public static Item sesame;
    public static Item okaraStick;
    public static Item tofuStrawberry;
    public static Item gelatin; // Contains gelatin base
    public static Item riceNatto;
    public static Item riceNattoLeek;
    public static Item zundama;
    public static Item fukumame;
    public static Item tofuHell;
    public static Item tofuGlow;
    public static Item tofuDiamond;
    public static Item bucketSoymilkHell;
    public static Item soybeansHell;
    public static Item tofuScoop;
    public static ItemTcMaterials materials;
    public static ItemFoodSetBase foodSet;
    public static ItemFoodSetBase foodSetStick;
    public static Item doubanjiang;
    public static Item strawberryJam;
    public static Item tofuRadar;
    public static Item yuba;
    public static Item tofuMiso;
    public static Item barrelMisoTofu;
    public static Item barrelGlowtofu;
    // === External Mod Items ===
    public static Item plantBall; // from IC2
    public static Item bambooFood; // from Bamboo Mod
    public static Item bambooBasket; // from Bamboo Mod

    public static void register()
    {
        soybeans = $("seeds_soybeans", new ItemSoybeans())
                .register();
        MinecraftForge.addGrassSeed(new ItemStack(soybeans), 2);

        soybeansHell = $("seeds_soybeansHell", new ItemSoybeansHell())
                .register();

        /*
         * === Tofu ===
         */
        tofuKinu = $("tofuKinu", new ItemTcFood(2, 0.1F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuKinu.setDropItem(tofuKinu);

        tofuMomen = $("tofuMomen", new ItemTcFood(2, 0.1F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuMomen.setDropItem(tofuMomen);

        tofuIshi = $("tofuIshi", new ItemTcFood(3, 0.4F, false))
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcBlocks.tofuIshi.setDropItem(tofuIshi);

        tofuMetal = $("tofuMetal", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcBlocks.tofuMetal.setDropItem(tofuMetal);

        tofuGrilled = $("tofuGrilled", new ItemTcFood(3, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuGrilled.setDropItem(tofuGrilled);

        tofuDried = $("tofuDried", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcBlocks.tofuDried.setDropItem(tofuDried);

        tofuFriedPouch = $("tofuPouchFried", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuFriedPouch.setDropItem(tofuFriedPouch);

        tofuFried = $("tofuFried", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuFried.setDropItem(tofuFried);

        tofuEgg = $("tofuEgg", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuEgg.setDropItem(tofuEgg);

        tofuAnnin = $("tofuAnnin", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuAnnin.setDropItem(tofuAnnin);

        tofuSesame = $("tofuSesame", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuSesame.setDropItem(tofuSesame);

        tofuZunda = $("tofuZunda", new ItemTcFood(4, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuZunda.setDropItem(tofuZunda);

        tofuStrawberry = $("tofuStrawberry", new ItemTcFood(3, 0.2F, false))
                .register()
                .setAlwaysEdible();
        TcBlocks.tofuStrawberry.setDropItem(tofuStrawberry);

        tofuMiso = $("tofuMiso", new ItemTcFood(5, 0.8F, false))
                .register()
                .setAlwaysEdible();
        if (TcPotion.filling != null)
        {
            ((ItemTcFood)tofuMiso).setPotionEffect(TcPotion.filling.id, 300, 0, 1.0F);
        }
        TcBlocks.tofuMiso.setDropItem(tofuMiso);

        tofuHell = $("tofuHell", new ItemTcFood(2, 0.2F, false))
                .register()
                .setPotionEffect(Potion.fireResistance.id, 30, 0, 1.0F)
                .setAlwaysEdible();
        TcBlocks.tofuHell.setDropItem(tofuHell);

        tofuGlow = $("tofuGlow", new ItemTcFood(2, 0.2F, false))
                .register()
                .setAlwaysEdible();
        if (TcPotion.glowing != null)
        {
            ((ItemTcFood)tofuGlow).setPotionEffect(TcPotion.glowing.id, 240, 0, 1.0F);
        }
        TcBlocks.tofuGlow.setDropItem(tofuGlow);

        tofuDiamond = $("tofuDiamond", new TcItem())
                .register();
        TcBlocks.tofuDiamond.setDropItem(tofuDiamond);

        /*
         * === Fluid Buckets ===
         */
        bucketSoymilk = $("bucketSoymilk", new ItemDrinkBucket(TcBlocks.soymilkStill, 1, 0.1F))
                .register()
                .setAlwaysEdible()
                .setCreativeTab(CreativeTabs.tabMisc)
                .setContainerItem(Items.bucket);

        bucketSoymilkHell = $("bucketSoymilkHell", new ItemDrinkBucket(TcBlocks.soymilkHellStill, 2, 0.2F))
                .register()
                .setPotionEffect(Potion.fireResistance.id, 60, 0, 1.0F)
                .setAlwaysEdible()
                .setCreativeTab(CreativeTabs.tabMisc)
                .setContainerItem(Items.bucket);

        bucketSoySauce = $("bucketSoySauce", new ItemTcBucket(TcBlocks.soySauceStill))
                .register()
                .setContainerItem(Items.bucket)
                .setCreativeTab(CreativeTabs.tabMisc);

        Object dispenserbehaviorfilledbucket = BlockDispenser.dispenseBehaviorRegistry.getObject(Items.water_bucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoymilk, dispenserbehaviorfilledbucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoymilkHell, dispenserbehaviorfilledbucket);
        BlockDispenser.dispenseBehaviorRegistry.putObject(bucketSoySauce, dispenserbehaviorfilledbucket);

        // Salt
        salt = $("salt", new ItemAltIcon())
                .register()
        		.setIconName("sugar")
                .setCreativeTab(CreativeTabs.tabMaterials);

        // Golden Salt
        goldenSalt = $("goldensalt", new ItemGoldenSalt())
                .register()
                .setIconName("glowstone_dust")
                .setCreativeTab(CreativeTabs.tabTools)
                .setMaxDamage(180);

        // Salty Melon
        saltyMelon = $("saltyMelon", new ItemTcFood(3, 0.5F, false))
                .register();

        // Tasty Stew
        tastyStew = $("tastyStew", new ItemSoupBase(20, 1.0F, false))
                .register();

        // Tasty Beef Stew
        tastyBeefStew = $("tastyBeefStew", new ItemSoupBase(20, 1.0F, false))
                .register();

        // Nigari
        nigari = $("nigari", new ItemNigari())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        // Tofu Cake
        tofuCake = $("tofuCake", new ItemTcReed(TcBlocks.tofuCake))
                .register()
        		.setMaxStackSize(1)
                .setCreativeTab(CreativeTabs.tabFood);

        tofuStick = $("tofuStick", new ItemTofuStick())
                .register()
                .setFull3D()
                .setCreativeTab(CreativeTabs.tabTools);

        koujiBase = $("koujiBase", new ItemKoujiBase())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        kouji = $("kouji", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        yudofu = $("yudofu", new ItemSoupBase(3, 0.3F, false))
                .register();

        miso = $("miso", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        tttBurger = $("tttBurger", new ItemTcFood(8, 0.4F, false))
                .register();

        morijio = $("morijio", new ItemMorijio())
                .register();

        bugle = $("bugle", new ItemTofuBugle())
                .register()
                .setCreativeTab(CreativeTabs.tabMisc);

        misoSoup = $("misoSoup", new ItemSoupBase(4, 0.6F, false))
                .register();

        misoDengaku = $("misoDengaku", new ItemFoodContainer(5, 0.6F, true))
                .register()
                .setContainedItem(new ItemStack(Items.stick))
                .setFull3D();

        edamame = $("edamame", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        zunda = $("zunda", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        edamameBoiled = $("edamameBoiled", new ItemTcFood(1, 0.25F, false))
                .register()
                .setAlwaysEdible();

        barrelEmpty = $("barrelEmpty", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        barrelMiso = $("barrelMiso", new ItemBlockBarrel(TcBlocks.barrelMiso))
                .register()
                .setCreativeTab(CreativeTabs.tabDecorations);

        zundaManju = $("zundaManju", new ItemTcFood(6, 0.8F, true))
                .register();

//        phialEmpty = $("", new ItemPhial())
//                .setCreativeTab(CreativeTabs.tabMaterials)
//                .setUnlocalizedName("tofucraft:phialEmpty");

        bottleSoySauce = $("bottleSoySause", new ItemSeasoningBottle(0x432709))
                .register()
                .setMaxDamage(19)
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcCraftingHandler.registerDurabilityItem(bottleSoySauce);

        soybeansParched = $("soybeansParched", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        kinako = $("kinako", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        nikujaga = $("nikujaga", new ItemSoupBase(10, 0.7F, false))
                .register();

        defattingPotion = $("defattingPotion", new ItemDefattingPotion(0xada1cc))
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        dashi = $("dashi", new ItemSeasoningBottle(0xfcf6ac))
                .register()
                .setMaxDamage(9)
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcCraftingHandler.registerDurabilityItem(dashi);

        soyOil = $("soyOil", new ItemSeasoningBottle(0xfeff82))
                .register()
                .setMaxDamage(19)
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcCraftingHandler.registerDurabilityItem(soyOil);

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

        natto = $("natto", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        nattoHiyayakko = $("nattoHiyayakko", new ItemSoupBase(8, 0.8f, false))
                .register();

        apricotSeed = $("apricotSeed", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        apricot = $("apricot", new ItemFoodContainer(3, 0.3f, false, new ItemStack(apricotSeed)))
                .register();

        filterCloth = $("filterCloth", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        okara = $("okara", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        mincedPotato = $("mincedPotato", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        starchRaw = $("starchRaw", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        starch = $("starch", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        kyoninso = $("kyoninso", new ItemAltIcon())
                .register()
        		.setIconName("tofucraft:starch")
                .setCreativeTab(CreativeTabs.tabMaterials);

        leek = $("leek", new ItemLeek())
                .register()
                .setFull3D()
                .setCreativeTab(CreativeTabs.tabMaterials);

        sesame = $("sesame", new ItemTcSeeds(TcBlocks.sesame, Blocks.farmland))
                .register()
                .setCreativeTab(CreativeTabs.tabMaterials);

        okaraStick = $("okaraStick", new ItemTcFood(5, 0.6f, false))
                .register()
                .setEatingDuration(8);

        riceNatto = $("riceNatto", new ItemFoodContainer(8, 0.8f, false))
                .register();

        riceNattoLeek = $("riceNattoLeek", new ItemFoodContainer(9, 0.8f, false))
                .register();

        zundama = $("zundama", new TcItem())
                .register();

        // Gelatin and Gelatin Base
        gelatin = $("gelatin", new ItemGelatin())
                .register();

        fukumame = $("fukumame", new ItemFukumame())
                .register();

        tofuScoop = $("tofuScoop", new ItemTofuScoop())
                .register();

        doubanjiang = $("doubanjiang", new ItemCraftingDurability())
                .register()
                .setMaxDamage(57)
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcCraftingHandler.registerDurabilityItem(doubanjiang);

        strawberryJam = $("strawberryJam", new ItemCraftingDurability())
                .register()
                .setMaxDamage(99)
                .setCreativeTab(CreativeTabs.tabMaterials);
        TcCraftingHandler.registerDurabilityItem(strawberryJam);

        tofuRadar = $("tofuRadar", new ItemTofuSlimeRadar())
                .register();

        yuba = $("yuba", new ItemYuba(1, 1.0f, false))
                .register()
                .setAlwaysEdible();

        barrelMisoTofu = $("barrelMisoTofu", new ItemBlockBarrel(TcBlocks.barrelMisoTofu))
                .register()
                .setCreativeTab(CreativeTabs.tabDecorations);

        barrelGlowtofu = $("barrelGlowtofu", new ItemBlockBarrel(TcBlocks.barrelGlowtofu))
                .register()
                .setCreativeTab(CreativeTabs.tabDecorations);

        /*
         * === Material Items - Added as of 1.4 ===
         */
        materials = $("materials", new ItemTcMaterials()).register();

        TcBlocks.oreTofu.setItemContained(
                new ItemStack(materials, 1, ItemTcMaterials.tofuGem.id));
        TcBlocks.oreTofuDiamond.setItemContained(
                new ItemStack(materials, 1, ItemTcMaterials.tofuDiamondNugget.id));

        /*
         * === Food Set ===
         */
        foodSet = $("foodSet", new ItemFoodSet()).register();
        foodSetStick = $("foodSetStick", new ItemFoodSetStick()).register();

        /*
         * Zunda Arrow & Bow
         */
        zundaBow = $("zundaBow", new ItemZundaBow())
                .register();
        zundaArrow = $("zundaArrow", new TcItem())
                .register()
                .setCreativeTab(CreativeTabs.tabCombat);

        /*
         * Armors & weapons
         */
        armorKinu = addArmorSet(TofuArmorMaterial.KINU);
        swordKinu = $("swordKinu", new ItemTofuSword(TofuToolMaterial.KINU))
                .register();

        armorMomen = addArmorSet(TofuArmorMaterial.MOMEN);
        swordMomen = $("swordMomen", new ItemTofuSword(TofuToolMaterial.MOMEN))
                .register();

        armorSolid = addArmorSet(TofuArmorMaterial.SOLID);
        swordSolid = $("swordSolid", new ItemTofuSword(TofuToolMaterial.SOLID))
                .register();

        armorMetal = addArmorSet(TofuArmorMaterial.METAL);
        swordMetal = $("swordMetal", new ItemTofuSword(TofuToolMaterial.METAL))
                .register();

        armorDiamond = addArmorSet(TofuArmorMaterial.DIAMOND);
        swordDiamond = $("swordDiamond", new ItemTofuSword(TofuToolMaterial.DIAMOND))
                .register();

        /*
         * Tools
         */
        toolKinu = addToolSet(TofuToolMaterial.KINU);
        toolMomen = addToolSet(TofuToolMaterial.MOMEN);
        toolSolid = addToolSet(TofuToolMaterial.SOLID);
        toolMetal = addToolSet(TofuToolMaterial.METAL);
        toolDiamond = addToolSet(TofuToolMaterial.DIAMOND);

    }

    private static Item[] addArmorSet(ItemArmor.ArmorMaterial material)
    {
        String key;
        if (material == TofuArmorMaterial.KINU) key = "kinu";
        else if (material == TofuArmorMaterial.MOMEN) key = "momen";
        else if (material == TofuArmorMaterial.SOLID) key = "solid";
        else if (material == TofuArmorMaterial.METAL) key = "metal";
        else if (material == TofuArmorMaterial.DIAMOND) key = "diamond";
        else throw new IllegalArgumentException("Unknown material for armor");

        Item[] armors = new Item[4];
        for (int i = 0; i < 4; i++)
        {
            armors[i] = $(getArmorName(key, i), new ItemTofuArmor(material, 2, i)).register()
                    .setArmorTexture(String.format("tofucraft:textures/armor/armor_%s_%d.png", key, i == 2 ? 2 : 1));
        }
        return armors;
    }

    private static Item[] addToolSet(Item.ToolMaterial material)
    {
        String key;
        if (material == TofuToolMaterial.KINU) key = "kinu";
        else if (material == TofuToolMaterial.MOMEN) key = "momen";
        else if (material == TofuToolMaterial.SOLID) key = "solid";
        else if (material == TofuToolMaterial.METAL) key = "metal";
        else if (material == TofuToolMaterial.DIAMOND) key = "diamond";
        else throw new IllegalArgumentException("Unknown material for armor");

        Item[] tools = new Item[3];
        tools[0] = $(getToolName(key, 0), new ItemTcSpade(material)).register();
        tools[1] = $(getToolName(key, 1), new ItemTcPickaxe(material)).register();
        tools[2] = $(getToolName(key, 2), new ItemTcAxe(material)).register();

        return tools;
    }

    private static String getArmorName(String key, int id)
    {
        return "armor" + Utils.capitalize(key) + Utils.capitalize(armorNameList[id]);
    }

    private static String getToolName(String key, int id)
    {
        return "tool" + Utils.capitalize(key) + Utils.capitalize(toolNameList[id]);
    }

    private static <T extends Item> ItemRegister<T> $(String name, T item)
    {
        return new ItemRegister<T>(name, item);
    }

    /**
     * === External Mod Items ===
     */
    public static void registerExternalModItems()
    {
        if (Loader.isModLoaded("IC2"))
        {
            plantBall = Utils.getIc2Item("plantBall");
        }

        if (Loader.isModLoaded("BambooMod"))
        {
            bambooBasket = Utils.getExternalModItemWithRegex("(?i)bamboobasket");
            bambooFood = Utils.getExternalModItemWithRegex("(?i)bamboofoods?");

            if (bambooBasket != null)
            {
                ((ItemFoodContainer)riceNatto).setContainedItem(new ItemStack(bambooBasket));
                ((ItemFoodContainer)riceNattoLeek).setContainedItem(new ItemStack(bambooBasket));
                ItemFoodSet.riceTofu.setContainer(new ItemStack(bambooBasket));
            }
        }
    }

    private static class ItemRegister<T extends Item>
    {
        T item;
        String uniqueName;
        String resourceName;

        public ItemRegister(String name, T item)
        {
            this.item = item;
            this.uniqueName = name;
            this.resourceName = name;
        }

        public ItemRegister<T> withResource(String name)
        {
            this.resourceName = name;
            return this;
        }

        public T register()
        {
            item.setUnlocalizedName(TofuCraftCore.resourceDomain + resourceName);
            item.setTextureName(TofuCraftCore.resourceDomain + resourceName);
            GameRegistry.registerItem(item, uniqueName);
            return item;
        }

    }
}
