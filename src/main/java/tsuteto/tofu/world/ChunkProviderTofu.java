package tsuteto.tofu.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.event.terraingen.TerrainGen;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.Utils;
import tsuteto.tofu.world.gen.TcMapGenBase;
import tsuteto.tofu.world.gen.TcMapGenCaves;
import tsuteto.tofu.world.gen.TcMapGenMineshaft;
import tsuteto.tofu.world.gen.TcMapGenRavine;
import tsuteto.tofu.world.gen.feature.WorldGenTofuDungeons;

import java.util.List;
import java.util.Random;

public class ChunkProviderTofu implements IChunkProvider
{
    /** RNG. */
    private final Random rand;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen1;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen2;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorOctaves noiseGen3;

    /** A NoiseGeneratorOctaves used in generating terrain */
    private NoiseGeneratorPerlin noiseGen4;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen5;

    /** A NoiseGeneratorOctaves used in generating terrain */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;

    /** Reference to the World object. */
    private final World worldObj;

    /** are map structures going to be generated (e.g. strongholds) */
    private final boolean mapFeaturesEnabled;
    private WorldType worldType;

    /** Holds the overall noise array used in chunk generation */
    private double[] noiseArray;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private final TcMapGenBase caveGenerator = new TcMapGenCaves();

    /** Holds Stronghold Generator */
    //private MapGenStronghold strongholdGenerator = new MapGenStronghold();

    /** Holds Village Generator */
    private final MapGenVillage villageGenerator = new MapGenVillage();

    /** Holds Mineshaft Generator */
    private final TcMapGenMineshaft mineshaftGenerator = new TcMapGenMineshaft();
    //private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();

    /** Holds ravine generator */
    private final TcMapGenRavine ravineGenerator = new TcMapGenRavine();

    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;

    double[] noise1;
    double[] noise2;
    double[] noise3;
    double[] noise4;

    int[][] field_73219_j = new int[32][32];

    public ChunkProviderTofu(World par1World, long par2, boolean par4)
    {
        this.worldObj = par1World;
        this.mapFeaturesEnabled = par4;
        this.worldType = par1World.getWorldInfo().getTerrainType();
        this.rand = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseArray = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }

