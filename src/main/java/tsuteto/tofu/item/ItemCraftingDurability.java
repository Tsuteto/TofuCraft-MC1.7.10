package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCraftingDurability extends TcItem implements ICraftingDurability
{
    public ItemStack emptyItem = new ItemStack(Items.glass_bottle);

    public ItemCraftingDurability()
    {
        super();
        this.setMaxDamage(9);
        this.setMaxStackSize(1);
    }

    public ItemCraftingDurability setEmptyItem(ItemStack itemStack)
    {
        this.emptyItem = itemStack;
        return this;
    }

    @Override
    public ItemStack getEmptyItem()
    {
        // Don't set container item here!
        return emptyItem.copy();
    }

}
