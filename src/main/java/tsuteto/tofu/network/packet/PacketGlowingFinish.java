package tsuteto.tofu.network.packet;

import cpw.mods.fml.client.FMLClientHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.glowtofu.GlowingHandler;
import tsuteto.tofu.network.AbstractPacket;

public class PacketGlowingFinish extends AbstractPacket
{
    private int entityId;

    public PacketGlowingFinish() {}

    public PacketGlowingFinish(int entityId)
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
        Minecraft mc = FMLClientHandler.instance().getClient();

        Entity entity = mc.theWorld.getEntityByID(entityId);

        if (entity != null && entity instanceof EntityLivingBase)
        {
            GlowingHandler.removeLight(mc.theWorld, (EntityLivingBase)entity);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {

    }
}
