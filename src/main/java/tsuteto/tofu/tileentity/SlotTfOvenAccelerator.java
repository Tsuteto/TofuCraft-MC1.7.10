package tsuteto.tofu.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;

public class SlotTfOvenAccelerator extends SlotTfMachine
{
    EntityPlayer thePlayer;

    public SlotTfOvenAccelerator(EntityPlayer player, IInventory par1IInventory, int par2, int par3, int par4, TfMachineGuiParts guiPart)
    {
        super(par1IInventory, par2, par3, par4, guiPart);
        this.thePlayer = player;
    }

    @Override
    public void onSlotChanged()
    {
        super.onSlotChanged();

        ItemStack stack = this.getStack();
        if (stack != null
                && stack.getItem() == TcItems.materials && stack.getItemDamage() == ItemTcMaterials.activatedHellTofu.id
                && stack.stackSize == 64)
        {
            TcAchievementMgr.achieve(thePlayer, TcAchievementMgr.Key.ultimateOven);
        }
    }
}
