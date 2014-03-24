package tsuteto.tofu.network.packet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tsuteto.tofu.network.AbstractPacket;

public class PacketDimTrip extends AbstractPacket
{
    public PacketDimTrip() {}

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {}

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {}

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();
        mc.getSoundHandler().playSound(
                PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), player.worldObj.rand.nextFloat() * 0.4F + 0.8F));
   }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
