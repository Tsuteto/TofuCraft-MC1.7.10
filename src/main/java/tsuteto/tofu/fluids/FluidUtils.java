package tsuteto.tofu.fluids;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Map;

public class FluidUtils
{
    public static Map<Integer, List<ItemStack>> fluidToFilledItemMap;

    static
    {
        FluidContainerRegistry.FluidContainerData[] list = FluidContainerRegistry.getRegisteredFluidContainerData();
        fluidToFilledItemMap = Maps.newHashMap();

        for (FluidContainerRegistry.FluidContainerData data : list)
        {
            int fluidId = data.fluid.getFluid().getID();
            if (!fluidToFilledItemMap.containsKey(fluidId))
            {
                fluidToFilledItemMap.put(fluidId, Lists.<ItemStack>newArrayList());
            }
            fluidToFilledItemMap.get(fluidId).add(data.filledContainer);
        }
    }

    public static ItemStack getSampleFilledItemFromFluid(Fluid fluid)
    {
        if (fluid != null)
        {
            List<ItemStack> list = fluidToFilledItemMap.get(fluid.getID());
            if (list != null && !list.isEmpty())
            {
                return list.get(0);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public static List<ItemStack> getFilledItemFromFluid(Fluid fluid)
    {
        List<ItemStack> list = fluidToFilledItemMap.get(fluid.getID());
        if (list != null)
        {
            return list;
        }
        else
        {
            return Lists.newArrayList();
        }
    }

    public static boolean isContainerForFluid(FluidStack fluid, ItemStack container)
    {
        return FluidContainerRegistry.fillFluidContainer(fluid, container) != null;
    }
}
