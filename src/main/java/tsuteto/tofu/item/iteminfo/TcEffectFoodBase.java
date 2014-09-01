package tsuteto.tofu.item.iteminfo;

import com.google.common.collect.Lists;
import net.minecraft.potion.Potion;

import java.util.List;

public class TcEffectFoodBase<T extends TcEffectFoodBase> extends TcFoodBase<T>
{
    public List<PotionEffectEntry> potionEffects = null;
    public boolean randomPotionEffect = false;
    public float randomPotionEffectProb = 0.0f;
    public double randomPotionProbTotal = 0.0D;

    public TcEffectFoodBase(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
    {
        super(id, healAmount, saturationModifier, alwaysEdible, name);
    }

    public T addPotionEffect(Potion potion, int duration, int amplifier, float probability)
    {
        if (potionEffects == null)
        {
            this.potionEffects = Lists.newArrayList();
        }
        this.potionEffects.add(new PotionEffectEntry(potion, duration, amplifier, probability));
        this.randomPotionProbTotal += probability;
        return (T)this;
    }

    public T setRandomPotionEffect(float probability)
    {
        this.randomPotionEffect = true;
        this.randomPotionEffectProb = probability;
        return (T)this;
    }

}
