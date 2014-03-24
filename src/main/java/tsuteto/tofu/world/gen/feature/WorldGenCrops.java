package tsuteto.tofu.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import tsuteto.tofu.util.ModLog;

import java.util.Random;

public class WorldGenCrops extends WorldGenerator
{
    /** The ID of the plant block used in this plant generator. */
    private Block plantBlock;

    public WorldGenCrops(Block par1)
    {
        this.plantBlock = par1;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int l = 0; l < 64; ++l)
        {
            int i1 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int j1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int k1 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(i1, j1, k1) && (!par1World.provider.hasNoSky || j1 < 127) && this.plantBlock.canBlockStay(par1World, i1, j1, k1))
            {
                par1World.setBlock(i1, j1, k1, this.plantBlock, 7, 2);
                ModLog.debug("Hellsoybeans generated at (%d, %d, %d)", i1, j1, k1);
            }
        }

        return true;
    }
}
