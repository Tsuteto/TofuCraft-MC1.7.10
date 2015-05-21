package tsuteto.tofu.init.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.item.*;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderCombat extends TcItemLoader
{

    public void load()
    {
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
        swordDiamond = $("swordDiamond", new ItemDiamondTofuSword(TofuToolMaterial.DIAMOND))
                .register();


        /*
         * Zunda Arrow & Bow
         */
        zundaBow = $("zundaBow", new ItemZundaBow())
                .register()
                .setCreativeTab(TcCreativeTabs.COMBAT);
        zundaArrow = $("zundaArrow", new TcItem())
                .register()
                .setCreativeTab(TcCreativeTabs.COMBAT);
    }

    private Item[] addArmorSet(ItemArmor.ArmorMaterial material)
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
            ItemTofuArmor armor;
            if (material == TofuArmorMaterial.DIAMOND)
            {
                armor = new ItemDiamondTofuArmor(material, 2, i);
            }
            else
            {
                armor = new ItemTofuArmor(material, 2, i);
            }
            armors[i] = $(getArmorName(key, i), armor).register()
                    .setArmorTexture(String.format("tofucraft:textures/armor/armor_%s_%d.png", key, i == 2 ? 2 : 1));
        }
        return armors;
    }

}