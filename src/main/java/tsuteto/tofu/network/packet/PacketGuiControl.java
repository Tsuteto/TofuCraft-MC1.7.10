package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToServer;

public class PacketGuiControl extends AbstractPacket implements MessageToServer
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
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeByte(windowId);
        buffer.writeShort(eventId);
        if (dataHandler != null) dataHandler.addData(buffer);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.windowId = buffer.readByte();
        this.eventId = buffer.readShort();
        this.buffer = buffer;
   }

    @Override
    public IMessage handleServerSide(EntityPlayer player)
    {
        if (player.openContainer != null && player.openContainer.windowId == windowId)
        {
            ((ContainerTfMachine)player.openContainer).onGuiControl(eventId, buffer);
        }
        return null;
    }

    public static interface DataHandler
    {
        void addData(ByteBuf buffer);
    }
}
