package tsuteto.tofu.world.biome;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenTofuPlains extends BiomeGenTofuBase
{
    public BiomeGenTofuPlains(int localId, int par1)
    {
        super(localId, par1);
        this.treesPerChunk = -999;
        this.rootHeight = 0.1F;
        this.heightVariation = 0.1F;
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
        super.decorate(par1World, par2Random, chunk_X, chunk_Z);
        
    }
}
