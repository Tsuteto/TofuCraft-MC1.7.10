package tsuteto.tofu.init.item;

import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.ItemBlockBarrel;
import tsuteto.tofu.item.ItemMorijio;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderDecoration extends TcItemLoader
{
    public void load()
    {
        morijio = $("morijio", new ItemMorijio())
                .register()
                .setCreativeTab(TcCreativeTabs.DECORATIONS);

        barrelMiso = $("barrelMiso", new ItemBlockBarrel(TcBlocks.barrelMiso))
                .register()
                .setCreativeTab(TcCreativeTabs.DECORATIONS);

        barrelMisoTofu = $("barrelMisoTofu", new ItemBlockBarrel(TcBlocks.barrelMisoTofu))
                .register()
                .setCreativeTab(TcCreativeTabs.DECORATIONS);

        barrelGlowtofu = $("barrelGlowtofu", new ItemBlockBarrel(TcBlocks.barrelGlowtofu))
                .register()
                .setCreativeTab(TcCreativeTabs.DECORATIONS);

        barrelAdvTofuGem = $("barrelAdvTofuGem", new ItemBlockBarrel(TcBlocks.barrelAdvTofuGem))
                .register()
                .setCreativeTab(TcCreativeTabs.DECORATIONS);
    }
}
