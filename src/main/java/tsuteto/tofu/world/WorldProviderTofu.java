package tsuteto.tofu.world;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
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

    @Override
    public void resetRainAndThunder() {
        super.resetRainAndThunder();

        if(this.worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
        {
            WorldInfo worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo) worldObj.getWorldInfo(), "theWorldInfo", "field_76115_a");
            long i = worldInfo.getWorldTime() + 24000L;
            worldInfo.setWorldTime(i - i % 24000L);
        }
    }

    @Override
    public boolean canRespawnHere()
    {
        return true;
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
