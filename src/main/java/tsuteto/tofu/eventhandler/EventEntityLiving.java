package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.apache.commons.lang3.ArrayUtils;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

public class EventEntityLiving
{

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        World world = event.world;
        EntityLivingBase living = event.entityLiving;
        
        if (living instanceof IMob)
        {
            int tileX = (int)event.x;
            int tileY = (int)event.y;
            int tileZ = (int)event.z;
            
            int i, j, k;
            for (i = -10; i <= 10; i++)
            {
                for (j = -10; j <= 10; j++)
                {
                    for (k = -10; k <= 10; k++)
                    {
                        Block block = world.getBlock(tileX + i, tileY + j, tileZ + k);
                        if (block == TcBlocks.morijio)
                        {
                            event.setResult(Event.Result.DENY);
                            ModLog.debug("%s canceled spawning by Morishio at (%.1f, %.1f, %.1f)", living.toString(), event.x, event.y, event.z);
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event)
    {
        if (event.ammount > 0.0f)
        {
            EntityLivingBase entityHurt = event.entityLiving;
            int diamondArmors = 0;

            for (ItemStack armor : entityHurt.getLastActiveItems())
            {
                if (armor != null && ArrayUtils.contains(TcItems.armorDiamond, armor.getItem())) diamondArmors++;
            }

            if (diamondArmors > 0)
            {
                float f1 = event.ammount;
                boolean isBlocking = entityHurt instanceof EntityPlayer && ((EntityPlayer)entityHurt).isBlocking();

                if (!event.source.isUnblockable() && isBlocking)
                {
                    f1 = (1.0F + f1) * 0.5F;
                }

                float f2 = ISpecialArmor.ArmorProperties.ApplyArmor(entityHurt, entityHurt.getLastActiveItems(), event.source, f1);

                if (event.source.getSourceOfDamage() != null)
                {
                    float dmgReflected = (f1 - f2) * 0.5f;
                    ModLog.debug("Damage reflected: %f to %s", dmgReflected, event.source.getSourceOfDamage().getCommandSenderName());
                    event.source.getSourceOfDamage().attackEntityFrom(
                            DamageSource.causeIndirectMagicDamage(event.source.getSourceOfDamage(), entityHurt),
                            dmgReflected);
                }
            }
        }
    }
}
