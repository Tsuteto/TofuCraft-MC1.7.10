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
import tsuteto.tofu.block.BlockSaltFurnace;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

public class TileEntitySaltFurnace extends TileEntity implements ISidedInventory
{
    private static final int[] slotForSide = new int[] {0};
    private static final int[] slotForLower = new int[] {1};

    /**
     * The ItemStacks that hold the items currently being used in the furnace
     * 0=fuel item, 1=output item
     */
    private ItemStack[] furnaceItemStacks = new ItemStack[2];

    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime = 0;

    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int currentItemBurnTime = 0;

    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime = 0;

    private int lastCauldronStatus = 0;

    private String customName;

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1)
    {
        return this.furnaceItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
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

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
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

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.furnaceItemStacks[par1] = par2ItemStack;

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

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        ModLog.debug("reading salt furnace packet on " + (this.worldObj != null ? this.worldObj.isRemote ? "client" : "server" : "?"));
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

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.customName = par1NBTTagCompound.getString("CustomName");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        ModLog.debug("writing salt furnace packet on " + (this.worldObj != null ? this.worldObj.isRemote ? "client" : "server" : "?"));
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
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

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1)
    {
        return this.furnaceCookTime * par1 / 200;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * par1 / this.currentItemBurnTime;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
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
        }

        this.updateCauldronStatus();

        if (var2)
        {
            this.markDirty();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
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

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
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

            // Decrease the water in the cauldron
            if (!worldObj.isRemote)
            {
                int waterLevel = this.getCauldronWaterLevel();
                this.setCauldronWaterLevel(waterLevel - 1);
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
        return true;
    }

    private int getLiveMetadata()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

	@Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return i == 0 ? TileEntityFurnace.isItemFuel(itemstack) : false;
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
}
