package tsuteto.tofu.item;

import net.minecraft.item.Item;
import tsuteto.tofu.block.BlockTofuBase;
import tsuteto.tofu.data.TofuInfo;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

public enum TofuMaterial
{
    kinu,
    momen,
    ishi,
    metal,
    grilled,
    dried,
    friedPouch,
    fried,
    egg,
    annin,
    sesame,
    zunda,
    strawberry,
    hell,
    glow,
    diamond,
    miso;

    TofuMaterial()
    {
    }

    public BlockTofuBase getBlock()
    {
        return this.getBlockInfo().getBlock();
    }

    public TofuInfo getBlockInfo()
    {
        return TcBlocks.tofuInfoMap.get(this);
    }

    public Item getItem()
    {
        return TcItems.tofuItems.get(this);
    }

    public static TofuMaterial get(int id)
    {
        return values()[id];
    }

    public int id()
    {
        return this.ordinal();
    }
}
