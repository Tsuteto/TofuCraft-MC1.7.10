package tsuteto.tofu.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import tsuteto.tofu.block.BlockLeek;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.entity.EntityTofuSlime;
import tsuteto.tofu.world.gen.feature.WorldGenTofuBuilding;
import tsuteto.tofu.world.gen.feature.WorldGenTofuTrees;

import java.util.Random;

abstract public class BiomeGenTofuBase extends BiomeGenBase
{
    protected static final BiomeGenBase.Height height_Tofu = new BiomeGenBase.Height(0.05F, 0.1F);
    protected static final BiomeGenBase.Height height_ShallowWaters = new BiomeGenBase.Height(-0.5F, 0.0F);

    protected int treesPerChunk;
    protected int tofuPerChunk;
    protected int maxGrassPerChunk;
    protected int chanceOfLeeks;
    protected boolean generateLakes;

    protected WorldGenTofuTrees worldGeneratorTrees;
    protected WorldGenTofuBuilding worldGeneratorTofuBuilding;

    public BiomeGenTofuBase(int localBiomeId, int par1)
    {
        super(par1);
        this.worldGeneratorTrees = new WorldGenTofuTrees(false);
        this.worldGeneratorTofuBuilding = new WorldGenTofuBuilding(false);

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityTofuSlime.class, 3, 4, 4));

        this.topBlock = TcBlocks.tofuTerrain;
        this.fillerBlock = TcBlocks.tofuTerrain;

        this.setHeight(height_Tofu);

        this.treesPerChunk = 0;
        this.tofuPerChunk = 0;
        this.maxGrassPerChunk = 1;
        this.chanceOfLeeks = 50;
        this.generateLakes = true;

        TcBiomes.tofuBiomeList[localBiomeId] = this;
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenAbstractTree func_150567_a(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }

    @Override
    public void decorate(World par1World, Random par2Random, int chunk_X, int chunk_Z)
    {
        int i, j, k, l, i1;

        i = this.treesPerChunk;
        if (par2Random.nextInt(10) == 0)
        {
            ++i;
        }

        for (j = 0; j < i; ++j)
        {
            k = chunk_X + par2Random.nextInt(16) + 8;
            l = chunk_Z + par2Random.nextInt(16) + 8;
            WorldGenerator worldgenerator = this.func_150567_a(par2Random);
            worldgenerator.setScale(1.0D, 1.0D, 1.0D);
            worldgenerator.generate(par1World, par2Random, k, par1World.getHeightValue(k, l), l);
        }

        if (par2Random.nextInt(this.chanceOfLeeks) == 0)
        {
            for (j = 0; j < maxGrassPerChunk; j++)
            {
                k = chunk_X + par2Random.nextInt(16) + 8;
                l = par2Random.nextInt(128);
                i1 = chunk_Z + par2Random.nextInt(16) + 8;
                WorldGenerator var6 = new WorldGenTallGrass(TcBlocks.leek, BlockLeek.META_NATURAL);
                var6.generate(par1World, par2Random, k, l, i1);
            }
        }

        if (this.generateLakes)
        {
            for (j = 0; j < 50; ++j)
            {
                k = chunk_X + par2Random.nextInt(16) + 8;
                l = par2Random.nextInt(par2Random.nextInt(120) + 8);
                i1 = chunk_Z + par2Random.nextInt(16) + 8;
                (new WorldGenLiquids(TcBlocks.soymilkStill)).generate(par1World, par2Random, k, l, i1);
            }
        }

        i = this.tofuPerChunk;
        if (par2Random.nextInt(50) == 0)
        {
            ++i;
        }
        for (j = 0; j < i; ++j)
        {
            k = chunk_X + par2Random.nextInt(16) + 8;
            l = chunk_Z + par2Random.nextInt(16) + 8;
            WorldGenerator worldgenerator = this.worldGeneratorTofuBuilding;
            worldgenerator.setScale(1.0D, 1.0D, 1.0D);
            worldgenerator.generate(par1World, par2Random, k, par1World.getHeightValue(k, l), l);
        }

    }


}
