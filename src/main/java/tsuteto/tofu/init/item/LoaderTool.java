package tsuteto.tofu.init.item;

import net.minecraft.item.Item;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.item.*;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderTool extends TcItemLoader
{

    public void load()
    {
        /*
         * Basic Tools
         */
        toolKinu = addToolSet(TofuToolMaterial.KINU);
        toolMomen = addToolSet(TofuToolMaterial.MOMEN);
        toolSolid = addToolSet(TofuToolMaterial.SOLID);
        toolMetal = addToolSet(TofuToolMaterial.METAL);
        toolDiamond = addToolSet(TofuToolMaterial.DIAMOND);

        tofuHoe = $("tofuHoe", new ItemTofuHoe(TofuToolMaterial.GEM))
                .register()
                .setCreativeTab(TcCreativeTabs.TOOLS);

        // Golden Salt
        goldenSalt = $("goldensalt", new ItemGoldenSalt())
                .register()
                .setIconName("glowstone_dust")
                .setCreativeTab(TcCreativeTabs.TOOLS)
                .setMaxDamage(180);

        // Tofu Stick
        tofuStick = $("tofuStick", new ItemTofuStick())
                .register()
                .setFull3D()
                .setCreativeTab(TcCreativeTabs.TOOLS);

        // Tofu Bugle
        bugle = $("bugle", new ItemTofuBugle())
                .register()
                .setCreativeTab(TcCreativeTabs.TOOLS);

        tofuRadar = $("tofuRadar", new ItemTofuSlimeRadar())
                .register()
                .setCreativeTab(TcCreativeTabs.TOOLS);

        tofuScoop = $("tofuScoop", new ItemTofuScoop())
                .register()
                .setCreativeTab(TcCreativeTabs.TOOLS);

        fukumame = $("fukumame", new ItemFukumame())
                .register()
                .setCreativeTab(TcCreativeTabs.TOOLS);
    }

    private Item[] addToolSet(Item.ToolMaterial material)
    {
        String key;
        if (material == TofuToolMaterial.KINU) key = "kinu";
        else if (material == TofuToolMaterial.MOMEN) key = "momen";
        else if (material == TofuToolMaterial.SOLID) key = "solid";
        else if (material == TofuToolMaterial.METAL) key = "metal";
        else if (material == TofuToolMaterial.DIAMOND) key = "diamond";
        else throw new IllegalArgumentException("Unknown material for armor");

        Item[] tools = new Item[3];
        if (material == TofuToolMaterial.DIAMOND)
        {
            tools[0] = $(getToolName(key, 0), new ItemDiamondTofuSpade(material)).register();
            tools[1] = $(getToolName(key, 1), new ItemDiamondTofuPickaxe(material)).register();
            tools[2] = $(getToolName(key, 2), new ItemDiamondTofuAxe(material)).register();
        }
        else
        {
            tools[0] = $(getToolName(key, 0), new ItemTcSpade(material)).register();
            tools[1] = $(getToolName(key, 1), new ItemTcPickaxe(material)).register();
            tools[2] = $(getToolName(key, 2), new ItemTcAxe(material)).register();
        }

        return tools;
    }

}
