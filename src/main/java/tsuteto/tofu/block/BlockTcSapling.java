package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.TerrainGen;
import tsuteto.tofu.world.gen.feature.WorldGenApricotTrees;
import tsuteto.tofu.world.gen.feature.WorldGenTcTreesBase;
import tsuteto.tofu.world.gen.feature.WorldGenTofuTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public class BlockTcSapling extends BlockFlower
{
    public static final String[] WOOD_TYPES = new String[] {"apricot", "tofu"};
    private IIcon[] icons;

    protected BlockTcSapling(int par1)
    {
        super(par1);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean canReplace(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack)
    {
        if (par6ItemStack.getItemDamage() == 1)
        {
            Block block = par1World.getBlock(par2, par3 - 1, par4);
            return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) &&
                    (block == TcBlocks.tofuMomen || block == TcBlocks.tofuTerrain);
        }
        else
        {
            return this.canPlaceBlockOnSide(par1World, par2, par3, par4, par5);
        }
    }

    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
        if (var6 == 1)
        {
            Block block = par1World.getBlock(par2, par3 - 1, par4);
            return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) &&
                    (block == TcBlocks.tofuMomen || block == TcBlocks.tofuTerrain);
        }
        else
        {
            return super.canBlockStay(par1World, par2, par3, par4);
        }
    }



    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            super.updateTick(par1World, par2, par3, par4, par5Random);

            if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
            {
                int var6 = par1World.getBlockMetadata(par2, par3, par4);

                if ((var6 & 8) == 0)
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 8, 3);
                }
                else
                {
                    this.growTree(par1World, par2, par3, par4, par5Random);
                }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        par2 &= 3;
        return this.icons[par2 < WOOD_TYPES.length ? par2 : 0];
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4)) return;

        int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
        WorldGenTcTreesBase var7 = null;
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;

        if (var6 != 1)
        {
            // Apricot
            var7 = new WorldGenApricotTrees(true);
            var7.setFruit(1, 5);
        }
        else
        {
            // Tofu
            var7 = new WorldGenTofuTrees(true);
        }

        if (var10)
        {
            par1World.setBlockToAir(par2 + var8, par3, par4 + var9);
            par1World.setBlockToAir(par2 + var8 + 1, par3, par4 + var9);
            par1World.setBlockToAir(par2 + var8, par3, par4 + var9 + 1);
            par1World.setBlockToAir(par2 + var8 + 1, par3, par4 + var9 + 1);
        }
        else
        {
            par1World.setBlockToAir(par2, par3, par4);
        }

        if (!var7.generate(par1World, par5Random, par2 + var8, par3, par4 + var9))
        {
            if (var10)
            {
                par1World.setBlock(par2 + var8, par3, par4 + var9, this, var6, 3);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, this, var6, 3);
                par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, this, var6, 3);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, this, var6, 3);
            }
            else
            {
                par1World.setBlock(par2, par3, par4, this, var6, 3);
            }
        }
    }

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
    {
        return par1World.getBlock(par2, par3, par4) == this && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1)
    {
        return par1 & 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.icons = new IIcon[WOOD_TYPES.length];
        for (int i = 0; i < WOOD_TYPES.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:sapling_" + WOOD_TYPES[i]);
        }
    }
}
