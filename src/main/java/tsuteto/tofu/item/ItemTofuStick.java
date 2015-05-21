package tsuteto.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tsuteto.tofu.Settings;
import tsuteto.tofu.init.TcBlocks;

public class ItemTofuStick extends TcItem
{
    public ItemTofuStick()
    {
        super();
        this.setMaxDamage(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        int currDim = player.dimension;
        if (currDim == Settings.tofuDimNo || currDim == 0)
        {
            if (!world.isRemote)
            {
                MovingObjectPosition mpos = this.getMovingObjectPositionFromPlayer(world, player, false);

                if (mpos != null && mpos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int i = mpos.blockX;
                    int j = mpos.blockY;
                    int k = mpos.blockZ;
                    boolean isSuccess = this.activate(itemstack, player, world, i, j, k, mpos.sideHit);
                }
            }
            else
            {
                // Emit particles
                for (int var1 = 0; var1 < 16; ++var1)
                {
                    double mx = (itemRand.nextFloat() * 2.0F - 1.0F);
                    double my = (itemRand.nextFloat() * 2.0F - 1.0F);
                    double mz = (itemRand.nextFloat() * 2.0F - 1.0F);
                    if (mx * mx + my * my + mz * mz <= 1.0D)
                    {
                        Vec3 lookVec = player.getLookVec();
                        world.spawnParticle("crit", player.posX + lookVec.xCoord, player.posY + lookVec.yCoord, player.posZ + lookVec.zCoord, mx, my + 0.2f, mz);
                    }
                }
            }
        }
        return itemstack;
    }

    public boolean activate(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7)
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

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else
        {
            if (par3World.isAirBlock(par4, par5, par6))
            {
                TcBlocks.tofuPortal.tryToCreatePortal(par3World, par4, par5, par6);
            }
            return true;
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }
}