package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.ItemTofuSlimeRadar;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.network.AbstractPacket;

public class PacketTofuRadar extends AbstractPacket
{
    boolean isSpawnChunk;

    public PacketTofuRadar() {}

    public PacketTofuRadar(boolean isSpawnChunk)
    {
        this.isSpawnChunk = isSpawnChunk;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeBoolean(isSpawnChunk);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        isSpawnChunk = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        ItemStack itemstack = player.getCurrentEquippedItem();

        if (itemstack != null && itemstack.getItem() == TcItems.tofuRadar)
        {
            ((ItemTofuSlimeRadar)TcItems.tofuRadar).onUse(isSpawnChunk, itemstack, player.worldObj, player);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
