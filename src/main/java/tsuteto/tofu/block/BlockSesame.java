package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.item.TcItems;

import java.util.Random;

public class BlockSesame extends BlockCrops
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockSesame()
    {
        super();
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 < 7)
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
        return 1;
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    @Override
    protected Item func_149866_i() // getSeedItem
    {
        return TcItems.sesame;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    @Override
    protected Item func_149865_P() // getCropItem
    {
        return TcItems.sesame;
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
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:sesame_" + i);
        }
    }
}
