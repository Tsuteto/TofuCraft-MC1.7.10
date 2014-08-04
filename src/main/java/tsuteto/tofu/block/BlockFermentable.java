package tsuteto.tofu.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItem;

public abstract class BlockFermentable extends TcBlock
{
    private int fermRate;

    public BlockFermentable(Material par2Material)
    {
        super(par2Material);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (this.checkEnvironment(par1World, par2, par3, par4))
        {
            int meta = par1World.getBlockMetadata(par2, par3, par4);
            int fermStep = getFermStep(meta);
            int extra = meta & 8;

            if (fermStep < 7 && par5Random.nextInt(fermRate) == 0)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, ++fermStep | extra, 3);
            }
        }
    }
    
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) // getBlockDropped
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
        if (this.getFermStep(metadata) == 7)
        {
            this.addFermentedItem(ret);
        }
        else
        {
            this.addIngredients(ret);
        }
        return ret;
    }
    
    public abstract void addFermentedItem(List list);
    
    public abstract void addIngredients(List list);
    
    public abstract boolean checkEnvironment(World world, int x, int y, int z);
    
    public Block setFermRate(int rate)
    {
        this.fermRate = rate;
        return this;
    }
    
    public int getFermStep(int metadata)
    {
        return metadata & 7;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int step = this.getFermStep(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        return step == 7 ? 0xcb944b : 0xffffff;
    }

}
