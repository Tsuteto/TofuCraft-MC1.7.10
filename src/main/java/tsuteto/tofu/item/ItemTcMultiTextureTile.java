package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemMultiTexture;
import tsuteto.tofu.util.ItemUtils;

public class ItemTcMultiTextureTile extends ItemMultiTexture
{

	public ItemTcMultiTextureTile(Block par2Block, String[] par3ArrayOfStr) {
		super(par2Block, par2Block, par3ArrayOfStr);
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ItemUtils.getCreativeTabs(this);
	}
}
