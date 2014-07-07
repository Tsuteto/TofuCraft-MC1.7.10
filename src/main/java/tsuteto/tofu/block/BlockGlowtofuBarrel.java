package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import tsuteto.tofu.item.TcItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class BlockGlowtofuBarrel extends BlockBarrelBase
{
    public BlockGlowtofuBarrel(Material par3Material)
    {
        super(par3Material);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelGlowtofu;
    }

    @Override
    public boolean checkEnvironment(IBlockAccess blockAccess, int x, int y, int z)
    {
        return blockAccess.getBiomeGenForCoords(x, z).biomeID == BiomeGenBase.hell.biomeID;
    }

    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.tofuGlow, 3));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.tofuMomen, 3));
        list.add(new ItemStack(Items.glowstone_dust, 3));
    }
}
