package tsuteto.tofu.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.tileentity.TileEntityTfAntenna;

public class BlockTfAntenna extends BlockContainer4Directions
{
    public static int MAX_RADIUS = 0;
    public static final WaveFreq[] WAVE_LIST = new WaveFreq[4];
    public static final WaveFreq MEDIUMWAVE = new WaveFreq(0, 10);
    public static final WaveFreq ULTRAWAVE = new WaveFreq(1, 16);
    public static final WaveFreq SUPERWAVE = new WaveFreq(2, 24);

    private WaveFreq waveFreq;

    public BlockTfAntenna(WaveFreq waveFreq)
    {
        super(Material.circuits);
        this.waveFreq = waveFreq;
    }

    public WaveFreq getAntennaType()
    {
        return waveFreq;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return -1;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
        float size = 0.4375F;
        float half = size / 2;

        this.setBlockBounds(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, 1.0F, 0.5F + half);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);

        if (tile != null)
        {
            par5EntityPlayer.openGui(TofuCraftCore.instance, TcGuiHandler.GUIID_TF_ANTENNA, par1World, par2, par3, par4);
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int par1)
    {
        return new TileEntityTfAntenna();
    }

    public static class WaveFreq
    {
        public final int id;
        public final int radius;

        public WaveFreq(int id, int radius)
        {
            this.id = id;
            this.radius = radius;
            WAVE_LIST[id] = this;

            if (radius > MAX_RADIUS) MAX_RADIUS = radius;
        }

        public WaveFreq(int id)
        {
            this.id = id;
            this.radius = -1;
            WAVE_LIST[id] = this;
        }
    }
}
