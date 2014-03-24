package tsuteto.tofu.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class BiomeGenTofuForest extends BiomeGenTofuBase
{
    public BiomeGenTofuForest(int localId, int par1)
    {
        super(localId, par1);
        this.treesPerChunk = 10;
        this.chanceOfLeeks = 10;
    }
    
    /**
     * Gets a WorldGen appropriate for this biome.
     */
    public WorldGenAbstractTree func_150567_a(Random par1Random) // getRandomWorldGenForTrees
    {
        return this.worldGeneratorTrees;
    }
    
    public void decorate(World par1World, Random par2Random, int chunk_X, int chunk_Z)
    {
        super.decorate(par1World, par2Random, chunk_X, chunk_Z);
    }
}
