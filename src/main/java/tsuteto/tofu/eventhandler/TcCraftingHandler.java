package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.item.TcItems;

public class TcCraftingHandler
{
    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event)
    {
        EntityPlayer player = event.player;
        ItemStack itemStack = event.crafting;
        IInventory craftMatrix = event.craftMatrix;

        boolean isFiltering = false;

        // Crafting mode
        for (int var2 = 0; var2 < craftMatrix.getSizeInventory(); ++var2)
        {
            ItemStack var3 = craftMatrix.getStackInSlot(var2);
            if (var3 != null)
            {
                if (var3.getItem() == TcItems.filterCloth)
                {
                    isFiltering = true;
                }
            }
        }
        
        for (int var2 = 0; var2 < craftMatrix.getSizeInventory(); ++var2)
        {
            ItemStack var3 = craftMatrix.getStackInSlot(var2);
            if (var3 != null)
            {
                if (isFiltering)
                {
                    if (var3.getItem() == TcItems.soybeans)
                    {
                        if (var3.stackSize == 1)
                        {
                            craftMatrix.setInventorySlotContents(var2, new ItemStack(TcItems.okara, 2));
                        }
                        else
                        {
                            player.inventory.addItemStackToInventory(new ItemStack(TcItems.okara, 1));
                        }
                        TcAchievementMgr.achieve(player, Key.okara);
                    }
                }
            }
        }

        TcAchievementMgr.achieveCraftingItem(itemStack, player);
    }

    @SubscribeEvent
    public void onSmelting(PlayerEvent.ItemSmeltedEvent event)
    {
        EntityPlayer player = event.player;
        ItemStack item = event.smelting;
        TcAchievementMgr.achieveSmeltingItem(item, player);
    }

}
