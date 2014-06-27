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
import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.TfMachineGuiParts;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerTfReformer extends ContainerTfMachine<TileEntityTfReformer>
{
    private float lastWholeTfOutput;
    private float lastTfOutput;
    private float lastTfCapacity;
    private float lastTfAmount;

    public ContainerTfReformer(InventoryPlayer invPlayer, TileEntityTfReformer machine)
    {
        super(machine);
        this.addSlotToContainer(new SlotTfMachine(machine, TileEntityTfReformer.SLOT_INPUT_ITEM, 58, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(machine, TileEntityTfReformer.SLOT_OUTPUT_ITEM, 60, 51, TfMachineGuiParts.itemSlot));

        super.preparePlayerInventory(invPlayer, playerInventoryPosX, playerInventoryPosY);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);

        this.sendTfMachineData(par1ICrafting, this, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.wholeTfOutput);
            }
        });

        this.sendTfMachineData(par1ICrafting, this, 1, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfOutput);
            }
        });

        this.sendTfMachineData(par1ICrafting, this, 2, new PacketTfMachineData.DataHandler() {
            
            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeFloat(machine.tfCapacity);
            }
        });
    
        this.sendTfMachineData(par1ICrafting, this, 3, new PacketTfMachineData.DataHandler() {
            
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

            if (this.lastWholeTfOutput != this.machine.wholeTfOutput)
            {
                this.sendTfMachineData(var2, this, 0, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.wholeTfOutput);
                    }
                });
            }

            if (this.lastTfOutput != this.machine.tfOutput)
            {
                this.sendTfMachineData(var2, this, 1, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfOutput);
                    }
                });
            }

            if (this.lastTfCapacity != this.machine.tfCapacity)
            {
                this.sendTfMachineData(var2, this, 2, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfCapacity);
                    }
                });
            }
            
            if (this.lastTfAmount != this.machine.tfAmount)
            {
                this.sendTfMachineData(var2, this, 3, new PacketTfMachineData.DataHandler() {

                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeFloat(machine.tfAmount);
                    }
                });
            }
        }

        this.lastWholeTfOutput = this.machine.wholeTfOutput;
        this.lastTfOutput = this.machine.tfOutput;
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
            this.machine.wholeTfOutput = data.readFloat();
        }

        if (id == 1)
        {
            this.machine.tfOutput = data.readFloat();
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
                return TransferResult.UNMATCHED;
            }
            return TransferResult.MATCHED;
        }
        return TransferResult.SKIPPED;
    }

}
