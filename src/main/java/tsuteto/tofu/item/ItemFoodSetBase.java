package tsuteto.tofu.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tsuteto.tofu.item.iteminfo.PotionEffectEntry;
import tsuteto.tofu.item.iteminfo.TcFoodBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

public abstract class ItemFoodSetBase<T extends TcFoodBase> extends ItemSetBase<T>
{
    public ItemFoodSetBase()
    {
        super();
        this.setCreativeTab(CreativeTabs.tabFood);
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        TcFoodBase<?> food = this.getItemInfo(par1ItemStack.getItemDamage());
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(food.healAmount, food.saturationModifier);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);

        if (!par2World.isRemote)
        {
            this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        }
        
        if (food.hasContainerItem())
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return food.getContainerItem();
            }
    
            if (!par3EntityPlayer.inventory.addItemStackToInventory(food.getContainerItem()))
            {
                par3EntityPlayer.dropPlayerItemWithRandomChoice(food.getContainerItem(), false);
            }
        }

        return par1ItemStack;
    }

    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        // Apply potion effects
        TcFoodBase<?> food = this.getItemInfo(par1ItemStack.getItemDamage());
        if (food.potionEffects != null)
        {
            if (!food.randomPotionEffect)
            {
                // Fixed
                for (PotionEffectEntry entry : food.potionEffects)
                {
                    if (par2World.rand.nextFloat() < entry.probability)
                    {
                        par3EntityPlayer.addPotionEffect(new PotionEffect(entry.potion.getId(), entry.duration * 20, entry.amplifier));
                    }
                }
            }
            else if (par2World.rand.nextFloat() < food.randomPotionEffectProb)
            {
                // Random
                double total = 0.0D;
                for (PotionEffectEntry entry : food.potionEffects)
                {
                    total += entry.probability;
                }

                for (PotionEffectEntry entry : food.potionEffects)
                {
                    double rand = par2World.rand.nextDouble() * total;
                    if (rand < entry.probability)
                    {
                        par3EntityPlayer.addPotionEffect(new PotionEffect(entry.potion.getId(), entry.duration * 20, entry.amplifier));
                        break;
                    }
                    total -= entry.probability;
                }
            }
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        TcFoodBase<?> food = this.getItemInfo(par1ItemStack.getItemDamage());
        return food.type == TcItemType.BOTTLE ? EnumAction.drink : EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        TcFoodBase<?> food = this.getItemInfo(par1ItemStack.getItemDamage());
        if (par3EntityPlayer.canEat(food.alwaysEdible))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

}
