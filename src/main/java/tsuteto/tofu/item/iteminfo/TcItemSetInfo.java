package tsuteto.tofu.item.iteminfo;

public class TcItemSetInfo<T extends TcItemSetInfo> extends TcItemInfoBase<T>
{
    public final int id;
    public final String name;

    public TcItemSetInfo(int id, TcItemType type, String name)
    {
        super(type);
        this.id = id;
        this.name = name;
    }
}
