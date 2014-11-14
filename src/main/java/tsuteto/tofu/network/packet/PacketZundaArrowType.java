package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.data.DataType;
import tsuteto.tofu.data.EntityInfo;
import tsuteto.tofu.item.ItemZundaBow.EnumArrowType;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;

public class PacketZundaArrowType extends AbstractPacket implements MessageToClient
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
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeInt(entityId);
        buffer.writeByte(type);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        entityId = buffer.readInt();
        type = buffer.readByte();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        EntityInfo.instance().set(entityId, DataType.ZundaArrowType, EnumArrowType.values()[type]);
        return null;
    }
}
