package tsuteto.tofu.util;

import com.google.common.base.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Utility Methods
 *
 * @author Tsuteto
 *
 */
public class Utils
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

    public static NBTTagCompound getNBTPlayerPersisted(EntityPlayer player)
    {
        NBTTagCompound nbt = player.getEntityData();

        if (!nbt.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
        {
            nbt.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }

        return nbt.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
    }

    /**
     * Get Tofu World seed from the world seed
     *
     * @param world
     * @return
     */
    public static long getSeedForTofuWorld(World world)
    {
        long upper = (world.getSeed() & 31) << 60;
        long lower = world.getSeed() >>> 4;
        return upper | lower;
    }

    public static String capitalize(String s)
    {
        if (Strings.isNullOrEmpty(s))
        {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + (s.length() >= 2 ? s.substring(1) : "");
    }
}
