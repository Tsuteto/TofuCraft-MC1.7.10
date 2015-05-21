package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import tsuteto.tofu.creativetabs.TcCreativeTabs;

abstract public class BlockTofuStepBase extends BlockSlab
{
    public BlockTofuStepBase(boolean par2, Material par3Material)
    {
        super(par2, par3Material);
        this.setCreativeTab(TcCreativeTabs.CONSTRUTION);
        this.setLightOpacity(0);
    }
    
    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (this.field_150004_a) // isDoubleSlab
        {
            return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
        }
        else if (par5 != 1 && par5 != 0 && !super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5))
        {
            return false;
        }
        else
        {
            int i1 = par2 + Facing.offsetsXForSide[Facing.oppositeSide[par5]];
            int j1 = par3 + Facing.offsetsYForSide[Facing.oppositeSide[par5]];
            int k1 = par4 + Facing.offsetsZForSide[Facing.oppositeSide[par5]];
            boolean flag = (par1IBlockAccess.getBlockMetadata(i1, j1, k1) & 8) != 0;
            return flag ? (par5 == 0 ? true : (par5 == 1 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlock(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0)) : (par5 == 1 ? true : (par5 == 0 && super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) ? true : !isBlockSingleSlab(par1IBlockAccess.getBlock(par2, par3, par4)) || (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0));
        }
    }

    abstract protected boolean isBlockSingleSlab(Block par0);
}
