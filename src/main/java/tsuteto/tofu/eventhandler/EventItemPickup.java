package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import tsuteto.tofu.api.achievement.TcAchievementMgr;

public class EventItemPickup
{

    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event)
    {
        if (event.pickedUp.getEntityItem() != null)
        {
            TcAchievementMgr.achieveItemPickup(event.pickedUp.getEntityItem(), event.player);
        }
    }
}
