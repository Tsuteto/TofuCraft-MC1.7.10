package tsuteto.tofu.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockUtils
{
    public static void onNeighborBlockChange_RedstoneSwitch(Block block, World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        boolean flag = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_) || p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_ + 1, p_149695_4_);
        int l = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        boolean flag1 = (l & 8) != 0;

        if (flag && !flag1)
        {
            p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, block, block.tickRate(p_149695_1_));
            p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, l | 8, 4);
        }
        else if (!flag && flag1)
        {
            p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, l & -9, 4);
        }
    }

    public static void handleEntityWeightingBlock(World world, Entity entity, Block block, IEntityWeightingBlockHandler handler)
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
                        if (world.getBlock(bx, by, bz) == block)
                        {
                            handler.apply(world, entity, block, bx, by, bz);
                        }
                    }
                }
            }
        }
    }

    public interface IEntityWeightingBlockHandler
    {
        public void apply(World world, Entity entity, Block block, int x, int y, int z);
    }
}
