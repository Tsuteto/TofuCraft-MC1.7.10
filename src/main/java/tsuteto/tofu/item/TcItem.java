package tsuteto.tofu.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import tsuteto.tofu.util.ItemUtils;

/**
 * Item Registry Class
 *
 * @author Tsuteto
 *
 */
public class TcItem extends Item
{

    public TcItem()
    {
    	super();
    }

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ItemUtils.getCreativeTabs(this);
	}

    public void breakItem(ItemStack itemstack, EntityLivingBase entityLiving)
    {
        entityLiving.renderBrokenItemStack(itemstack);

        if (entityLiving instanceof EntityPlayer)
        {
            ((EntityPlayer)entityLiving).addStat(StatList.objectBreakStats[Item.getIdFromItem(this)], 1);
        }

        --itemstack.stackSize;

        if (itemstack.stackSize < 0)
        {
            itemstack.stackSize = 0;
        }

        itemstack.setItemDamage(0);

        if (itemstack.stackSize == 0 && entityLiving instanceof EntityPlayer)
        {
            ((EntityPlayer)entityLiving).inventory.mainInventory[((EntityPlayer)entityLiving).inventory.currentItem] = null;
        }
    }
}
