package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;

public class ContainerTfReformer extends ContainerTfReformerBase
{
    public ContainerTfReformer(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(invPlayer, machine);
    }

    @Override
    public void prepareMachineInventory()
    {
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 53, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 51, 49, TfMachineGuiParts.itemSlotL2));
    }

}
