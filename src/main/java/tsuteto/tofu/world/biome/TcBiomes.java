package tsuteto.tofu.world.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.util.ModLog;

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

    public static void register(Configuration conf)
    {
        conf.addCustomCategoryComment(CONF_CATEGORY, "Biome IDs. They must be 127 or less");

        try
        {
            tofuPlains = (BiomeGenTofuBase) (new BiomeGenTofuPlains(0, assignId("tofuPlains", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuPlains")
                    .setTemperatureRainfall(0.422F, 0.917F);

            tofuForest = (BiomeGenTofuBase) (new BiomeGenTofuForest(1, assignId("tofuForest", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuForest")
                    .setTemperatureRainfall(0.475F, 0.969F);

            tofuBuildings = (BiomeGenTofuBase) (new BiomeGenTofuBuildings(2, assignId("tofuBuildings", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuBuildings")
                    .setTemperatureRainfall(0.422F, 0.917F);

            tofuExtremeHills = (BiomeGenTofuBase) (new BiomeGenTofuHills(3, assignId("tofuExtremeHills", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuExtremeHills")
                    .setTemperatureRainfall(0.317F, 0.759F)
                    .setHeight(new BiomeGenBase.Height(1.2F, 0.3F));

            tofuPlainHills = (BiomeGenTofuBase) (new BiomeGenTofuPlains(4, assignId("tofuPlainHills", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuPlainHills")
                    .setTemperatureRainfall(0.422F, 0.917F)
                    .setHeight(new BiomeGenBase.Height(0.3F, 0.7F));

            tofuForestHills = (BiomeGenTofuBase) (new BiomeGenTofuForest(5, assignId("tofuForestHills", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuForestHills")
                    .setTemperatureRainfall(0.475F, 0.969F)
                    .setHeight(new BiomeGenBase.Height(0.3F, 0.7F));

            tofuHillsEdge = (BiomeGenTofuBase) (new BiomeGenTofuHills(6, assignId("tofuHillsEdge", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuExtremeHillsEdge")
                    .setTemperatureRainfall(0.317F, 0.759F)
                    .setHeight(new BiomeGenBase.Height(0.2F, 0.8F));

            tofuLeekPlains = (BiomeGenTofuBase) (new BiomeGenLeekPlains(7, assignId("tofuLeekPlains", conf)))
                    .setColor(9286496)
                    .setBiomeName("LeekPlains")
                    .setTemperatureRainfall(0.510F, 0.934F);

            tofuRiver = (BiomeGenTofuBase) (new BiomeGenTofuRiver(8, assignId("tofuRiver", conf)))
                    .setColor(9286496)
                    .setBiomeName("TofuRiver")
                    .setHeight(BiomeGenTofuBase.height_ShallowWaters)
                    .setTemperatureRainfall(0.510F, 0.934F);

            decorationBiomes = new BiomeGenTofuBase[]{
                    tofuPlains, tofuLeekPlains, tofuPlains, tofuForest, tofuBuildings, tofuExtremeHills};
        }
        catch (Exception e)
        {
            ModLog.log(Level.WARN, e, e.getLocalizedMessage());
        }

    }

    public static int assignId(String confKey, Configuration conf) throws Exception
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
        throw new RuntimeException("Failed to register BIOME. Seems to be running out of biome ID.");
    }

}
