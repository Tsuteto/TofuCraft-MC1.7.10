package tsuteto.tofu.world.gen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import tsuteto.tofu.util.Utils;

public class TcMapGenBase extends MapGenBase
{
    @Override
    public void func_151539_a(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, Block[] par5ArrayOfByte) // generate
    {
        long tofuSeed = Utils.getSeedForTofuWorld(par2World);
        int k = this.range;
        this.worldObj = par2World;
        this.rand.setSeed(tofuSeed);
        long l = this.rand.nextLong();
        long i1 = this.rand.nextLong();

        for (int j1 = par3 - k; j1 <= par3 + k; ++j1)
        {
            for (int k1 = par4 - k; k1 <= par4 + k; ++k1)
            {
                long l1 = j1 * l;
                long i2 = k1 * i1;
                this.rand.setSeed(l1 ^ i2 ^ tofuSeed);
                this.func_151538_a(par2World, j1, k1, par3, par4, par5ArrayOfByte); // recursiveGenerate
            }
        }
    }
}
