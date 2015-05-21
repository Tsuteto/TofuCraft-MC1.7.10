package tsuteto.tofu.block;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.entity.EntitySmokeTofuFX;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.texture.TcTextures;
import tsuteto.tofu.tileentity.TileEntityTfSaturator;
import tsuteto.tofu.util.BlockUtils;

import java.util.Random;

public class BlockTfSaturator extends BlockTfMachineBase
{
    private static boolean keepMachineInventory = false;
    private IIcon iconTop;

    public BlockTfSaturator(boolean isActive)
    {
        super(isActive);
        if (isActive) setTickRandomly(true);
    }

    @Override
    protected String getFrontIconActive()
    {
        return "tofucraft:tfSaturator_front_lit";
    }

    @Override
    protected String getFrontIconIdle()
    {
        return "tofucraft:tfSaturator_front";
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfSaturatorActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfSaturatorIdle;
    }

    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.iconTop;
        }
        else
        {
            return super.getIcon(par1, par2);
        }
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
            par1World.setBlock(par2, par3, par4, TcBlocks.tfSaturatorActive);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.tfSaturatorIdle);
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

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);

        if (tile != null)
        {
            par5EntityPlayer.openGui(TofuCraftCore.instance, TcGuiHandler.GUIID_TF_SATURATOR, par1World, par2, par3, par4);
        }

        return true;
    }

    @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (this.isActive)
        {
            this.spoutSmoke(par1World, par2, par3, par4, par5Random);
        }
    }

    @SideOnly(Side.CLIENT)
    public void spoutSmoke(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        for (int i = 0; i < par5Random.nextInt(3) + 1; i++)
        {
            double r = 0.8D + par5Random.nextDouble();
            double t = par5Random.nextDouble() * 2.0D * Math.PI;
            double d0 = (double) par2 + 0.5D;
            double d1 = (double) par3 + 0.3D + par5Random.nextDouble();
            double d2 = (double) par4 + 0.5D;
            double d3 = Math.sin(t) / 64.0D;
            double d4 = 0.05D + par5Random.nextDouble() * 0.05D;
            double d5 = Math.cos(t) / 64.0D;
            EntitySmokeTofuFX entityFX = new EntitySmokeTofuFX(par1World, d0, d1, d2, d3, d4, d5);

            entityFX.setParticleIcon(TcTextures.tofuSmoke);
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(entityFX);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityTfSaturator();
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        super.registerBlockIcons(par1IconRegister);
        this.iconTop = par1IconRegister.registerIcon(TofuCraftCore.resourceDomain + "tfSaturator_top");
    }
}