        NoiseGenerator[] noiseGens = {noiseGen1, noiseGen2, noiseGen3, noiseGen4, noiseGen5, noiseGen6, mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves)noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves)noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves)noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
    }

    /**
     * Generates the shape of the terrain for the chunk though its all stone though the water is frozen if the
     * temperature is low enough
     */
    public void generateTerrain(int par1, int par2, Block[] par3ArrayOfByte)
    {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, 10, 10);
        this.initializeNoiseField(par1 * 4, 0, par2 * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.125D;
                    double d1 = this.noiseArray[k1 + k2];
                    double d2 = this.noiseArray[l1 + k2];
                    double d3 = this.noiseArray[i2 + k2];
                    double d4 = this.noiseArray[j2 + k2];
                    double d5 = (this.noiseArray[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.noiseArray[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.noiseArray[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.noiseArray[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    par3ArrayOfByte[j3 += short1] = TcBlocks.tofuTerrain;
                                }
                                else if (k2 * 8 + l2 < b0)
                                {
                                    par3ArrayOfByte[j3 += short1] = TcBlocks.soymilkStill;
                                }
                                else
                                {
                                    par3ArrayOfByte[j3 += short1] = null;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /**
     * Replaces the stone that was placed in with blocks that match the biome
     */
    public void replaceBlocksForBiome(int par1, int par2, Block[] par3ArrayOfBlock, byte[] par4ArrayOfByte, BiomeGenBase[] par5ArrayOfBiomeGenBase)
    {
        double d0 = 0.03125D;
        this.stoneNoise = this.noiseGen4.func_151599_a(this.stoneNoise, (double) (par1 * 16), (double) (par2 * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                BiomeGenBase biomegenbase = par5ArrayOfBiomeGenBase[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, par3ArrayOfBlock, par4ArrayOfByte, par1 * 16 + k, par2 * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    @Override
    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    @Override
    public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed(par1 * 341873128711L + par2 * 132897987542L);
        Block[] var3 = new Block[65536];
        byte[] byteblob = new byte[65536];
        this.generateTerrain(par1, par2, var3);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, var3, byteblob, this.biomesForGeneration);
        this.caveGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
        this.ravineGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
            this.villageGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
            //this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
            //this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, var3);
        }

        Chunk var4 = new Chunk(this.worldObj, var3, byteblob, par1, par2);
        byte[] var5 = var4.getBiomeArray();

        for (int var6 = 0; var6 < var5.length; ++var6)
        {
            var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
        }

        var4.generateSkylightMap();
        return var4;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the [empty] noise array, the position, and the
     * size.
     */
    private void initializeNoiseField(int par2, int par3, int par4)
    {
        double var44 = 684.412D;
        double var45 = 684.412D;
        double var43 = 512.0D;
        double var42 = 512.0D;
        this.noise4 = this.noiseGen6.generateNoiseOctaves(this.noise4, par2, par4, 5, 5, 200.0D, 200.0D, 0.5D);
        this.noise1 = this.noiseGen3.generateNoiseOctaves(this.noise1, par2, par3, par4, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.noise2 = this.noiseGen1.generateNoiseOctaves(this.noise2, par2, par3, par4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.noise3 = this.noiseGen2.generateNoiseOctaves(this.noise3, par2, par3, par4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1)
        {
            for (int k1 = 0; k1 < 5; ++k1)
            {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        if (this.worldType == WorldType.AMPLIFIED && f3 > 0.0F)
                        {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
                        {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.noise4[i1] / 8000.0D;

                if (d13 < 0.0D)
                {
                    d13 = -d13 * 0.3D;
                }

                d13 = d13 * 3.0D - 2.0D;

                if (d13 < 0.0D)
                {
                    d13 /= 2.0D;

                    if (d13 < -1.0D)
                    {
                        d13 = -1.0D;
                    }

                    d13 /= 1.4D;
                    d13 /= 2.0D;
                }
                else
                {
                    if (d13 > 1.0D)
                    {
                        d13 = 1.0D;
                    }

                    d13 /= 8.0D;
                }

                ++i1;
                double d12 = (double)f1;
                double d14 = (double)f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2)
                {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.noise2[l] / 512.0D;
                    double d8 = this.noise3[l] / 512.0D;
                    double d9 = (this.noise1[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)
                    {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseArray[l] = d10;
                    ++l;
                }
            }
        }
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockSand.fallInstantly = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        long var9 = this.rand.nextLong() / 2L * 2L + 1L;
        long tofuSeed = Utils.getSeedForTofuWorld(this.worldObj);
        this.rand.setSeed(par2 * var7 + par3 * var9 ^ tofuSeed);
        boolean var11 = false;

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            var11 = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            if (var11)
            {
                ModLog.debug("village generated: x=%d z=%d, biome: %s", par2 << 4, par3 << 4, var6);
            }
            //this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            //this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        }

        int var12;
        int var13;
        int var14;

        if (this.rand.nextInt(4) == 0)
        {
            var12 = var4 + this.rand.nextInt(16) + 8;
            var13 = this.rand.nextInt(128);
            var14 = var5 + this.rand.nextInt(16) + 8;
            (new WorldGenLakes(TcBlocks.soymilkStill)).generate(this.worldObj, this.rand, var12, var13, var14);
        }

        int var15;
        WorldGenMinable worldgenminable;

        worldgenminable = new WorldGenMinable(TcBlocks.tofuKinu, 32, TcBlocks.tofuTerrain);

        for (var12 = 0; var12 < 18; ++var12)
        {
            var13 = var4 + this.rand.nextInt(16);
            var14 = this.rand.nextInt(60);
            var15 = var5 + this.rand.nextInt(16);
            worldgenminable.generate(this.worldObj, this.rand, var13, var14, var15);
        }

        worldgenminable = new WorldGenMinable(TcBlocks.oreTofuDiamond, 4, TcBlocks.tofuTerrain);

        for (var12 = 0; var12 < 6; ++var12)
        {
            var13 = var4 + this.rand.nextInt(16);
            var14 = this.rand.nextInt(20) + 5;
            var15 = var5 + this.rand.nextInt(16);
            worldgenminable.generate(this.worldObj, this.rand, var13, var14, var15);
        }

        for (var14 = 0; var14 < 8; ++var14)
        {
            var13 = var4 + this.rand.nextInt(16) + 8;
            var14 = this.rand.nextInt(128);
            var15 = var5 + this.rand.nextInt(16) + 8;

            if ((new WorldGenTofuDungeons()).generate(this.worldObj, this.rand, var13, var14, var15))
            {
                ModLog.debug("Tofu dungeon spawned at (%d, %d, %d)", var13, var14, var15);
            }
        }


        var6.decorate(this.worldObj, this.rand, var4, var5);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;

        for (var12 = 0; var12 < 16; ++var12)
        {
            for (var13 = 0; var13 < 16; ++var13)
            {
                var14 = this.worldObj.getPrecipitationHeight(var4 + var12, var5 + var13);

                if (this.worldObj.isBlockFreezable(var12 + var4, var14 - 1, var13 + var5))
                {
                    this.worldObj.setBlock(var12 + var4, var14 - 1, var13 + var5, Blocks.ice);
                }

                if (this.worldObj.func_147478_e(var12 + var4, var14, var13 + var5, true))
                {
                    this.worldObj.setBlock(var12 + var4, var14, var13 + var5, Blocks.snow);
                }
            }
        }

        // Replace mob spawners
        for (int i = 0; i < 64; i++)
        {
            for (int j = 0; j < 16; j++)
            {
                for (int k = 0; k < 16; k++)
                {
                    int x = var4 + j;
                    int z = var5 + k;
                    if (worldObj.getBlock(x, i, z) == Blocks.mob_spawner)
                    {
                        this.replaceMobSpawner(x, i, z);
                    }
                }
            }
        }

        BlockSand.fallInstantly = false;
    }

    /**
     * Replace mob spawners to spawn mobs excepting Tofu Creeper
     * @param bx
     * @param by
     * @param bz
     */
    private void replaceMobSpawner(int bx, int by, int bz)
    {
        TileEntityMobSpawner tile = (TileEntityMobSpawner)worldObj.getTileEntity(bx, by, bz);
        if (tile != null && !tile.func_145881_a().getEntityNameToSpawn().equals("TofuCreeper"))
        {
            tile.func_145881_a().setEntityName("TofuSlime");

            // Remove web in surroundings
            for (int x = -20; x < 20; x++)
            {
                for (int y = 0; y < 2; y++)
                {
                    for (int z = -20; z < 20; z++)
                    {
                        if ((x > -2 && x < 2 || z > -2 && z < 2)
                                && worldObj.getBlock(x + bx, y + by, z + bz) == Blocks.web)
                        {
                            worldObj.setBlockToAir(x + bx, y + by, z + bz);
                        }
                    }
                }
            }

            //ModLog.debug("Replaced spawner at (%d, %d, %d)", bx, by, bz);
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString()
    {
        return "TofuRandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    /**
     * Returns the location of the closest structure of the specified type. If not found returns null.
     */
    @Override
    public ChunkPosition func_147416_a(World par1World, String par2Str, int par3, int par4, int par5) // findClosestStructure
    {
        //return "Stronghold".equals(par2Str) && this.strongholdGenerator != null ? this.strongholdGenerator.getNearestInstance(par1World, par3, par4, par5) : null;
        return null;
    }

    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }

    @Override
    public void recreateStructures(int par1, int par2)
    {
        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
            this.villageGenerator.func_151539_a(this, this.worldObj, par1, par2, null);
            //this.strongholdGenerator.func_151539_a(this, this.worldObj, par1, par2, (byte[])null);
            //this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, par1, par2, (byte[])null);
        }
    }

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}

    @Override
    public void saveExtraData() {}
}
