package tsuteto.tofu.world.gen.layer;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;

import java.util.concurrent.Callable;

public abstract class GenLayerTofu extends GenLayer
{
    /**
     * the first array item is a linked list of the bioms, the second is the zoom function, the third is the same as the
     * first.
     */
    public static GenLayer initializeAllBiomeGeneratorsTofu(long par0, WorldType par2WorldType)
    {
        GenLayerIsland genlayerisland = new GenLayerIsland(1L);
        GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerisland);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(50L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(70L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(3L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayeraddisland);
        GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland, GenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        genlayerzoom = new GenLayerZoom(2002L, genlayeredge);
        genlayerzoom = new GenLayerZoom(2003L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);

        GenLayerTofu genlayer3 = GenLayerZoom.magnify(1000L, genlayeraddisland, 0);

        byte b0 = 3;

        if (par2WorldType == WorldType.LARGE_BIOMES)
        {
            b0 = 6;
        }

        GenLayerTofu genlayer = GenLayerZoom.magnify(1000L, genlayer3, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
        Object object = getBiomeLayer(par0, genlayer3, par2WorldType);

        GenLayerTofu genlayer1 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayerHills genlayerhills = new GenLayerHills(1000L, (GenLayerTofu)object, genlayer1);
        genlayer = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer = GenLayerZoom.magnify(1000L, genlayer, b0);
        GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        object = GenLayerZoom.magnify(1000L, genlayerhills, 2);

        for (int j = 0; j < b0; ++j)
        {
            object = new GenLayerZoom((long)(1000 + j), (GenLayerTofu)object);

            if (j == 0)
            {
                object = new GenLayerAddIsland(3L, (GenLayerTofu)object);
            }
            if (j == 1)
            {
                object = new GenLayerShore(1000L, (GenLayerTofu)object);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, (GenLayerTofu)object);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);

        genlayerrivermix.initWorldGenSeed(par0);
        return genlayerrivermix;
    }

    public static GenLayerTofu getBiomeLayer(long worldSeed, GenLayerTofu parentLayer, WorldType worldType)
    {
        GenLayerTofu ret = new GenLayerBiome(200L, parentLayer, worldType);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }


    public GenLayerTofu(long par1)
    {
        super(par1);
    }

    protected static boolean compareBiomesById(final int p_151616_0_, final int p_151616_1_)
    {
        if (p_151616_0_ == p_151616_1_)
        {
            return true;
        }
        else if (p_151616_0_ != BiomeGenBase.mesaPlateau_F.biomeID && p_151616_0_ != BiomeGenBase.mesaPlateau.biomeID)
        {
            try
            {
                return BiomeGenBase.getBiome(p_151616_0_) != null && BiomeGenBase.getBiome(p_151616_1_) != null ? BiomeGenBase.getBiome(p_151616_0_).isEqualTo(BiomeGenBase.getBiome(p_151616_1_)) : false;
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Comparing biomes");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Biomes being compared");
                crashreportcategory.addCrashSection("Biome A ID", Integer.valueOf(p_151616_0_));
                crashreportcategory.addCrashSection("Biome B ID", Integer.valueOf(p_151616_1_));
                crashreportcategory.addCrashSectionCallable("Biome A", new Callable()
                {
                    private static final String __OBFID = "CL_00000560";
                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.getBiome(p_151616_0_));
                    }
                });
                crashreportcategory.addCrashSectionCallable("Biome B", new Callable()
                {
                    private static final String __OBFID = "CL_00000561";
                    public String call()
                    {
                        return String.valueOf(BiomeGenBase.getBiome(p_151616_1_));
                    }
                });
                throw new ReportedException(crashreport);
            }
        }
        else
        {
            return p_151616_1_ == BiomeGenBase.mesaPlateau_F.biomeID || p_151616_1_ == BiomeGenBase.mesaPlateau.biomeID;
        }
    }

    /**
     * returns true if the biomeId is one of the various ocean biomes.
     */
    protected static boolean isBiomeOceanic(int p_151618_0_)
    {
        return false;
    }

    /**
     * selects a random integer from a set of provided integers
     */
    protected int selectRandom(int... par1)
    {
        return par1[this.nextInt(par1.length)];
    }

    /**
     * returns the most frequently occurring number of the set, or a random number from those provided
     */
    protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_)
    {
        return p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_ ? p_151617_2_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_ ? p_151617_1_ : (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_ ? p_151617_1_ : (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_ ? p_151617_2_ : (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_ ? p_151617_2_ : (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_ ? p_151617_3_ : this.selectRandom(new int[] {p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_}))))))))));
    }
}
