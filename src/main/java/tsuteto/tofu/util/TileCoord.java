package tsuteto.tofu.util;

import net.minecraft.nbt.NBTTagCompound;

public class TileCoord
{
    public final int x;
    public final int y;
    public final int z;
    public final int dim;

    public TileCoord(int x, int y, int z)
    {
        this(x, y, z, 0);
    }

    public TileCoord(int x, int y, int z, int dim)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("CoordX", this.x);
        nbt.setInteger("CoordY", this.y);
        nbt.setInteger("CoordZ", this.z);
        nbt.setInteger("CoordD", this.dim);
    }

    public static TileCoord readFromNBT(NBTTagCompound nbt)
    {
        int x = nbt.getInteger("CoordX");
        int y = nbt.getInteger("CoordY");
        int z = nbt.getInteger("CoordZ");
        int dim = nbt.getInteger("CoordD");
        return new TileCoord(x, y, z, dim);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TileCoord)
        {
            TileCoord another = (TileCoord)obj;
            return this.x == another.x
                    && this.y == another.y
                    && this.z == another.z
                    && this.dim == another.dim;
        }
        else return false;
    }

    @Override
    public int hashCode()
    {
        return ((this.x << 20) + (this.z << 8) + this.y) + this.dim;
    }
}
