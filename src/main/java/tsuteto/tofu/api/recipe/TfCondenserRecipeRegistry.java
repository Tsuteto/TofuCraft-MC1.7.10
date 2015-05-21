package tsuteto.tofu.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.util.ModLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TF Condenser recipe entry class
 *
 * For API users:
 * To register, call register() from init() in your mod class.
 */
public class TfCondenserRecipeRegistry
{
    public static final List<TfCondenserRecipe> recipeRegistry = new ArrayList<TfCondenserRecipe>();
    public static final Map<Fluid, TfCondenserRecipe> ingredientToRecipeMap = new HashMap<Fluid, TfCondenserRecipe>();

    public static void init()
    {
        register(new TfCondenserRecipe(
                1200,
                new FluidStack(TcFluids.MINERAL_SOYMILK, 2000),
                2400,
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedTofuGem.id)));

        register(new TfCondenserRecipe(
                2800,
                new FluidStack(TcFluids.SOYMILK_HELL, 5000),
                2400,
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedHellTofu.id)));

        register(new TfCondenserRecipe(
                20,
                new FluidStack(TcFluids.STRAWBERRY_JAM, 5),
                200,
                new ItemStack(TcBlocks.tofuStrawberry)));
    }

    public static void register(TfCondenserRecipe recipe)
    {
        if (recipe.ingredient != null && ingredientToRecipeMap.containsKey(recipe.ingredient.getFluid()))
        {
            ModLog.log(Level.WARN, "Duplicated recipe for TF Condenser! Already registered ingredient: %s", recipe.ingredient.toString());
            return;
        }

        recipeRegistry.add(recipe);
        if (recipe.ingredient != null)
        {
            ingredientToRecipeMap.put(recipe.ingredient.getFluid(), recipe);
        }
        else
        {
            ingredientToRecipeMap.put(null, recipe);
        }
    }


}
