package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemReed;
import tsuteto.tofu.util.ItemUtils;

public class ItemBlockBarrel extends ItemReed
{
    public ItemBlockBarrel(Block block)
    {
        super(block);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return ItemUtils.getCreativeTabs(this);
    }
}
