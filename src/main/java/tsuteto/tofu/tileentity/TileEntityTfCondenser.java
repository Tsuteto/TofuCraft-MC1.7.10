package tsuteto.tofu.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import tsuteto.tofu.block.BlockTfCondenser;
import tsuteto.tofu.fluids.FluidUtils;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.api.recipe.TfCondenserRecipe;
import tsuteto.tofu.api.recipe.TfCondenserRecipeRegistry;
import tsuteto.tofu.item.ICraftingDurability;
import tsuteto.tofu.item.TcItems;

public class TileEntityTfCondenser extends TileEntityTfMachineSidedInventoryBase implements ITfConsumer
{
    public static final int SLOT_NIGARI_INPUT = 0;
    public static final int SLOT_NIGARI_OUTPUT = 1;
    public static final int SLOT_SPECIAL_INPUT = 2;
    public static final int SLOT_SPECIAL_OUTPUT = 3;
    public static final int SLOT_TOFU_OUTPUT = 4;

    public static final int NIGARI_COST_MB = 5;

    public static final int[] slotsTop = new int[]{0, 2};
    public static final int[] slotsSides = new int[]{0, 1, 2, 3, 4};
    public static final int[] slotsBottom = new int[]{1, 3, 4};

    public double tfPooled = 0;
    public double tfNeeded = 0;
    public int processTimeOutput = 0;

    public int wholeTimeOutput = 0;
    public FluidTank nigariTank = new FluidTank(0);
    public FluidTank additiveTank = new FluidTank(0);
    public ItemStack additiveFluidItem;
    private boolean isProcessing = false;
    private boolean isTfCharging = false;

    public TileEntityTfCondenser()
    {
        this.itemStacks = new ItemStack[5];

        this.nigariTank.setFluid(new FluidStack(TcFluids.NIGARI, 0));
        this.nigariTank.setCapacity(200);
        this.additiveTank.setCapacity(10000);
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfCondenser";
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        this.processTimeOutput = nbtTagCompound.getShort("ProcO");
        this.tfPooled = nbtTagCompound.getDouble("TfP");
        this.tfNeeded = nbtTagCompound.getDouble("TfN");
        this.isTfCharging = nbtTagCompound.getBoolean("TfC");
        this.isProcessing = isProcessing();

        this.nigariTank.readFromNBT(nbtTagCompound.getCompoundTag("Tank1"));
        this.additiveTank.readFromNBT(nbtTagCompound.getCompoundTag("Tank2"));
        this.updateAdditiveItem();
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("ProcO", (short) this.processTimeOutput);
        nbtTagCompound.setShort("WholeO", (short) this.wholeTimeOutput);
        nbtTagCompound.setDouble("TfP", this.tfPooled);
        nbtTagCompound.setDouble("TfN", this.tfNeeded);
        nbtTagCompound.setBoolean("TfC", this.isTfCharging);

        NBTTagCompound tag1 = this.nigariTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("Tank1", tag1);

        NBTTagCompound tag2 = this.additiveTank.writeToNBT(new NBTTagCompound());
        nbtTagCompound.setTag("Tank2", tag2);
    }

    public boolean isProcessing()
    {
        return this.wholeTimeOutput > 0 && this.isRedstonePowered()
                && (this.tfPooled < tfNeeded && isTfCharging || this.tfPooled >= tfNeeded);
    }

