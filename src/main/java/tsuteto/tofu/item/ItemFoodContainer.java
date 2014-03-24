package tsuteto.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodContainer extends ItemTcFood
{
    private ItemStack itemContained = null;

    public ItemFoodContainer(int par2, float par3, boolean par4, ItemStack itemContained)
    {
        super(par2, par3, par4);
        this.itemContained = itemContained;
    }
    
    public ItemFoodContainer(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
    }
    
    public ItemFoodContainer setContainedItem(ItemStack itemstack)
    {
        this.itemContained = itemstack;
        return this;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        
        if (itemContained != null)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return itemContained.copy();
            }
    
            par3EntityPlayer.inventory.addItemStackToInventory(itemContained.copy());
        }
        
        return par1ItemStack;
    }    
}
