package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.api.recipe.TfReformerRecipe;
import tsuteto.tofu.api.recipe.TfReformerRecipeRegistry;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import tsuteto.tofu.block.BlockTfReformer;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.ICraftingDurability;
import tsuteto.tofu.item.ItemCraftingDurability;
import tsuteto.tofu.recipe.Ingredient;

public class TileEntityTfReformer extends TileEntityTfMachineSidedInventoryBase implements IFluidHandler, ITfConsumer
{
    public enum Model {
        simple(0), mix(1);

        public final int id;

        Model(int id)
        {
            this.id = id;
        }
    }
    public static final int SLOT_INPUT_ITEM = 0;
    public static final int SLOT_OUTPUT_ITEM = 1;
    public static final int SLOT_INGREDIENT_ITEM1 = 2;
    public static final int SLOT_INGREDIENT_ITEM2 = 3;
    public static final int SLOT_INGREDIENT_ITEM3 = 4;
    public static final int[] slotsIng = new int[]{SLOT_INGREDIENT_ITEM1, SLOT_INGREDIENT_ITEM2, SLOT_INGREDIENT_ITEM3};

    public static final double COST_TF_PER_TICK = 1;

    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsSides1 = new int[]{0, 1};
    private static final int[] slotsSides2 = new int[]{1, 2, 3, 4};
    private static final int[] slotsBottom = new int[]{1};

    public Model model;
    public double tfCapacity = 100;
    public double tfAmount = 0;
    public TfReformerRecipe currentRecipe;
    public double tfOutput = 0;
    public double wholeTfOutput = 0;
    public int externalProcessTime = 0;
    public boolean isExternalProcessed = false;
    public FluidTank fluidTank = new FluidTank(0);

    private double tfConsumed = 0.0D;
    private TfReformerRecipe lastRecipe = null;

    public TileEntityTfReformer()
    {
        this.itemStacks = new ItemStack[5];
        this.fluidTank.setFluid(new FluidStack(TcFluids.SOYMILK, 0));
        this.fluidTank.setCapacity(TfMaterialRegistry.calcFluidAmountFrom(this.tfCapacity, TcFluids.SOYMILK));
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfReformer." + model.name();
    }

    public static Model getModelById(int id)
    {
        for (Model model : Model.values())
        {
            if (model.id == id) return model;
        }
        return null;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        int rev = nbtTagCompound.getByte("Rev");

        if (rev == 1)
        {
            this.model = Model.simple;
            this.tfOutput = nbtTagCompound.getShort("ProcO");
            this.tfCapacity = nbtTagCompound.getFloat("TfCap");
            this.tfAmount = nbtTagCompound.getFloat("TfAmount");
        }
        else
        {
            this.model = getModelById(nbtTagCompound.getByte("Model"));
            if (this.model == null) this.invalidate();

            this.tfOutput = nbtTagCompound.getDouble("ProcO");
            this.tfCapacity = nbtTagCompound.getDouble("TfCap");
            this.tfAmount = nbtTagCompound.getDouble("TfAmount");
        }

        this.fluidTank.setCapacity(TfMaterialRegistry.calcFluidAmountFrom(this.tfCapacity, TcFluids.SOYMILK));
        this.updateFluidTank();
        this.updateCurrentRecipe();
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte("Model", (byte)this.model.id);
        nbtTagCompound.setDouble("ProcO", this.tfOutput);
        nbtTagCompound.setDouble("TfCap", this.tfCapacity);
        nbtTagCompound.setDouble("TfAmount", this.tfAmount);
    }

    @Override
    protected int getNBTRevision()
    {
        return 2;
    }

    @SideOnly(Side.CLIENT)
    public double getProgressScaledOutput()
    {
        if (wholeTfOutput > 0)
        {
            return this.tfOutput / this.wholeTfOutput;
        }
        else
        {
            return 0;
        }
    }

    /**
     * Returns true if the machine is currently processing
     */
    public boolean isProcessing()
    {
        return this.tfOutput > 0 || this.externalProcessTime > 0;
    }

