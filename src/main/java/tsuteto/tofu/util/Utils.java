package tsuteto.tofu.util;

import com.google.common.base.Strings;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.Settings;
import tsuteto.tofu.TofuCraftCore;

import java.util.Iterator;
import java.util.List;

/**
 * Utility Methods
 *
 * @author Tsuteto
 *
 */
public class Utils
{

    /**
     * Get Tofu World seed from the world seed
     *
     * @param world
     * @return
     */
    public static long getSeedForTofuWorld(World world)
    {
        long upper = (world.getSeed() & 31) << 60;
        long lower = world.getSeed() >>> 4;
        return upper | lower;
    }

    public static String capitalize(String s)
    {
        if (Strings.isNullOrEmpty(s))
        {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + (s.length() >= 2 ? s.substring(1) : "");
    }
}
