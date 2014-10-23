package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.world.World;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.util.ItemUtils;

public class ItemTcBucket extends ItemBucket {
    public final Block isFull;

	public ItemTcBucket(Block par2) {
		super(par2);
        this.isFull = par2;
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ItemUtils.getCreativeTabs(this);
	}

    @Override
    public boolean tryPlaceContainedLiquid(World par1World, int par8, int par9, int par10)
    {
        if (this.isFull == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = par1World.getBlock(par8, par9, par10).getMaterial();
            boolean isSolid = material.isSolid();
            if (!par1World.isAirBlock(par8, par9, par10) && isSolid)
            {
                return false;
            }
            else
            {
                if (par1World.provider.isHellWorld && this.isFull != TcBlocks.soymilkHellStill)
                {
                    par1World.playSoundEffect((par8 + 0.5F), (par9 + 0.5F), (par10 + 0.5F), "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);

                    for (int l = 0; l < 8; ++l)
                    {
                        par1World.spawnParticle("largesmoke", par8 + Math.random(), par9 + Math.random(), par10 + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
                }
                else
                {
                    if (!par1World.isRemote && !isSolid && !material.isLiquid())
                    {
                        par1World.func_147480_a(par8, par9, par10, true);
                    }
                    par1World.setBlock(par8, par9, par10, this.isFull, 0, 3);
                }

                return true;
            }
        }
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        this.setTextureName(name);
        return this;
    }
}
