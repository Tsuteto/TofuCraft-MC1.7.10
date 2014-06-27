package tsuteto.tofu.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketBugle;

public class ItemTofuBugle extends TcItem
{

    public ItemTofuBugle()
    {
        super();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            PacketDispatcher.packet(
                    new PacketBugle((float) player.posX, (float) player.posY, (float) player.posZ, player.getEntityId()))
                    .sendToAllAround(player.posX, player.posY, player.posZ, 64, player.dimension);
        }
        
        player.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
}
