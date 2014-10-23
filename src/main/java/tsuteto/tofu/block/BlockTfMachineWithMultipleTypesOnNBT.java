package tsuteto.tofu.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

abstract public class BlockTfMachineWithMultipleTypesOnNBT extends BlockTfMachineBase
{
    protected BlockTfMachineWithMultipleTypesOnNBT(boolean isActive)
    {
        super(isActive);
    }

    @Override
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (par6EntityPlayer.capabilities.isCreativeMode)
        {
            // Set 8 for no drops
            par5 |= 8;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5, 4);

        }

        // Force to drop items here before removing tileentity
        this.dropBlockAsItem(par1World, par2, par3, par4, par5, 0);

        super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if ((metadata & 8) == 0) // no drops when the metadata is 8
        {
            // Called twice, but drops items once when the tileentity is alive. Based on BlockSkull
            TileEntity te = world.getTileEntity(x, y, z);
            if (te != null)
            {
                Item drop = this.getItemDropped(metadata, world.rand, fortune);
                if (drop != null)
                {
                    ret.add(new ItemStack(drop, 1, this.getDamageValueFromTileEntity(world, x, y, z, te)));
                }
            }
        }
        return ret;
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        TileEntity tileEntity = par1World.getTileEntity(par2, par3, par4);
        return this.getDamageValueFromTileEntity(par1World, par2, par3, par4, tileEntity);
    }

    abstract public int getDamageValueFromTileEntity(World world, int x, int y, int z, TileEntity te);
}
