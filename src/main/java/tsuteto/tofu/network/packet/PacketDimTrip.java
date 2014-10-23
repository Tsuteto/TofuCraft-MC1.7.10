package tsuteto.tofu.network.packet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;

public class PacketDimTrip extends AbstractPacket implements MessageToClient
{
    public PacketDimTrip() {}

    @Override
    public void encodeInto(ByteBuf buffer) {}

    @Override
    public void decodeInto(ByteBuf buffer) {}

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.getSoundHandler().playSound(
                PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), player.worldObj.rand.nextFloat() * 0.4F + 0.8F));
        return null;
    }
}
