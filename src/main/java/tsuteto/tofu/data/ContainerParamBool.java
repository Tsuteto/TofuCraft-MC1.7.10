package tsuteto.tofu.data;

import io.netty.buffer.ByteBuf;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerParamBool extends ContainerParam<Boolean>
{
    public ContainerParamBool(int id, boolean value)
    {
        super(id, value);
    }

    @Override
    public void receive(ByteBuf data)
    {
        this.value = data.readBoolean();
    }

    @Override
    public PacketTfMachineData.DataHandler getDataHandler()
    {
        return new PacketTfMachineData.DataHandler()
        {
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeBoolean(value);
            }
        };
    }
}
