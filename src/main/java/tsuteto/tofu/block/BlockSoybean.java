package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import tsuteto.tofu.init.TcItems;

import java.util.Random;

public class BlockSoybean extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockSoybean()
    {
        super();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 < 5)
        {
            return this.icons[par2 >> 1];
        }
        else if (par2 < 7)
        {
            return this.icons[3];
        }
        else
        {
            return this.icons[4];
        }
    }
    
    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        if (meta == 5 || meta == 6)
        {
            int ret = 1;
            for (int n = 0; n < 3 + fortune; n++)
            {
                if (random.nextInt(15) <= meta)
                {
                    ret++;
                }
            }
            return ret;
        }
        else
        {
            return 1;
        }
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
        return TcItems.soybeans;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return par1 >= 7 ? this.func_149865_P() : par1 >= 5 ? TcItems.edamame : this.func_149866_i();
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
        this.icons = new IIcon[5];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:soybean_" + i);
        }
    }
}
