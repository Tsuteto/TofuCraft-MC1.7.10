package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tsuteto.tofu.item.iteminfo.TcItemInfoBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

abstract public class ItemColoredBottleImpl<T extends TcItemInfoBase> extends TcItem
{
    private IIcon iconContent;

    public ItemColoredBottleImpl()
    {
        super();
        // Don't set any container items!
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
        this.iconContent = par1IconRegister.registerIcon("potion_overlay");
    }

    abstract public T getItemInfo(int itemDmg);
}
