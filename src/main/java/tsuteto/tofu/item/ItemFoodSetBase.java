package tsuteto.tofu.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemFoodSetBase extends TcItem
{
    private IIcon[] icons;
    
    public ItemFoodSetBase()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabFood);
    }
    
    abstract public TcFoodBase getFood(int dmg);
    abstract public TcFoodBase[] getFoodList();

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        TcFoodBase food = getFood(par1ItemStack.getItemDamage());
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(food.healAmount, food.saturationModifier);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        
        if (food.container != null)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return food.getNewContainer();
            }
    
            if (!par3EntityPlayer.inventory.addItemStackToInventory(food.getNewContainer()))
            {
                par3EntityPlayer.dropPlayerItemWithRandomChoice(food.getNewContainer(), false);
            }
        }

        return par1ItemStack;
    }

    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        TcFoodBase food = getFood(par1ItemStack.getItemDamage());
        
        if (!par2World.isRemote && food.potionId > 0 && par2World.rand.nextFloat() < food.potionEffectProbability)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(food.potionId, food.potionDuration * 20, food.potionAmplifier));
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        TcFoodBase food = getFood(stack.getItemDamage());
        return food.getNewContainer();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        TcFoodBase food = getFood(stack.getItemDamage());
        return food.hasContainerItem();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        TcFoodBase food = getFood(par1ItemStack.getItemDamage());
        return food.itemUseDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        TcFoodBase food = getFood(par1ItemStack.getItemDamage());
        if (par3EntityPlayer.canEat(food.alwaysEdible))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int dmg = par1ItemStack.getItemDamage();
        TcFoodBase[] list = getFoodList();
        return "item.tofucraft:" + list[dmg < list.length ? dmg : 0].name;
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        TcFoodBase[] list = getFoodList();
        if (par1 < list.length)
        {
            return icons[par1];
        }
        else
        {
            return icons[0];
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        TcFoodBase[] list = getFoodList();
        for (int i = 0; i < list.length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        TcFoodBase[] list = getFoodList();
        
        this.icons = new IIcon[list.length];

        for (int i = 0; i < list.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:" + list[i].name);
        }
    }
}
