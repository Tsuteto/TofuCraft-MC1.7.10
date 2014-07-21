package tsuteto.tofu.eventhandler;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.item.*;

public class TcCraftingHandler
{
    private static ArrayList<Item> durabilityItemRegistry = new ArrayList<Item>();
    private static ArrayList<Item> itemsMadeOfBottleContent = new ArrayList<Item>();

    public static void registerDurabilityItem(Item item)
    {
        if (!(item instanceof ICraftingDurability))
        {
            throw new RuntimeException("Not ICraftingDurability implemented!");
        }
        durabilityItemRegistry.add(item);
    }

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
        
        if (itemsMadeOfBottleContent.contains(itemStack.getItem()))
        {
            for (int var2 = 0; var2 < craftMatrix.getSizeInventory(); ++var2)
            {
                ItemStack var3 = craftMatrix.getStackInSlot(var2);
                if (var3.getItem() == Items.potionitem)
                {
                    if (var3.stackSize == 1)
                    {
                        craftMatrix.setInventorySlotContents(var2, new ItemStack(Items.glass_bottle, 2));
                    }
                    else
                    {
                        player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle, 1));
                    }
                }
            }
        }

        for (int var2 = 0; var2 < craftMatrix.getSizeInventory(); ++var2)
        {
            ItemStack var3 = craftMatrix.getStackInSlot(var2);
            if (var3 != null)
            {
                Item item = var3.getItem();
                if (durabilityItemRegistry.contains(var3.getItem()))
                {
                    ItemStack var4 = new ItemStack(item, var3.stackSize + 1, var3.getItemDamage() + 1);
                    if(item.isDamageable() && var3.getItemDamage() >= var3.getMaxDamage())
                    {
                        ItemStack emptyItem = ((ICraftingDurability)item).getEmptyItem();
                        if (var3.stackSize == 1)
                        {
                            var4 = emptyItem;
                            var4.stackSize = 2;
                        }
                        else
                        {
                            player.inventory.addItemStackToInventory(emptyItem);
                        }
                    }
                    craftMatrix.setInventorySlotContents(var2, var4);
                }

                if (item instanceof INonDurabilityTool
                        || item instanceof ItemSetBase && ((ItemSetBase)item).getItemInfo(var3.getItemDamage()).isNonDurabilityTool)
                {
                    ItemStack var4 = new ItemStack(item, var3.stackSize + 1, var3.getItemDamage());
                    craftMatrix.setInventorySlotContents(var2, var4);
                }

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
