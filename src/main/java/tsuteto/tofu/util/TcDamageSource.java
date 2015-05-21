package tsuteto.tofu.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import tsuteto.tofu.entity.EntityFukumame;
import tsuteto.tofu.entity.EntityZundaArrow;

public class TcDamageSource
{
    public static DamageSource causeZundaArrowDamage(EntityZundaArrow par0EntityArrow, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("zundaArrow", par0EntityArrow, par1Entity)).setProjectile();
    }

    public static DamageSource causeFukumameDamage(EntityFukumame par0EntityArrow, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("fukumame", par0EntityArrow, par1Entity)).setProjectile();
    }

    public static DamageSource causeIndirectDamage(Entity par0Entity, Entity par1Entity)
    {
        return (new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity));
    }

}
