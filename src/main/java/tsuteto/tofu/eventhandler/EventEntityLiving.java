package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import tsuteto.tofu.block.TcBlocks;
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
}
