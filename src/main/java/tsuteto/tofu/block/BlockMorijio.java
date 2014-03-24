package tsuteto.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.block.tileentity.TileEntityMorijio;
import tsuteto.tofu.item.TcItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class BlockMorijio extends BlockContainer
{

    public BlockMorijio()
    {
        super(Material.circuits);
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
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 7;
        float size = 0.3125F;
        float half = size / 2;

        switch (var5)
        {
//            case 1:
//            default:
//                this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
//                break;
//            case 2:
//                this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
//                break;
//            case 3:
//                this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
//                break;
//            case 4:
//                this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
//                break;
//            case 5:
//                this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
            case 1:
            default:
                this.setBlockBounds(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, size, 0.5F + half);
                break;
            case 2:
                this.setBlockBounds(0.5F - half, 0.5F - half, 1.0F - size, 0.5F + half, 0.5F + half, 1.0F);
                break;
            case 3:
                this.setBlockBounds(0.5F - half, 0.5F - half, 0.0F, 0.5F + half, 0.5F + half, size);
                break;
            case 4:
                this.setBlockBounds(0.5F + half, 0.5F - half, 0.5F - half, 1.0F, 0.5F + half, 0.5F + half);
                break;
            case 5:
                this.setBlockBounds(0.0F, 0.5F - half, 0.5F - half, size, 0.5F + half, 0.5F + half);
        }
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        int var6 = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World, int i)
    {
        return new TileEntityMorijio();
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.morijio;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return TcItems.morijio;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("wool_colored_white");
    }
}
