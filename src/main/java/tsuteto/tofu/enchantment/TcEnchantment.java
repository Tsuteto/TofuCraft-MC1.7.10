package tsuteto.tofu.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

public class TcEnchantment
{
    private static final String CONF_CATEGORY = "enchantment";
    public static final EnumEnchantmentType typeDiamondTofu = EnumHelper.addEnchantmentType("diamond_tofu");

    public static Enchantment enchantmentBatch = null;
    public static Enchantment enchantmentDrain = null;
    public static Enchantment enchantmentReflection = null;

    public static void register(Configuration conf)
    {
        try {
            enchantmentBatch = new Enchantment(assignId("batch", conf), 10, typeDiamondTofu)
            {
                {
                    this.setName("batch");
                }

                public int getMinEnchantability(int par1)
                {
                    return 15 + (par1 - 1) * 9;
                }

                public int getMaxEnchantability(int par1)
                {
                    return this.getMinEnchantability(par1) + 50;
                }

                @Override
                public int getMaxLevel()
                {
                    return 3;
                }

                @Override
                public boolean canApply(ItemStack par1ItemStack)
                {
                    return ArrayUtils.contains(TcItems.toolDiamond, par1ItemStack.getItem());
                }
            };

            enchantmentDrain = new Enchantment(assignId("drain", conf), 10, typeDiamondTofu)
            {
                {
                    this.setName("drain");
                }

                public int getMinEnchantability(int par1)
                {
                    return 1 + 10 * (par1 - 1);
                }

                public int getMaxEnchantability(int par1)
                {
                    return this.getMinEnchantability(par1) + 50;
                }

                @Override
                public int getMaxLevel()
                {
                    return 5;
                }

                @Override
                public boolean canApply(ItemStack par1ItemStack)
                {
                    return par1ItemStack.getItem() == TcItems.swordDiamond;
                }
            };
            enchantmentReflection = new Enchantment(assignId("reflection", conf), 10, typeDiamondTofu)
            {
                {
                    this.setName("reflection");
                }

                public int getMinEnchantability(int par1)
                {
                    return 1 + 10 * (par1 - 1);
                }

                public int getMaxEnchantability(int par1)
                {
                    return this.getMinEnchantability(par1) + 50;
                }

                @Override
                public int getMaxLevel()
                {
                    return 5;
                }

                @Override
                public boolean canApply(ItemStack par1ItemStack)
                {
                    return ArrayUtils.contains(TcItems.armorDiamond, par1ItemStack.getItem());
                }
            };
        }
        catch (Exception e)
        {
            ModLog.log(Level.WARN, e, e.getLocalizedMessage());
        }
    }

    public static int assignId(String confKey, Configuration conf) throws Exception
    {
        int maxId = Math.min(Short.MAX_VALUE, Enchantment.enchantmentsList.length - 1);

        if (conf.hasKey(CONF_CATEGORY, confKey))
        {
            int id = conf.get(CONF_CATEGORY, confKey, -1).getInt();
            if (id >= 0 && id <= maxId)
            {
                return id;
            }
        }

        // Find an undefined entry
        for (int i = maxId; i >= 0; i--)
        {
            Enchantment entry = Enchantment.enchantmentsList[i];
            if (entry == null)
            {
                conf.get(CONF_CATEGORY, confKey, i).set(i);
                return i;
            }
        }

        // Unable to find entry
        throw new Exception("Failed to register ENCHANTMENT. Seems to be running out of enchantment ID.");
    }
}
