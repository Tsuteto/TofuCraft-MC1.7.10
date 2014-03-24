package tsuteto.tofu.util;

public class TilePos
{
    public int x;
    public int y;
    public int z;
    
    public TilePos(int x, int y, int z)
    {
        this.setPos(x, y, z);
    }

    public void setPos(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode()
    {
        return this.x + this.y + this.z;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof TilePos)) return false;
        TilePos tilePos = (TilePos)o;
        return this.x == tilePos.x && this.y == tilePos.y && this.z == tilePos.z;
    }
    
    @Override
    public String toString()
    {
        return String.format("(%d, %d, %d)", x, y, z);
    }
}
