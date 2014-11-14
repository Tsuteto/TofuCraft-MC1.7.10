package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;
import tsuteto.tofu.potion.TcPotion;
import tsuteto.tofu.util.ModLog;

public class PacketPotionIdCheck extends AbstractPacket implements MessageToClient
{
    public int glowing;
    public int filling;

    public PacketPotionIdCheck() {}

    public PacketPotionIdCheck(int... ids)
    {
        this.glowing = ids[0];
        this.filling = ids[1];
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeInt(glowing);
        buffer.writeInt(filling);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.glowing = buffer.readInt();
        this.filling = buffer.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        ModLog.debug("Received potion check packet");
        if (this.glowing != TcPotion.glowing.id || this.filling != TcPotion.filling.id)
        {
            IChatComponent chat;
            chat = new ChatComponentTranslation("tofucraft.wrongPotionId1");
            player.addChatMessage(chat);
            chat = new ChatComponentTranslation("tofucraft.wrongPotionId2");
            player.addChatMessage(chat);
            chat = new ChatComponentTranslation("tofucraft.wrongPotionId3",
                    this.glowing, this.filling);
            player.addChatMessage(chat);
        }
        return null;
    }
}