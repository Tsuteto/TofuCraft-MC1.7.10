package tsuteto.tofu.item.iteminfo;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import java.util.List;

public class TcFoodBase<T extends TcFoodBase> extends TcItemSetInfo<T>
{
    public int healAmount;
    public float saturationModifier;
    public boolean alwaysEdible;

    public List<PotionEffectEntry> potionEffects = null;
    public boolean randomPotionEffect = false;
    public float randomPotionEffectProb = 0.0f;

    public TcFoodBase(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
    {
        super(id, TcItemType.NORMAL, name);
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
        this.alwaysEdible = alwaysEdible;
    }
    
    public T addPotionEffect(Potion potion, int duration, int amplifier, float probability)
    {
        if (potionEffects == null)
        {
            this.potionEffects = Lists.newArrayList();
        }
        this.potionEffects.add(new PotionEffectEntry(potion, duration, amplifier, probability));
        return (T)this;
    }
    
    public T setContainer(ItemStack item)
    {
        this.container = item;
        return (T)this;
    }

    public T setRandomPotionEffect(float probability)
    {
        this.randomPotionEffect = true;
        this.randomPotionEffectProb = probability;
        return (T)this;
    }
}
