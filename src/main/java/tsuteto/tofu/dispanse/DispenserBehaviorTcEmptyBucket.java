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
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

public class DispenserBehaviorTcEmptyBucket extends BehaviorDefaultDispenseItem
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
        Block id = world.getBlock(i, j, k);
        Material material = world.getBlock(i, j, k).getMaterial();
        int l = world.getBlockMetadata(i, j, k);
        Item item;

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
        else if (Material.water.equals(material) && l == 0)
        {
            item = Items.water_bucket;
        }
        else
        {
            if (!Material.lava.equals(material) || l != 0)
            {
                return super.dispenseStack(par1IBlockSource, par2ItemStack);
            }

            item = Items.lava_bucket;
        }

        world.setBlockToAir(i, j, k);

        if (--par2ItemStack.stackSize == 0)
        {
            par2ItemStack.func_150996_a(item); // setItem
            par2ItemStack.stackSize = 1;
        }
        else if (((TileEntityDispenser)par1IBlockSource.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0) // addItem
        {
            this.defaultDispenserItemBehavior.dispense(par1IBlockSource, new ItemStack(item));
        }

        return par2ItemStack;
    }
}
