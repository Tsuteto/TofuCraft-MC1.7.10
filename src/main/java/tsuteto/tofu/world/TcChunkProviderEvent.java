package tsuteto.tofu.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import tsuteto.tofu.Settings;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.world.biome.BiomeGenTofuBase;
import tsuteto.tofu.world.gen.feature.WorldGenCrops;

import java.util.Random;

public class TcChunkProviderEvent
{
    @SubscribeEvent
    public void decorateBiome(DecorateBiomeEvent.Post event)
    {
        World worldObj = event.world;
        Random rand = event.rand;

        // Hellsoybeans
        if (rand.nextInt(600) < Math.min((Math.abs(event.chunkX) + Math.abs(event.chunkZ)) / 2, 400) - 100)
        {
            if (worldObj.getBiomeGenForCoords(event.chunkX, event.chunkZ).biomeID == BiomeGenBase.hell.biomeID)
            {
                int k = event.chunkX;
                int l = event.chunkZ;

                for (int i = 0; i < 10; ++i)
                {
                    int j1 = k + rand.nextInt(16) + 8;
                    int k1 = rand.nextInt(128);
                    int l1 = l + rand.nextInt(16) + 8;
                    (new WorldGenCrops(TcBlocks.soybeanHell)).generate(worldObj, rand, j1, k1, l1);
                }
            }
        }
    }

    private final WorldGenerator tofuOreGen = new WorldGenMinable(TcBlocks.oreTofu, 4);

    @SubscribeEvent
    public void generateTofuOres(OreGenEvent.Post event)
    {
        World worldObj = event.world;
        Random rand = event.rand;
        int x = event.worldX;
        int y = event.worldZ;

        for (int l = 0; l < 8; ++l)
        {
            int i1 = x + rand.nextInt(16);
            int j1 = rand.nextInt(82 - 45) + 45;
            int k1 = y + rand.nextInt(16);
            tofuOreGen.generate(worldObj, rand, i1, j1, k1);
        }
    }
}
