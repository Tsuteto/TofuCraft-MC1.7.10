package tsuteto.tofu.item;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class TofuToolMaterial
{
    public static Item.ToolMaterial KINU;
    public static Item.ToolMaterial MOMEN;
    public static Item.ToolMaterial SOLID;
    public static Item.ToolMaterial METAL;
    public static Item.ToolMaterial DIAMOND;
    public static Item.ToolMaterial GEM;
    
    static
    {
        KINU    = EnumHelper.addToolMaterial("TOFU_KINU",    0,    1, 1.0F, -3, 50);
        MOMEN   = EnumHelper.addToolMaterial("TOFU_MOMEN",   0,    5, 1.2F, -3,  2);
        SOLID   = EnumHelper.addToolMaterial("TOFU_SOLID",   1,  183, 3.0F, -1,  6);
        METAL   = EnumHelper.addToolMaterial("TOFU_METAL",   2,  415, 6.0F,  2,  8);
        DIAMOND = EnumHelper.addToolMaterial("TOFU_DIAMOND", 3, 0x1212, 8.0F,  4, 18);
        GEM     = EnumHelper.addToolMaterial("TOFU_GEM",     2,  868, 6.0F,  2, 10);
    }
}
