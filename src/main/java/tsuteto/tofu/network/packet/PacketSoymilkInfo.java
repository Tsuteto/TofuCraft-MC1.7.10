package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.item.iteminfo.SoymilkPlayerInfo;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;
import tsuteto.tofu.util.ModLog;

import java.io.IOException;

public class PacketSoymilkInfo extends AbstractPacket implements MessageToClient
{
    private NBTTagCompound rootNBT;

    public PacketSoymilkInfo() {}

    public PacketSoymilkInfo(SoymilkPlayerInfo info)
    {
        rootNBT = new NBTTagCompound();
        info.writeNBTTo(rootNBT);
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);

        try
        {
            packet.writeNBTTagCompoundToBuffer(rootNBT);
        }
        catch (IOException e)
        {
            ModLog.log(Level.WARN, e, "Failed to send NBT tag!");
        }
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        PacketBuffer packet = new PacketBuffer(buffer);
        try
        {
            rootNBT = packet.readNBTTagCompoundFromBuffer();
        }
        catch (IOException e)
        {
            ModLog.log(Level.WARN, e, "Failed to receive NBT tag!");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(player).readNBTFrom(rootNBT);
        info.writeNBTToPlayer();
        return null;
    }
}
