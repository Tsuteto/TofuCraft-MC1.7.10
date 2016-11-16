package tsuteto.tofu.data;

import io.netty.buffer.ByteBuf;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerParamDouble extends ContainerParam<Double>
{

    public ContainerParamDouble(int id, double value)
    {
        super(id, value);
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

    public double add(double value)
    {
        this.value += value;
        return this.value;
    }

    public double subtract(double value)
    {
        this.value -= value;
        return this.value;
    }
}
