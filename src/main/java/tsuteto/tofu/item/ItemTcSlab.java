package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSlab;
import tsuteto.tofu.block.BlockTofuStep;
import tsuteto.tofu.block.BlockTofuStepFaces;
import tsuteto.tofu.block.BlockTofuStepSimple;
import tsuteto.tofu.util.Utils;

public class ItemTcSlab extends ItemSlab {

	public ItemTcSlab(Block par1, BlockTofuStep par2BlockHalfSlab, BlockTofuStep par3BlockHalfSlab, Boolean par4) {
		super(par1, par2BlockHalfSlab, par3BlockHalfSlab, par4);
	}

    public ItemTcSlab(Block par1, BlockTofuStepFaces par2BlockHalfSlab, BlockTofuStepFaces par3BlockHalfSlab, Boolean par4) {
        super(par1, par2BlockHalfSlab, par3BlockHalfSlab, par4);
    }

    public ItemTcSlab(Block par1, BlockTofuStepSimple par2BlockHalfSlab, BlockTofuStepSimple par3BlockHalfSlab, Boolean par4) {
        super(par1, par2BlockHalfSlab, par3BlockHalfSlab, par4);
    }

    @Override
	public CreativeTabs[] getCreativeTabs() {
		return Utils.getCreativeTabs(this);
	}
}
