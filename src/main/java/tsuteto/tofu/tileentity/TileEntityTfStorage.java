package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.api.tileentity.ITfTank;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import tsuteto.tofu.api.util.TfUtils;
import tsuteto.tofu.block.BlockTfStorage;
import tsuteto.tofu.fluids.TcFluids;

public class TileEntityTfStorage extends TileEntityTfMachineSidedInventoryBase implements IFluidHandler, ITfTank
{
    
    public static final int SLOT_INPUT_ITEM = 0;
    public static final int SLOT_INPUT_CONTAINER_ITEM = 1;

    private static final int[] slotForUpper = new int[] {0};
    private static final int[] slotForSide = new int[] {0, 1};
    private static final int[] slotForLower = new int[] {1};
    
    /** For fluid handling. Synchronized with the tf amount the machine has */
    private final FluidTank fluidTank = new FluidTank(0);

    public float tfInput = 0;
    public float wholeTfInput = 0;

    public int externalProcessTime = 0;
    public boolean isExternalFluidAccepted = false;

    public float tfAmount = 0;
    public float tfCapacity = 5000;
    public boolean isWorking;
    private boolean prevWorking;

    public TileEntityTfStorage()
    {
        this.itemStacks = new ItemStack[2];
        this.fluidTank.setFluid(new FluidStack(TcFluids.SOYMILK, 0));
        this.fluidTank.setCapacity(TfMaterialRegistry.calcFluidAmountFrom(this.tfCapacity, TcFluids.SOYMILK));
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfStorage";
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (nbtTagCompound.hasKey("Rev"))
        {
            this.tfInput = nbtTagCompound.getFloat("TfIn");
            this.wholeTfInput = nbtTagCompound.getFloat("TfInW");
        }
        else
        {
            this.tfInput = nbtTagCompound.getShort("ProcI");
            this.wholeTfInput = nbtTagCompound.getShort("WholeI");
        }

        this.tfCapacity = nbtTagCompound.getFloat("TfCap");
        this.tfAmount = nbtTagCompound.getFloat("TfAmount");

        this.fluidTank.setCapacity(TfMaterialRegistry.calcFluidAmountFrom(this.tfCapacity, TcFluids.SOYMILK));
        this.updateFluidTank();
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setFloat("TfIn", (short)this.tfInput);
        nbtTagCompound.setFloat("TfInW", (short)this.wholeTfInput);

        nbtTagCompound.setFloat("TfCap", this.tfCapacity);
        nbtTagCompound.setFloat("TfAmount", this.tfAmount);
    }

    @SideOnly(Side.CLIENT)
    public double getProgressScaledInput()
    {
        if (this.wholeTfInput > 0)
        {
            return this.tfInput / this.wholeTfInput;
        }
        else
        {
            return 0;
        }
    }

