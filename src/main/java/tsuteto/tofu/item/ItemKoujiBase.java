package tsuteto.tofu.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.util.ModLog;

public class ItemKoujiBase extends TcItem
{

    public ItemKoujiBase()
    {
        super();
        this.setMaxStackSize(1);
    }

    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int inventorySlot, boolean isCurrentItem)
    {
        if ((par2World.getWorldTime() & 15) != 0) return;

        if (par3Entity instanceof EntityLivingBase && !isCurrentItem)
        {
            int damage = par1ItemStack.getItemDamage();
            if (damage < 60)
            {
                if (itemRand.nextInt(10) == 0)
                {
                    par1ItemStack.setItemDamage(damage + 1);
                    ModLog.debug("koujibase proceeded to step %d", damage + 1);
                }
                //System.out.println(par1ItemStack.getItemDamage());
            }
            else
            {
                par1ItemStack.func_150996_a(TcItems.kouji);
                par1ItemStack.setItemDamage(0);

                if (par3Entity instanceof EntityPlayer)
                {
                    TcAchievementMgr.achieve((EntityPlayer)par3Entity, Key.kouji);
                }
            }
        }
    }

}
