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
    public ItemStack container;
    
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

    public E setPotionEffect(int par1, int par2, int par3, float par4)
    {
        this.potionId = par1;
        this.potionDuration = par2;
        this.potionAmplifier = par3;
        this.potionEffectProbability = par4;
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
}
