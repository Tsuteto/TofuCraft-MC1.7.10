package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.TfMachineGuiParts;

public class ContainerTfReformer2 extends ContainerTfReformerBase
{
    public ContainerTfReformer2(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(invPlayer, machine);
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 48, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 50, 51, TfMachineGuiParts.itemSlot));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM1, 100, 35, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM2, 119, 35, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM3, 138, 35, TfMachineGuiParts.itemSlotL1));
    }

}
