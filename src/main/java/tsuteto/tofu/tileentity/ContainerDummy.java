package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerDummy extends Container
{
    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return false;
    }
}
