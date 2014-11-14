package tsuteto.tofu.network.packet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import tsuteto.tofu.glowtofu.GlowingHandler;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToClient;

public class PacketGlowingFinish extends AbstractPacket implements MessageToClient
{
    private int entityId;

    public PacketGlowingFinish() {}

    public PacketGlowingFinish(int entityId)
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
    @SideOnly(Side.CLIENT)
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();

        Entity entity = mc.theWorld.getEntityByID(entityId);

        if (entity != null && entity instanceof EntityLivingBase)
        {
            GlowingHandler.removeLight(mc.theWorld, (EntityLivingBase)entity);
        }
        return null;
    }
}
