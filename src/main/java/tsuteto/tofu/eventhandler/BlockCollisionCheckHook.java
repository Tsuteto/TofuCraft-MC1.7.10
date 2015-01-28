package tsuteto.tofu.eventhandler;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import tsuteto.tofu.entity.EntityFallingChikuwaPlatform;

import java.util.List;

public class BlockCollisionCheckHook
{
    public static boolean onBlockCollisionCheck(World world, AxisAlignedBB boundingBox)
    {
        List list;
        list = world.getEntitiesWithinAABB(EntityFallingChikuwaPlatform.class, boundingBox.addCoord(0.0D, -1.0D, 0.0D));
        if (!list.isEmpty()) return true;

        return false;
    }
}
