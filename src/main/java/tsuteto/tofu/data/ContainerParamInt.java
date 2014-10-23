package tsuteto.tofu.data;

import io.netty.buffer.ByteBuf;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerParamInt extends ContainerParam<Integer>
{

    public ContainerParamInt(ContainerTfMachine container, int id)
    {
        super(container, id);
    }

    @Override
    public void receive(ByteBuf data)
    {
        this.value = data.readInt();
    }

    @Override
    public PacketTfMachineData.DataHandler getDataHandler()
    {
        return new PacketTfMachineData.DataHandler()
        {
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeInt(value);
            }
        };
    }
}
