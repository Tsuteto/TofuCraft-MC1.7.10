package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSeasoningBottle extends ItemColoredBottle implements ICraftingDurability
{
    public ItemSeasoningBottle(int color)
    {
        super(color);
        this.setMaxDamage(9);
        this.setMaxStackSize(1);
    }

    @Override
    public Item getEmptyItem()
    {
        return Items.glass_bottle;
    }
}
