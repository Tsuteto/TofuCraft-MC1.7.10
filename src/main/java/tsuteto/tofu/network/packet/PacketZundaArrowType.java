package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.item.ItemZundaBow.EnumArrowType;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.params.DataType;
import tsuteto.tofu.params.EntityInfo;

public class PacketZundaArrowType extends AbstractPacket
{
    private int entityId;
    private int type;

    public PacketZundaArrowType() {}

    public PacketZundaArrowType(int entityId, EnumArrowType type)
    {
        this.entityId = entityId;
        this.type = type.ordinal();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(entityId);
        buffer.writeByte(type);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        entityId = buffer.readInt();
        type = buffer.readByte();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        EntityInfo.instance().set(entityId, DataType.ZundaArrowType, EnumArrowType.values()[type]);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
