package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.Utils;

import java.util.List;

public class BlockAdvTofuGemBarrel extends BlockBarrelBase
{
    public BlockAdvTofuGemBarrel(Material par3Material)
    {
        super(par3Material);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelAdvTofuGem;
    }

    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.materials, 1, ItemTcMaterials.advTofuGem.id));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id));
        list.add(new ItemStack(Blocks.redstone_block, 1));
    }

    @Override
    public boolean checkEnvironment(IBlockAccess blockAccess, int x, int y, int z)
    {
        return blockAccess.getBlock(x, y + 1, z) == Blocks.anvil
                && (blockAccess.getBlockMetadata(x, y, z) & 8) == 8;
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        Utils.onNeighborBlockChange_RedstoneSwitch(this, p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
}
