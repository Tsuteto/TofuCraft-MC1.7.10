package tsuteto.tofu.api.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class TfCondenserRecipe
{
    public double tfAmountNeeded;
    public FluidStack additive;
    public ItemStack result;
    public int ticksNeeded;

    public TfCondenserRecipe(double tfAmount, FluidStack additive, int ticks, ItemStack result)
    {
        this.tfAmountNeeded = tfAmount;
        this.additive = additive;
        this.ticksNeeded = ticks;
        this.result = result;
    }
}
