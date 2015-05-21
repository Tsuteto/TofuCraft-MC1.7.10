package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tsuteto.tofu.item.iteminfo.TcItemInfoBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

abstract public class ItemWithState<T extends TcItemInfoBase> extends TcItem
{
    private IIcon iconContent;

    public ItemWithState()
    {
        super();
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        TcItemInfoBase info = this.getItemInfo(stack.getItemDamage());
        return !((info.isNonDurabilityTool || info.isCraftingDurabilityTool) && !this.isRepairable());
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        TcItemInfoBase info = getItemInfo(stack.getItemDamage());
        return info.itemUseDuration;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        int dmg = stack.getItemDamage();
        TcItemInfoBase info = getItemInfo(dmg);
        if (info.isNonDurabilityTool)
        {
            return stack;
        }
        else if (info.isCraftingDurabilityTool && dmg < this.getMaxDamage(stack))
        {
            stack.setItemDamage(dmg + 1);
            return stack;
        }
        else
        {
            return info.getContainerItem();
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        int dmg = stack.getItemDamage();
        TcItemInfoBase info = getItemInfo(dmg);
        if (info.isNonDurabilityTool || info.isCraftingDurabilityTool && dmg < this.getMaxDamage(stack))
        {
            return true;
        }
        else
        {
            return info.hasContainerItem();
        }
    }

    @Override
    public Item getContainerItem()
    {
        TcItemInfoBase info = getItemInfo();
        if (info != null)
        {
            if (info.isNonDurabilityTool)
            {
                return this;
            }
            else
            {
                return info.getContainerItem().getItem();
            }
        }
        else
        {
            return super.getContainerItem();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        TcItemInfoBase info = this.getItemInfo(par1ItemStack.getItemDamage());
        if (info.type == TcItemType.BOTTLE && pass == 0 || info.type != TcItemType.BOTTLE)
        {
            return info.hasEnchantEffect;
        }
        else
        {
            return false;
        }
    }

    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        TcItemInfoBase info = this.getItemInfo(par1);
        if (info.type == TcItemType.BOTTLE)
        {
            return par2 == 0 ? iconContent : super.getIconFromDamageForRenderPass(par1, par2);
        }
        else
        {
            return super.getIconFromDamageForRenderPass(par1, par2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return par2 == 0 ? this.getColorFromDamage(par1ItemStack.getItemDamage()) : 0xffffff;
    }

    @SideOnly(Side.CLIENT)
    protected int getColorFromDamage(int itemDamage)
    {
        TcItemInfoBase info = this.getItemInfo(itemDamage);
        if (info.type == TcItemType.BOTTLE)
        {
            return info.liquidColor;
        }
        else
        {
            return 0xffffff;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        // For bottles. Others' icon is registered in each classes
        this.iconContent = par1IconRegister.registerIcon("potion_overlay");
    }

    abstract public T getItemInfo(int itemDmg);

    public T getItemInfo()
    {
        return null;
    }
}
