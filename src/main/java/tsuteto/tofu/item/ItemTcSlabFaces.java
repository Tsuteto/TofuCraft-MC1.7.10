package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemTcSlabFaces extends ItemTcBlock {
    private final boolean isFullBlock;

    /** Instance of BlockHalfSlab. */
    private final BlockSlab theHalfSlab;

    /** The double-slab block corresponding to this item. */
    private final BlockSlab doubleSlab;


    public ItemTcSlabFaces(Block par1, BlockSlab par2BlockHalfSlab, BlockSlab par3BlockHalfSlab, boolean par4)
    {
        super(par1);
        this.theHalfSlab = par2BlockHalfSlab;
        this.doubleSlab = par3BlockHalfSlab;
        this.isFullBlock = par4;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    
    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        return this.getIcon(new ItemStack(this), par1);
    }
    
    /**
     * Returns the metadata of the block register this Item (ItemBlock) can place
     */
    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.theHalfSlab.func_150002_b(par1ItemStack.getItemDamage()); // getFullSlabName
    }
    
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (this.isFullBlock)
        {
            return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
        }
        else if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            Block i1 = par3World.getBlock(par4, par5, par6);
            int j1 = par3World.getBlockMetadata(par4, par5, par6);
            int k1 = j1 & 3; // I only wanted to change this and...
            boolean flag = (j1 & 8) != 0;
    
            if ((par7 == 1 && !flag || par7 == 0 && flag) && i1 == this.theHalfSlab && k1 == par1ItemStack.getItemDamage())
            {
                if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6))
                        && par3World.setBlock(par4, par5, par6, this.doubleSlab, j1, 3)) // this
                {
                    par3World.playSoundEffect((par4 + 0.5F), (par5 + 0.5F), (par6 + 0.5F), this.doubleSlab.stepSound.getBreakSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getPitch() * 0.8F);
                    --par1ItemStack.stackSize;
                }
    
                return true;
            }
            else
            {
                return this.func_77888_a(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7) ? true : super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
            }
        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    
    /**
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
    public boolean func_150936_a(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack) // canPlaceItemBlockOnSide
    {
        int i1 = par2;
        int j1 = par3;
        int k1 = par4;
        Block l1 = par1World.getBlock(par2, par3, par4);
        int i2 = par1World.getBlockMetadata(par2, par3, par4);
        int j2 = i2 & 3; // this
        boolean flag = (i2 & 8) != 0;

        if ((par5 == 1 && !flag || par5 == 0 && flag) && l1 == this.theHalfSlab && j2 == par7ItemStack.getItemDamage())
        {
            return true;
        }
        else
        {
            if (par5 == 0)
            {
                --par3;
            }

            if (par5 == 1)
            {
                ++par3;
            }

            if (par5 == 2)
            {
                --par4;
            }

            if (par5 == 3)
            {
                ++par4;
            }

            if (par5 == 4)
            {
                --par2;
            }

            if (par5 == 5)
            {
                ++par2;
            }

            l1 = par1World.getBlock(par2, par3, par4);
            i2 = par1World.getBlockMetadata(par2, par3, par4);
            j2 = i2 & 3; // this
            flag = (i2 & 8) != 0;
            return l1 == this.theHalfSlab && j2 == par7ItemStack.getItemDamage() ? true : super.func_150936_a(par1World, i1, j1, k1, par5, par6EntityPlayer, par7ItemStack);
        }
    }

    private boolean func_77888_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
    {
        if (par7 == 0)
        {
            --par5;
        }

        if (par7 == 1)
        {
            ++par5;
        }

        if (par7 == 2)
        {
            --par6;
        }

        if (par7 == 3)
        {
            ++par6;
        }

        if (par7 == 4)
        {
            --par4;
        }

        if (par7 == 5)
        {
            ++par4;
        }

        Block i1 = par3World.getBlock(par4, par5, par6);
        int j1 = par3World.getBlockMetadata(par4, par5, par6);
        int k1 = j1 & 3; // this

        if (i1 == this.theHalfSlab && k1 == par1ItemStack.getItemDamage())
        {
            if (par3World.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(par3World, par4, par5, par6))
                    && par3World.setBlock(par4, par5, par6, this.doubleSlab, j1, 3)) // and this!!
            {
                par3World.playSoundEffect((par4 + 0.5F), (par5 + 0.5F), (par6 + 0.5F), this.doubleSlab.stepSound.getBreakSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
