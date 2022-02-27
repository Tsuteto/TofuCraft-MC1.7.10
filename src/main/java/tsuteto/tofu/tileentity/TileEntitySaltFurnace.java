package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import tsuteto.tofu.block.BlockSaltFurnace;
import tsuteto.tofu.fluids.FluidUtils;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.init.TcItems;

public class TileEntitySaltFurnace extends TileEntity implements ISidedInventory, IFluidHandler
{
    private static final int[] slotForSide = new int[] {0, 1, 2, 3};
    private static final int[] slotForLower = new int[] {1, 3};
    private static final FluidStack nigari = new FluidStack(TcFluids.NIGARI, 10);

    // 0=Fuel, 1=Salt output, 2=Glass bottle, 3=Nigari output
    private ItemStack[] furnaceItemStacks = new ItemStack[4];
    public FluidTank nigariTank = new FluidTank(TcFluids.NIGARI, 0, 120);
    public int furnaceBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int furnaceCookTime = 0;
    private int lastCauldronStatus = 0;
    private String customName;

    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack var3;

            if (this.furnaceItemStacks[par1].stackSize <= par2)
            {
                var3 = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0)
                {
                    this.furnaceItemStacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.furnaceItemStacks[par1] != null)
        {
            ItemStack var2 = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.tofucraft.SaltFurnace";
    }

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

