package tsuteto.tofu.api.util;

import net.minecraft.world.World;

/**
 * Provides jitter to TF flow
 */
public class TfJitterControl
{
    public double typical;
    public double max;
    public double min;
    public int interval;

    private boolean isInit = false;
    private double prevRate;
    private double nextRate;
    private long seed;

    private static final long mask = (1L << 48) - 1;

    public TfJitterControl(double typical, double max, double min, int interval, long baseSeed)
    {
        this.typical = typical;
        this.max = max;
        this.min = min;
        this.interval = interval;
        this.seed = baseSeed;
    }

    public double getCurrentValue(World world, int x, int y, int z)
    {
        long wtime = world.getTotalWorldTime();

        if (!isInit)
        {
            this.seed *= world.getSeed();
            this.prevRate = this.getRate(wtime, x, y, z);
            this.nextRate = this.getRate(wtime + interval, x, y, z);
            isInit = true;
        }

        if (wtime % interval == 0)
        {
            this.prevRate = this.nextRate;
            this.nextRate = this.getRate(wtime + interval, x, y, z);
        }
        double jitterRate = (double)(wtime % interval) / (double)interval * (this.nextRate - this.prevRate) + this.prevRate;
        //ModLog.debug("Jitter: %.8f, curr=%.8f, next=%.8f, next in %d", jitterRate, prevRate, nextRate, interval - wtime % interval);
        return jitterRate;
    }

    private double getRate(long worldTime, int x, int y, int z)
    {
        long pos = ((long)x << 33) + ((long)z << 8) + y;
        double base = (double)(seed * (worldTime / interval) * pos * 2305843009213693951L + 7859210193484829329L & mask) / (double)(1L << 48);

        if (base > 0.5D)
        {
            return (base - 0.5D) * (max - typical) + typical;
        }
        else
        {
            return base * (typical - min) + min;
        }
    }
}
