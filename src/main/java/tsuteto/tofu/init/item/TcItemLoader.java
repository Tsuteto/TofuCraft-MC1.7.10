package tsuteto.tofu.init.item;

import net.minecraft.item.Item;
import tsuteto.tofu.init.registery.ItemRegister;

abstract public class TcItemLoader
{
    public static void loadAll()
    {
        new LoaderTofuItem().load();
        new LoaderMaterial().load();
        new LoaderConstruction().load();
        new LoaderDecoration().load();
        new LoaderFood().load();
        new LoaderCombat().load();
        new LoaderTool().load();
        new LoaderExternalItem().load();
    }

    abstract public void load();

    public <T extends Item> ItemRegister<T> $(String name, T item)
    {
        return new ItemRegister<T>(name, item);
    }
}
