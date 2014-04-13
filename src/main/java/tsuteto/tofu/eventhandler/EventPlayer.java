package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import tsuteto.tofu.TofuCraftCore;

public class EventPlayer
{
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (TofuCraftCore.update != null)
        {
            TofuCraftCore.update.notifyUpdate(event.player, Side.CLIENT);
        }
    }
}
