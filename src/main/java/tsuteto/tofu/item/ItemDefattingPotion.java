package tsuteto.tofu.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemDefattingPotion extends ItemColoredBottle
{
    public ItemDefattingPotion(int color)
    {
        super(color);
        this.itemInfo.setNonDurabilityTool();
    }

    @Override
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }
    
    
}
