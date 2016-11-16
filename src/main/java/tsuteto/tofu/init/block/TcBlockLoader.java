package tsuteto.tofu.init.block;

import net.minecraft.block.Block;
import tsuteto.tofu.init.registery.BlockRegister;

abstract public class TcBlockLoader
{
    public static void loadAll()
    {
        new LoaderTofuBlock().load();
        new LoaderConstructionBlock().load();
        new LoaderDecorationBlock().load();
        new LoaderExternalBlock().load();
    }

    abstract public void load();

    public <T extends Block> BlockRegister<T> $(String name, T block)
    {
        return new BlockRegister<T>(name, block);
    }
}
