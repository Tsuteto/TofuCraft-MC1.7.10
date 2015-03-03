package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.data.MorijioManager;

public class EventWorld
{
    @SubscribeEvent
    public void onDataSave(WorldEvent.Save event)
    {
        MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
        TofuCraftCore.getSaveHandler().saveMorijioData(morijioManager);
    }
}
