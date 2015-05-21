package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.material.TcMaterial;

import java.util.List;
import java.util.Random;

public class BlockTofuStepSimple extends BlockTofuStepBase
{
    public BlockTofuStepSimple(boolean par2)
    {
        super(par2, TcMaterial.tofu);
        this.setLightOpacity(0);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TcBlocks.tofuSingleSlabGlow);
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks register do not support subtypes. Blocks register cannot be harvested should return null.
     */
    @Override
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(this, 2, 0);
    }

    /**
     * Returns the slab block name with step type.
     */
    @Override
    public String func_150002_b(int par1)
    {
        return super.getUnlocalizedName();
    }
    
    /**
     * Takes a block ID, returns true if it's the same as the ID for a stone or wooden single slab.
     */
    @Override
    protected boolean isBlockSingleSlab(Block par0)
    {
        return par0 == TcBlocks.tofuSingleSlabGlow;
    }
    
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return Item.getItemFromBlock(TcBlocks.tofuSingleSlabGlow);
    }
    
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (isBlockSingleSlab(Block.getBlockFromItem(par1)))
        {
            super.getSubBlocks(par1, par2CreativeTabs, par3List);
        }
    }
}
