package tsuteto.tofu.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class DimensionTeleportation
{
    public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2)
    {
        par1EntityPlayerMP.mcServer.getConfigurationManager().transferPlayerToDimension(
                par1EntityPlayerMP, par2,
                new TofuTeleporter(par1EntityPlayerMP.mcServer.worldServerForDimension(par2)));
    }

    public Entity transferEntityToDimension(Entity entity, int par1)
    {
        if (!entity.worldObj.isRemote && !entity.isDead)
        {
            entity.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int j = entity.dimension;
            WorldServer worldserver = minecraftserver.worldServerForDimension(j);
            WorldServer worldserver1 = minecraftserver.worldServerForDimension(par1);
            entity.dimension = par1;

            entity.worldObj.removeEntity(entity);
            entity.isDead = false;
            entity.worldObj.theProfiler.startSection("reposition");
            this.transferEntityToWorld(entity, j, worldserver, worldserver1);
            entity.worldObj.theProfiler.endStartSection("reloading");
            Entity entity2 = EntityList.createEntityByName(EntityList.getEntityString(entity), worldserver1);

            if (entity2 != null)
            {
                entity2.copyDataFrom(entity, true);

                worldserver1.spawnEntityInWorld(entity2);
            }

            entity.isDead = true;
            entity.worldObj.theProfiler.endSection();
            worldserver.resetUpdateEntityTick();
            worldserver1.resetUpdateEntityTick();
            entity.worldObj.theProfiler.endSection();
            return entity2;
        }
        return null;
    }
    
    /**
     * Transfers an entity from a world to another world.
     */
    private void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer)
    {
        MinecraftServer.getServer().getConfigurationManager().transferEntityToWorld(
                par1Entity, par2, par3WorldServer, par4WorldServer, new TofuTeleporter(par4WorldServer));
    }

}
