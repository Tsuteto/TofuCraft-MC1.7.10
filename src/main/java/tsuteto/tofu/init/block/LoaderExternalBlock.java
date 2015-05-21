package tsuteto.tofu.init.block;

import cpw.mods.fml.common.Loader;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.util.ItemUtils;
import tsuteto.tofu.util.ModLog;

public class LoaderExternalBlock
{
    /**
     * === External Mod Items ===
     */
    public void load()
    {
        if (Loader.isModLoaded("BambooMod"))
        {
            TcBlocks.sakuraLeaves = ItemUtils.getExternalModBlockWithRegex("(?i)sakuraleaves");
            ModLog.debug("sakuraLeaves: " + TcBlocks.sakuraLeaves);
        }
    }

}
