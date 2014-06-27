package tsuteto.tofu.fluids;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.HashMap;
import java.util.Map;

public class FluidUtils
{
    public static Map<Integer, ItemStack> fluidToFilledItemMap;

    public static void init()
    {
        FluidContainerRegistry.FluidContainerData[] list = FluidContainerRegistry.getRegisteredFluidContainerData();
        fluidToFilledItemMap = new HashMap<Integer, ItemStack>();

        for (FluidContainerRegistry.FluidContainerData data : list)
        {
            if (!fluidToFilledItemMap.containsKey(data.fluid.fluidID))
            {
                fluidToFilledItemMap.put(data.fluid.fluidID, data.filledContainer);
            }
        }
    }

    public static ItemStack getSampleFilledItemFromFluid(Fluid fluid)
    {
        if (fluid != null)
        {
            return fluidToFilledItemMap.get(fluid.getID());
        }
        else
        {
            return null;
        }
    }
}
