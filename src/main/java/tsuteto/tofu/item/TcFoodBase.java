package tsuteto.tofu.item;

import net.minecraft.item.ItemStack;

public class TcFoodBase<E extends TcFoodBase>
{
    public final int id;
    public final String name;
    public int itemUseDuration;
    public int healAmount;
    public float saturationModifier;
    public boolean alwaysEdible;
    public ItemStack container = null;

    public int potionId = 0;
    public int potionDuration = 0;
    public int potionAmplifier = 0;
    public float potionEffectProbability = 0F;
    
    TcFoodBase(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
    {
        this(id, healAmount, saturationModifier, alwaysEdible, 32, name);
    }
    
    TcFoodBase(int id, int healAmount, float saturationModifier, boolean alwaysEdible, int itemUseDuration, String name)
    {
        this.id = id;
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
        this.alwaysEdible = alwaysEdible;
        
        this.itemUseDuration = itemUseDuration;
        
        this.name = name;
    }
    
    public E setMaxItemUseDuration(int itemUseDuration)
    {
        this.itemUseDuration = itemUseDuration;
        return (E)this;
    }

    public E setPotionEffect(int potionId, int duration, int amplifier, float probability)
    {
        this.potionId = potionId;
        this.potionDuration = duration;
        this.potionAmplifier = amplifier;
        this.potionEffectProbability = probability;
        return (E)this;
    }
    
    public E setContainer(ItemStack item)
    {
        this.container = item;
        return (E)this;
    }
    
    public ItemStack getNewContainer()
    {
        return container.copy();
    }

    public boolean hasContainerItem()
    {
        return container != null;
    }
}
