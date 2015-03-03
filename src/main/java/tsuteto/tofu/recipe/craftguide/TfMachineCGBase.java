package tsuteto.tofu.recipe.craftguide;

import net.minecraft.item.ItemStack;
import tsuteto.tofu.TofuCraftCore;
import uristqwerty.CraftGuide.api.*;

public abstract class TfMachineCGBase extends CraftGuideAPIObject implements RecipeProvider
{
    protected Slot[] slots;

    @Override
    public void generateRecipes(RecipeGenerator generator)
    {
        RecipeTemplate template;
        ItemStack machine = getMachineBlock();
        int posY = 58 * this.getTextureRow();
        template = generator.createRecipeTemplate(this.slots, machine,
                TofuCraftCore.resourceDomain + "textures/gui/craftGuideRecipe.png", 0, posY, 79, posY);
        this.generateRecipes(generator, template, machine);
    }

    protected abstract int getTextureRow();
    protected abstract ItemStack getMachineBlock();
    protected abstract void generateRecipes(RecipeGenerator generator, RecipeTemplate template, ItemStack machine);
}
