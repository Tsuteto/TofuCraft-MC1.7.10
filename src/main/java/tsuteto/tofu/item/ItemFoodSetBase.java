package tsuteto.tofu.item;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tsuteto.tofu.item.iteminfo.PotionEffectEntry;
import tsuteto.tofu.item.iteminfo.TcEffectFoodBase;

import java.util.List;

abstract public class ItemFoodSetBase<T extends TcEffectFoodBase> extends ItemBasicFoodSetBase<T>
{
    @Override
    public List<PotionEffect> getPotionEffect(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer)
    {
        World world = par2EntityPlayer.getEntityWorld();

        TcEffectFoodBase<?> food = this.getItemInfo(par1ItemStack.getItemDamage());
        List<PotionEffect> list = Lists.newArrayList();

        if (food.potionEffects != null)
        {
            if (!food.randomPotionEffect)
            {
                // Stable
                for (PotionEffectEntry entry : food.potionEffects)
                {
                    if (world.rand.nextFloat() < entry.probability)
                    {
                        list.add(new PotionEffect(entry.potion.getId(), entry.duration * 20, entry.amplifier));
                    }
                }
            }
            else if (world.rand.nextFloat() < food.randomPotionEffectProb)
            {
                // Random
                double total = food.randomPotionProbTotal;

                for (PotionEffectEntry entry : food.potionEffects)
                {
                    double rand = world.rand.nextDouble() * total;
                    if (rand < entry.probability)
                    {
                        list.add(new PotionEffect(entry.potion.getId(), entry.duration * 20, entry.amplifier));
                        break;
                    }
                    total -= entry.probability;
                }
            }
        }
        return list;
    }

}
