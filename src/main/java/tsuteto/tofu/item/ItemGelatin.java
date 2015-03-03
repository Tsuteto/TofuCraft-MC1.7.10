package tsuteto.tofu.item;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import tsuteto.tofu.item.iteminfo.TcItemSetInfo;
import tsuteto.tofu.item.iteminfo.TcItemType;

import java.util.List;

public class ItemGelatin extends ItemSetBase<ItemGelatin.ItemInfo>
{
    public static final List<ItemInfo> itemList = Lists.newArrayList();
    public static final ItemInfo gelatin = new ItemInfo(0, TcItemType.NORMAL, "gelatin");
    public static final ItemInfo gelatinRaw = new ItemInfo(1, TcItemType.NORMAL, "gelatinRaw");

    public static class ItemInfo extends TcItemSetInfo<ItemInfo>
    {

        public ItemInfo(int id, TcItemType type, String name)
        {
            super(id, type, name);
            itemList.add(id, this);
        }

        @Override
        public Item getItemInstance()
        {
            return TcItems.gelatin;
        }
    }

    public ItemGelatin()
    {
        super();
    }

    @Override
    public List<ItemInfo> getItemList()
    {
        return itemList;
    }
}
