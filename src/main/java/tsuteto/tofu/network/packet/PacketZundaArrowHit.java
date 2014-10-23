package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.entity.EntityZundaArrow;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;

public class PacketZundaArrowHit extends AbstractPacket implements MessageToClient
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
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        EntityZundaArrow.emitArrowHitEffect(x, y, z);
        return null;
    }
}
