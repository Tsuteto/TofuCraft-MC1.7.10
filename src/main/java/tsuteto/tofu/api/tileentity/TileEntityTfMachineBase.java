package tsuteto.tofu.api.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketGuiControl;

abstract public class TileEntityTfMachineBase extends TileEntity implements IInventory
{
    protected ItemStack[] itemStacks = new ItemStack[0];

    protected String customName;

    abstract protected String getInventoryNameTranslate();

    public void onTileEntityCreated(World world, int x, int y, int z, EntityLivingBase entityCreatedBy, ItemStack itemstack) {};

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : this.getInventoryNameTranslate();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public void setCustomName(String par1Str)
    {
        this.customName = par1Str;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        if (itemStacks.length > 0)
        {
            NBTTagList nbttaglist = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            this.itemStacks = new ItemStack[this.getSizeInventory()];

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                byte b0 = nbttagcompound1.getByte("Slot");

                if (b0 >= 0 && b0 < this.itemStacks.length)
                {
                    this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                }
            }
        }

        if (nbtTagCompound.hasKey("CustomName", 8))
        {
            this.customName = nbtTagCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setByte("Rev", (byte) this.getNBTRevision());

        if (itemStacks.length > 0)
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 0; i < this.itemStacks.length; ++i)
            {
                if (this.itemStacks[i] != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.itemStacks[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }

            nbtTagCompound.setTag("Items", nbttaglist);
        }

        if (this.hasCustomInventoryName())
        {
            nbtTagCompound.setString("CustomName", this.customName);
        }
    }

    protected int getNBTRevision()
    {
        return 1;
    }

    /**
     * Overriden in a sign to provide the text.
     */
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

    @SideOnly(Side.CLIENT)
    public void postGuiControl(int windowId, int eventId)
    {
        this.postGuiControl(windowId, eventId, null);
    }

    @SideOnly(Side.CLIENT)
    public void postGuiControl(int windowId, int eventId, PacketGuiControl.DataHandler data)
    {
        PacketGuiControl packet = new PacketGuiControl(windowId, eventId, data);
        PacketDispatcher.packet(packet).sendToServer();
    }

    public boolean isRedstonePowered()
    {
        return (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 8) == 8;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
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
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    protected int getLiveMetadata()
    {
        return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}
}
