package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemFoodSet;
import tsuteto.tofu.util.ModLog;

import java.util.Random;

public class BlockSprouts extends BlockCrops
{
    private IIcon[] icons;
    
    public BlockSprouts()
    {
        super();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        this.checkAndDropBlock(par1World, par2, par3, par4);

        int l = par1World.getBlockMetadata(par2, par3, par4);

        if (l < 7)
        {
            float f = this.getGrowthRate(par1World, par2, par3, par4);
            ModLog.debug("rate: %.1f", f);

            if (par5Random.nextInt((int)(25.0F / f) + 1) == 0)
            {
                ++l;
                par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
                ModLog.debug("growth: %d", l);
            }
        }
    }

    /**
     * Gets the growth rate for the crop. Setup to encourage rows by halving growth rate if there is diagonals, crops on
     * different sides that aren't opposing, and by adding growth for every crop next to this one (and for crop below
     * this one). Args: x, y, z
     */
    private float getGrowthRate(World world, int par2, int par3, int par4)
    {
        float f = 1.0F;

        Block j3 = world.getBlock(par2, par3 - 1, par4);

        if (j3 == Blocks.wool)
        {
            f = 3.0F;
            
            boolean hasWater = (
                    world.getBlock(par2 - 1, par3 - 1, par4    ).getMaterial() == Material.water ||
                    world.getBlock(par2 + 1, par3 - 1, par4    ).getMaterial() == Material.water ||
                    world.getBlock(par2,     par3 - 1, par4 - 1).getMaterial() == Material.water ||
                    world.getBlock(par2,     par3 - 1, par4 + 1).getMaterial() == Material.water);

            if (hasWater)
            {
                f = 25.0F;
            }
        }

        return f;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        if (par3 >= 0 && par3 < 256)
        {
            Block soil = par1World.getBlock(par2, par3 - 1, par4);
            return (par1World.getFullBlockLightValue(par2, par3, par4) < 10) &&
                   (soil != null && soil == Blocks.wool);
        }
        else
        {
            return false;
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 >= 0 && par2 < 7)
        {
            return this.icons[par2 >> 1];
        }
        else
        {
            return this.icons[3];
        }
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 1;
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return meta >= 7 ? 2 : 1;
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    @Override
    protected Item func_149866_i()
    {
        return TcItems.soybeans;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    @Override
    protected Item func_149865_P()
    {
        return TcItems.foodSet;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return par1 >= 2 ? this.func_149865_P() : this.func_149866_i();
    }
    
    @Override
    public int damageDropped(int par1)
    {
        return par1 >= 2 ? ItemFoodSet.sprouts.id : 0;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.icons = new IIcon[4];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:sprouts_" + i);
        }
    }

    @Override
    public boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.wool;
    }
}
