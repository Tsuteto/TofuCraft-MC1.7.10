package tsuteto.tofu.eventhandler;

import com.google.common.primitives.Ints;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemDiamondTofuArmor;

import java.util.List;

public class PotionEventHook
{
    public static PotionEffect onPotionEffectApplied(EntityLivingBase entity, PotionEffect potionEffect)
    {
        if (entity.worldObj.isRemote || potionEffect == null || potionEffect.getIsAmbient()) return potionEffect;

        int diamondArmors = 0;
        boolean[] armorsEquipped = new boolean[]{false, false, false, false};
        for (ItemStack armor : entity.getLastActiveItems())
        {
            if (armor != null)
            {
                for (int i = 0; i < 4; i++)
                {
                    if (armor.getItem() == TcItems.armorDiamond[i])
                    {
                        armorsEquipped[i] = true;
                        diamondArmors++;
                    }
                }
            }
        }

        if (diamondArmors > 0)
        {
            Boolean isBadEffect = ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[potionEffect.getPotionID()], "field_76418_K", "isBadEffect");
            if (isBadEffect)
            {
                int potionId = potionEffect.getPotionID();
                int amplifier = potionEffect.getAmplifier();
                int duration = potionEffect.getDuration();
                boolean isAmbient = potionEffect.getIsAmbient();
                List curativeItems = potionEffect.getCurativeItems();

                switch (diamondArmors)
                {
                    case 4:
                        amplifier = 1;
                    case 3:
                        duration /= 2;
                    case 2:
                        amplifier = Math.max(0, amplifier - 1);
                    case 1:
                        duration /= 2;
                }

                for (int i = 0; i < 4; i++)
                {
                    if (armorsEquipped[i] && Ints.contains(ItemDiamondTofuArmor.registanceList[i], potionEffect.getPotionID()))
                    {
                        duration = 0;
                    }
                }

                PotionEffect newPotionEffect = new PotionEffect(potionId, duration, amplifier, isAmbient);
                newPotionEffect.setCurativeItems(curativeItems);
                return newPotionEffect;
            }
        }
        return potionEffect;
    }
}
