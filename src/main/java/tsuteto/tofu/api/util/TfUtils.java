package tsuteto.tofu.api.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.init.TcFluids;

public class TfUtils
{
    /**
     * Get a capacity of soymilk the container item has
     * @param container
     * @return
     */
    public static int getSoymilkCapacityOf(ItemStack container, FluidStack source)
    {
        if (container.getItem() instanceof IFluidContainerItem)
        {
            IFluidContainerItem fluidItem = (IFluidContainerItem)container.getItem();
            ItemStack target = container.copy();
            target.stackSize = 1;
            return fluidItem.fill(target, source, false);
        }
        ItemStack is = TfUtils.fillSoymilk(container, source);
        if (is == null)
        {
            return 0;
        }
        return FluidContainerRegistry.getFluidForFilledItem(is).amount;
    }

    /**
     * fill soymilk to an empty container item
     * @param container
     * @return a filled container if successful, otherwise null
     */
    public static ItemStack fillSoymilk(ItemStack container, FluidStack source)
    {
        if (container.getItem() instanceof IFluidContainerItem)
        {
            return fillFluidContainer(container, source);
        }
        else
        {
            return FluidContainerRegistry.fillFluidContainer(new FluidStack(TcFluids.SOYMILK, Integer.MAX_VALUE), container);
        }
    }

    /**
     * Returns a filled one of the fluid container that implements IFluidContainerItem
     * @param input
     * @return
     */
    public static ItemStack fillFluidContainer(ItemStack input, FluidStack source)
    {
        // Take one from the stack
        ItemStack target = input.copy();
        target.stackSize = 1;

        IFluidContainerItem fluidItem = (IFluidContainerItem)input.getItem();
        fluidItem.fill(target, source, true);
        return target;
    }

    /**
     * Returns an empty one of the fluid container that implements IFluidContainerItem
     * @param input
     * @return
     */
    public static ItemStack drainFluidContainer(ItemStack input)
    {
        // Take one from the stack
        ItemStack target = input.copy();
        target.stackSize = 1;

        // Drain its fluid and check if it is soymilk
        IFluidContainerItem fluidItem = (IFluidContainerItem)input.getItem();
        FluidStack content = fluidItem.getFluid(input);
        if (content == null || content.getFluid().getID() != TcFluids.SOYMILK.getID())
        {
            return null;
        }
        fluidItem.drain(target, Integer.MAX_VALUE, true);
        return target;
    }

    /**
     * Obtains how much TF the item contains
     * @param itemstack
     * @return
     */
    public static double getItemTfAmount(ItemStack itemstack)
    {
        if (itemstack == null)
        {
            return 0;
        }
        else
        {
            FluidStack content;
            if (itemstack.getItem() instanceof IFluidContainerItem)
            {
                IFluidContainerItem fluidHandler = (IFluidContainerItem)itemstack.getItem();
                ItemStack target = itemstack.copy();
                target.stackSize = 1;
                content = fluidHandler.drain(target, Integer.MAX_VALUE, false);
            }
            else
            {
                content = FluidContainerRegistry.getFluidForFilledItem(itemstack);
            }

            if (content != null)
            {
                return TfMaterialRegistry.calcTfAmountFrom(content);
            }
            else
            {
                return TfMaterialRegistry.getTfAmount(itemstack);
            }
        }
    }
}
