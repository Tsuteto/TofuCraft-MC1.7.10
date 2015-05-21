package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

public class ItemTcSeeds extends ItemSeeds {

	public ItemTcSeeds(Block par2, Block par3) {
		super(par2, par3);
	}

    @Override
    public ItemTcSeeds setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        this.setTextureName(name);
        return this;
    }
}
