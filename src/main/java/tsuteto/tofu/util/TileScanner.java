package tsuteto.tofu.util;

import net.minecraft.world.World;

public class TileScanner
{
    private final World world;
    private final int ox, oy, oz;

    public enum Method
    {
        partial(new ScanMethodImpl()
        {
            @Override
            public void apply(World world, int x, int y, int z, int ax, int ay, int az, int size, Impl impl)
            {
                int dist = Math.abs(x + y + z);

                if (dist == size)
                {
                    impl.apply(world, ax, ay, az);
                }
            }
        }),

        whole(new ScanMethodImpl()
        {
            @Override
            public void apply(World world, int x, int y, int z, int ax, int ay, int az, int size, Impl impl)
            {
                impl.apply(world, ax, ay, az);
            }
        });

        public ScanMethodImpl impl;

        Method(ScanMethodImpl impl)
        {
           this.impl = impl;
        }
    }

    public TileScanner(World world, int x, int y, int z)
    {
        this.world = world;
        this.ox = x;
        this.oy = y;
        this.oz = z;
    }

    public <T> T scan(int size,  Impl<T> impl)
    {
        return this.scan(size, Method.partial, impl);
    }

    public <T> T scan(int size, Method method,  Impl<T> impl)
    {
        impl.ox = this.ox;
        impl.oy = this.oy;
        impl.oz = this.oz;

        for (int x = -size; x <= size; x++)
        {
            for (int y = -size; y <= size; y++)
            {
                for (int z = -size; z <= size; z++)
                {
                    if (x == 0 && y == 0 && z == 0) continue;

                    int ax = ox + x;
                    int ay = oy + y;
                    int az = oz + z;
                    method.impl.apply(world, x, y, z, ax, ay, az, size, impl);
                }
            }
        }
        return impl.getReturn();
    }

    private interface ScanMethodImpl
    {
        void apply(World world, int x, int y, int z, int ax, int ay, int az, int size, Impl impl);
    }

    abstract public static class Impl<T>
    {
        public int ox, oy, oz;
        abstract public void apply(World world, int x, int y, int z);
        public T getReturn()
        {
            return null;
        }
    }
}
