package tsuteto.tofu.block;

import net.minecraft.item.Item;
import tsuteto.tofu.item.TcItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class BlockTofuCake extends BlockCake
{
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconInner;
    
    protected BlockTofuCake()
    {
        super();
    }

    @SideOnly(Side.CLIENT)

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.tofuCake;
    }
    
    @Override
    public Block disableStats()
    {
        return super.disableStats();
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconTop : (par1 == 0 ? this.iconBottom : (par2 > 0 && par1 == 4 ? this.iconInner : this.blockIcon));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("tofucraft:tofuCake_side");
        this.iconInner = par1IconRegister.registerIcon("tofucraft:tofuCake_inner");
        this.iconTop = par1IconRegister.registerIcon("tofucraft:tofuCake_top");
        this.iconBottom = par1IconRegister.registerIcon("tofucraft:tofuCake_bottom");
    }
}
