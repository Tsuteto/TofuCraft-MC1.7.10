package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.ItemSomenTsuyuBowl;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToServer;

public class PacketSomenScooping extends AbstractPacket implements MessageToServer
{
    private int entityId;

    public PacketSomenScooping() {}

    public PacketSomenScooping(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeInt(entityId);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        entityId = buffer.readInt();
    }

    @Override
    public IMessage handleServerSide(EntityPlayer player)
    {
        Entity entity = player.worldObj.getEntityByID(entityId);
        ItemStack itemHeld = player.getHeldItem();
        ItemStack newItem = ItemSomenTsuyuBowl.scoopSomen(entity, itemHeld, player, false);
        player.inventory.mainInventory[player.inventory.currentItem] = newItem;
        return null;
    }
}
