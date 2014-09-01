package tsuteto.tofu.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tsuteto.tofu.item.iteminfo.TcFoodBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

import java.util.List;

public abstract class ItemBasicFoodSetBase<T extends TcFoodBase> extends ItemSetBase<T>
{
    public ItemBasicFoodSetBase()
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
        List<PotionEffect> effectList = this.getPotionEffect(par1ItemStack, par3EntityPlayer);
        for (PotionEffect effect : effectList)
        {
            par3EntityPlayer.addPotionEffect(effect);
        }

    }

    abstract public List<PotionEffect> getPotionEffect(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer);

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
