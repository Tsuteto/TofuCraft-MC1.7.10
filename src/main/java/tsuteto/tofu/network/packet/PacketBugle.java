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

public class PacketBugle extends AbstractPacket implements MessageToClient
{
    private float x;
    private float y;
    private float z;
    private int entityId;

    public PacketBugle() {}

    public PacketBugle(float x, float y, float z, int entityId)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeFloat(x);
        buffer.writeFloat(y);
        buffer.writeFloat(z);
        buffer.writeInt(entityId);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        x = buffer.readFloat();
        y = buffer.readFloat();
        z = buffer.readFloat();
        entityId = buffer.readInt();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IMessage handleClientSide(EntityPlayer player)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();

        if (player.getEntityId() == entityId)
        {
            mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("tofucraft:tofubugle"), 1.0F));
        }
        else
        {
            mc.theWorld.playSound(x, y, z, "tofucraft:tofubugle", 4.0F, 1.0F, false);
        }
        return null;
    }
}
