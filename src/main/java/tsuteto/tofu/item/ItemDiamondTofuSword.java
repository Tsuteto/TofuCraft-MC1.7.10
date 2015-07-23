package tsuteto.tofu.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.data.DataType;
import tsuteto.tofu.data.EntityInfo;
import tsuteto.tofu.enchantment.TcEnchantmentHelper;

public class ItemDiamondTofuSword extends ItemTofuSword
{

    public ItemDiamondTofuSword(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
        if (entity instanceof EntityLivingBase)
        {
            EntityInfo.instance().set(player.getEntityId(), DataType.DiamondSwordAttack, ((EntityLivingBase) entity).getHealth());
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase targetEntity, EntityLivingBase entityActed)
    {
        // Drain
        Float healthBeforeAttack = EntityInfo.instance().<Float>get(entityActed.getEntityId(), DataType.DiamondSwordAttack);
        if (healthBeforeAttack != null)
        {
            float damage = healthBeforeAttack - targetEntity.getHealth();
            if (damage > 0.0f)
            {
                int lvl = TcEnchantmentHelper.getDrainModifier(entityActed);
                entityActed.heal(damage * (lvl * 0.1f + 0.1f));
            }

            EntityInfo.instance().remove(entityActed.getEntityId(), DataType.DiamondSwordAttack);
        }

        return super.hitEntity(par1ItemStack, targetEntity, entityActed);
    }
}
