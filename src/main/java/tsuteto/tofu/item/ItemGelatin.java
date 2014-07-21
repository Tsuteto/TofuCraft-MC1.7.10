package tsuteto.tofu.item;

import tsuteto.tofu.item.iteminfo.TcItemSetInfo;
import tsuteto.tofu.item.iteminfo.TcItemType;

public class ItemGelatin extends ItemSetBase<ItemGelatin.ItemInfo>
{
    public static final ItemInfo[] itemList = new ItemInfo[2];
    public static final ItemInfo gelatin = new ItemInfo(0, TcItemType.NORMAL, "gelatin");
    public static final ItemInfo gelatinRaw = new ItemInfo(1, TcItemType.NORMAL, "gelatinRaw");

    public static class ItemInfo extends TcItemSetInfo<ItemInfo>
    {

        public ItemInfo(int id, TcItemType type, String name)
        {
            super(id, type, name);
            itemList[id] = this;
        }
    }

    public ItemGelatin()
    {
        super();
    }

    @Override
    public ItemInfo[] getItemList()
    {
        return itemList;
    }
}
