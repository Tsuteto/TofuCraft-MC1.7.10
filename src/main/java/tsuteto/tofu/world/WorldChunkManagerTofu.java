package tsuteto.tofu.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import tsuteto.tofu.util.Utils;
import tsuteto.tofu.world.biome.BiomeGenTofuBase;
import tsuteto.tofu.world.biome.TcBiomes;
import tsuteto.tofu.world.gen.layer.GenLayerTofu;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldChunkManagerTofu extends WorldChunkManager
{
    public static ArrayList<BiomeGenTofuBase> allowedBiomes = new ArrayList<BiomeGenTofuBase>(Arrays.asList(
            TcBiomes.tofuPlains,
            TcBiomes.tofuForest,
            TcBiomes.tofuBuildings,
            TcBiomes.tofuExtremeHills));

    private GenLayer genBiomes;

    /** The BiomeCache object for this world. */
    private BiomeCache biomeCache;

    /** A list of biomes that the player can spawn in. */
    private List biomesToSpawnIn;

    protected WorldChunkManagerTofu()
    {
        this.biomeCache = new BiomeCache(this);
        this.biomesToSpawnIn = new ArrayList();
        this.biomesToSpawnIn.addAll(allowedBiomes);
    }

    public WorldChunkManagerTofu(long seed, WorldType worldType)
    {
        this();
        GenLayer agenlayer = GenLayerTofu.initializeAllBiomeGeneratorsTofu(seed, worldType);
        this.genBiomes = agenlayer;
    }

    public WorldChunkManagerTofu(World world)
    {
        this(Utils.getSeedForTofuWorld(world), world.getWorldInfo().getTerrainType());
    }

    /**
     * Gets the list of valid biomes for the player to spawn in.
     */
    @Override
    public List getBiomesToSpawnIn()
    {
        return this.biomesToSpawnIn;
    }

    /**
     * Returns the BiomeGenTofuBase related to the x, z position on the world.
     */
    @Override
    public BiomeGenBase getBiomeGenAt(int par1, int par2)
    {
        return this.biomeCache.getBiomeGenAt(par1, par2);
    }

    /**
     * Returns a list of rainfall values for the specified blocks. Args: listToReuse, x, z, width, length.
     */
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
        {
            par1ArrayOfFloat = new float[par4 * par5];
        }

        int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);

        for (int i1 = 0; i1 < par4 * par5; ++i1)
        {
            float f = BiomeGenTofuBase.getBiome(aint[i1]).getIntRainfall() / 65536.0F;

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            par1ArrayOfFloat[i1] = f;
        }

        return par1ArrayOfFloat;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return an adjusted version of a given temperature based on the y height
     */
    @Override
    public float getTemperatureAtHeight(float par1, int par2)
    {
        return par1;
    }

    /**
     * Returns a list of temperatures to use for the specified blocks.  Args: listToReuse, x, y, width, length
     *
     * This seems gone as of 1.7...
     */
//    public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
//    {
//        IntCache.resetIntCache();
//
//        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
//        {
//            par1ArrayOfFloat = new float[par4 * par5];
//        }
//
//        int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);
//
//        for (int i1 = 0; i1 < par4 * par5; ++i1)
//        {
//            float f = BiomeGenTofuBase.tofuBiomeList[aint[i1]].getFloatTemperature();
//
//            if (f > 1.0F)
//            {
//                f = 1.0F;
//            }
//
//            par1ArrayOfFloat[i1] = f;
//        }
//
//        return par1ArrayOfFloat;
//    }

    /**
     * Returns an array of biomes for the location input.
     */
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);

        for (int i1 = 0; i1 < par4 * par5; ++i1)
        {
            par1ArrayOfBiomeGenBase[i1] = BiomeGenTofuBase.getBiomeGenArray()[aint[i1]];
        }

        return par1ArrayOfBiomeGenBase;
    }

    /**
     * Returns biomes to use for the blocks and loads the other data like temperature and humidity onto the
     * WorldChunkManager Args: oldBiomeList, x, z, width, depth
     */
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
    {
        return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
    }

    /**
     * Return a list of biomes for the specified blocks. Args: listToReuse, x, y, width, length, cacheFlag (if false,
     * don't check biomeCache to avoid infinite loop in BiomeCacheBlock)
     */
    @Override
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
    {
        IntCache.resetIntCache();

        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
        {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }

        if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
        {
            BiomeGenBase[] aBiomeGenBase1 = this.biomeCache.getCachedBiomes(par2, par3);
            System.arraycopy(aBiomeGenBase1, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
            return par1ArrayOfBiomeGenBase;
        }
        else
        {
            int[] aint = this.genBiomes.getInts(par2, par3, par4, par5);

            for (int i1 = 0; i1 < par4 * par5; ++i1)
            {
                par1ArrayOfBiomeGenBase[i1] = BiomeGenTofuBase.getBiomeGenArray()[aint[i1]];
            }

            return par1ArrayOfBiomeGenBase;
        }
    }

    /**
     * checks given Chunk's Biomes against List of allowed ones
     */
    @Override
    public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
    {
        IntCache.resetIntCache();
        int l = par1 - par3 >> 2;
        int i1 = par2 - par3 >> 2;
        int j1 = par1 + par3 >> 2;
        int k1 = par2 + par3 >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);

        for (int j2 = 0; j2 < l1 * i2; ++j2)
        {
            BiomeGenBase biomeGenTofuBase = BiomeGenTofuBase.getBiomeGenArray()[aint[j2]];

            if (!par4List.contains(biomeGenTofuBase))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Finds a valid position within a range, that is in one of the listed biomes. Searches {par1,par2} +-par3 blocks.
     * Strongly favors positive y positions.
     */
    @Override
    public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
    {
        IntCache.resetIntCache();
        int l = par1 - par3 >> 2;
        int i1 = par2 - par3 >> 2;
        int j1 = par1 + par3 >> 2;
        int k1 = par2 + par3 >> 2;
        int l1 = j1 - l + 1;
        int i2 = k1 - i1 + 1;
        int[] aint = this.genBiomes.getInts(l, i1, l1, i2);
        ChunkPosition chunkposition = null;
        int j2 = 0;

        for (int k2 = 0; k2 < l1 * i2; ++k2)
        {
            int l2 = l + k2 % l1 << 2;
            int i3 = i1 + k2 / l1 << 2;
            BiomeGenBase biomeGenTofuBase = BiomeGenTofuBase.getBiomeGenArray()[aint[k2]];

            if (par4List.contains(biomeGenTofuBase) && (chunkposition == null || par5Random.nextInt(j2 + 1) == 0))
            {
                chunkposition = new ChunkPosition(l2, 0, i3);
                ++j2;
            }
        }

        return chunkposition;
    }

    /**
     * Calls the WorldChunkManager's biomeCache.cleanupCache()
     */
    @Override
    public void cleanupCache()
    {
        this.biomeCache.cleanupCache();
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original)
    {
        return original;
    }
}
