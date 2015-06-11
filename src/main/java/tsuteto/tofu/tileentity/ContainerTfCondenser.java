package tsuteto.tofu.tileentity;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.ObjectUtils;
import tsuteto.tofu.api.recipe.TfCondenserRecipeRegistry;
import tsuteto.tofu.api.tileentity.ContainerTfMachine;
import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.api.tileentity.SlotTfMachineCrafting;
import tsuteto.tofu.api.tileentity.SlotTfMachineOutput;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.network.packet.PacketTfMachineData;

public class ContainerTfCondenser extends ContainerTfMachine<TileEntityTfCondenser>
{
    private int lastWholeTimeOutput;
    private int lastProcessTimeOutput;
    private double lastTfPooled;
    private double lastTfNeeded;
    private int lastNigariAmount;
    private FluidStack lastIngredientTank;
    private ItemStack lastIngredientItem;

    public ContainerTfCondenser(InventoryPlayer invPlayer, TileEntityTfCondenser machine)
    {
        super(machine);

        this.addSlotToContainer(new SlotTfMachine(
                machine, TileEntityTfCondenser.SLOT_NIGARI_INPUT, 10, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(
                machine, TileEntityTfCondenser.SLOT_NIGARI_OUTPUT, 12, 47, TfMachineGuiParts.itemSlot));
        this.addSlotToContainer(new SlotTfMachine(
                machine, TileEntityTfCondenser.SLOT_SPECIAL_INPUT, 50, 20, TfMachineGuiParts.itemSlotL1));
        this.addSlotToContainer(new SlotTfMachineOutput(
                machine, TileEntityTfCondenser.SLOT_SPECIAL_OUTPUT, 52, 47, TfMachineGuiParts.itemSlot));
        this.addSlotToContainer(new SlotTfMachineCrafting(
                invPlayer.player, machine, TileEntityTfCondenser.SLOT_TOFU_OUTPUT, 142, 32, TfMachineGuiParts.itemSlotL2));

        super.preparePlayerInventory(invPlayer, playerInventoryPosX, playerInventoryPosY + 5);

        this.addContainerParam(machine.paramTfPowered);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.machine.wholeTimeOutput);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.machine.processTimeOutput);

