package tsuteto.tofu.data;

import io.netty.buffer.ByteBuf;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerParamDouble extends ContainerParam<Double>
{

    public ContainerParamDouble(ContainerTfMachine container, int id)
    {
        super(container, id);
    }

    public void receive(ByteBuf data)
    {
        this.value = data.readDouble();
    }

    public PacketTfMachineData.DataHandler getDataHandler()
    {
        return new PacketTfMachineData.DataHandler()
        {
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(value);
            }
        };
    }
}