    @SideOnly(Side.CLIENT)
    public double getProgressScaledTfAmount()
    {
        if (this.tfCapacity > 0)
        {
            return this.tfAmount / this.tfCapacity;
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
        return this.isWorking;
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void updateEntity()
    {
        boolean isInventoryChanged = false;
        this.isWorking = false;

        if (this.wholeTfInput > 0 && this.tfAmount < this.tfCapacity)
        {
            float tfGained = Math.min(this.tfCapacity - this.tfAmount, 1);
            this.tfInput += tfGained;
            this.tfAmount += tfGained;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.tfInput == 0 && this.canProcessInput())
            {
                this.wholeTfInput = (int)TfUtils.getItemTfAmount(this.itemStacks[SLOT_INPUT_ITEM]);

                if (this.wholeTfInput > 0)
                {
                    this.onInputStarted();
                    isInventoryChanged = true;
                }
            }

            if (this.wholeTfInput > 0)
            {
                this.isWorking = true;
                if (this.tfInput >= wholeTfInput)
                {
                    this.tfInput = 0;
                    this.wholeTfInput = 0;
                }
            }
            if (this.isExternalFluidAccepted)
            {
                this.externalProcessTime = 20;
                this.isExternalFluidAccepted = false;
            }
            else if (this.externalProcessTime > 0)
            {
                --this.externalProcessTime;
                this.isWorking = true;
            }

            if (this.isWorking != this.prevWorking)
            {
                isInventoryChanged = true;
                BlockTfStorage.updateMachineState(this.isProcessing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (isInventoryChanged)
        {
            this.markDirty();
        }

        this.prevWorking = this.isWorking;
    }

    /**
     * Checks if the machine accepts the item inputed
     */
    private boolean canProcessInput()
    {
        if (itemStacks[SLOT_INPUT_ITEM] == null) return false;
        if (this.tfAmount >= this.tfCapacity) return false;

        // Check if the empty item drained can be stacked to the input container slot
        ItemStack inputContainer = itemStacks[SLOT_INPUT_CONTAINER_ITEM];
        if (inputContainer == null) return true;
        
        // Attempt to drain
        ItemStack inputItem = itemStacks[SLOT_INPUT_ITEM];
        ItemStack container;
        if (inputItem.getItem() instanceof IFluidContainerItem)
        {
            container = TfUtils.drainFluidContainer(inputItem);
        }
        else
        {
            Item item = inputItem.getItem().getContainerItem();
            if (item == null) return true;
            container = new ItemStack(item);
        }
        
        if (container != null && !inputContainer.isItemEqual(container)) return false;
        
        // Check if the stack will overflow
        int result = inputContainer.stackSize + 1;
        return (result <= getInventoryStackLimit() && result <= inputContainer.getMaxStackSize());
    }

    /**
     * When the input process started. Drain Tofu Force from the input item and put an empty item instead if possible
     */
    public void onInputStarted()
    {
        ItemStack container = this.itemStacks[SLOT_INPUT_CONTAINER_ITEM];
        ItemStack input = this.itemStacks[SLOT_INPUT_ITEM];
        
        if (input != null)
        {
            ItemStack containerItem;
            
            if (input.getItem() instanceof IFluidContainerItem)
            {
                containerItem = TfUtils.drainFluidContainer(input);
            }
            else
            {
                containerItem = input.getItem().getContainerItem(input);
            }
            
            if (container == null) 
            {
                this.itemStacks[SLOT_INPUT_CONTAINER_ITEM] = containerItem;   
            }
            else if (containerItem != null && container.isItemEqual(containerItem))
            {
                ++container.stackSize;
            }
            
            if (--input.stackSize == 0)
            {
                this.itemStacks[SLOT_INPUT_ITEM] = null;
            }
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 0 ? slotForLower : var1 == 1 ? slotForUpper : slotForSide;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i == SLOT_INPUT_ITEM && TfMaterialRegistry.isTfMaterial(itemstack);
    }

    /**
	 * Fill a container item by Forge fluid system (IFluidHandler)
	 * Applicable to some industrial mods
	 */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (canFill(from, resource.getFluid()))
        {
            int amount = fluidTank.fill(resource, doFill);
            if (amount > 0 && doFill)
            {
                tfAmount += TfMaterialRegistry.calcTfAmountFrom(new FluidStack(resource, amount));
                this.updateFluidTank();
                this.isExternalFluidAccepted = true;
            }
            return amount;
        }
        return 0;
    }

    /**
     * Drain soymilk from the machine by Forge fluid system (IFluidHandler)
     * Applicable to industrial mods or some
     */
    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return null;
    }

    /**
     * Drain soymilk from the machine by Forge fluid system (IFluidHandler) for pipes
     * Applicable to industrial mods or some
     */
    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (this.tfAmount >= this.tfCapacity) return false;
        return fluidTank.getFluid().getFluid().getID() == fluid.getID();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        this.updateFluidTank();
        return new FluidTankInfo[] { this.fluidTank.getInfo() };
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

    @Override
    public double getMaxTfOffered()
    {
        return Math.min(this.tfAmount, 5);
    }

    @Override
    public void drawTf(double amount)
    {
        this.tfAmount -= amount;
        
    }

    @Override
    public double getMaxTfCapacity()
    {
        return Math.min(tfCapacity - this.tfAmount, 5);
    }
}
