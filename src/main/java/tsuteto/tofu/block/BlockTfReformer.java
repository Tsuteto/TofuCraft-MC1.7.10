package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidContainerItem;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.tileentity.TileEntityTfReformer;

import java.util.List;

/**
 * Tofu Force Storage block
 * 
 * @author Tsuteto
 *
 */
public class BlockTfReformer extends BlockTfMachineWithMultipleTypesOnNBT
{
    public static final String[] blockNames = new String[]{
            TileEntityTfReformer.Model.simple.name(), TileEntityTfReformer.Model.mix.name()};

    private static boolean keepMachineInventory = false;
    private IIcon iconFront2;

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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        super.registerBlockIcons(par1IconRegister);
        this.iconFront2 = par1IconRegister.registerIcon(this.isActive ? "tofucraft:tfReformer2_front_lit" : "tofucraft:tfReformer2_front");
    }

    /**
     * For blocks placed
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
    {
        int par1 = p_149673_5_;
        int par2 = p_149673_1_.getBlockMetadata(p_149673_2_, p_149673_3_, p_149673_4_);
        TileEntityTfReformer tileEntity = (TileEntityTfReformer)p_149673_1_.getTileEntity(p_149673_2_, p_149673_3_, p_149673_4_);
        IIcon front = tileEntity.model == TileEntityTfReformer.Model.mix ? iconFront2 : iconFront;

        if (par1 == 1) // top
        {
            return this.blockIcon;
        }
        else if (par1 == 0) // bottom
        {
            return this.blockIcon;
        }
        else if (par1 == 3 && par2 == 0) // for inventory
        {
            return front;
        }
        else
        {
            return par1 != (par2 & 7) ? this.blockIcon // side & back
                    : front; // front
        }
    }

    /**
     * For blocks in inventory
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.blockIcon;
        }
        else if (par1 == 0) // bottom
        {
            return this.blockIcon;
        }
        else if (par1 == 3)
        {
            return TileEntityTfReformer.getModelById(par2) == TileEntityTfReformer.Model.mix ? iconFront2 : iconFront;
        }
        else
        {
            return this.blockIcon;
        }
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityTfReformer tile = (TileEntityTfReformer)par1World.getTileEntity(par2, par3, par4);
        
        ItemStack itemEquipped = par5EntityPlayer.getCurrentEquippedItem();

        if (tile != null && (itemEquipped == null || !(itemEquipped.getItem() instanceof IFluidContainerItem)))
        {
            int guiId = tile.model == TileEntityTfReformer.Model.mix ? TcGuiHandler.GUIID_TF_REFORMER2 : TcGuiHandler.GUIID_TF_REFORMER;
            par5EntityPlayer.openGui(TofuCraftCore.instance, guiId, par1World, par2, par3, par4);
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

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
    }

    @Override
    public int getDamageValueFromTileEntity(World world, int x, int y, int z, TileEntity te)
    {
        return ((TileEntityTfReformer)te).model.id;
    }
}
