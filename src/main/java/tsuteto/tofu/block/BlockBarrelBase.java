package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItems;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

abstract public class BlockBarrelBase extends BlockFermentable
{
	private IIcon iconTop;
	private IIcon iconBottom;

//    private static final Field fldChunkCacheWorldObj = ReflectionHelper.findField(ChunkCache.class, "field_72815_e", "worldObj");

    public BlockBarrelBase(Material par3Material)
    {
        super(par3Material);
    }

    public boolean isUnderWeight(IBlockAccess blockAccess, int x, int y, int z)
    {
        Block weightBlock = blockAccess.getBlock(x, y + 1, z);
        Block baseBlock = blockAccess.getBlock(x, y - 1, z);

        boolean isWeightValid = weightBlock != null
                && (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron);

//        float baseHardness;
//        if (blockAccess instanceof ChunkCache)
//        {
//            try
//            {
//                baseHardness = baseBlock.getBlockHardness((World)fldChunkCacheWorldObj.get(blockAccess), x, y, z);
//            }
//            catch (IllegalAccessException e)
//            {
//                throw new RuntimeException("Failed to call worldObj in ChunkCache", e);
//            }
//        }
//        else if (blockAccess instanceof World)
//        {
//            baseHardness = baseBlock.getBlockHardness((World)blockAccess, x, y, z);
//        }
//        else
//        {
//            baseHardness = 0.0F;
//        }
//        boolean isBaseValid = baseBlock.isNormalCube() && (baseHardness >= 1.0F || baseHardness < 0.0F);
        boolean isBaseValid = baseBlock.isNormalCube();

        return isWeightValid && isBaseValid;
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelMisoTofu;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return TcItems.barrelEmpty;
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

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int metadata = this.getFermStep(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        return metadata == 7 ? 0x885511 : this.checkEnvironment(par1IBlockAccess, par2, par3, par4) ? 0xffd399 : 0xffffff;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
        this.iconTop = par1IconRegister.registerIcon("tofucraft:barrel_top");
        this.iconBottom = par1IconRegister.registerIcon("tofucraft:barrel_bottom");
    }

    abstract public boolean checkEnvironment(IBlockAccess blockAccess, int x, int y, int z);

    @Override
    public boolean checkEnvironment(World world, int x, int y, int z)
    {
        return this.checkEnvironment((IBlockAccess) world, x, y, z);
    }
}
