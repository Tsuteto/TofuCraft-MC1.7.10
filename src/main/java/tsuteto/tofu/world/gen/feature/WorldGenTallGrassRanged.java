package tsuteto.tofu.world.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenTallGrassRanged extends WorldGenerator
{
    private Block tallGrassID;
    private int tallGrassMetadata;
    private int radius = 8;

    public WorldGenTallGrassRanged(Block par1, int par2, int radius)
    {
        this.tallGrassID = par1;
        this.tallGrassMetadata = par2;
        this.radius = radius;
    }

    @Override
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int l;

        Block block = null;
        do 
        {
            block = par1World.getBlock(par3,  par4, par5);
            if (block != null && !block.isLeaves(par1World, par3, par4, par5))
            {
                break;
            }
            par4--;
        } while (par4 > 0);

        int planting = radius * radius * 2;
        
        for (int i1 = 0; i1 < planting; ++i1)
        {
            int j1 = par3 + par2Random.nextInt(radius) - par2Random.nextInt(radius);
            int k1 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int l1 = par5 + par2Random.nextInt(radius) - par2Random.nextInt(radius);

            if (par1World.isAirBlock(j1, k1, l1) && this.tallGrassID.canBlockStay(par1World, j1, k1, l1))
            {
                par1World.setBlock(j1, k1, l1, this.tallGrassID, this.tallGrassMetadata, 2);
            }
        }

        return true;
    }

}
