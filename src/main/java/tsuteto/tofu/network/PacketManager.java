package tsuteto.tofu.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import tsuteto.tofu.network.packet.*;
import tsuteto.tofu.util.ModLog;

public class PacketManager
{
    private static SimpleNetworkWrapper networkHandler = null;
    private static int id = 0;

    public static void init(String modId)
    {
        networkHandler = NetworkRegistry.INSTANCE.newSimpleChannel(modId);

        registerPacket(PacketDimTrip.class);
        registerPacket(PacketBugle.class);
        registerPacket(PacketZundaArrowHit.class);
        registerPacket(PacketZundaArrowType.class);
        registerPacket(PacketTofuRadar.class);
        registerPacket(PacketGlowingFinish.class);
        registerPacket(PacketTfMachineData.class);
        registerPacket(PacketGuiControl.class);
        registerPacket(PacketSomenScooping.class);
        registerPacket(PacketSoymilkInfo.class);
        registerPacket(PacketBatchDigging.class);
        registerPacket(PacketPotionIdCheck.class);
    }

    public static SimpleNetworkWrapper getNetworkHandler()
    {
        return networkHandler;
    }

    private PacketManager() {}

    @SuppressWarnings("unchecked")
    private static void registerPacket(Class<? extends AbstractPacket> packetClass)
    {
        Class<AbstractPacket> message = (Class<AbstractPacket>)packetClass;
        if (MessageToServer.class.isAssignableFrom(packetClass))
        {
            networkHandler.registerMessage(packetClass, message, id, Side.SERVER);
            ModLog.debug("Registered Packet: %s at ID %d", packetClass.getName(), id);
            id++;
        }

        if (MessageToClient.class.isAssignableFrom(packetClass))
        {
            networkHandler.registerMessage(packetClass, message, id, Side.CLIENT);
            ModLog.debug("Registered Packet: %s at ID %d", packetClass.getName(), id);
            id++;
        }
    }

}
