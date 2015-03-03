package tsuteto.tofu.item.iteminfo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class TcItemSetInfo<T extends TcItemSetInfo> extends TcItemInfoBase<T>
{
    public final int id;
    public final String name;

    public TcItemSetInfo(int id, TcItemType type, String name)
    {
        super(type);
        this.id = id;
        this.name = name;
    }

    public ItemStack getStack()
    {
        return this.getStack(1);
    }

    public ItemStack getStack(int qty)
    {
        return new ItemStack(this.getItemInstance(), qty, id);
    }

    public abstract Item getItemInstance();
}
