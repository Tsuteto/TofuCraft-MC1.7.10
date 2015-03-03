package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerTfStorage extends ContainerTfMachine<TileEntityTfStorage>
{
    private float lastWholeTfInput;
    private float lastTfInput;
    private float lastTfCapacity;
    private float lastTfAmount;

    public ContainerTfStorage(InventoryPlayer invPlayer, TileEntityTfStorage machine)
    {
        super(machine);
        this.addSlotToContainer(new SlotTfStorage(invPlayer.player, machine, TileEntityTfStorage.SLOT_INPUT_ITEM, 43, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfStorage.SLOT_INPUT_CONTAINER_ITEM, 45, 51, TfMachineGuiParts.itemSlot));

        super.preparePlayerInventory(invPlayer, playerInventoryPosX, playerInventoryPosY);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);

        this.sendTfMachineData(par1ICrafting, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.wholeTfInput);
            }
        });

        this.sendTfMachineData(par1ICrafting, 1, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfInput);
            }
        });

        this.sendTfMachineData(par1ICrafting, 2, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfCapacity);
            }
        });
    
        this.sendTfMachineData(par1ICrafting, 3, new PacketTfMachineData.DataHandler() {
            
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

            if (this.lastWholeTfInput != this.machine.wholeTfInput)
            {
                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.wholeTfInput);
                    }
                });
            }

            if (this.lastTfInput != this.machine.tfInput)
            {
                this.sendTfMachineData(var2, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfInput);
                    }
                });
            }

            if (this.lastTfCapacity != this.machine.tfCapacity)
            {
                this.sendTfMachineData(var2, 2, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfCapacity);
                    }
                });
            }
            
            if (this.lastTfAmount != this.machine.tfAmount)
            {
                this.sendTfMachineData(var2, 3, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfAmount);
                    }
                });
            }
        }

        this.lastWholeTfInput = this.machine.wholeTfInput;
        this.lastTfInput = this.machine.tfInput;
        this.lastTfCapacity = this.machine.tfCapacity;
        this.lastTfAmount = this.machine.tfAmount;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
    }

    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {

        if (id == 0)
        {
            this.machine.wholeTfInput = data.readFloat();
        }

        if (id == 1)
        {
            this.machine.tfInput = data.readFloat();
        }

        if (id == 2)
        {
            this.machine.tfCapacity = data.readFloat();
        }

        if (id == 3)
        {
            this.machine.tfAmount = data.readFloat();
        }
    }

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slot, ItemStack itemStack)
    {
        if (TfMaterialRegistry.isTfMaterial(itemStack))
        {
            if (!this.mergeToSingleItemStack(itemStack, TileEntityTfStorage.SLOT_INPUT_ITEM))
            {
                return TransferResult.MISMATCHED;
            }
            return TransferResult.MATCHED;
        }
        return TransferResult.SKIPPING;
    }

}
