package tsuteto.tofu.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import tsuteto.tofu.Settings;
import tsuteto.tofu.util.Utils;

public class WorldProviderTofu extends WorldProvider
{
    /**
     * creates a new world chunk manager for WorldProvider
     */
    @Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new WorldChunkManagerTofu(worldObj);
        this.dimensionId = Settings.tofuDimNo;
    }

    /**
     * Returns a new chunk provider register generates chunks for this world
     */
    @Override
    public IChunkProvider createChunkGenerator()
    {
        long newSeed = Utils.getSeedForTofuWorld(this.worldObj);
        return new ChunkProviderTofu(this.worldObj, newSeed, true);
    }

    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    @Override
    public String getDimensionName()
    {
        return "Tofu World";
    }

    @Override
    public String getWelcomeMessage()
    {
        return "Entering the Tofu World";
    }

    /**
     * A Message to display to the user when they transfer out of this dismension.
     *
     * @return The message to be displayed
     */
    @Override
    public String getDepartMessage()
    {
        return "Leaving the Tofu World";
    }
}
