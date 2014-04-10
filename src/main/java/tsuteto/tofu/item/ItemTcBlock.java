package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import tsuteto.tofu.util.ItemUtils;

public class ItemTcBlock extends ItemBlock {

	public ItemTcBlock(Block par1) {
		super(par1);
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ItemUtils.getCreativeTabs(this);
	}
}
