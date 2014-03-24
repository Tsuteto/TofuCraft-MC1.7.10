package tsuteto.tofu.eventhandler;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.ICraftingDurability;
import tsuteto.tofu.item.INonDurabilityTool;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.item.TcItems;

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
        ItemStack item = event.crafting;
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
        
        if (itemsMadeOfBottleContent.contains(item))
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
                if (durabilityItemRegistry.contains(var3))
                {
                    ItemStack var4 = new ItemStack(var3.getItem(), var3.stackSize + 1, var3.getItemDamage() + 1);
                    if(var3.getItem().isDamageable() && var3.getItemDamage() >= var3.getMaxDamage())
                    {
                        Item emptyItem = ((ICraftingDurability)var3.getItem()).getEmptyItem();
                        if (var3.stackSize == 1)
                        {
                            var4 = new ItemStack(emptyItem, 2);
                        }
                        else
                        {
                            player.inventory.addItemStackToInventory(new ItemStack(emptyItem, 1));
                        }
                    }
                    craftMatrix.setInventorySlotContents(var2, var4);
                }

                if (var3.getItem() instanceof INonDurabilityTool)
                {
                    ItemStack var4 = new ItemStack(var3.getItem(), var3.stackSize + 1);
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

        TcAchievementMgr.achieveCraftingItem(item, player);
    }

    @SubscribeEvent
    public void onSmelting(PlayerEvent.ItemSmeltedEvent event)
    {
        EntityPlayer player = event.player;
        ItemStack item = event.smelting;
        TcAchievementMgr.achieveSmeltingItem(item, player);
    }

}
