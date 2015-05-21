package tsuteto.tofu.init.item;

import cpw.mods.fml.common.Loader;
import tsuteto.tofu.util.ItemUtils;

import static tsuteto.tofu.init.TcItems.*;

public class LoaderExternalItem
{
    /**
     * === External Mod Items ===
     */
    public void load()
    {
        if (Loader.isModLoaded("IC2"))
        {
            plantBall = ItemUtils.getIc2Item("plantBall");
        }

        if (Loader.isModLoaded("BambooMod"))
        {
            bambooBasket = ItemUtils.getExternalModItemWithRegex("(?i)bamboobasket");
            bambooFood = ItemUtils.getExternalModItemWithRegex("(?i)bamboofoods?");
        }
    }
}
