package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.TfMachineGuiParts;

public class ContainerTfReformer extends ContainerTfReformerBase
{
    public ContainerTfReformer(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(invPlayer, machine);
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 58, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 60, 51, TfMachineGuiParts.itemSlot));
    }

}
