package tsuteto.tofu.world.biome;

import java.util.Random;

import net.minecraft.world.World;
import tsuteto.tofu.block.TcBlocks;

public class BiomeGenTofuHills extends BiomeGenTofuBase
{
    protected BiomeGenTofuHills(int localId, int par1)
    {
        super(localId, par1);
    }

    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        super.decorate(par1World, par2Random, par3, par4);

        for (int k = 0; k < 1; ++k)
        {
            int x = par3 + par2Random.nextInt(16) + 8;
            int z = par4 + par2Random.nextInt(16) + 8;

            int y = par1World.getHeightValue(x, z) - 1;

            if (y > 80 && par1World.getBlock(x, y, z) == TcBlocks.tofuTerrain)
            {
                if (par1World.getBlock(x + 1, y, z) == TcBlocks.tofuTerrain
                        && par1World.getBlock(x, y, z + 1) == TcBlocks.tofuTerrain
                        && par1World.getBlock(x - 1, y, z) == TcBlocks.tofuTerrain
                        && par1World.getBlock(x, y, z - 1) == TcBlocks.tofuTerrain)
                {
                    int h = par2Random.nextInt(3) + 3;
                    for (int i = 0; i < h; i++)
                    {
                        par1World.setBlock(x, y + i, z, TcBlocks.soymilkStill, 0, 2);
                    }
                }
            }
        }

    }
}
