package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.iteminfo.SoymilkPlayerInfo;

public class EventPlayer
{
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (TofuCraftCore.update != null)
        {
            TofuCraftCore.update.notifyUpdate(event.player, Side.CLIENT);
        }

        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(event.player).readNBTFromPlayer();
        info.onLogin();
        info.update();
    }

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(event.player).readNBTFromPlayer();
        info.onLogout();
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        this.syncPlayerNBTWithClient(event.player);
    }

    @SubscribeEvent
    public void onPlayerTraveled(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        this.syncPlayerNBTWithClient(event.player);
    }

    private void syncPlayerNBTWithClient(EntityPlayer player)
    {
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(player).readNBTFromPlayer();
        info.update();
    }
}
