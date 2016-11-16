package tsuteto.tofu.tileentity;

import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;

public class SlotTfReformer extends SlotTfMachine
{
    TileEntityTfReformer machine;

    public SlotTfReformer(TileEntityTfReformer machine, int slotInputItem, int x, int y, TfMachineGuiParts guiPart)
    {
        super(machine, slotInputItem, x, y, guiPart);
        this.machine = machine;
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();
    }

}
