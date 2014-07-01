package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.item.TcItems;

public class BlockMisoBarrel extends BlockFermentable
{
	private IIcon iconTop;
	private IIcon iconBottom;
	
    public BlockMisoBarrel(Material par3Material)
    {
        super(par3Material);
        this.setTickRandomly(true);
    }

    public boolean isUnderWeight(IBlockAccess blockAccess, int x, int y, int z)
    {
        Block weightBlock = blockAccess.getBlock(x, y + 1, z);
        if (weightBlock != null)
        {
            return weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron;
        }
        else
        {
            return false;
        }
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelMiso;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return TcItems.barrelEmpty;
    }
    
    public boolean removeSoySauce(World world, int x, int y, int z)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        if (this.hasSoySauce(metadata))
        {
            world.setBlockMetadataWithNotify(x, y, z, 15, 2);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean hasSoySauce(int metadata)
    {
        return metadata == 7;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.iconTop;
        }
        else if (par1 == 0) // bottom
        {
            return this.iconBottom;
        }
        else
        {
            return this.blockIcon;
        }
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int metadata = this.getFermStep(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        return metadata == 7 ? 0x885511 : this.isUnderWeight(par1IBlockAccess, par2, par3, par4) ? 0xffd399 : 0xffffff;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
        this.iconTop = par1IconRegister.registerIcon("tofucraft:barrel_top");
        this.iconBottom = par1IconRegister.registerIcon("tofucraft:barrel_bottom");
    }

    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.miso, 6));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.salt, 3));
        list.add(new ItemStack(TcItems.kouji, 3));
    }

    @Override
    public boolean checkEnvironment(World world, int x, int y, int z)
    {
        return this.isUnderWeight(world, x, y, z);
    }
}
