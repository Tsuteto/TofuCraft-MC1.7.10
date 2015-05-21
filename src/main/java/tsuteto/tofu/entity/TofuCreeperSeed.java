package tsuteto.tofu.entity;

import net.minecraft.world.World;

public class TofuCreeperSeed
{
    private static TofuCreeperSeed instance = null;
    
    private long s;
    private long bs;

    public static TofuCreeperSeed instance()
    {
        return instance;
    }
    
    public static void initialize(long par1)
    {
        instance = new TofuCreeperSeed(par1);
    }

    private TofuCreeperSeed(long par1)
    {
        this.bs = par1;
        this.bs *= this.bs * 6364136223846793005L + 1442695040888963407L;
        this.bs += par1;
        this.bs *= this.bs * 6364136223846793005L + 1442695040888963407L;
        this.bs += par1;
        this.bs *= this.bs * 6364136223846793005L + 1442695040888963407L;
        this.bs += par1;
    }

    public void initSeed(long worldSeed)
    {
        this.s = worldSeed;

        this.s *= this.s * 6364136223846793005L + 1442695040888963407L;
        this.s += this.bs;
        this.s *= this.s * 6364136223846793005L + 1442695040888963407L;
        this.s += this.bs;
        this.s *= this.s * 6364136223846793005L + 1442695040888963407L;
        this.s += this.bs;
    }

    public int[] getSpawnId(World world, int par1)
    {
        int[] j = new int[4];
        long d = world.getWorldTime() / 24000L + 1;
        long a = this.s * d;

        j[0] = (int)((a >> 24) % par1);

        for (int i = 1; i < j.length; i++)
        {
            a *= this.s * 6364136223846793005L + 1442695040888963407L;
            a += this.bs;
            j[i] = (int) ((a >> 24) % par1);
        }

        for (int i = 0; i < j.length; i++)
        {
            if (j[i] < 0) j[i] += par1;
        }
        //ModLog.debug("Tofu Creeper: %d, %d, %s", j1, j2, a);

        return j;
    }
}
