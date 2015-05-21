package tsuteto.tofu.item;

import net.minecraft.item.ItemSword;
import tsuteto.tofu.creativetabs.TcCreativeTabs;

public class ItemTofuSword extends ItemSword
{
    public ItemTofuSword(ToolMaterial material)
    {
        super(material);
        this.setCreativeTab(TcCreativeTabs.COMBAT);
    }
}
