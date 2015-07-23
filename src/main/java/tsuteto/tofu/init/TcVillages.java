package tsuteto.tofu.init;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import tsuteto.tofu.Settings;
import tsuteto.tofu.village.*;
import tsuteto.tofu.world.biome.TcBiomes;
import tsuteto.tofu.world.tofuvillage.EntityJoinWorldEventHandler;
import tsuteto.tofu.world.tofuvillage.GetVillageBlockIDEventHandler;

public class TcVillages
{
    public static void register()
    {
        // Tofu Village handler
        BiomeManager.addVillageBiome(TcBiomes.tofuPlains, true);
        BiomeManager.addVillageBiome(TcBiomes.tofuLeekPlains, true);
        BiomeManager.addVillageBiome(TcBiomes.tofuForest, true);
        MinecraftForge.TERRAIN_GEN_BUS.register(new GetVillageBlockIDEventHandler());
        MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());

        // Register the profession of Tofu Cook
        VillagerRegistry vill = VillagerRegistry.instance();
        vill.registerVillagerId(Settings.professionIdTofucook);
        vill.registerVillageTradeHandler(Settings.professionIdTofucook, new TradeHandlerTofuCook());

        // Register the profession of Tofunian
        vill.registerVillagerId(Settings.professionIdTofunian);
        vill.registerVillageTradeHandler(Settings.professionIdTofunian, new TradeHandlerTofunian());

        // Add Trade Recipes
        vill.registerVillageTradeHandler(0, new TradeHandlerFarmer());

        // Tofu cook's house
        vill.registerVillageCreationHandler(new TofuCookHouseHandler());
        net.minecraft.world.gen.structure.MapGenStructureIO.func_143031_a(ComponentVillageHouseTofu.class, "ViTfH");

        // Blacksmith chest
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
                new WeightedRandomChestContent(new ItemStack(TcItems.bugle), 1, 1, 5));

    }
}
