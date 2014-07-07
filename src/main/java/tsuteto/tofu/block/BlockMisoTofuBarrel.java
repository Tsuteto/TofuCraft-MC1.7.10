package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class BlockMisoTofuBarrel extends BlockBarrelBase
{
    public BlockMisoTofuBarrel(Material par3Material)
    {
        super(par3Material);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelMisoTofu;
    }

    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.tofuMiso, 3));
        list.add(new ItemStack(TcItems.miso, 2));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.tofuMomen, 3));
        list.add(new ItemStack(TcItems.miso, 3));
    }

    @Override
    public boolean checkEnvironment(IBlockAccess blockAccess, int x, int y, int z)
    {
        return true;
    }
}
