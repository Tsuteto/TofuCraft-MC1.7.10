package tsuteto.tofu.eventhandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;

public class DispenserBehaviorHook
{
    private static final BehaviorDefaultDispenseItem defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();

    public static boolean onDispenseEmptyBucket(IBlockSource blockSource, ItemStack itemStack)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata()); // getFacing
        World world = blockSource.getWorld();
        int i = blockSource.getXInt() + enumfacing.getFrontOffsetX();
        int j = blockSource.getYInt() + enumfacing.getFrontOffsetY();
        int k = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
        Block id = world.getBlock(i, j, k);
        int l = world.getBlockMetadata(i, j, k);
        Item item = null;

        if (id == TcBlocks.soymilkStill && l == 0)
        {
            item = TcItems.bucketSoymilk;
        }
        else if (id == TcBlocks.soymilkHellStill && l == 0)
        {
            item = TcItems.bucketSoymilkHell;
        }
        else if (id == TcBlocks.soySauceStill && l == 0)
        {
            item = TcItems.bucketSoySauce;
        }

        if (item != null)
        {
            world.setBlockToAir(i, j, k);

            if (--itemStack.stackSize == 0)
            {
                itemStack.func_150996_a(item); // setItem
                itemStack.stackSize = 1;
            }
            else if (((TileEntityDispenser) blockSource.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0) // addItem
            {
                defaultDispenserItemBehavior.dispense(blockSource, new ItemStack(item));
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
