package tsuteto.tofu.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;

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

    private static final String CONF_CATEGORY = "biome";
    private static boolean haveIdsAssigned = true;

    public static void register(Configuration conf)
    {
        conf.addCustomCategoryComment(CONF_CATEGORY, "Biome IDs. They must be 255 or less");

        int tofuPlainsId = assignId("tofuPlains", conf);
        int tofuForestId = assignId("tofuForest", conf);
        int tofuBuildingsId = assignId("tofuBuildings", conf);
        int tofuExtremeHillsId = assignId("tofuExtremeHills", conf);
        int tofuPlainHillsId = assignId("tofuPlainHills", conf);
        int tofuForestHillsId = assignId("tofuForestHills", conf);
        int tofuHillsEdgeId = assignId("tofuHillsEdge", conf);
        int tofuLeekPlainsId = assignId("tofuLeekPlains", conf);
        int tofuRiverId = assignId("tofuRiver", conf);

        if (!haveIdsAssigned)
        {
            conf.save();
            throw new RuntimeException("Failed to register BIOME. Seems to be running out of biome ID.");
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

    }

    private static int assignId(String confKey, Configuration conf)
    {
        if (conf.hasKey(CONF_CATEGORY, confKey))
        {
            int id = conf.get(CONF_CATEGORY, confKey, -1).getInt();
            if (id != -1)
            {
                return id;
            }
        }

        // Find an undefined entry
        for (int i = 127; i >= 0; i--)
        {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[i];
            if (biome == null)
            {
                conf.get(CONF_CATEGORY, confKey, i).set(i);
                return i;
            }
        }

        // Unable to find entry
        haveIdsAssigned = false;
        conf.get(CONF_CATEGORY, confKey, -1).set(-1);
        return -1;
    }

}
