package tsuteto.tofu.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.util.ModLog;

import java.util.Arrays;

public class TcBiomes
{
    public static BiomeGenTofuBase[] tofuBiomeList = new BiomeGenTofuBase[32];

    public static BiomeGenTofuBase tofuPlains;
    public static BiomeGenTofuBase tofuForest;
    public static BiomeGenTofuBase tofuBuildings;
    public static BiomeGenTofuBase tofuExtremeHills;
    public static BiomeGenTofuBase tofuPlainHills;
    public static BiomeGenTofuBase tofuForestHills;
    public static BiomeGenTofuBase tofuHillsEdge;
    public static BiomeGenTofuBase tofuLeekPlains;
    public static BiomeGenTofuBase tofuRiver;

    public static BiomeGenTofuBase[] decorationBiomes;

    public static final String CONF_CATEGORY = "biome";

    public static void register(Configuration conf)
    {
        BiomeIdManager idManager = new BiomeIdManager();

        conf.addCustomCategoryComment(CONF_CATEGORY, "Biome IDs. They must be 255 or less");

        int tofuPlainsId = idManager.assignId("tofuPlains", conf);
        int tofuForestId = idManager.assignId("tofuForest", conf);
        int tofuBuildingsId = idManager.assignId("tofuBuildings", conf);
        int tofuExtremeHillsId = idManager.assignId("tofuExtremeHills", conf);
        int tofuPlainHillsId = idManager.assignId("tofuPlainHills", conf);
        int tofuForestHillsId = idManager.assignId("tofuForestHills", conf);
        int tofuHillsEdgeId = idManager.assignId("tofuHillsEdge", conf);
        int tofuLeekPlainsId = idManager.assignId("tofuLeekPlains", conf);
        int tofuRiverId = idManager.assignId("tofuRiver", conf);

        if (!idManager.isCompleted())
        {
            conf.save();
            throw new RuntimeException("[TofuCraft] Failed to register BIOME. Seems to be running out of biome ID.");
        }

        tofuPlains = (BiomeGenTofuBase) (new BiomeGenTofuPlains(0, tofuPlainsId))
                .setColor(9286496)
                .setBiomeName("TofuPlains")
                .setTemperatureRainfall(0.422F, 0.917F);

        tofuForest = (BiomeGenTofuBase) (new BiomeGenTofuForest(1, tofuForestId))
                .setColor(9286496)
                .setBiomeName("TofuForest")
                .setTemperatureRainfall(0.475F, 0.969F);

        tofuBuildings = (BiomeGenTofuBase) (new BiomeGenTofuBuildings(2, tofuBuildingsId))
                .setColor(9286496)
                .setBiomeName("TofuBuildings")
                .setTemperatureRainfall(0.422F, 0.917F);

        tofuExtremeHills = (BiomeGenTofuBase) (new BiomeGenTofuHills(3, tofuExtremeHillsId))
                .setColor(9286496)
                .setBiomeName("TofuExtremeHills")
                .setTemperatureRainfall(0.317F, 0.759F)
                .setHeight(new BiomeGenBase.Height(1.2F, 0.3F));

        tofuPlainHills = (BiomeGenTofuBase) (new BiomeGenTofuPlains(4, tofuPlainHillsId))
                .setColor(9286496)
                .setBiomeName("TofuPlainHills")
                .setTemperatureRainfall(0.422F, 0.917F)
                .setHeight(new BiomeGenBase.Height(0.3F, 0.7F));

        tofuForestHills = (BiomeGenTofuBase) (new BiomeGenTofuForest(5, tofuForestHillsId))
                .setColor(9286496)
                .setBiomeName("TofuForestHills")
                .setTemperatureRainfall(0.475F, 0.969F)
                .setHeight(new BiomeGenBase.Height(0.3F, 0.7F));

        tofuHillsEdge = (BiomeGenTofuBase) (new BiomeGenTofuHills(6, tofuHillsEdgeId))
                .setColor(9286496)
                .setBiomeName("TofuExtremeHillsEdge")
                .setTemperatureRainfall(0.317F, 0.759F)
                .setHeight(new BiomeGenBase.Height(0.2F, 0.8F));

        tofuLeekPlains = (BiomeGenTofuBase) (new BiomeGenLeekPlains(7, tofuLeekPlainsId))
                .setColor(9286496)
                .setBiomeName("LeekPlains")
                .setTemperatureRainfall(0.510F, 0.934F);

        tofuRiver = (BiomeGenTofuBase) (new BiomeGenTofuRiver(8, tofuRiverId))
                .setColor(9286496)
                .setBiomeName("TofuRiver")
                .setHeight(BiomeGenTofuBase.height_ShallowWaters)
                .setTemperatureRainfall(0.510F, 0.934F);

        decorationBiomes = new BiomeGenTofuBase[]{
                tofuPlains, tofuLeekPlains, tofuPlains, tofuForest, tofuBuildings, tofuExtremeHills};

        // Register in the Forge Biome Dictionary
        for (BiomeGenTofuBase biome : tofuBiomeList)
        {
            if (biome != null) BiomeDictionary.registerBiomeType(biome, TofuCraftCore.BIOME_TYPE_TOFU);
        }
        ModLog.debug("Registered biomes as TOFU: " + Arrays.toString(BiomeDictionary.getBiomesForType(TofuCraftCore.BIOME_TYPE_TOFU)));
    }

}
