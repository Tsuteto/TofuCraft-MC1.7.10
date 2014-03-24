package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.util.Utils;

public class ItemBlockBarrel extends ItemReed
{
    public ItemBlockBarrel(Block block)
    {
        super(block);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return Utils.getCreativeTabs(this);
    }
}
