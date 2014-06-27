package tsuteto.tofu.eventhandler;

import java.util.EnumSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import tsuteto.tofu.params.DataType;
import tsuteto.tofu.params.EntityInfo;
import tsuteto.tofu.params.PortalTripInfo;
import tsuteto.tofu.util.ModLog;

public class GameTickHandler
{

    @SubscribeEvent
    public void tickEnd(TickEvent.ServerTickEvent event)
    {
        if (event.type == TickEvent.Type.SERVER && event.phase == TickEvent.Phase.END)
        {
            EntityInfo pinfo = EntityInfo.instance();
            Integer[] array = pinfo.getEntitySet().toArray(new Integer[pinfo.getEntitySet().size()]);
            for (Integer entityId : array)
            {
                if (pinfo.doesDataExist(entityId, DataType.TicksPortalCooldown))
                {
                    PortalTripInfo info = pinfo.get(entityId, DataType.TicksPortalCooldown);
                    MinecraftServer server = MinecraftServer.getServer();
                    World world = server.worldServerForDimension(info.dimensionIdTripTo);
                    if (world != null)
                    {
                        Entity entity = world.getEntityByID(entityId);
                        if (entity != null && entity.addedToChunk && entity.worldObj.blockExists(
                                (int)entity.posX, (int)entity.posY, (int)entity.posZ))
                        {
                            int ticks = info.ticksCooldown;
                            if (ticks >= 20)
                            {
                                pinfo.remove(entityId, DataType.TicksPortalCooldown);
                            }
                            else
                            {
                                info.ticksCooldown += 1;
                            }
                            ModLog.debug("cooldown: %d", info.ticksCooldown);
                        }
                    }
                }
            }
        }
    }
}