    public void setCustomName(String par1Str)
    {
        this.customName = par1Str;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.furnaceItemStacks[1]);
        this.nigariTank.readFromNBT(par1NBTTagCompound.getCompoundTag("NigariTank"));

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short) this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);

        NBTTagCompound tag1 = this.nigariTank.writeToNBT(new NBTTagCompound());
        par1NBTTagCompound.setTag("NigariTank", tag1);

        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < this.furnaceItemStacks.length; ++var3)
        {
            if (this.furnaceItemStacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.furnaceItemStacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);

        if (this.hasCustomInventoryName())
        {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbttagcompound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.furnaceCookTime * par1 / 200;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public FluidTank getNigariTank()
    {
        return this.nigariTank;
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @Override
    public void updateEntity()
    {
        boolean var1 = this.furnaceBurnTime > 0;
        boolean var2 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime == 0 && this.canBoil())
            {
                this.currentItemBurnTime = this.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(this.furnaceItemStacks[0]);

                if (this.furnaceBurnTime > 0)
                {
                    var2 = true;

                    if (this.furnaceItemStacks[0] != null)
                    {
                        --this.furnaceItemStacks[0].stackSize;

                        if (this.furnaceItemStacks[0].stackSize == 0)
                        {
                            this.furnaceItemStacks[0] = this.furnaceItemStacks[0].getItem().getContainerItem(furnaceItemStacks[0]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canBoil() && this.getCauldronStatus() >= 1)
            {
                ++this.furnaceCookTime;

                if (this.furnaceCookTime == 200)
                {
                    this.furnaceCookTime = 0;
                    this.boilDown();
                    var2 = true;
                }
            }
            else
            {
                this.furnaceCookTime = 0;
            }
    
            if (var1 != this.furnaceBurnTime > 0)
            {
                var2 = true;
                BlockSaltFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
                
            if (this.isBurning())
            {
                Block blockAbove = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
                if (blockAbove == Blocks.air)
                {
                    this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
                }
                else if (blockAbove == Blocks.fire)
                {
                    this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord + 1, this.zCoord, 0, 2);
                }
                else if (this.worldObj.isAirBlock(this.xCoord, this.yCoord + 2, this.zCoord)
                        && this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord).getMaterial().getCanBurn())
                {
                    if (this.worldObj.rand.nextInt(200) == 0)
                    {
                        this.worldObj.setBlock(this.xCoord, this.yCoord + 2, this.zCoord, Blocks.fire);
                    }
                }
            }

            // Output nigari
            if (this.canOutputNigari())
            {
                this.outputNigari();
            }
        }

        this.updateCauldronStatus();

        if (var2)
        {
            this.markDirty();
        }
    }

    private boolean canBoil()
    {
        int cauldronStatus = this.getCauldronStatus();
        if (cauldronStatus == 0) return true;
        if (cauldronStatus != 2) return false;

        ItemStack var1 = new ItemStack(TcItems.salt);
        if (this.furnaceItemStacks[1] == null) return true;
        if (!this.furnaceItemStacks[1].isItemEqual(var1)) return false;
        int result = furnaceItemStacks[1].stackSize + var1.stackSize;
        return (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
    }

    public void boilDown()
    {
        if (this.canBoil())
        {
            ItemStack var1 = new ItemStack(TcItems.salt, 2);

            if (this.furnaceItemStacks[1] == null)
            {
                this.furnaceItemStacks[1] = var1.copy();
            }
            else if (this.furnaceItemStacks[1].isItemEqual(var1))
            {
                furnaceItemStacks[1].stackSize += var1.stackSize;
            }

            this.nigariTank.fill(new FluidStack(TcFluids.NIGARI, 20), true);

            // Decrease the water in the cauldron
            if (!worldObj.isRemote)
            {
                int waterLevel = this.getCauldronWaterLevel();
                this.setCauldronWaterLevel(waterLevel - 1);
            }
        }
    }

    private boolean canOutputNigari()
    {
        ItemStack containerStack = this.furnaceItemStacks[2];

        if (containerStack != null && containerStack.stackSize > 0)
        {
            ItemStack filledStack = FluidContainerRegistry.fillFluidContainer(nigariTank.getFluid(), containerStack);
            if (filledStack == null) return false;
            int containerCapacity = FluidContainerRegistry.getContainerCapacity(filledStack);

            if (this.nigariTank.getFluidAmount() < containerCapacity) return false;
            if (furnaceItemStacks[3] == null) return true;
            int result = furnaceItemStacks[3].stackSize + containerStack.stackSize;
            return result <= getInventoryStackLimit() && result <= filledStack.getMaxStackSize();
        }
        return false;
    }

    public void outputNigari()
    {
        ItemStack containerStack = this.furnaceItemStacks[2];
        ItemStack filledStack = FluidContainerRegistry.fillFluidContainer(nigariTank.getFluid(), containerStack);
        int containerCapacity = FluidContainerRegistry.getContainerCapacity(filledStack);
        if (this.furnaceItemStacks[3] == null)
        {
            this.furnaceItemStacks[3] = filledStack.copy();
        }
        else if (this.furnaceItemStacks[3].isItemEqual(filledStack))
        {
            furnaceItemStacks[3].stackSize += filledStack.stackSize;
        }

        this.nigariTank.drain(containerCapacity, true);

        furnaceItemStacks[2].stackSize -= 1;
        if (furnaceItemStacks[2].stackSize == 0)
        {
            furnaceItemStacks[2] = null;
        }
    }

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
		return var1 == 0 ? slotForLower : slotForSide;
	}

    @Override
    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    @Override
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return par1 == 1 || par1 == 3;
    }

    private int getLiveMetadata()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

	@Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i == 0 ? TileEntityFurnace.isItemFuel(itemstack) : i == 2 ? FluidUtils.isContainerForFluid(nigari.copy(), itemstack) : false;
    }

    /*
     * === Cauldron Control ===
     */
    private void updateCauldronStatus()
    {
        int stat;
        if (!this.isCauldronOnFurnace()) stat = 0;
        else if (this.getCauldronWaterLevel() == 0) stat = 1;
        else stat = 2;

        if (lastCauldronStatus != stat)
        {
            int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            int newMetadata = metadata & 3 | stat << 2;
            this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMetadata, 2);
            lastCauldronStatus = stat;
        }
    }
    private boolean isCauldronOnFurnace()
    {
        Block blockId = worldObj.getBlock(xCoord, yCoord + 1, zCoord);
        return blockId == Blocks.cauldron;
    }
    private int getCauldronWaterLevel()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord);
    }
    private void setCauldronWaterLevel(int level)
    {
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord, level, 2);
    }
    public int getCauldronStatus()
    {
        return TileEntitySaltFurnace.getCauldronStatus(this.getLiveMetadata());
    }
    public static int getCauldronStatus(int metadata)
    {
        return metadata >> 2 & 3;
    }

    /*
     * === Fluid Handler ===
     */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if ((resource == null) || (!resource.isFluidEqual(nigariTank.getFluid())))
        {
            return null;
        }

        if (!canDrain(from, resource.getFluid())) return null;

        return nigariTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return nigariTank.drain(maxDrain, doDrain);
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
        // do fluid amount check because getFluid() returns null when the tank is empty
        return nigariTank.getFluidAmount() != 0 && nigariTank.getFluid().getFluidID() == fluid.getID();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { this.nigariTank.getInfo() };
    }
}
