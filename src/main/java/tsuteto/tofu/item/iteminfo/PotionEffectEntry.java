package tsuteto.tofu.item.iteminfo;

import net.minecraft.potion.Potion;

public class PotionEffectEntry
{
    public final Potion potion;
    public final int duration;
    public final int amplifier;
    public final float probability;

    public PotionEffectEntry(Potion potion, int duration, int amplifier, float probability)
    {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
        this.probability = probability;
    }
}
