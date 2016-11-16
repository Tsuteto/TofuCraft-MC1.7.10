package tsuteto.tofu.init.item;

import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.item.ItemTofuDoor;

import static tsuteto.tofu.init.TcItems.tofuDoor;

public class LoaderConstruction extends TcItemLoader
{
    @Override
    public void load()
    {
        tofuDoor = $("tofuDoor", new ItemTofuDoor())
                .register()
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);

    }
}
