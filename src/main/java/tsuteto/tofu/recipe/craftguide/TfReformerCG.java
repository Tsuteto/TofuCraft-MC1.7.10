package tsuteto.tofu.recipe.craftguide;

import net.minecraft.item.ItemStack;
import tsuteto.tofu.api.recipe.TfReformerRecipe;
import tsuteto.tofu.api.recipe.TfReformerRecipeRegistry;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.tileentity.TileEntityTfReformer;
import uristqwerty.CraftGuide.api.*;

public class TfReformerCG extends TfMachineCGBase
{
    public static final int SLOT_CONTAINER = 0;
    public static final int SLOT_INGREDIENT_1 = 1;
    public static final int SLOT_INGREDIENT_2 = 2;
    public static final int SLOT_INGREDIENT_3 = 3;
    public static final int SLOT_OUTPUT = 4;
    public static final int SLOT_MACHINE = 5;
    public static final int SLOT_TF = 6;
    public static final int[] INGREDIENT_SLOTS = new int[]{SLOT_INGREDIENT_1, SLOT_INGREDIENT_2, SLOT_INGREDIENT_3};

    public TfReformerCG()
    {
        this.slots = new Slot[7];
        this.slots[SLOT_CONTAINER] = new ItemSlot(8, 5, 16, 16, true);
        this.slots[SLOT_INGREDIENT_1] = new ItemSlot(54, 4, 16, 16, true);
        this.slots[SLOT_INGREDIENT_2] = new ItemSlot(54, 21, 16, 16, true);
        this.slots[SLOT_INGREDIENT_3] = new ItemSlot(54, 38, 16, 16, true);

        this.slots[SLOT_OUTPUT] = new ItemSlot(8, 35, 16, 16, true).setSlotType(SlotType.OUTPUT_SLOT);
        this.slots[SLOT_MACHINE] = new ItemSlot(32, 35, 20, 20).setSlotType(SlotType.MACHINE_SLOT);
        this.slots[SLOT_TF] = new TfAmountSlot(32, 17);
    }

    @Override
    public void generateRecipes(RecipeGenerator generator, RecipeTemplate template, ItemStack machine)
    {
        for (TfReformerRecipe recipe : TfReformerRecipeRegistry.recipeRegistry)
        {
            Object[] items = new Object[7];
            items[SLOT_CONTAINER] = recipe.containerItem.getApplicableItems();
            TfReformerRecipe.IngInfo[] ingList = recipe.getIngredients();
            for (int i = 0; i < ingList.length; i++)
            {
                items[INGREDIENT_SLOTS[i]] = ingList[i].ingredient.getApplicableItems();
            }
            items[SLOT_OUTPUT] = recipe.result;
            items[SLOT_MACHINE] = machine;
            items[SLOT_TF] = recipe.tfAmountNeeded;
            generator.addRecipe(template, items);
        }
    }

    protected int getTextureRow()
    {
        return 0;
    }

    @Override
    public ItemStack getMachineBlock()
    {
        return new ItemStack(TcBlocks.tfReformerIdle, 1, TileEntityTfReformer.Model.mix.id);
    }
}
