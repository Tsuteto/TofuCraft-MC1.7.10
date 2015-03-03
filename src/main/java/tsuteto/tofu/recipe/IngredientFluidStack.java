package tsuteto.tofu.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class IngredientFluidStack extends Ingredient<FluidStack>
{

    public IngredientFluidStack(FluidStack item)
    {
        super(item);
    }

    @Override
    public boolean matches(ItemStack input)
    {
        return false;
    }

    @Override
    public List<ItemStack> getApplicableItems()
    {
        return null;
    }

    @Override
    public boolean equals(Object obj)
    {
        return false;
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}
