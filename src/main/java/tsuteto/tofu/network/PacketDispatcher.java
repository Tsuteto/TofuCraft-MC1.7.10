package tsuteto.tofu.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Handles PacketPipeline and dispatches a packet
 *
 * @author Tsuteto
 *
 */
public class PacketDispatcher
{
    private static final SimpleNetworkWrapper networkHandler = PacketManager.getNetworkHandler();
    private AbstractPacket packet;

    public static PacketDispatcher packet(AbstractPacket packet)
    {
        return new PacketDispatcher(packet);
    }

    private PacketDispatcher(AbstractPacket packet)
    {
        this.packet = packet;
    }

    public void sendToServer()
    {
        networkHandler.sendToServer(packet);
    }

    public void sendToPlayer(EntityPlayer player)
    {
        networkHandler.sendTo(packet, (EntityPlayerMP) player);
    }

    public void sendToPlayer(EntityPlayerMP player)
    {
        networkHandler.sendTo(packet, player);
    }

    public void sendToAllInDimension(int dimId)
    {
        networkHandler.sendToDimension(packet, dimId);
    }

    public void sendToAllAround(double X, double Y, double Z, int range, int dimensionId)
    {
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(dimensionId, X, Y, Z, range);
        networkHandler.sendToAllAround(packet, targetPoint);
    }
    
    public void sendPacketToAllPlayers()
    {
        networkHandler.sendToAll(packet);
    }

}
