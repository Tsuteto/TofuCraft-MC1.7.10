package tsuteto.tofu.block.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import tsuteto.tofu.network.packet.PacketTfMachineData;
import tsuteto.tofu.util.SimplePacketDispatcher;

abstract public class ContainerTfMachine extends Container
{

    public abstract void updateTfMachineData(int id, ByteBuf data);

    public void sendTfMachineData(ICrafting crafter, ContainerTfMachine par1Container, int par2, PacketTfMachineData.DataHandler data)
    {
        if (crafter instanceof EntityPlayerMP)
        {
            PacketTfMachineData packet = new PacketTfMachineData(par1Container.windowId, par2, data);
            SimplePacketDispatcher.packet(packet).sendToPlayer((EntityPlayerMP) crafter);
        }
    }
}
