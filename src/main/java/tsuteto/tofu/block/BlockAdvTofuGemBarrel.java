package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.util.BlockUtils;

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
        list.add(ItemTcMaterials.advTofuGem.getStack());
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(ItemTcMaterials.tofuGem.getStack(3));
        list.add(new ItemStack(Items.redstone, 3));
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
        BlockUtils.onNeighborBlockChange_RedstoneSwitch(this, p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
}
