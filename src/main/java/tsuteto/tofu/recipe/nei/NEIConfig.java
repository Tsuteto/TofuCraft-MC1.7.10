package tsuteto.tofu.recipe.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.gui.GuiTfCondenser;
import tsuteto.tofu.gui.GuiTfReformer2;

public class NEIConfig implements IConfigureNEI
{

    @Override
    public void loadConfig()
    {
        API.registerRecipeHandler(new TfStorageRecipeHandler());
        API.registerUsageHandler(new TfStorageRecipeHandler());
        API.registerGuiOverlay(GuiTfCondenser.class, "tfStorage", 5, 11);

        API.registerRecipeHandler(new TfReformerRecipeHandler());
        API.registerUsageHandler(new TfReformerRecipeHandler());
        API.registerGuiOverlay(GuiTfReformer2.class, "tfReformer", 5, 11);

        API.registerRecipeHandler(new TfCondenserRecipeHandler());
        API.registerUsageHandler(new TfCondenserRecipeHandler());
        API.registerGuiOverlay(GuiTfCondenser.class, "tfCondenser", 5, 11);
    }

    @Override
    public String getName()
    {
        return "TofuCraft";
    }

    @Override
    public String getVersion()
    {
        return TofuCraftCore.version;
    }
}
