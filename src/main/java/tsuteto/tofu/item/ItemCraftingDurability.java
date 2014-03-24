package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemCraftingDurability extends TcItem implements ICraftingDurability
{
    public ItemCraftingDurability()
    {
        super();
        this.setMaxDamage(9);
        this.setMaxStackSize(1);
    }

    @Override
    public Item getEmptyItem()
    {
        return Items.glass_bottle;
    }

}
