package tsuteto.tofu.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.ItemSomenTsuyuBowl;
import tsuteto.tofu.network.AbstractPacket;

public class PacketSomenScooping extends AbstractPacket
{
    private int entityId;

    public PacketSomenScooping() {}

    public PacketSomenScooping(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(entityId);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        entityId = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(entityId);
        ItemStack itemHeld = player.getHeldItem();
        ItemStack newItem = ItemSomenTsuyuBowl.scoopSomen(entity, itemHeld, player, false);
        player.inventory.mainInventory[player.inventory.currentItem] = newItem;
    }
}
