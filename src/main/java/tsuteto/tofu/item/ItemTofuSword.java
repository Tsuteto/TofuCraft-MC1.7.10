package tsuteto.tofu.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;
import tsuteto.tofu.util.ItemUtils;

public class ItemTofuSword extends ItemSword
{
    public ItemTofuSword(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return ItemUtils.getCreativeTabs(this);
    }
}
