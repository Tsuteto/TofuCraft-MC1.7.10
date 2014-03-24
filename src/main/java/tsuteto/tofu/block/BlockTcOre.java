package tsuteto.tofu.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTcOre extends TcBlock
{
    private Item itemContained;
    private int itemDmgContained;
    private int minXp;
    private int maxXp;

    public BlockTcOre(int minXp, int maxXp)
    {
        this(Material.rock, minXp, maxXp);
    }
    public BlockTcOre(Material material, int minXp, int maxXp)
    {
        super(material);
        this.minXp = minXp;
        this.maxXp = maxXp;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BlockTcOre setItemContained(ItemStack itemstack)
    {
        this.itemContained = itemstack.getItem();
        this.itemDmgContained = itemstack.getItemDamage();
        return this;
    }

    public BlockTcOre setItemContained(Item item)
    {
        this.itemContained = item;
        this.itemDmgContained = 0;
        return this;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return this.itemContained;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        if (par1 > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, par2Random, par1))
        {
            int j = par2Random.nextInt(par1 + 2) - 1;

            if (j < 0)
            {
                j = 0;
            }

            return this.quantityDropped(par2Random) * (j + 1);
        }
        else
        {
            return this.quantityDropped(par2Random);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (this.getItemDropped(par5, par1World.rand, par7) != Item.getItemFromBlock(this))
        {
            int j1 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
            this.dropXpOnBlockBreak(par1World, par2, par3, par4, j1);
        }
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    @Override
    public int damageDropped(int par1)
    {
        return itemDmgContained;
    }
}
