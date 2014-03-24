package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class BlockNattoBed extends BlockFermentable
{
	private IIcon iconTop;
	
    public BlockNattoBed(int par2, Material par3Material)
    {
        super(par3Material);
        this.setTickRandomly(true);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 <= 1) // top/bottom
        {
            return this.iconTop;
        }
        else
        {
            return this.blockIcon;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("tofucraft:nattoBed_side");
        this.iconTop = par1IconRegister.registerIcon("tofucraft:nattoBed_top");
    }
    
    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.natto, 6));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.soybeans, 6));
        list.add(new ItemStack(Items.wheat, 3));
    }

    @Override
    public boolean checkEnvironment(World world, int x, int y, int z)
    {
        boolean isSnowEnabled = world.getBiomeGenForCoords(x, z).getEnableSnow();
        return isSnowEnabled && world.getBlockLightValue(x, y, z) >= 8 || !isSnowEnabled;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
