package tsuteto.tofu.api.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;

public class SlotTfMachineCrafting extends SlotTfMachineOutput
{
    private EntityPlayer thePlayer;
    private int amountCrafted;

    public SlotTfMachineCrafting(EntityPlayer player, IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
        this.thePlayer = player;
    }

    @Override
    public ItemStack decrStackSize(int par1)
    {
        if (this.getHasStack())
        {
            this.amountCrafted += Math.min(par1, this.getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.amountCrafted += par2;
        this.onCrafting(par1ItemStack);
    }

    @Override
    protected void onCrafting(ItemStack par1ItemStack)
    {
        super.onCrafting(par1ItemStack);
        par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        TcAchievementMgr.achieveCraftingItem(par1ItemStack, thePlayer);
    }

}
