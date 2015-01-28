package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.util.BlockUtils;
import tsuteto.tofu.util.ModLog;

import java.util.Random;

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

    public boolean canDrain()
    {
        return this.canDrain;
    }

    public int getDrainRate()
    {
        return drainRate;
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

                BlockUtils.handleEntityWeightingBlock(par1World, par5Entity, this, new BlockUtils.IEntityWeightingBlockHandler()
                {
                    @Override
                    public void apply(World world, Entity entity, Block block, int x, int y, int z)
                    {
                        collapseBlock(entity, world, x, y, z);
                    }
                });
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
           if (isUnderWeight(par1World, par2, par3, par4))
           {
                if (isFragile)
                {
                    dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                    par1World.setBlockToAir(par2, par3, par4);
                }
                else if (canDrain)
                {
                    this.drainOneStep(par1World, par2, par3, par4, par5Random);
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

    public boolean isUnderWeight(World world, int x, int y, int z)
    {
        Block weightBlock = world.getBlock(x, y + 1, z);
        Block baseBlock = world.getBlock(x, y - 1, z);

        boolean isWeightValid = weightBlock != null
                && (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron);

        float baseHardness = baseBlock.getBlockHardness(world, x, y - 1, z);
        boolean isBaseValid = baseBlock.isNormalCube() && (baseHardness >= 1.0F || baseHardness < 0.0F);

        return isWeightValid && isBaseValid;
    }

    public void drainOneStep(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int drainStep = par1World.getBlockMetadata(par2, par3, par4);

        if (drainStep < 7 && par5Random.nextInt(drainRate) == 0)
        {
            ++drainStep;
            ModLog.debug(drainStep);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, drainStep, 2);
        }
        else if (drainStep == 7 && par5Random.nextInt(2 * drainRate) == 0)
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
        return world.getBiomeGenForCoords(x, z).getFloatTemperature(x, y, z) < 0.15F
                && world.getHeightValue(x, z) - 10 < y
                && world.isAirBlock(x, y + 1, z);
    }
}
