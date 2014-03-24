package tsuteto.tofu.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tsuteto.tofu.TfMaterialRegistry;
import tsuteto.tofu.block.BlockTfStorage;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.TcItems;

public class TileEntityTfStorage extends TileEntity implements ISidedInventory, IFluidHandler, ITfSupplier
{
    
    public static final int SLOT_INPUT_ITEM = 0;
    public static final int SLOT_INPUT_CONTAINER_ITEM = 3;
    public static final int SLOT_OUTPUT_ITEM = 2;
    public static final int SLOT_OUTPUT_CONTAINER_ITEM = 1;

    private static final int[] slotForSide = new int[] {1};
    private static final int[] slotForUpper = new int[] {0};
    private static final int[] slotForLower = new int[] {2, 3};
    
    private ItemStack[] itemStacks = new ItemStack[4];
    
    /** For fluid handling. Synchronized with the tf amount the machine has */
    private final FluidTank fluidTank = new FluidTank(0);

    public int wholeTimeInput = 0;
    public int wholeTimeOutput = 0;

    public int processTimeInput = 0;
    public int processTimeOutput = 0;
    
    public int externalProcessTime = 0;
    public boolean isExternalProcessed = false;
    
    public float tfAmount = 0;
    public float tfCapacity = 5000;

    private String customName;
    
