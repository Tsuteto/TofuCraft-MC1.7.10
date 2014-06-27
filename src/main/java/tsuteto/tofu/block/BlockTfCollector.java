package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.tileentity.TileEntityTfCollector;

import java.util.Random;

public class BlockTfCollector extends BlockTfMachineBase
{
    private static boolean keepMachineInventory = false;

    protected BlockTfCollector()
    {
        super(true);
    }

    @Override
    protected String getFrontIconActive()
    {
        return "tofucraft:tfCollector";
    }

    @Override
    protected String getFrontIconIdle()
    {
        return "tofucraft:tfCollector";
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfCollector;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfCollector;
    }

    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.iconFront;
        }
        else if (par1 == 0) // bottom
        {
            return this.blockIcon;
        }
        else
        {
            return this.iconFront; // side
        }
    }

    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6, keepMachineInventory);
    }

    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfCollector();
    }
}
