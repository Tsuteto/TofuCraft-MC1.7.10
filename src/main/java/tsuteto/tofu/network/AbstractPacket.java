package tsuteto.tofu.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;


public abstract class AbstractPacket implements IMessage, IMessageHandler<AbstractPacket, IMessage>
{
    public void fromBytes(ByteBuf buf)
    {
        this.decodeInto(buf);
    }

    public void toBytes(ByteBuf buf)
    {
        this.encodeInto(buf);
    }

    @Override
    public IMessage onMessage(AbstractPacket message, MessageContext ctx)
    {
        EntityPlayer player;
        IMessage reply = null;
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT:
                if (message instanceof MessageToClient)
                {
                    player = this.getClientPlayer();
                    reply = ((MessageToClient)message).handleClientSide(player);
                }
                break;

            case SERVER:
                if (message instanceof MessageToServer)
                {
                    player = ((NetHandlerPlayServer) ctx.netHandler).playerEntity;
                    reply = ((MessageToServer)message).handleServerSide(player);
                }
                break;

            default:
        }
        return reply;
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public abstract void encodeInto(ByteBuf buffer);

    public abstract void decodeInto(ByteBuf buffer);
}