    @Override
    public void updateEntity()
    {
        boolean isProcessingOutput = this.tfOutput > 0;
        boolean isExternalProcessing = this.externalProcessTime > 0;
        boolean isInventoryChanged = false;

        if (!this.worldObj.isRemote)
        {
            tfConsumed = 0;

            if (this.tfOutput == 0 && this.canProcessOutput())
            {
                this.updateFluidTank();
//                int containerVol = TfUtils.getSoymilkCapacityOf(this.itemStacks[SLOT_INPUT_ITEM], fluidTank.getFluid());
//                this.wholeTfOutput = (int) TfMaterialRegistry.calcTfAmountFrom(new FluidStack(TcFluids.SOYMILK, containerVol));
            }

            if (this.wholeTfOutput > 0 && this.canProcessOutput())
            {
                this.tfConsumed = Math.min(this.tfAmount, COST_TF_PER_TICK);
                this.tfOutput += tfConsumed;
                this.tfAmount -= tfConsumed;

                if (this.tfOutput >= wholeTfOutput)
                {
                    this.tfOutput = 0;
                    this.onOutputCompleted();
                    this.updateCurrentRecipe();
                    isInventoryChanged = true;
                }
            }
            else if (this.lastRecipe != this.currentRecipe)
            {
                // Stop processing
                this.tfOutput = 0;
                this.wholeTfOutput = 0;
            }

            if (this.isExternalProcessed)
            {
                this.externalProcessTime = 20;
                this.isExternalProcessed = false;
            }
            else if (this.externalProcessTime > 0)
            {
                --this.externalProcessTime;
            }

            if (isProcessingOutput != this.tfOutput > 0
                    || isExternalProcessing != this.externalProcessTime > 0)
            {
                isInventoryChanged = true;
                BlockTfReformer.updateMachineState(this.isProcessing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (isInventoryChanged)
        {
            this.markDirty();
        }

        this.lastRecipe = this.currentRecipe;
    }

    /**
     * Checks if the machine accepts the item to output soymilk
     */
    private boolean canProcessOutput()
    {
        if (this.itemStacks[SLOT_INPUT_ITEM] == null) return false;
        if (this.tfAmount <= 0) return false;
        if (this.currentRecipe == null) return false;

        Ingredient containerItem = this.currentRecipe.containerItem;
        if (!containerItem.matchesWithItemStack(this.itemStacks[SLOT_INPUT_ITEM])) return false;

        ItemStack var1 = this.currentRecipe.result;

        // Check if the filled item can be stacked to the output slot
        if (this.itemStacks[SLOT_OUTPUT_ITEM] == null) return true;
        if (!this.itemStacks[SLOT_OUTPUT_ITEM].isItemEqual(var1)) return false;

        // Check if the stack will overflow
        int result = itemStacks[SLOT_OUTPUT_ITEM].stackSize + var1.stackSize;
        return result <= getInventoryStackLimit() && result <= var1.getMaxStackSize();
    }

    /**
     * When the output process completed. Take one from the output container stack and fill soymilk converted from TF
     */
    public void onOutputCompleted()
    {
        ItemStack var1 = this.currentRecipe.result;

        if (this.itemStacks[SLOT_OUTPUT_ITEM] == null)
        {
            this.itemStacks[SLOT_OUTPUT_ITEM] = var1.copy();
        }
        else if (this.itemStacks[SLOT_OUTPUT_ITEM].isItemEqual(var1))
        {
            itemStacks[SLOT_OUTPUT_ITEM].stackSize += var1.stackSize;
        }

        if (--this.itemStacks[SLOT_INPUT_ITEM].stackSize == 0)
        {
            this.itemStacks[SLOT_INPUT_ITEM] = null;
        }

        // Ingredient slots
        for (int id : slotsIng)
        {
            if (this.itemStacks[id] != null)
            {
                if (!this.currentRecipe.isCatalystItem(this.itemStacks[id]))
                {
                    if (this.itemStacks[id].getItem() instanceof ItemCraftingDurability)
                    {
                        ItemStack var3 = this.itemStacks[id];
                        Item item = this.itemStacks[id].getItem();
                        var3.setItemDamage(var3.getItemDamage() + 1);
                        if(item.isDamageable() && var3.getItemDamage() >= var3.getMaxDamage())
                        {
                            this.itemStacks[id] = ((ICraftingDurability)item).getEmptyItem();
                        }
                    }
                    else
                    {
                        if (--this.itemStacks[id].stackSize == 0)
                        {
                            this.itemStacks[id] = null;
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates the fluid tank to synchronize with the tf amount of the machine
     */
    private void updateFluidTank()
    {
        if (fluidTank.getFluid() == null)
        {
            this.fluidTank.setFluid(new FluidStack(TcFluids.SOYMILK, 0));
        }
        this.fluidTank.getFluid().amount = TfMaterialRegistry.calcFluidAmountFrom(this.tfAmount, TcFluids.SOYMILK);
    }

    public void updateCurrentRecipe()
    {
        TfReformerRecipe recipe = TfReformerRecipeRegistry.getRecipe(this.itemStacks[SLOT_INPUT_ITEM],
                new ItemStack[]{
                    this.itemStacks[SLOT_INGREDIENT_ITEM1],
                    this.itemStacks[SLOT_INGREDIENT_ITEM2],
                    this.itemStacks[SLOT_INGREDIENT_ITEM3]
                }
        );

        if (recipe != null)
        {
            this.wholeTfOutput = recipe.tfAmountNeeded;
        }

        this.currentRecipe = recipe;
    }

    /**
     * Fill a container item by Forge fluid system (IFluidHandler)
     * Applicable to some industrial mods
     */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return 0;
    }

    /**
     * Drain soymilk from the machine by Forge fluid system (IFluidHandler)
     * Applicable to industrial mods or some
     */
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        this.updateFluidTank();

        if ((resource == null) || (!resource.isFluidEqual(fluidTank.getFluid())))
        {
            return null;
        }

        if (!canDrain(from, resource.getFluid())) return null;

        FluidStack fluid = fluidTank.drain(resource.amount, doDrain);

        if (fluid != null && doDrain && fluid.amount > 0)
        {
            this.tfAmount -= TfMaterialRegistry.calcTfAmountFrom(fluid);
            this.updateFluidTank();
            this.isExternalProcessed = true;
        }

        return fluid;
    }

    /**
     * Drain soymilk from the machine by Forge fluid system (IFluidHandler) for pipes
     * Applicable to industrial mods or some
     */
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        this.updateFluidTank();
        FluidStack fluid = fluidTank.drain(maxDrain, doDrain);

        if (fluid != null && doDrain && fluid.amount > 0)
        {
            this.tfAmount -= TfMaterialRegistry.calcTfAmountFrom(fluid);
            this.updateFluidTank();
            this.isExternalProcessed = true;
        }
        return fluid;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        // This machine cannot be filled
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        if (this.tfAmount <= 0) return false;
        return fluidTank.getFluid().getFluid().getID() == fluid.getID();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        this.updateFluidTank();
        return new FluidTankInfo[] { this.fluidTank.getInfo() };
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return tfConsumed;
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfAmount += amount;
    }

    @Override
    public double getMaxTfCapacity()
    {
        return Math.min(tfCapacity - this.tfAmount, 5);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 0 ? slotsBottom : (var1 == 1 ? slotsTop : model == Model.simple ? slotsSides1 : slotsSides2);
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 != 1;
    }

    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
        return slot == 1;
    }
}
