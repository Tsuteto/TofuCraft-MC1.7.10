package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;

import java.util.Random;

public class BlockTofuStairs extends BlockStairs
{
    private boolean isFragile = false;

    protected BlockTofuStairs(Block par2Block, int par3)
    {
        super(par2Block, par3);
        this.setLightOpacity(0);
    }

    public BlockStairs setFragile()
    {
        isFragile = true;
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
        int meta = par1World.getBlockMetadata(par2, par3, par4);

        if (isFragile)
        {
            Block weightBlock = par1World.getBlock(par2, par3 + 1, par4);

            if (weightBlock != null)
            {
               if (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron)
               {
                   dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                   par1World.setBlockToAir(par2, par3, par4);
               }
            }
        }
    }
}
