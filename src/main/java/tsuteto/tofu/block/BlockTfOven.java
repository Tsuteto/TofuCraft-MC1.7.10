package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.tileentity.TileEntityTfOven;
import tsuteto.tofu.util.BlockUtils;

public class BlockTfOven extends BlockTfMachineBase
{
    private static boolean keepMachineInventory = false;

    protected BlockTfOven(boolean isActive)
    {
        super(isActive);
        setGuiScreen(TcGuiHandler.GUIID_TF_OVEN);
    }

    @Override
    protected String getFrontIconActive()
    {
        return "tofucraft:tfOven_front_lit";
    }

    @Override
    protected String getFrontIconIdle()
    {
        return "tofucraft:tfOven_front";
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfOvenActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfOvenIdle;
    }

    /**
     * Update which block ID the furnace is using depending on whether or not it is burning
     */
    public static void updateMachineState(boolean par0, World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity var6 = par1World.getTileEntity(par2, par3, par4);
        keepMachineInventory = true;

        if (par0)
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.tfOvenActive);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.tfOvenIdle);
        }

        keepMachineInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);

        if (var6 != null)
        {
            var6.validate();
            par1World.setTileEntity(par2, par3, par4, var6);
        }
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        BlockUtils.onNeighborBlockChange_RedstoneSwitch(this, p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6, keepMachineInventory);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfOven();
    }
}
