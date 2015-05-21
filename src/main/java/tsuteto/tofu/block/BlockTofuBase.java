package tsuteto.tofu.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.material.TcMaterial;

import java.util.Random;

public abstract class BlockTofuBase extends TcBlock
{
    public BlockTofuBase()
    {
        super(TcMaterial.tofu);
    }

    public BlockTofuBase(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        TcBlocks.tofuBlockMap.put(tofuMaterial, this);
    }

    /** Whether the tofu can be scooped with Tofu Scoop */
    private boolean scoopable = true;

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 4;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    abstract public Item getItemDropped(int par1, Random par2Random, int par3);

    public BlockTofuBase setScoopable(boolean b)
    {
        this.scoopable = b;
        return this;
    }

    public boolean isScoopable()
    {
        return this.scoopable;
    }


    public ItemStack createScoopedBlockStack()
    {
        return new ItemStack(this);
    }
}
