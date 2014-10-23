package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;

public class ContainerTfDistanceAntenna extends ContainerTfMachine<TileEntityTfDistanceAntenna>
{
    public ContainerTfDistanceAntenna(InventoryPlayer invPlayer, TileEntityTfDistanceAntenna machine)
    {
        super(machine);
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.machine.isUseableByPlayer(var1);
    }
}
