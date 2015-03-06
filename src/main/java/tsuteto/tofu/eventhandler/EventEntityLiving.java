package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.apache.commons.lang3.ArrayUtils;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.data.DataType;
import tsuteto.tofu.data.EntityInfo;
import tsuteto.tofu.data.MorijioManager;
import tsuteto.tofu.data.PortalTripInfo;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

public class EventEntityLiving
{

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event)
    {
        EntityInfo pinfo = EntityInfo.instance();
        int entityId = event.entity.getEntityId();
        Entity entity = event.entity;

        if (!entity.worldObj.isRemote && entity.worldObj instanceof WorldServer)
        {
            if (pinfo.doesDataExist(event.entity.getEntityId(), DataType.TicksPortalCooldown))
            {
                PortalTripInfo info = pinfo.get(entityId, DataType.TicksPortalCooldown);
                if (!(entity.worldObj instanceof WorldServer)) ModLog.debug(entity.worldObj.getClass().getSimpleName());
                if (entity.addedToChunk
                        && entity.worldObj.blockExists((int)entity.posX, (int)entity.posY, (int)entity.posZ))
                {
                    int ticks = info.ticksCooldown;
                    if (ticks >= 20)
                    {
                        pinfo.remove(entityId, DataType.TicksPortalCooldown);
                    }
                    else
                    {
                        info.ticksCooldown += 1;
                    }
                    ModLog.debug("cooldown: %d", info.ticksCooldown);
                }
            }
        }
    }

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

            MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
            if (morijioManager.isInRangeOfMorijio(world, tileX, tileY, tileZ, living.dimension))
            {
                event.setResult(Event.Result.DENY);
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

    @SubscribeEvent
    public void onEndermanTeleporting(EnderTeleportEvent event)
    {
        World world = event.entity.worldObj;
        EntityLivingBase living = event.entityLiving;
        int tileX = (int)event.targetX;
        int tileY = (int)event.targetY;
        int tileZ = (int)event.targetZ;

        if (!world.isRemote)
        {
            MorijioManager morijioManager = TofuCraftCore.getMorijioManager();
            if (morijioManager.isInRangeOfMorijio(world, tileX, tileY, tileZ, living.dimension))
            {
                if (living instanceof EntityEnderman)
                {
                    ((EntityEnderman) living).setTarget(null);
                }
                event.setCanceled(true);
            }
        }
    }
}
