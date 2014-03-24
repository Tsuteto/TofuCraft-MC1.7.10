package tsuteto.tofu.block.tileentity;

import java.io.DataOutputStream;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.TfMaterialRegistry;
import tsuteto.tofu.network.packet.PacketTfMachineData;
import tsuteto.tofu.util.SimplePacketDispatcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTfStorage extends ContainerTfMachine
{
    private final TileEntityTfStorage machine;
    private final int lastCookTime = 0;
    private final int lastBurnTime = 0;
    private final int lastItemBurnTime = 0;
    private int lastWholeTimeInput;
    private int lastWholeTimeOutput;
    private int lastProcessTimeInput;
    private int lastProcessTimeOutput;
    private float lastTfCapacity;
    private float lastTfAmount;

    public ContainerTfStorage(InventoryPlayer invPlayer, TileEntityTfStorage machine)
    {
        this.machine = machine;
        this.addSlotToContainer(new Slot(machine, TileEntityTfStorage.SLOT_INPUT_ITEM, 45, 22));
        this.addSlotToContainer(new Slot(machine, TileEntityTfStorage.SLOT_INPUT_CONTAINER_ITEM, 18, 22));
        this.addSlotToContainer(new Slot(machine, TileEntityTfStorage.SLOT_OUTPUT_CONTAINER_ITEM, 45, 51));
        this.addSlotToContainer(new Slot(machine, TileEntityTfStorage.SLOT_OUTPUT_ITEM, 18, 51));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 98 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, 8 + var3 * 18, 156));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.machine.wholeTimeInput);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.machine.processTimeInput);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.machine.wholeTimeOutput);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.machine.processTimeOutput);
        
        this.sendTfMachineData(par1ICrafting, this, 0, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfCapacity);
            }
        });
    
        this.sendTfMachineData(par1ICrafting, this, 1, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfAmount);
            }
        });
    }
    
    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastWholeTimeInput != this.machine.wholeTimeInput)
            {
                var2.sendProgressBarUpdate(this, 0, this.machine.wholeTimeInput);
            }

            if (this.lastProcessTimeInput != this.machine.processTimeInput)
            {
                var2.sendProgressBarUpdate(this, 1, this.machine.processTimeInput);
            }

            if (this.lastWholeTimeOutput != this.machine.wholeTimeOutput)
            {
                var2.sendProgressBarUpdate(this, 2, this.machine.wholeTimeOutput);
            }
            
            if (this.lastProcessTimeOutput != this.machine.processTimeOutput)
            {
                var2.sendProgressBarUpdate(this, 3, this.machine.processTimeOutput);
            }
            
            if (this.lastTfCapacity != this.machine.tfCapacity)
            {
                this.sendTfMachineData(var2, this, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfCapacity);
                    }
                });
            }
            
            if (this.lastTfAmount != this.machine.tfAmount)
            {
                this.sendTfMachineData(var2, this, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfAmount);
                    }
                });
            }
        }

        this.lastWholeTimeInput = this.machine.wholeTimeInput;
        this.lastProcessTimeInput = this.machine.processTimeInput;
        this.lastWholeTimeOutput = this.machine.wholeTimeOutput;
        this.lastProcessTimeOutput = this.machine.processTimeOutput;
        this.lastTfCapacity = this.machine.tfCapacity;
        this.lastTfAmount = this.machine.tfAmount;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.machine.wholeTimeInput = par2;
        }

        if (par1 == 1)
        {
            this.machine.processTimeInput = par2;
        }

        if (par1 == 2)
        {
            this.machine.wholeTimeOutput = par2;
        }
        
        if (par1 == 3)
        {
            this.machine.processTimeOutput = par2;
        }
    }


    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {
        
        if (id == 0)
        {
            this.machine.tfCapacity = data.readFloat();
        }

        if (id == 1)
        {
            this.machine.tfAmount = data.readFloat();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.machine.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        // 0-2: Salt furnace inventory
        // 3-29: Player inventory
        // 30-38: Hot bar in the player inventory
        
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 <= 2)
            {
                if (!this.mergeItemStack(var5, 3, 38, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 > 2)
            {
                if (TfMaterialRegistry.isTfMaterial(var5))
                {
                    if (!this.mergeItemStack(var5, 0, 0, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(var5, 30, 38, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 29 && par2 < 38 && !this.mergeItemStack(var5, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 38, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }

            if (var5.stackSize == var3.stackSize)
            {
                return null;
            }

            var4.onPickupFromSlot(par1EntityPlayer, var5);
        }

        return var3;
    }
}
