package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;

public class ContainerTfReformer2 extends ContainerTfReformerBase
{
    public ContainerTfReformer2(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(invPlayer, machine);
    }

    @Override
    public void prepareMachineInventory()
    {
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 38, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 36, 49, TfMachineGuiParts.itemSlotL2));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM1, 90, 28, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM2, 109, 28, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfReformer(machine, TileEntityTfReformer.SLOT_INGREDIENT_ITEM3, 128, 28, TfMachineGuiParts.itemSlotL1));
    }

}
