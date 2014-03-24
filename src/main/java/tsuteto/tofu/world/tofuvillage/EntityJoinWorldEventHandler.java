package tsuteto.tofu.world.tofuvillage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import tsuteto.tofu.Settings;
import tsuteto.tofu.entity.EntityTofunian;

public class EntityJoinWorldEventHandler {

	@SubscribeEvent
	public void EntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.entity.dimension == Settings.tofuDimNo)
		{
			if(event.entity.getClass() == EntityVillager.class)
			{
                EntityLiving tofunian = new EntityTofunian(event.world);
                tofunian.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, MathHelper.wrapAngleTo180_float(event.world.rand.nextFloat() * 360.0F), 0.0F);
                tofunian.rotationYawHead = tofunian.rotationYaw;
                tofunian.renderYawOffset = tofunian.rotationYaw;
                event.world.spawnEntityInWorld(tofunian);
                event.setCanceled(true);
			}
		}
	}
}
