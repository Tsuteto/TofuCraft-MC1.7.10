package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.util.TofuBlockUtils;

import java.util.Random;

public class BlockTofuStairs extends BlockStairs
{
    private boolean isFragile = false;

    public BlockTofuStairs(Block par2Block, int par3)
    {
        super(par2Block, par3);
        this.setCreativeTab(TcCreativeTabs.CONSTRUCTION);
        this.useNeighborBrightness = true;
    }

    public BlockStairs setFragile()
    {
        isFragile = true;
        this.setTickRandomly(true);
        return this;
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (isFragile)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        int meta = par1World.getBlockMetadata(par2, par3, par4);

        if (isFragile)
        {
            Block weightBlock = par1World.getBlock(par2, par3 + 1, par4);

            if (weightBlock != null)
            {
               if (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron)
               {
                   dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                   par1World.setBlockToAir(par2, par3, par4);
               }
            }
        }
    }
}
