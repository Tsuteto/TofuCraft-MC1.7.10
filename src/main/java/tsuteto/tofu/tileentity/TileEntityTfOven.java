package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineSidedInventoryBase;
import tsuteto.tofu.block.BlockTfOven;
import tsuteto.tofu.item.ItemTcMaterials;

public class TileEntityTfOven extends TileEntityTfMachineSidedInventoryBase implements ITfConsumer
{
    public static final int SLOT_ITEM_INPUT = 0;
    public static final int SLOT_ITEM_RESULT = 1;
    public static final int SLOT_ACCELERATION = 2;

    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {1};
    private static final int[] slotsSides = new int[] {1};

    public static final double COST_TF_PER_TICK = 0.025D;
    public static final double TF_CAPACITY = 20.0D;
    public static final int WHOLE_COOK_TIME_BASE = 200;

    public int ovenCookTime;
    public int wholeCookTime;
    public double tfPooled;
    private ItemStack lastInputItem = null;
    private boolean isHeating;
    private boolean isTfNeeded;
    public boolean isCharging;

    public TileEntityTfOven()
    {
        this.itemStacks = new ItemStack[3];
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfOven";
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        this.ovenCookTime = nbtTagCompound.getShort("CookTime");
        this.wholeCookTime = nbtTagCompound.getShort("CookTimeW");
        this.tfPooled = nbtTagCompound.getFloat("TfP");
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("CookTime", (short) this.ovenCookTime);
        nbtTagCompound.setShort("CookTimeW", (short) this.wholeCookTime);
        nbtTagCompound.setFloat("TfP", (float) this.tfPooled);
    }

    @SideOnly(Side.CLIENT)
    public double getCookProgressScaled()
    {
        return (double)this.ovenCookTime / (double)wholeCookTime;
    }

    @SideOnly(Side.CLIENT)
    public double getHeaterScaled()
    {
        return this.tfPooled / TF_CAPACITY;
    }

    public boolean isHeating()
    {
        return ovenCookTime > 0 && this.isCharging;
    }

    public void updateEntity()
    {
        boolean flag1 = false;

        if (!this.worldObj.isRemote)
        {
            if (this.canSmelt())
            {
                this.wholeCookTime = this.getWholeCookTime();

                // Cooking
                ++this.ovenCookTime;
                this.tfPooled -= this.getTfAmountNeeded();

                if (this.ovenCookTime >= wholeCookTime)
                {
                    // Finish
                    this.ovenCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                // Stop cooking
                if (lastInputItem != this.itemStacks[SLOT_ITEM_INPUT])
                {
                    this.ovenCookTime = 0;
                }
            }

            if (this.isHeating != isHeating())
            {
                flag1 = true;
                BlockTfOven.updateMachineState(isHeating(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }

        }

        if (flag1)
        {
            this.markDirty();
        }
        lastInputItem = itemStacks[SLOT_ITEM_INPUT];
        this.isHeating = isHeating();
        this.isCharging = false;
    }

    public boolean isAccelerated()
    {
        ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
        return ItemTcMaterials.activatedHellTofu.isItemEqual(itemStack);
    }

    private int getWholeCookTime()
    {
        if (this.isAccelerated())
        {
            ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
            return WHOLE_COOK_TIME_BASE / itemStack.stackSize;
        }
        return WHOLE_COOK_TIME_BASE;
    }

    private double getTfAmountNeeded()
    {
        if (this.isAccelerated())
        {
            ItemStack itemStack = this.itemStacks[SLOT_ACCELERATION];
            return 5.0D / (double) this.getWholeCookTime() + COST_TF_PER_TICK / 10.0D * Math.pow(1.1, itemStack.stackSize);
        }
        return COST_TF_PER_TICK;
    }

    private boolean canSmelt()
    {
        isTfNeeded = false;
        if (this.itemStacks[SLOT_ITEM_INPUT] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemStacks[SLOT_ITEM_INPUT]);
            if (itemstack != null)
            {
                if (this.itemStacks[SLOT_ITEM_RESULT] != null)
                {
                    if (!this.itemStacks[SLOT_ITEM_RESULT].isItemEqual(itemstack)) return false;

                    int result = itemStacks[SLOT_ITEM_RESULT].stackSize + itemstack.stackSize;
                    if (result > getInventoryStackLimit() || result > this.itemStacks[SLOT_ITEM_RESULT].getMaxStackSize())
                    {
                        return false;
                    }

                }
                isTfNeeded = true;
                return tfPooled >= this.getTfAmountNeeded();
            }
        }
        return false;
    }

    public void smeltItem()
    {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.itemStacks[SLOT_ITEM_INPUT]);

        if (this.itemStacks[SLOT_ITEM_RESULT] == null)
        {
            this.itemStacks[SLOT_ITEM_RESULT] = itemstack.copy();
        }
        else if (this.itemStacks[SLOT_ITEM_RESULT].getItem() == itemstack.getItem())
        {
            this.itemStacks[SLOT_ITEM_RESULT].stackSize += itemstack.stackSize;
        }

        --this.itemStacks[SLOT_ITEM_INPUT].stackSize;

        if (this.itemStacks[SLOT_ITEM_INPUT].stackSize <= 0)
        {
            this.itemStacks[SLOT_ITEM_INPUT] = null;
        }
    }

    @Override
    public double getMaxTfCapacity()
    {
        return TF_CAPACITY - this.tfPooled;
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return isTfNeeded ? this.getTfAmountNeeded() : 0;
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfPooled += amount;
        this.isCharging = amount != 0.0D;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
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