    @Override
    public void updateEntity()
    {
        boolean isInventoryChanged = false;

        if (!this.worldObj.isRemote && this.isRedstonePowered())
        {
            if ((wholeTimeOutput == 0 || this.tfNeeded == 0) && this.canCondenseTf())
            {
                // Make a start on process
                TfCondenserRecipe recipe = getCurrentRecipe();
                this.wholeTimeOutput = recipe.ticksNeeded;
                this.tfNeeded = recipe.tfAmountNeeded;
            }

            if (this.wholeTimeOutput > 0 && this.canCondenseTf())
            {
                if (this.tfPooled >= tfNeeded)
                {
                    // Condensing
                    this.processTimeOutput++;
                }

                if (this.processTimeOutput >= this.wholeTimeOutput)
                {
                    // Process Complete
                    this.processTimeOutput = 0;
                    this.wholeTimeOutput = 0;
                    this.tfPooled = 0;
                    this.tfNeeded = 0;
                    this.onOutputCompleted();
                    isInventoryChanged = true;
                }
            }
            else if (this.wholeTimeOutput > 0)
            {
                // Process stopped
                this.processTimeOutput = 0;
                this.wholeTimeOutput = 0;
                this.tfPooled = 0;
            }
        }

        // Update machine appearance
        if (isProcessing != this.isProcessing())
        {
            isInventoryChanged = true;
            BlockTfCondenser.updateMachineState(this.isProcessing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
        }

        isProcessing = this.isProcessing();
        isTfCharging = false;

        // Nigari slot
        this.addFluidToTank(SLOT_NIGARI_INPUT, SLOT_NIGARI_OUTPUT, nigariTank);

        // Additive slot
        ItemStack additiveItem = itemStacks[SLOT_SPECIAL_INPUT];
        if (additiveTank.getFluid() == null)
        {
            FluidStack fluidStackInput = FluidContainerRegistry.getFluidForFilledItem(additiveItem);
            if (fluidStackInput != null
                    && TfCondenserRecipeRegistry.additiveToRecipeMap.containsKey(fluidStackInput.getFluid()))
            {
                additiveTank.fill(fluidStackInput, true);
                this.drainAndMoveSlotItem(SLOT_SPECIAL_INPUT, SLOT_SPECIAL_OUTPUT);
                this.updateAdditiveItem();
            }
        }
        else
        {
            this.addFluidToTank(SLOT_SPECIAL_INPUT, SLOT_SPECIAL_OUTPUT, additiveTank);
        }

        if (isInventoryChanged)
        {
            this.markDirty();
        }
    }

    private boolean addFluidToTank(int inputSlotId, int outputSlotId, FluidTank tank)
    {
        ItemStack slotItemInput = itemStacks[inputSlotId];
        ItemStack slotItemOutput = itemStacks[outputSlotId];
        if (slotItemInput != null && canAddItemToSlot(slotItemOutput, slotItemInput.getItem().getContainerItem(slotItemInput)))
        {
            FluidStack fluidInput = FluidContainerRegistry.getFluidForFilledItem(slotItemInput);
            if (fluidInput != null && fluidInput.isFluidEqual(tank.getFluid()))
            {
                // If the tank will overflow adding fluid, do not pour the fluid into the tank
                if (fluidInput.amount == tank.fill(fluidInput, false))
                {
                    tank.fill(fluidInput, true);
                    this.drainAndMoveSlotItem(inputSlotId, outputSlotId);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canAddItemToSlot(ItemStack slotItem, ItemStack inputItem)
    {
        if (slotItem == null || inputItem == null) return true;
        if (!slotItem.isItemEqual(inputItem)) return false;

        // Check if the stack will overflow
        int result = slotItem.stackSize + 1;
        return (result <= getInventoryStackLimit() && result <= slotItem.getMaxStackSize());
    }

    private void drainAndMoveSlotItem(int inputSlotId, int outputSlotId)
    {
        ItemStack slotItemInput = itemStacks[inputSlotId];
        ItemStack slotItemOutput = itemStacks[outputSlotId];
        ItemStack container;

        if (slotItemInput.getItem() instanceof ICraftingDurability)
        {
            container = ((ICraftingDurability)slotItemInput.getItem()).getEmptyItem();
        }
        else
        {
            container = slotItemInput.getItem().getContainerItem(slotItemInput);
        }
        if (container != null)
        {
            this.canAddItemToSlot(slotItemOutput, container);
            if (slotItemOutput == null)
            {
                itemStacks[outputSlotId] = container.copy();
            }
            else
            {
                ++slotItemOutput.stackSize;
            }
        }

        if (--slotItemInput.stackSize == 0)
        {
            this.itemStacks[inputSlotId] = null;
        }
    }

    public void updateAdditiveItem()
    {
        if (additiveTank.getFluid() != null)
        {
            this.additiveFluidItem = FluidUtils.getSampleFilledItemFromFluid(additiveTank.getFluid().getFluid());
        }
        else
        {
            this.additiveFluidItem = null;
        }
    }

    /**
     * Checks if the machine accepts the item to output soymilk
     */
    private boolean canCondenseTf()
    {
        if (!this.isRedstonePowered()) return false;

        TfCondenserRecipe recipe = this.getCurrentRecipe();
        if (recipe == null) return false;

        // Has enough ingredients?
        if (recipe.additive != null && additiveTank.getFluidAmount() < recipe.additive.amount
                || nigariTank.getFluidAmount() < NIGARI_COST_MB) return false;

        // Check if an output item can be stacked to the output slot
        if (this.itemStacks[SLOT_TOFU_OUTPUT] == null) return true;

        if (!this.itemStacks[SLOT_TOFU_OUTPUT].isItemEqual(recipe.result)) return false;

        // Check if the stack will overflow
        int resultStacks = itemStacks[SLOT_TOFU_OUTPUT].stackSize + recipe.result.stackSize;
        return (resultStacks <= getInventoryStackLimit() && resultStacks <= recipe.result.getMaxStackSize());
    }

    /**
     * When the output process completed. Take one from the output container stack and fill soymilk converted from TF
     */
    public void onOutputCompleted()
    {
        TfCondenserRecipe recipe = this.getCurrentRecipe();
        ItemStack var1 = recipe.result;

        if (this.itemStacks[SLOT_TOFU_OUTPUT] == null)
        {
            this.itemStacks[SLOT_TOFU_OUTPUT] = var1.copy();
        }
        else if (this.itemStacks[SLOT_TOFU_OUTPUT].isItemEqual(var1))
        {
            itemStacks[SLOT_TOFU_OUTPUT].stackSize += var1.stackSize;
        }

        if (recipe.additive != null)
        {
            this.additiveTank.drain(recipe.additive.amount, true);
            this.updateAdditiveItem();
        }
        this.nigariTank.drain(NIGARI_COST_MB, true);
    }

    public TfCondenserRecipe getCurrentRecipe()
    {
        if (additiveTank.getFluid() != null)
        {
            return TfCondenserRecipeRegistry.additiveToRecipeMap.get(additiveTank.getFluid().getFluid());
        }
        else
        {
            return null;
        }
    }

    @Override
    public double getMaxTfCapacity()
    {
        if (this.isRedstonePowered() && this.tfPooled < this.tfNeeded)
        {
            return Math.min(2, this.tfNeeded - this.tfPooled);
        }
        else
        {
            return 0;
        }
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return getMaxTfCapacity();
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfPooled += amount;
        if (amount > 0)
        {
            this.isTfCharging = true;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 0 ? slotsBottom : (var1 == 1 ? slotsTop : slotsSides);
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        if (par1 == SLOT_NIGARI_INPUT)
        {
            return par2ItemStack.getItem() == TcItems.nigari;
        }
        else if (par1 == SLOT_SPECIAL_INPUT)
        {
            FluidStack fluidStackInput = FluidContainerRegistry.getFluidForFilledItem(par2ItemStack);
            return fluidStackInput != null
                    && TfCondenserRecipeRegistry.additiveToRecipeMap.containsKey(fluidStackInput.getFluid());
        }
        return false;

    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
        return slot == SLOT_NIGARI_OUTPUT || slot == SLOT_SPECIAL_OUTPUT || slot == SLOT_TOFU_OUTPUT;
    }
}
