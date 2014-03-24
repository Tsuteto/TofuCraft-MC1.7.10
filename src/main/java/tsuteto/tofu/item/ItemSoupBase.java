package tsuteto.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoupBase extends ItemTcFood
{

    public ItemSoupBase(int recoveryAmount, float saturation, boolean isWolfsFavorite)
    {
        super(recoveryAmount, saturation, isWolfsFavorite);
        this.setMaxStackSize(1);
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
        return new ItemStack(Items.bowl);
    }
}
