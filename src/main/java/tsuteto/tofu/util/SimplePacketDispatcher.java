package tsuteto.tofu.util;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.PacketPipeline;

/**
 * Dispatches a packet in one-liner
 *
 * @author Tsuteto
 *
 */
public class SimplePacketDispatcher
{
    private PacketPipeline pipeline = TofuCraftCore.packetPipeline;

    private AbstractPacket packet;

    public static SimplePacketDispatcher packet(AbstractPacket packet)
    {
        return new SimplePacketDispatcher(packet);
    }

    private SimplePacketDispatcher(AbstractPacket packet)
    {
        this.packet = packet;
    }

    public void sendToServer()
    {
        pipeline.sendToServer(packet);
    }

    public void sendToPlayer(EntityPlayerMP player)
    {
        pipeline.sendTo(packet, player);
    }

    public void sendToAllInDimension(int dimId)
    {
        pipeline.sendToDimension(packet, dimId);
    }

    public void sendToAllAround(double X, double Y, double Z, int range, int dimensionId)
    {
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(dimensionId, X, Y, Z, range);
        pipeline.sendToAllAround(packet, targetPoint);
    }
    
    public void sendPacketToAllPlayers()
    {
        pipeline.sendToAll(packet);
    }
}
