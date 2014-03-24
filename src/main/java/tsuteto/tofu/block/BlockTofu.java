package tsuteto.tofu.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.util.ModLog;

public class BlockTofu extends BlockTofuBase
{
    private boolean isFragile = false;
    private boolean canDrain = false;
    private boolean canFreeze = false;
    private int drainRate;

    public BlockTofu(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BlockTofu setFragile()
    {
        isFragile = true;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setDrain(int rate)
    {
        this.canDrain = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    public BlockTofu setFreeze(int rate)
    {
        this.canFreeze = true;
        this.drainRate = rate;
        this.setTickRandomly(true);
        return this;
    }

    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (!par1World.isRemote && isFragile)
        {
            if (par6 > 0.5F)
            {
                if (!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    return;
                }
                this.onEntityWeightedBlock(par1World, par5Entity);
            }
        }
    }

    private void onEntityWeightedBlock(World world, Entity entity)
    {
        int i = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
        int j = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
        int k = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
        int l = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
        int i1 = MathHelper.floor_double(entity.boundingBox.maxY - 0.001D);
        int j1 = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

        if (entity.worldObj.checkChunksExist(i, j, k, l, i1, j1))
        {
            for (int k1 = i; k1 <= l; ++k1)
            {
                for (int l1 = j; l1 <= i1; ++l1)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        int bx = k1;
                        int by = l1 - 1;
                        int bz = i2;
                        if (world.getBlock(bx, by, bz) == this)
                        {
                            this.collapseBlock(entity, world, bx, by, bz);
                        }
                    }
                }
            }
        }
    }

    private void collapseBlock(Entity entity, World world, int x, int y, int z)
    {
        dropBlockAsItem(world, x, y, z, 0, 0);
        world.setBlockToAir(x, y, z);

        if (entity instanceof EntityPlayer)
        {
            TcAchievementMgr.achieve((EntityPlayer)entity, Key.tofuMental);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (isFragile || canDrain)
        {
            Block weightBlock = par1World.getBlock(par2, par3 + 1, par4);

            if (weightBlock != null)
            {
               if (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron)
               {

                   int drainStep = par1World.getBlockMetadata(par2, par3, par4);

                    if (isFragile)
                    {
                        dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                        par1World.setBlockToAir(par2, par3, par4);
                    }
                    else if (canDrain)
                    {
                        if (drainStep < 7 && par5Random.nextInt((drainRate)) == 0)
                        {
                            ++drainStep;
                            ModLog.debug(drainStep);
                            par1World.setBlockMetadataWithNotify(par2, par3, par4, drainStep, 2);
                        }
                        else if (drainStep == 7 && par5Random.nextInt((2 * drainRate)) == 0)
                        {
                            Block newBlock;
                            if (this == TcBlocks.tofuMomen)
                            {
                                newBlock = TcBlocks.tofuIshi;
                            }
                            else if (this == TcBlocks.tofuIshi)
                            {
                                newBlock = TcBlocks.tofuMetal;
                            }
                            else
                            {
                                newBlock = this;
                            }

                            par1World.setBlock(par2, par3, par4, newBlock, 0, 3);
                        }
                    }
                }
            }
        }
        if (canFreeze)
        {
            if (isValidPlaceForDriedTofu(par1World, par2, par3, par4))
            {
                int freezeStep = par1World.getBlockMetadata(par2, par3, par4);
                if (freezeStep < 7 && par5Random.nextInt((drainRate)) == 0)
                {
                    ++freezeStep;
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, freezeStep, 2);
                }
                else if (freezeStep == 7)
                {
                    Block newBlock = TcBlocks.tofuDried;
                    par1World.setBlock(par2, par3, par4, newBlock, 0, 2);
                }
            }
        }
    }

//    @SideOnly(Side.CLIENT)
//
//    /**
//     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
//     */
//    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
//    {
//        for (int var4 = 0; var4 < 3; ++var4)
//        {
//            par3List.add(new ItemStack(par1, 1, var4));
//        }
//    }

    public static boolean isValidPlaceForDriedTofu(World world, int x, int y, int z)
    {
        return world.getBiomeGenForCoords(x, z).getEnableSnow()
                && world.getHeightValue(x, z) - 10 < y
                && world.isAirBlock(x, y + 1, z);
    }
}
