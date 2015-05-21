package tsuteto.tofu.block;

import net.minecraft.block.BlockFalling;
import net.minecraft.item.Item;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemFoodSet;
import tsuteto.tofu.material.TcMaterial;

import java.util.Random;

public class BlockTofuMinced extends BlockFalling implements ITofuScoopable
{
    public BlockTofuMinced()
    {
        super(TcMaterial.tofu);
        this.setCreativeTab(TcCreativeTabs.CONSTRUTION);
    }

    @Override
    public int quantityDropped(Random par1Random)
    {
        return 4;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return TcItems.foodSet;
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        return ItemFoodSet.tofuMinced.id;
    }
}
