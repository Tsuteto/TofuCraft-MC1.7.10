package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.item.BatchDigging;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToServer;

public class PacketBatchDigging extends AbstractPacket implements MessageToServer
{

    public int w;
    public int d;
    public int h;
    public int bx;
    public int by;
    public int bz;
    public int blockId;
    public int blockMeta;
    public int sideHit;

    public PacketBatchDigging() {}

    public PacketBatchDigging(int w, int d, int h, int bx, int by, int bz, Block block, int meta, int sideHit)
    {
        this.w = w;
        this.d = d;
        this.h = h;
        this.bx = bx;
        this.by = by;
        this.bz = bz;
        this.blockId = Block.getIdFromBlock(block);
        this.blockMeta = meta;
        this.sideHit = sideHit;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeShort(w);
        buffer.writeShort(d);
        buffer.writeShort(h);
        buffer.writeInt(bx);
        buffer.writeInt(by);
        buffer.writeInt(bz);
        buffer.writeShort(blockId);
        buffer.writeByte(blockMeta);
        buffer.writeByte(sideHit);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.w = buffer.readShort();
        this.d = buffer.readShort();
        this.h = buffer.readShort();
        this.bx = buffer.readInt();
        this.by = buffer.readInt();
        this.bz = buffer.readInt();
        this.blockId = buffer.readShort();
        this.blockMeta = buffer.readByte();
        this.sideHit = buffer.readByte();
    }

    @Override
    public IMessage handleServerSide(EntityPlayer player)
    {
        new BatchDigging(this, player).execute();
        return null;
    }

}
