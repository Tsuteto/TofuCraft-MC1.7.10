package tsuteto.tofu.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tsuteto.tofu.init.TcItems;

public class TcCreativeTabs
{
    public static final CreativeTabs MAIN = new CreativeTabTofuCraft("main")
    {
        @Override
        public Item getTabIconItem()
        {
            return TcItems.tofuMomen;
        }
    };
    public static final CreativeTabs FOOD = MAIN;
    public static final CreativeTabs DECORATIONS = MAIN;
    public static final CreativeTabs CONSTRUCTION = new CreativeTabTofuCraft("construction")
    {
        @Override
        public Item getTabIconItem()
        {
            return TcItems.tofuDoor;
        }
    };
    public static final CreativeTabs TOOLS = new CreativeTabTofuCraft("tools")
    {
        @Override
        public Item getTabIconItem()
        {
            return TcItems.toolMomen[1];
        }
    };
    public static final CreativeTabs COMBAT = TOOLS;
    public static final CreativeTabs MATERIALS = MAIN;
    public static final CreativeTabs MISC = MAIN;
}
