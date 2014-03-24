package tsuteto.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.material.TcMaterial;

public class BlockYuba extends Block
{
    protected BlockYuba()
    {
        super(TcMaterial.tofu);
        this.setTickRandomly(true);
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return RenderYubaBlock.renderId;
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
        par2EntityPlayer.addExhaustion(0.025F);

        if (this.canSilkHarvest(par1World, par2EntityPlayer, par3, par4, par5, par6) && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
        {
            this.dropYuba(par1World, par3, par4, par5);
        }
        else
        {
            ItemStack equipped = par2EntityPlayer.getCurrentEquippedItem();
            if (equipped != null && equipped.getItem() == Items.stick)
            {
                this.dropYuba(par1World, par3, par4, par5);
            }
        }
    }
    
    protected void dropYuba(World par1World, int par3, int par4, int par5)
    {
        this.dropBlockAsItem(par1World, par3, par4, par5, new ItemStack(TcBlocks.yuba));
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return par3 >= 0 && par3 < 256 && par1World.getBlock(par2, par3 - 1, par4).getMaterial() == Material.water;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
