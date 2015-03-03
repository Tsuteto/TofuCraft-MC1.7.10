package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ItemSeasoningBottle extends ItemColoredBottle
{
    public ItemSeasoningBottle(int color, int durability)
    {
        super(color);
        this.setMaxDamage(durability);
        this.setNoRepair();
        itemInfo.setCraftingDurabilityTool();
        itemInfo.setContainerItem(new ItemStack(Items.glass_bottle));
        this.setMaxStackSize(1);
    }
}
