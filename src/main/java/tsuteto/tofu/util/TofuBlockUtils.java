package tsuteto.tofu.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.init.TcBlocks;

public class TofuBlockUtils
{
    public static void onFallenUponFragileTofu(World par1World, Entity par5Entity, Block block, float par6)
    {
        onFallenUponFragileTofu(par1World, par5Entity, block, par6, tofuWeightingHandler);
    }

    public static void onFallenUponFragileTofu(World par1World, Entity par5Entity, Block block, float par6, BlockUtils.IEntityWeightingBlockHandler handler)
    {
        if (!par1World.isRemote)
        {
            if (par6 > 0.5F)
            {
                if (!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    return;
                }
                BlockUtils.handleEntityWeightingBlock(par1World, par5Entity, block, handler);
            }
        }
    }

    private static BlockUtils.IEntityWeightingBlockHandler tofuWeightingHandler = new BlockUtils.IEntityWeightingBlockHandler()
    {
        @Override
        public void apply(World world, Entity entity, Block block, int x, int y, int z)
        {
            float prob = block == TcBlocks.tofuKinu ? 0.4F : 1.0F;
            block.dropBlockAsItemWithChance(world, x, y, z, 0, prob, 0);
            world.setBlockToAir(x, y, z);

            if (entity instanceof EntityPlayer)
            {
                TcAchievementMgr.achieve((EntityPlayer) entity, TcAchievementMgr.Key.tofuMental);
            }
        }
    };
}
