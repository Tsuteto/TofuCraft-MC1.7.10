package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidContainerItem;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.tileentity.TileEntityTfReformer;
import tsuteto.tofu.tileentity.TileEntityTfStorage;

/**
 * Tofu Force Storage block
 * 
 * @author Tsuteto
 *
 */
public class BlockTfReformer extends BlockTfMachineBase
{
    private static boolean keepMachineInventory = false;

    protected BlockTfReformer(boolean par2)
    {
        super(par2);
    }

    @Override
    protected String getFrontIconActive()
    {
        return "tofucraft:tfReformer_front_lit";
    }

    @Override
    protected String getFrontIconIdle()
    {
        return "tofucraft:tfReformer_front";
    }

    @Override
    protected Block getBlockActive()
    {
        return TcBlocks.tfReformerActive;
    }

    @Override
    protected Block getBlockIdle()
    {
        return TcBlocks.tfReformerIdle;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tile = par1World.getTileEntity(par2, par3, par4);
        
        ItemStack itemEquipped = par5EntityPlayer.getCurrentEquippedItem();

        if (tile != null && (itemEquipped == null || !(itemEquipped.getItem() instanceof IFluidContainerItem)))
        {
            par5EntityPlayer.openGui(TofuCraftCore.instance, TcGuiHandler.GUIID_TF_REFORMER, par1World, par2, par3, par4);
        }

        return true;
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
            par1World.setBlock(par2, par3, par4, TcBlocks.tfReformerActive);
        }
        else
        {
            par1World.setBlock(par2, par3, par4, TcBlocks.tfReformerIdle);
        }

        keepMachineInventory = false;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);

        if (var6 != null)
        {
            var6.validate();
            par1World.setTileEntity(par2, par3, par4, var6);
        }
    }

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
        return new TileEntityTfReformer();
    }
}
