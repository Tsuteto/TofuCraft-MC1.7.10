package tsuteto.tofu.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import tsuteto.tofu.network.packet.*;

/**
 * Handles PacketPipeline and dispatches a packet
 *
 * @author Tsuteto
 *
 */
public class PacketDispatcher
{
    private static final PacketPipeline packetPipeline = new PacketPipeline();
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
        packetPipeline.sendToServer(packet);
    }

    public void sendToPlayer(EntityPlayerMP player)
    {
        packetPipeline.sendTo(packet, player);
    }

    public void sendToAllInDimension(int dimId)
    {
        packetPipeline.sendToDimension(packet, dimId);
    }

    public void sendToAllAround(double X, double Y, double Z, int range, int dimensionId)
    {
        NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(dimensionId, X, Y, Z, range);
        packetPipeline.sendToAllAround(packet, targetPoint);
    }
    
    public void sendPacketToAllPlayers()
    {
        packetPipeline.sendToAll(packet);
    }

    public static void init()
    {
        packetPipeline.initalise();
        packetPipeline.registerPacket(PacketDimTrip.class);
        packetPipeline.registerPacket(PacketBugle.class);
        packetPipeline.registerPacket(PacketZundaArrowHit.class);
        packetPipeline.registerPacket(PacketZundaArrowType.class);
        packetPipeline.registerPacket(PacketTofuRadar.class);
        packetPipeline.registerPacket(PacketGlowingFinish.class);
        packetPipeline.registerPacket(PacketTfMachineData.class);
        packetPipeline.registerPacket(PacketGuiControl.class);
        packetPipeline.registerPacket(PacketSomenScooping.class);
    }

    public static void postInit()
    {
        packetPipeline.postInitialise();
    }
}
