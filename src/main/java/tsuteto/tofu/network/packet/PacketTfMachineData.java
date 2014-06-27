package tsuteto.tofu.network.packet;

import cpw.mods.fml.client.FMLClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.AbstractPacket;

public class PacketTfMachineData extends AbstractPacket
{
    private int windowId;
    private int dataId;
    private DataHandler dataHandler;
    private ByteBuf buffer;

    public PacketTfMachineData() {}

    public PacketTfMachineData(int windowId, int dataId, DataHandler dataHandler)
    {
        this.windowId = windowId;
        this.dataId = dataId;

        this.dataHandler = dataHandler;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeByte(windowId);
        buffer.writeShort(dataId);
        dataHandler.addData(buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.windowId = buffer.readByte();
        this.dataId = buffer.readShort();
        this.buffer = buffer;
   }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        EntityClientPlayerMP entityclientplayermp = mc.thePlayer;

        if (entityclientplayermp.openContainer != null && entityclientplayermp.openContainer.windowId == windowId)
        {
            ((ContainerTfMachine)entityclientplayermp.openContainer).updateTfMachineData(dataId, buffer);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }

    public static interface DataHandler
    {
        void addData(ByteBuf buffer);
    }
}
