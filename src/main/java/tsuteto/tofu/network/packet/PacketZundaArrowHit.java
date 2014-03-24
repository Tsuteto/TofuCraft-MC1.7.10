package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.entity.EntityZundaArrow;
import tsuteto.tofu.network.AbstractPacket;

public class PacketZundaArrowHit extends AbstractPacket
{
    double x;
    double y;
    double z;

    public PacketZundaArrowHit() {}

    public PacketZundaArrowHit(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        EntityZundaArrow.emitArrowHitEffect(x, y, z);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
