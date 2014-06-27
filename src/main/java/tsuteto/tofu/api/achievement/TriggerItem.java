package tsuteto.tofu.api.achievement;

import net.minecraft.item.ItemStack;

public class TriggerItem implements AchievementTrigger
{
    public static final int DMG_WILDCARD = 0x7FFF;

    private final ItemStack itemstack;

    public TriggerItem(ItemStack itemstack)
    {
        this.itemstack = itemstack;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof ItemStack)
        {
            if (itemstack.getItemDamage() == DMG_WILDCARD)
            {
                return  ((ItemStack) obj).getItem() == itemstack.getItem();
            }
            else
            {
                return ((ItemStack)obj).isItemEqual(itemstack);
            }
        }
        else
        {
            return false;
        }
    }
}