    public TileEntityTfStorage()
    {
        this.fluidTank.setFluid(new FluidStack(TcFluids.SOYMILK, 0));
        this.fluidTank.setCapacity(TfMaterialRegistry.calcSoymilkAmountFrom(this.tfCapacity));
    }
    
    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.itemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.itemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.itemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.itemStacks[par1].stackSize <= par2)
            {
                var3 = this.itemStacks[par1];
                this.itemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.itemStacks[par1].splitStack(par2);

                if (this.itemStacks[par1].stackSize == 0)
                {
                    this.itemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.itemStacks[par1] != null)
        {
            ItemStack var2 = this.itemStacks[par1];
            this.itemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.itemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.tofucraft.TfStorage";
    }

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

    public void setCustomName(String par1Str)
    {
        this.customName = par1Str;
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 9);
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.itemStacks.length)
            {
                this.itemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.processTimeInput = par1NBTTagCompound.getShort("ProcI");
        this.processTimeOutput = par1NBTTagCompound.getShort("ProcO");
        this.wholeTimeInput = par1NBTTagCompound.getShort("WholeI");
        this.tfCapacity = par1NBTTagCompound.getFloat("TfCap");
        this.tfAmount = par1NBTTagCompound.getFloat("TfAmount");
        
        if (this.canProcessOutput())
        {
            this.wholeTimeOutput = (int)getItemTfAmount(new ItemStack(TcItems.bucketSoymilk));
        }

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
        
        this.fluidTank.setCapacity(TfMaterialRegistry.calcSoymilkAmountFrom(this.tfCapacity));
        this.updateFluidTank();
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("ProcI", (short)this.processTimeInput);
        par1NBTTagCompound.setShort("ProcO", (short)this.processTimeOutput);
        par1NBTTagCompound.setShort("WholeI", (short)this.wholeTimeInput);
        par1NBTTagCompound.setFloat("TfCap", this.tfCapacity);
        par1NBTTagCompound.setFloat("TfAmount", this.tfAmount);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.itemStacks.length; ++var3)
        {
            if (this.itemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.itemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);

        if (this.hasCustomInventoryName())
        {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)

    public int getProgressScaledInput(int par1)
    {
        if (this.wholeTimeInput > 0)
        {
            return this.processTimeInput * par1 / this.wholeTimeInput;
        }
        else
        {
            return 0;
        }
    }

    @SideOnly(Side.CLIENT)

    public int getProgressScaledOutput(int par1)
    {
        if (this.wholeTimeOutput > 0)
        {
            return this.processTimeOutput * par1 / this.wholeTimeOutput;
        }
        else
        {
            return 0;
        }
    }

    public int getProgressScaledTfAmount(int par1)
    {
        if (this.tfCapacity > 0)
        {
            return (int)(this.tfAmount * par1 / this.tfCapacity);
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
        return this.wholeTimeInput > 0 || this.wholeTimeOutput > 0;
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void updateEntity()
    {
        boolean isProcessingInput = this.wholeTimeInput > 0;
        boolean isProcessingOutput = this.wholeTimeOutput > 0;
        boolean isExternalProcessing = this.externalProcessTime > 0;
        boolean isInventoryChanged = false;

        if (this.wholeTimeInput > 0 && this.tfAmount < this.tfCapacity)
        {
            ++this.processTimeInput;
            ++this.tfAmount;
        }
        
        if (!this.worldObj.isRemote)
        {
            if (this.processTimeInput == 0 && this.canProcessInput())
            {
                this.wholeTimeInput = (int)getItemTfAmount(this.itemStacks[SLOT_INPUT_ITEM]);

                if (this.wholeTimeInput > 0)
                {
                    this.onInputStarted();
                    isInventoryChanged = true;
                }
            }

            if (this.processTimeOutput == 0 && this.canProcessOutput())
            {
                int containerVol = this.getSoymilkCapacityOf(this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM]);
                this.wholeTimeOutput = (int)TfMaterialRegistry.calcTfAmountFrom(containerVol);
            }
            
            if (this.wholeTimeInput > 0)
            {
                if (this.processTimeInput >= wholeTimeInput)
                {
                    this.processTimeInput = 0;
                    this.wholeTimeInput = 0;
                }
            }
            if (this.wholeTimeOutput > 0 && this.canProcessOutput())
            {
                ++this.processTimeOutput;
                --this.tfAmount;

                if (this.processTimeOutput >= wholeTimeOutput)
                {
                    this.processTimeOutput = 0;
                    this.wholeTimeOutput = 0;
                    this.onOutputCompleted();
                    isInventoryChanged = true;
                }
            }
            else if (this.processTimeOutput > 0)
            {
                this.processTimeOutput = 0;
                this.wholeTimeOutput = 0;
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

            if (isProcessingInput != this.wholeTimeInput > 0 || isProcessingOutput != this.wholeTimeOutput > 0
                    || isExternalProcessing != this.externalProcessTime > 0)
            {
                isInventoryChanged = true;
                BlockTfStorage.updateMachineState(this.isProcessing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (isInventoryChanged)
        {
            this.markDirty();
        }
    }

    /**
     * Checks if the machine accepts the item inputed
     */
    private boolean canProcessInput()
    {
        if (itemStacks[SLOT_INPUT_ITEM] == null) return false;
        if (this.tfAmount >= this.tfCapacity) return false;

        // Check if the empty item drained can be stacked to the input container slot
        ItemStack var1 = itemStacks[SLOT_INPUT_CONTAINER_ITEM];
        if (var1 == null) return true;
        
        // Attempt to drain
        ItemStack container;
        if (var1.getItem() instanceof IFluidContainerItem)
        {
            container = this.drainFluidContainer(var1);
        }
        else
        {
            Item item = itemStacks[SLOT_INPUT_ITEM].getItem().getContainerItem();
            if (item == null) return true;
            container = new ItemStack(item);
        }
        
        if (!var1.isItemEqual(container)) return false;
        
        // Check if the stack will overflow
        int result = itemStacks[SLOT_INPUT_CONTAINER_ITEM].stackSize + 1;
        return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
    }

    /**
     * Checks if the machine accepts the item to output soymilk
     */
    private boolean canProcessOutput()
    {
        if (this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM] == null) return false;
        if (this.tfAmount <= 0) return false;
        
        // Get a item filled with soymilk from the container item for output
        ItemStack var1 = this.fillSoymilk(this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM]);
        if (var1 == null) return false;
        
        // Check if the filled item can be stacked to the output slot
        if (this.itemStacks[SLOT_OUTPUT_ITEM] == null) return true;
        if (!this.itemStacks[SLOT_OUTPUT_ITEM].isItemEqual(var1)) return false;
        
        // Check if the stack will overflow
        int result = itemStacks[SLOT_OUTPUT_ITEM].stackSize + var1.stackSize;
        return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
    }

    /**
     * Get a capacity of soymilk the container item has
     * @param container
     * @return
     */
    private int getSoymilkCapacityOf(ItemStack container)
    {
        if (container.getItem() instanceof IFluidContainerItem)
        {
            this.updateFluidTank();
            
            IFluidContainerItem fluidItem = (IFluidContainerItem)container.getItem();
            ItemStack target = container.copy();
            target.stackSize = 1;
            return fluidItem.fill(target, fluidTank.getFluid(), false);
        }
        ItemStack is = this.fillSoymilk(container);
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
    private ItemStack fillSoymilk(ItemStack container)
    {
        if (container.getItem() instanceof IFluidContainerItem)
        {
            return fillFluidContainer(container);
        }
        else
        {
            return FluidContainerRegistry.fillFluidContainer(new FluidStack(TcFluids.SOYMILK, Integer.MAX_VALUE), container);
        }
    }
    
    /**
     * Returns an empty one of the fluid container that implements IFluidContainerItem
     * @param input
     * @return
     */
    private ItemStack drainFluidContainer(ItemStack input)
    {
        // Take one from the stack
        ItemStack target = input.copy();
        target.stackSize = 1;
        
        // Drain its fluid and check if it is soymilk
        IFluidContainerItem fluidItem = (IFluidContainerItem)input.getItem();
        FluidStack content = fluidItem.getFluid(input);
        if (content == null || !content.isFluidEqual(fluidTank.getFluid()))
        {
            return null;
        }
        fluidItem.drain(target, Integer.MAX_VALUE, true);
        return target;
    }
    
    /**
     * Returns a filled one of the fluid container that implements IFluidContainerItem
     * @param input
     * @return
     */
    private ItemStack fillFluidContainer(ItemStack input)
    {
        // Take one from the stack
        ItemStack target = input.copy();
        target.stackSize = 1;
        
        IFluidContainerItem fluidItem = (IFluidContainerItem)input.getItem();
        fluidItem.fill(target, fluidTank.getFluid(), true);
        return target;
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
                containerItem = this.drainFluidContainer(input);
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

    /**
     * When the output process completed. Take one from the output container stack and fill soymilk converted from TF
     */
    public void onOutputCompleted()
    {
        ItemStack var1 = this.fillSoymilk(this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM]);

        if (this.itemStacks[SLOT_OUTPUT_ITEM] == null)
        {
            this.itemStacks[SLOT_OUTPUT_ITEM] = var1.copy();
        }
        else if (this.itemStacks[SLOT_OUTPUT_ITEM].isItemEqual(var1))
        {
            itemStacks[SLOT_OUTPUT_ITEM].stackSize += var1.stackSize;
        }
        
        if (--this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM].stackSize == 0)
        {
            this.itemStacks[SLOT_OUTPUT_CONTAINER_ITEM] = null;
        }
    }

    public float getItemTfAmount(ItemStack itemstack)
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
            
            if (content != null && content.isFluidEqual(fluidTank.getFluid()))
            {
                return TfMaterialRegistry.calcTfAmountFrom(content.amount);
            }
            else
            {
                return TfMaterialRegistry.getTfAmount(itemstack);
            }
        }   
    }
    
    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 0 ? slotForLower : var1 == 1 ? slotForUpper : slotForSide;
	}

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return true;
    }

    private int getLiveMetadata()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

	@Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i == SLOT_INPUT_ITEM ? TfMaterialRegistry.isTfMaterial(itemstack) : i == SLOT_OUTPUT_CONTAINER_ITEM ? true : false;
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
                tfAmount += TfMaterialRegistry.calcTfAmountFrom(amount);
                this.updateFluidTank();
                this.isExternalProcessed = true;
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
        this.updateFluidTank();
        
        if ((resource == null) || (!resource.isFluidEqual(fluidTank.getFluid())))
        {
            return null;
        }

        if (!canDrain(from, resource.getFluid())) return null;

        FluidStack fluid = fluidTank.drain(resource.amount, doDrain);
        
        if (fluid != null && doDrain && fluid.amount > 0)
        {
            tfAmount -= TfMaterialRegistry.calcTfAmountFrom(fluid.amount);
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
            tfAmount -= TfMaterialRegistry.calcTfAmountFrom(fluid.amount);
            this.updateFluidTank();
            this.isExternalProcessed = true;
        }
        return fluid;
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
        if (this.tfAmount <= 0) return false;
        return fluidTank.getFluid().getFluid().getID() == fluid.getID();
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
        this.fluidTank.getFluid().amount = TfMaterialRegistry.calcSoymilkAmountFrom(this.tfAmount);
    }

    @Override
    public float getMaxTfOffered()
    {
        return Math.min(this.tfAmount, 40);
    }

    @Override
    public void drawTf(float amount)
    {
        this.tfAmount -= amount;
        
    }
}