        this.sendTfMachineData(par1ICrafting, 0, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfNeeded);
            }
        });

        this.sendTfMachineData(par1ICrafting, 1, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeDouble(machine.tfPooled);
            }
        });

        this.sendTfMachineData(par1ICrafting, 2, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeInt(machine.nigariTank.getFluidAmount());
            }
        });

        this.sendTfMachineData(par1ICrafting, 3, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                machine.ingredientTank.writeToNBT(nbt);
                ByteBufUtils.writeTag(buffer, nbt);
            }
        });

        this.sendTfMachineData(par1ICrafting, 4, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                buffer.writeInt(machine.ingredientTank.getFluidAmount());
            }
        });

        this.sendTfMachineData(par1ICrafting, 5, new PacketTfMachineData.DataHandler() {

            @Override
            public void addData(ByteBuf buffer)
            {
                if (machine.ingredientFluidItem != null)
                {
                    buffer.writeBoolean(true);
                    buffer.writeInt(Item.getIdFromItem(machine.ingredientFluidItem.getItem()));
                    buffer.writeInt(machine.ingredientFluidItem.getItemDamage());
                }
                else
                {
                    buffer.writeBoolean(false);
                }
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
            ICrafting var2 = (ICrafting) this.crafters.get(var1);

            if (this.lastWholeTimeOutput != this.machine.wholeTimeOutput)
            {
                var2.sendProgressBarUpdate(this, 0, this.machine.wholeTimeOutput);
            }

            if (this.lastProcessTimeOutput != this.machine.processTimeOutput)
            {
                var2.sendProgressBarUpdate(this, 1, this.machine.processTimeOutput);
            }

            if (this.lastTfNeeded != this.machine.tfNeeded)
            {
                this.sendTfMachineData(var2, 0, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfNeeded);
                    }
                });
            }

            if (this.lastTfPooled != this.machine.tfPooled)
            {
                this.sendTfMachineData(var2, 1, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeDouble(machine.tfPooled);
                    }
                });
            }
            if (this.lastNigariAmount != this.machine.nigariTank.getFluidAmount())
            {
                this.sendTfMachineData(var2, 2, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeInt(machine.nigariTank.getFluidAmount());
                    }
                });
            }
            if (!ObjectUtils.equals(this.lastIngredientTank, this.machine.ingredientTank.getFluid())
                    || this.lastIngredientTank != null
                        && !this.lastIngredientTank.isFluidEqual(this.machine.ingredientTank.getFluid()))
            {
                this.sendTfMachineData(var2, 3, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        NBTTagCompound nbt = new NBTTagCompound();
                        machine.ingredientTank.writeToNBT(nbt);
                        ByteBufUtils.writeTag(buffer, nbt);
                    }
                });
            }
            if (this.lastIngredientTank == null
                    || this.lastIngredientTank.amount != this.machine.ingredientTank.getFluidAmount())
            {
                this.sendTfMachineData(var2, 4, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        buffer.writeInt(machine.ingredientTank.getFluidAmount());
                    }
                });
            }
            if (this.lastIngredientItem != null && this.machine.ingredientFluidItem == null
                    || this.lastIngredientItem == null && this.machine.ingredientFluidItem != null
                    || this.lastIngredientItem != null
                        && !this.lastIngredientItem.isItemEqual(this.machine.ingredientFluidItem))
            {
                this.sendTfMachineData(var2, 5, new PacketTfMachineData.DataHandler()
                {
                    @Override
                    public void addData(ByteBuf buffer)
                    {
                        if (machine.ingredientFluidItem != null)
                        {
                            buffer.writeBoolean(true);
                            buffer.writeInt(Item.getIdFromItem(machine.ingredientFluidItem.getItem()));
                            buffer.writeInt(machine.ingredientFluidItem.getItemDamage());
                        }
                        else
                        {
                            buffer.writeBoolean(false);
                        }
                    }
                });
            }

        }

        this.lastWholeTimeOutput = this.machine.wholeTimeOutput;
        this.lastProcessTimeOutput = this.machine.processTimeOutput;
        this.lastTfNeeded = this.machine.tfNeeded;
        this.lastTfPooled = this.machine.tfPooled;
        this.lastNigariAmount = this.machine.nigariTank.getFluidAmount();
        if (this.machine.ingredientTank.getFluid() != null)
        {
            this.lastIngredientTank = this.machine.ingredientTank.getFluid().copy();
        }
        else
        {
            this.lastIngredientTank = null;
        }
        this.lastIngredientItem = ItemStack.copyItemStack(this.machine.ingredientFluidItem);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.machine.wholeTimeOutput = par2;
        }

        if (par1 == 1)
        {
            this.machine.processTimeOutput = par2;
        }
    }

    @Override
    public void updateTfMachineData(int id, ByteBuf data)
    {
        super.updateTfMachineData(id, data);

        if (id == 0)
        {
            this.machine.tfNeeded = data.readDouble();
        }

        if (id == 1)
        {
            this.machine.tfPooled = data.readDouble();
        }
        if (id == 2)
        {
            this.machine.nigariTank.getFluid().amount = data.readInt();
        }
        if (id == 3)
        {
            NBTTagCompound nbt = ByteBufUtils.readTag(data);
            this.machine.ingredientTank.readFromNBT(nbt);
        }
        if (id == 4)
        {
            if (this.machine.ingredientTank.getFluid() != null)
            {
                this.machine.ingredientTank.getFluid().amount = data.readInt();
            }
        }
        if (id == 5)
        {
            if (data.readBoolean())
            {
                this.machine.ingredientFluidItem = new ItemStack(Item.getItemById(data.readInt()), 1, data.readInt());
            }
            else
            {
                this.machine.ingredientFluidItem = null;
            }
        }
    }

    @Override
    public void onGuiControl(int eventId, ByteBuf buffer)
    {
        if (eventId == 0)
        {
            this.machine.ingredientTank.setFluid(null);
            this.machine.updateIngredientItem();
         }
    }

    public TransferResult transferStackInMachineSlot(EntityPlayer player, int slot, ItemStack itemStack)
    {
        FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(itemStack);
        if (fluidStack != null)
        {
            if (TfCondenserRecipeRegistry.ingredientToRecipeMap.containsKey(fluidStack.getFluid()))
            {
                if (!this.mergeToSingleItemStack(itemStack, TileEntityTfCondenser.SLOT_SPECIAL_INPUT))
                {
                    return TransferResult.MISMATCHED;
                }
                return TransferResult.MATCHED;
            }
        }
        return TransferResult.SKIPPING;
    }
}
