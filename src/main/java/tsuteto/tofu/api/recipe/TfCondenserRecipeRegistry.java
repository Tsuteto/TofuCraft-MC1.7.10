package tsuteto.tofu.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.block.TcBlock;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TfCondenserRecipeRegistry
{
    public static final List<TfCondenserRecipe> recipeRegistry = new ArrayList<TfCondenserRecipe>();
    public static final Map<Fluid, TfCondenserRecipe> additiveToRecipeMap = new HashMap<Fluid, TfCondenserRecipe>();

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
        if (recipe.additive != null && additiveToRecipeMap.containsKey(recipe.additive.getFluid()))
        {
            ModLog.log(Level.WARN, "Duplicated recipe for TF Condenser! Already registered additive: %s", recipe.additive.toString());
            return;
        }

        recipeRegistry.add(recipe);
        if (recipe.additive != null)
        {
            additiveToRecipeMap.put(recipe.additive.getFluid(), recipe);
        }
        else
        {
            additiveToRecipeMap.put(null, recipe);
        }
    }


}
