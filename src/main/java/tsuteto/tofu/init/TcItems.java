package tsuteto.tofu.init;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import tsuteto.tofu.item.*;
import tsuteto.tofu.util.Utils;

import java.util.EnumMap;

public class TcItems
{
    public static final String[] toolNameList = new String[]{"shovel", "pickaxe", "axe"};
    public static final String[] armorNameList = new String[]{"helmet", "chestplate", "leggings", "boots"};

    public static EnumMap<TofuMaterial, Item> tofuItems = Maps.newEnumMap(TofuMaterial.class);

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
    public static ItemBottleSoyMilk bottleSoymilk;
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
    public static ItemFoodSet foodSet;
    public static ItemFoodSetStick foodSetStick;
    public static Item doubanjiang;
    public static Item strawberryJam;
    public static Item tofuRadar;
    public static Item yuba;
    public static Item tofuMiso;
    public static Item barrelMisoTofu;
    public static Item barrelGlowtofu;
    public static Item barrelAdvTofuGem;
    public static Item somenTsuyuBowl;
    public static Item tofuHoe;
    public static Item tofuDoor;

    // === External Mod Items ===
    public static Item plantBall; // from IC2
    public static Item bambooFood; // from Bamboo Mod
    public static Item bambooBasket; // from Bamboo Mod

    public static String getArmorName(String key, int id)
    {
        return "armor" + Utils.capitalize(key) + Utils.capitalize(armorNameList[id]);
    }

    public static String getToolName(String key, int id)
    {
        return "tool" + Utils.capitalize(key) + Utils.capitalize(toolNameList[id]);
    }
}
