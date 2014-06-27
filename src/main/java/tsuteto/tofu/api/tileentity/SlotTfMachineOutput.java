package tsuteto.tofu.api.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.gui.TfMachineGuiParts;

/**
 * Created by luisan on 14/06/08.
 */
public class SlotTfMachineOutput extends SlotTfMachine
{
    public SlotTfMachineOutput(IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
}
