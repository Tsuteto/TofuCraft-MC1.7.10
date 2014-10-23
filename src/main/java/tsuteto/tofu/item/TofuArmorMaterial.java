package tsuteto.tofu.item;

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class TofuArmorMaterial
{
    public static ItemArmor.ArmorMaterial KINU;
    public static ItemArmor.ArmorMaterial MOMEN;
    public static ItemArmor.ArmorMaterial SOLID;
    public static ItemArmor.ArmorMaterial METAL;
    public static ItemArmor.ArmorMaterial DIAMOND;
    
    static
    {
        KINU    = EnumHelper.addArmorMaterial("TOFU_KINU",     0, new int[]{1, 1, 1, 1},  1);
        MOMEN   = EnumHelper.addArmorMaterial("TOFU_MOMEN",    1, new int[]{1, 2, 1, 1},  3);
        SOLID   = EnumHelper.addArmorMaterial("TOFU_SOLID",    8, new int[]{2, 4, 3, 2},  8);
        METAL   = EnumHelper.addArmorMaterial("TOFU_METAL",   13, new int[]{2, 7, 6, 2},  9); // IRON={2, 6, 5, 2}
        DIAMOND = EnumHelper.addArmorMaterial("TOFU_DIAMOND", 80, new int[]{3, 8, 6, 3}, 20); // DIAMOND={3, 8, 6, 3}
    }
}
