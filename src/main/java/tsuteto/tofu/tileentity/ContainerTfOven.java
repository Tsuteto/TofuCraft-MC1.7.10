package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.gui.TfMachineGuiParts;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerTfOven extends ContainerTfMachine<TileEntityTfOven>
{
    private int lastCookTime = 0;
    private double lastTfPooled = 0;
    private int lastWholeCookTime = 0;
    private boolean lastCharging = false;

    public ContainerTfOven(InventoryPlayer invPlayer, TileEntityTfOven machine)
    {
        super(machine);
        this.addSlotToContainer(new SlotTfMachine(machine, TileEntityTfOven.SLOT_ITEM_INPUT, 45, 30, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfOvenResult(invPlayer.player, machine, TileEntityTfOven.SLOT_ITEM_RESULT, 111, 28, TfMachineGuiParts.itemSlotL2));
        this.addSlotToContainer(new SlotTfOvenAccelerator(invPlayer.player, machine, TileEntityTfOven.SLOT_ACCELERATION, 86, 53, TfMachineGuiParts.itemSlot));

        this.preparePlayerInventory(invPlayer, playerInventoryPosX, playerInventoryPosY);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.machine.ovenCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.machine.wholeCookTime);

        this.sendTfMachineData(par1ICrafting, this, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfPooled);
            }
        });
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastCookTime != this.machine.ovenCookTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.machine.ovenCookTime);
            }
            if (this.lastWholeCookTime != this.machine.wholeCookTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.machine.wholeCookTime);
            }
            if (this.lastTfPooled != this.machine.tfPooled)
            {
                this.sendTfMachineData(var2, this, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfPooled);
                    }
                });
            }
            if (this.lastCharging != this.machine.isCharging)
            {
                this.sendTfMachineData(var2, this, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeBoolean(machine.isCharging);
                    }
                });
            }
        }

        this.lastCookTime = this.machine.ovenCookTime;
        this.lastWholeCookTime = this.machine.wholeCookTime;
        this.lastTfPooled = this.machine.tfPooled;
        this.lastCharging = this.machine.isCharging;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.machine.ovenCookTime = par2;
        }
        if (par1 == 1)
        {
            this.machine.wholeCookTime = par2;
        }
    }


    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {

        if (id == 0)
        {
            this.machine.tfPooled = data.readDouble();
        }
        if (id == 1)
        {
            this.machine.isCharging = data.readBoolean();
        }
    }

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slotId, ItemStack itemStack)
    {
        if (FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null)
        {
            if (!this.mergeToSingleItemStack(itemStack, TileEntityTfOven.SLOT_ITEM_INPUT))
            {
                return TransferResult.MISMATCHED;
            }
            return TransferResult.MATCHED;
        }
        return TransferResult.SKIPPING;
    }
}
