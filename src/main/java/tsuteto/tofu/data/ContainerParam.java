package tsuteto.tofu.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.ICrafting;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.packet.PacketTfMachineData;

abstract public class ContainerParam<T>
{
    public final int id;
    protected T value;
    protected T lastValue;
    protected ContainerTfMachine container;

    public ContainerParam(ContainerTfMachine container, int id)
    {
        this.container = container;
        this.id = id;
        this.container.addContainerParam(this);
    }

    public void send(ICrafting iCrafting)
    {
        container.sendTfMachineData(iCrafting, this.id, this.getDataHandler());
    }

    public void sendIfChanged(ICrafting iCrafting)
    {
        if (!value.equals(lastValue))
        {
            this.send(iCrafting);
        }

        this.lastValue = this.value;
    }

    abstract public void receive(ByteBuf data);

    public T get()
    {
        return value;
    }

    public void set(T value)
    {
        this.value = value;
    }

    abstract public PacketTfMachineData.DataHandler getDataHandler();
}
