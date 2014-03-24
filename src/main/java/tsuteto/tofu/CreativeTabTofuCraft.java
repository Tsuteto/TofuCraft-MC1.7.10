package tsuteto.tofu;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tsuteto.tofu.item.TcItems;

final class CreativeTabTofuCraft extends CreativeTabs {
	
	CreativeTabTofuCraft(String par2Str)
    {
        super(par2Str);
    }

    @Override
    @SideOnly(Side.CLIENT)

    /**
     * the itemID for the item to be displayed on the tab
     */
    public Item getTabIconItem()
    {
        return TcItems.tofuMomen;
    }

}
