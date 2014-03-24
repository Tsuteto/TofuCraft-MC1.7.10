package tsuteto.tofu.eventhandler;

import java.util.EnumSet;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import tsuteto.tofu.params.DataType;
import tsuteto.tofu.params.EntityInfo;

public class GameTickHandler
{

    @SubscribeEvent
    public void tickEnd(TickEvent.ServerTickEvent event)
    {
        if (event.type == TickEvent.Type.SERVER && event.phase == TickEvent.Phase.END)
        {
            EntityInfo pinfo = EntityInfo.instance();
            Integer[] array = pinfo.getEntitySet().toArray(new Integer[0]);
            for (Integer entityId : array)
            {
                if (pinfo.doesDataExist(entityId, DataType.TicksPortalCooldown))
                {
                    int ticks = pinfo.<Integer>get(entityId, DataType.TicksPortalCooldown);

                    if (ticks >= 20)
                    {
                        pinfo.remove(entityId, DataType.TicksPortalCooldown);
                    } else
                    {
                        pinfo.set(entityId, DataType.TicksPortalCooldown, ++ticks);
                    }
                }
            }
        }
    }
}
