package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidStack;
import tsuteto.tofu.init.TcFluids;

public class ContainerSaltFurnace extends Container
{
    private TileEntitySaltFurnace furnace;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;
    private int lastCauldronStatus = 0;
    private int lastNigariAmount = 0;

    public ContainerSaltFurnace(InventoryPlayer invPlayer, TileEntitySaltFurnace saltFurnace)
    {
        this.furnace = saltFurnace;
        this.addSlotToContainer(new Slot(saltFurnace, 0, 23, 53));
        this.addSlotToContainer(new SlotSaltFurnace(invPlayer.player, saltFurnace, 1, 82, 35));
        this.addSlotToContainer(new Slot(saltFurnace, 2, 134, 17));
        this.addSlotToContainer(new SlotSaltFurnace(invPlayer.player, saltFurnace, 3, 134, 52));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.furnace.nigariTank.getFluidAmount());

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

            if (this.lastCookTime != this.furnace.furnaceCookTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.furnace.furnaceCookTime);
            }

            if (this.lastBurnTime != this.furnace.furnaceBurnTime)
            {
                var2.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.furnace.currentItemBurnTime)
            {
                var2.sendProgressBarUpdate(this, 2, this.furnace.currentItemBurnTime);
            }

            if (this.lastNigariAmount != this.furnace.nigariTank.getFluidAmount())
            {
                var2.sendProgressBarUpdate(this, 3, this.furnace.nigariTank.getFluidAmount());
            }
        }

        this.lastCookTime = this.furnace.furnaceCookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentItemBurnTime;
        this.lastNigariAmount = this.furnace.nigariTank.getFluidAmount();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.furnace.furnaceCookTime = par2;
        }

        if (par1 == 1)
        {
            this.furnace.furnaceBurnTime = par2;
        }

        if (par1 == 2)
        {
            this.furnace.currentItemBurnTime = par2;
        }

        if (par1 == 3)
        {
            this.furnace.nigariTank.setFluid(new FluidStack(TcFluids.NIGARI, par2));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.furnace.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        // 0-3: Salt furnace inventory
        // 4-30: Player inventory
        // 31-39: Hot bar in the player inventory

        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(par2);

        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (par2 == 1 || par2 == 3)
            {
                if (!this.mergeItemStack(var5, 4, 40, true))
                {
                    return null;
                }

                var4.onSlotChange(var5, var3);
            }
            else if (par2 >= 4)
            {
                if (TileEntityFurnace.isItemFuel(var5))
                {
                    if (!this.mergeItemStack(var5, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (var5.getItem() == Items.glass_bottle)
                {
                    if (!this.mergeItemStack(var5, 2, 3, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 4 && par2 < 31)
                {
                    if (!this.mergeItemStack(var5, 31, 40, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 31 && par2 < 40 && !this.mergeItemStack(var5, 4, 31, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 4, 40, false))
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
