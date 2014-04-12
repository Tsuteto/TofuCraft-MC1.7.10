package tsuteto.tofu.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.TfMaterialRegistry;
import tsuteto.tofu.achievement.TcAchievementMgr;

public class SlotTfStorage extends Slot
{
    private EntityPlayer thePlayer;

    public SlotTfStorage(EntityPlayer thePlayer, IInventory par1IInventory, int par2, int par3, int par4)
    {
        super(par1IInventory, par2, par3, par4);
        this.thePlayer = thePlayer;
    }

    public void putStack(ItemStack par1ItemStack)
    {
        super.putStack(par1ItemStack);

        if (TfMaterialRegistry.isTfMaterial(par1ItemStack))
        {
            TcAchievementMgr.achieve(thePlayer, TcAchievementMgr.Key.tofuForce);
        }
    }
}
