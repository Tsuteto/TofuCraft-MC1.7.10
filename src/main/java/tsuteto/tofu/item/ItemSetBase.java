package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import tsuteto.tofu.item.iteminfo.TcItemSetInfo;
import tsuteto.tofu.item.iteminfo.TcItemInfoBase;
import tsuteto.tofu.item.iteminfo.TcItemType;
import tsuteto.tofu.util.ModLog;

import java.util.List;

abstract public class ItemSetBase<T extends TcItemSetInfo> extends ItemColoredBottleImpl<T>
{
    protected IIcon icons[];

    public ItemSetBase()
    {
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    abstract public T[] getItemList();

    public T getItemInfo(int dmg)
    {
        T[] list = this.getItemList();
        return list[dmg >= 0 && dmg < list.length ? dmg : 0];
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack)
    {
        TcItemInfoBase info = getItemInfo(stack.getItemDamage());
        return info.getNewContainer();
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        TcItemInfoBase info = getItemInfo(stack.getItemDamage());
        return info.hasContainerItem();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        TcItemInfoBase info = getItemInfo(stack.getItemDamage());
        return info.itemUseDuration;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int dmg = par1ItemStack.getItemDamage();
        TcItemInfoBase[] list = this.getItemList();
        return "item." + this.getFullName(dmg < list.length ? dmg : 0);
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        TcItemInfoBase[] list = getItemList();
        if (par1 >= 0 && par1 < list.length)
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
        TcItemInfoBase[] list = getItemList();
        for (int i = 0; i < list.length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);

        TcItemInfoBase[] list = getItemList();

        this.icons = new IIcon[list.length];

        for (int i = 0; i < list.length; ++i)
        {
            if (list[i].type == TcItemType.BOTTLE)
            {
                this.icons[i] = par1IconRegister.registerIcon("potion_bottle_drinkable");
            }
            else
            {
                this.icons[i] = par1IconRegister.registerIcon(this.getFullName(i));
            }
        }
    }

    private String getFullName(int dmg)
    {
        return "tofucraft:" + (this.getItemSetName() != null ? this.getItemSetName() + "." : "") + this.getItemInfo(dmg).name;
    }

    public String getItemSetName()
    {
        return null;
    }

    public ItemStack getItemStack(T info)
    {
        return this.getItemStack(info, 1);
    }

    public ItemStack getItemStack(T info, int qty)
    {
        return new ItemStack(this, qty, info.id);
    }
}
