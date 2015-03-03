package tsuteto.tofu.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Represents recipe for TF Condenser.
 *
 * For API users:
 * There are samples in TfCondenserRecipeRegister class.
 */
public class TfCondenserRecipe
{
    public double tfAmountNeeded;
    public FluidStack ingredient;
    public ItemStack result;
    public int ticksNeeded;

    /**
     * Constructor
     *
     * @param tfAmount TF amount needed as ingredients
     * @param ingredient Fluid needed as ingredients
     * @param ticks Ticks needed to condense since the machine got enough ingredients
     * @param result Result item
     */
    public TfCondenserRecipe(double tfAmount, FluidStack ingredient, int ticks, ItemStack result)
    {
        this.tfAmountNeeded = tfAmount;
        this.ingredient = ingredient;
        this.ticksNeeded = ticks;
        this.result = result;
    }
}
