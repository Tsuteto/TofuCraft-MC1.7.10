package tsuteto.tofu.creativetabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tsuteto.tofu.TofuCraftCore;

abstract public class CreativeTabTofuCraft extends CreativeTabs
{
	public CreativeTabTofuCraft(String name)
    {
        super(TofuCraftCore.modid + "." + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    abstract public Item getTabIconItem();

}
