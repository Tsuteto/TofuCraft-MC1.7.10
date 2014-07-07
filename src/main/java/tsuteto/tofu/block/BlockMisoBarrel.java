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

public class BlockMisoBarrel extends BlockBarrelBase
{
    public BlockMisoBarrel(Material par3Material)
    {
        super(par3Material);
        this.setTickRandomly(true);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelMiso;
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
    public boolean checkEnvironment(IBlockAccess blockAccess, int x, int y, int z)
    {
        return this.isUnderWeight(blockAccess, x, y, z);
    }
}
