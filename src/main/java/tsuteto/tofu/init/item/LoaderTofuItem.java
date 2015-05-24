package tsuteto.tofu.init.item;

import net.minecraft.potion.Potion;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.item.ItemTcFood;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.item.TofuMaterial;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderTofuItem extends TcItemLoader
{
    @Override
    public void load()
    {
        tofuKinu = $("tofuKinu", new ItemTcFood(2, 0.1F, false))
                .asTofu(TofuMaterial.kinu)
                .register()
                .setAlwaysEdible();

        tofuMomen = $("tofuMomen", new ItemTcFood(2, 0.1F, false))
                .asTofu(TofuMaterial.momen)
                .register()
                .setAlwaysEdible();

        tofuIshi = $("tofuIshi", new ItemTcFood(3, 0.4F, false))
                .asTofu(TofuMaterial.ishi)
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        tofuMetal = $("tofuMetal", new TcItem())
                .asTofu(TofuMaterial.metal)
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        tofuGrilled = $("tofuGrilled", new ItemTcFood(3, 0.2F, false))
                .asTofu(TofuMaterial.grilled)
                .register()
                .setAlwaysEdible();

        tofuDried = $("tofuDried", new TcItem())
                .asTofu(TofuMaterial.dried)
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);

        tofuFriedPouch = $("tofuPouchFried", new ItemTcFood(4, 0.2F, false))
                .withResource("tofuFriedPouch")
                .asTofu(TofuMaterial.friedPouch)
                .register()
                .setAlwaysEdible();

        tofuFried = $("tofuFried", new ItemTcFood(4, 0.2F, false))
                .asTofu(TofuMaterial.fried)
                .register()
                .setAlwaysEdible();

        tofuEgg = $("tofuEgg", new ItemTcFood(4, 0.2F, false))
                .asTofu(TofuMaterial.egg)
                .register()
                .setAlwaysEdible();

        tofuAnnin = $("tofuAnnin", new ItemTcFood(4, 0.2F, false))
                .asTofu(TofuMaterial.annin)
                .register()
                .setAlwaysEdible();

        tofuSesame = $("tofuSesame", new ItemTcFood(4, 0.2F, false))
                .asTofu(TofuMaterial.sesame)
                .register()
                .setAlwaysEdible();

        tofuZunda = $("tofuZunda", new ItemTcFood(4, 0.2F, false))
                .asTofu(TofuMaterial.zunda)
                .register()
                .setAlwaysEdible();

        tofuStrawberry = $("tofuStrawberry", new ItemTcFood(3, 0.2F, false))
                .asTofu(TofuMaterial.strawberry)
                .register()
                .setAlwaysEdible();

        tofuMiso = $("tofuMiso", new ItemTcFood(5, 0.8F, false))
                .asTofu(TofuMaterial.miso)
                .register()
                .setAlwaysEdible();

        tofuHell = $("tofuHell", new ItemTcFood(2, 0.2F, false))
                .asTofu(TofuMaterial.hell)
                .register()
                .setPotionEffect(Potion.fireResistance.id, 30, 0, 1.0F)
                .setAlwaysEdible();

        tofuGlow = $("tofuGlow", new ItemTcFood(2, 0.2F, false))
                .asTofu(TofuMaterial.glow)
                .register()
                .setAlwaysEdible();

        tofuDiamond = $("tofuDiamond", new TcItem())
                .asTofu(TofuMaterial.diamond)
                .register()
                .setCreativeTab(TcCreativeTabs.MATERIALS);
    }
}
