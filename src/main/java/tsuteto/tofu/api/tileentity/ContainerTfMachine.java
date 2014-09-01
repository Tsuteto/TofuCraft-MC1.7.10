package tsuteto.tofu.api.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketTfMachineData;

abstract public class ContainerTfMachine<T extends TileEntityTfMachineBase> extends Container
{
    public static final int playerInventoryPosX = 8;
    public static final int playerInventoryPosY = 98;

    protected T machine;

    public ContainerTfMachine(T machine)
    {
        this.machine = machine;
    }

    public abstract void updateTfMachineData(int id, ByteBuf data);

    public void sendTfMachineData(ICrafting crafter, ContainerTfMachine par1Container, int par2, PacketTfMachineData.DataHandler data)
    {
        if (crafter instanceof EntityPlayerMP)
        {
            PacketTfMachineData packet = new PacketTfMachineData(par1Container.windowId, par2, data);
            PacketDispatcher.packet(packet).sendToPlayer((EntityPlayerMP) crafter);
        }
    }

    public void onGuiControl(int eventId, ByteBuf buffer) {}

    public void preparePlayerInventory(InventoryPlayer invPlayer, int ox, int oy)
    {
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, ox + var4 * 18, oy + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, ox + var3 * 18, oy + 58));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return this.machine.isUseableByPlayer(var1);
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        // 0-2: Salt furnace inventory
        // 3-29: Player inventory
        // 30-38: Hot bar in the player inventory

        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);
        int machineSlotIdFrom = 0;
        int machineSlotIdTo = this.machine.itemStacks.length - 1;
        int playerInvSlotIdFrom = machineSlotIdTo + 1;
        int playerInvSlotIdTo = playerInvSlotIdFrom + 26;
        int hotbarSlotIdFrom = playerInvSlotIdTo + 1;
        int hotbarSlotIdTo = hotbarSlotIdFrom + 8;


        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 <= machineSlotIdTo)
            {
                if (!this.mergeItemStack(var5, playerInvSlotIdFrom, hotbarSlotIdTo + 1, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 > machineSlotIdTo)
            {
                TransferResult result = transferStackInMachineSlot(par1EntityPlayer, par2, var5);
                if (result != TransferResult.SKIPPING)
                {
                    if (result == TransferResult.MISMATCHED) return null;
                    // DO NOTHING WHEN MATCHED
                }
                else if (par2 >= playerInvSlotIdFrom && par2 <= playerInvSlotIdTo)
                {
                    if (!this.mergeItemStack(var5, hotbarSlotIdFrom, hotbarSlotIdTo + 1, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= hotbarSlotIdFrom && par2 <= hotbarSlotIdTo && !this.mergeItemStack(var5, playerInvSlotIdFrom, playerInvSlotIdTo + 1, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, playerInvSlotIdFrom, hotbarSlotIdTo + 1, false))
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

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slot, ItemStack itemStack)
    {
        return TransferResult.SKIPPING;
    }

    protected boolean mergeToSingleItemStack(ItemStack itemStack, int slotId)
    {
        return this.mergeItemStack(itemStack, slotId, slotId + 1, false);
    }


    protected enum TransferResult
    {
        SKIPPING,
        MATCHED,
        MISMATCHED;
    }
}
