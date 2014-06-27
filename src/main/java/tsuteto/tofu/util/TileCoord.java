package tsuteto.tofu.util;

public class TileCoord
{
    public int x;
    public int y;
    public int z;

    public TileCoord(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TileCoord)
        {
            TileCoord another = (TileCoord)obj;
            return this.x == another.x && this.y == another.y && this.z == another.z;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return (this.x << 20) + (this.z << 8) + this.y;
    }
}
