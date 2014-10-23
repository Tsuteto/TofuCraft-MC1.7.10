package tsuteto.tofu.enchantment;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;

public class TcEnchantmentHelper
{
    public static int getBatchModifier(EntityLivingBase par0EntityLivingBase)
    {
        return EnchantmentHelper.getEnchantmentLevel(TcEnchantment.enchantmentBatch.effectId, par0EntityLivingBase.getHeldItem());
    }

    public static int getDrainModifier(EntityLivingBase par0EntityLivingBase)
    {
        return EnchantmentHelper.getEnchantmentLevel(TcEnchantment.enchantmentDrain.effectId, par0EntityLivingBase.getHeldItem());
    }
}
