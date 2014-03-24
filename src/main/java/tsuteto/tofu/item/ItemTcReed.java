package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemReed;
import tsuteto.tofu.util.Utils;

public class ItemTcReed extends ItemReed {
	
	public ItemTcReed(Block par2Block) {
		super(par2Block);
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return Utils.getCreativeTabs(this);
	}

}
