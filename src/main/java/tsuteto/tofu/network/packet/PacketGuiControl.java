package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.AbstractPacket;

public class PacketGuiControl extends AbstractPacket
{
    private int windowId;
    private int eventId;
    private DataHandler dataHandler;
    private ByteBuf buffer;

    public PacketGuiControl() {}

    public PacketGuiControl(int windowId, int eventId, DataHandler dataHandler)
    {
        this.windowId = windowId;
        this.eventId = eventId;

        this.dataHandler = dataHandler;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeByte(windowId);
        buffer.writeShort(eventId);
        if (dataHandler != null) dataHandler.addData(buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.windowId = buffer.readByte();
        this.eventId = buffer.readShort();
        this.buffer = buffer;
   }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        if (player.openContainer != null && player.openContainer.windowId == windowId)
        {
            ((ContainerTfMachine)player.openContainer).onGuiControl(eventId, buffer);
        }
    }

    public static interface DataHandler
    {
        void addData(ByteBuf buffer);
    }
}
