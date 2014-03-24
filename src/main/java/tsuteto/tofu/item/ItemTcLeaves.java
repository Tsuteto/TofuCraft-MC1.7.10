package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tsuteto.tofu.block.BlockTcLeaves;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.block.TcBlocks;

public class ItemTcLeaves extends ItemTcBlock
{
    public ItemTcLeaves(Block par1)
    {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    /**
     * Returns the metadata of the block register this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1)
    {
        return par1 | 4;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return TcBlocks.tcLeaves.getIcon(0, par1);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

        if (var2 < 0 || var2 >= BlockTcLeaves.LEAF_TYPES.length)
        {
            var2 = 0;
        }

        return super.getUnlocalizedName() + "." + BlockTcLeaves.LEAF_TYPES[var2];
    }

}
