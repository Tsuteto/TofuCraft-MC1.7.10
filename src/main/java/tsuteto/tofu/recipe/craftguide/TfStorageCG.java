package tsuteto.tofu.recipe.craftguide;

import net.minecraft.item.ItemStack;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.init.TcBlocks;
import uristqwerty.CraftGuide.api.*;

public class TfStorageCG extends TfMachineCGBase
{
    public static final int SLOT_INGREDIENT = 0;
    public static final int SLOT_TF = 1;
    public static final int SLOT_MACHINE = 2;

    public TfStorageCG()
    {
        this.slots = new Slot[3];
        this.slots[SLOT_INGREDIENT] = new ItemSlot(11, 20, 16, 16, true);
        this.slots[SLOT_TF] = new TfGuageSlot(51, 20).setSlotType(SlotType.OUTPUT_SLOT);
        this.slots[SLOT_MACHINE] = new ItemSlot(32, 20, 20, 20).setSlotType(SlotType.MACHINE_SLOT);
    }

    @Override
    protected void generateRecipes(RecipeGenerator generator, RecipeTemplate template, ItemStack machine)
    {
        for (TfMaterialRegistry recipe : TfMaterialRegistry.getTfMaterialList())
        {
            Object[] items = new Object[3];
            items[SLOT_INGREDIENT] = recipe.getItemStack();
            items[SLOT_TF] = recipe.tfAmount;
            items[SLOT_MACHINE] = machine;
            generator.addRecipe(template, items);
        }

    }

    @Override
    protected int getTextureRow()
    {
        return 2;
    }

    @Override
    protected ItemStack getMachineBlock()
    {
        return new ItemStack(TcBlocks.tfStorageIdle);
    }

}
