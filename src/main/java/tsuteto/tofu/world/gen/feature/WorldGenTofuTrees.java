package tsuteto.tofu.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import tsuteto.tofu.block.TcBlocks;

import java.util.Random;

public class WorldGenTofuTrees extends WorldGenTcTreesBase
{
    public WorldGenTofuTrees(boolean par1)
    {
        super(par1, 4, TcBlocks.tofuIshi, 0, TcBlocks.tcLeaves, 2, false);
    }
    
    @Override
    public boolean isSoil(Block block, World world, int x, int y, int z)
    {
        return block == TcBlocks.tofuTerrain
                || block == TcBlocks.tofuMomen;
    }
    
    @Override
    protected void putLeaves(int ox, int oy, int oz, int height, World par1World, Random par2Random)
    {
        byte zero = 0;
        int var9 = 3;
        int leavesY, blocksHighFromTop, radius, leavesX, relX;
        for (leavesY = oy - var9 + height; leavesY <= oy + height; ++leavesY)
        {
            blocksHighFromTop = leavesY - (oy + height);
            radius = height / 3;

            for (leavesX = ox - radius; leavesX <= ox + radius; ++leavesX)
            {
                relX = leavesX - ox;

                for (int leavesZ = oz - radius; leavesZ <= oz + radius; ++leavesZ)
                {
                    int relZ = leavesZ - oz;

                    Block block = par1World.getBlock(leavesX, leavesY, leavesZ);

                    if (block == null || block.canBeReplacedByLeaves(par1World, leavesX, leavesY, leavesZ))
                    {
                        int metadata;
                        if (fruitChance > 0 && par2Random.nextInt(fruitChance) == 0)
                        {
                            metadata = this.metaFruit;
                        }
                        else
                        {
                            metadata = this.metaLeaves;
                        }
                        this.setBlockAndNotifyAdequately(par1World, leavesX, leavesY, leavesZ, blockLeaves, metadata);
                    }
                }
            }
        }
    }

}
