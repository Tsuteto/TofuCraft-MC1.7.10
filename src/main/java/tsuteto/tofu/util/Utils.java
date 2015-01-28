package tsuteto.tofu.util;

import com.google.common.base.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Utility Methods
 *
 * @author Tsuteto
 *
 */
public class Utils
{

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


    public static MovingObjectPosition getBlockMop(World world, Vec3 p_147447_1_, Vec3 p_147447_2_)
    {
        if (!Double.isNaN(p_147447_1_.xCoord) && !Double.isNaN(p_147447_1_.yCoord) && !Double.isNaN(p_147447_1_.zCoord))
        {
            if (!Double.isNaN(p_147447_2_.xCoord) && !Double.isNaN(p_147447_2_.yCoord) && !Double.isNaN(p_147447_2_.zCoord))
            {
                int l = MathHelper.floor_double(p_147447_1_.xCoord);
                int i1 = MathHelper.floor_double(p_147447_1_.yCoord);
                int j1 = MathHelper.floor_double(p_147447_1_.zCoord);
                Block block = Blocks.stone;
                int k1 = 0;

                if ((block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block.canCollideCheck(k1, false))
                {
                    MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, l, i1, j1, p_147447_1_, p_147447_2_);

                    if (movingobjectposition != null)
                    {
                        return movingobjectposition;
                    }
                }
            }
        }
        return null;
    }

    public static long generateRandomFromCoord(int x, int y, int z)
    {
        long i1 = (long) (x * 3129871) ^ (long) y * 116129781L ^ (long) z;
        i1 = i1 * i1 * 42317861L + i1 * 11L + i1;
        return i1 >> 16;
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
