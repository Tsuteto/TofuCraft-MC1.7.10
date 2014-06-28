package tsuteto.tofu.dispanse;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;

public class DispenserBehaviorNigari extends BehaviorDefaultDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();

    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    @Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(par1IBlockSource.getBlockMetadata()); // getFacing
        World world = par1IBlockSource.getWorld();
        int i = par1IBlockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = par1IBlockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = par1IBlockSource.getZInt() + enumfacing.getFrontOffsetZ();
        Block block = world.getBlock(i, j, k);
        //Material material = world.getBlock(i, j, k).getMaterial();
        //int l = world.getBlockMetadata(i, j, k);
        Item item;

        if (block == TcBlocks.soymilkStill)
        {
            world.setBlock(i, j, k, TcBlocks.tofuKinu);
            item = Items.glass_bottle;

            if (--par2ItemStack.stackSize == 0)
            {
                par2ItemStack.func_150996_a(item); // setItem
                par2ItemStack.stackSize = 1;
            }
            else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0) // addItem
            {
                this.defaultDispenserItemBehavior.dispense(par1IBlockSource, new ItemStack(item));
            }
        }
        else
        {
            super.dispenseStack(par1IBlockSource, par2ItemStack);
        }

        return par2ItemStack;
    }
}
