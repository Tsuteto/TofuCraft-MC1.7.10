package tsuteto.tofu.item;

import java.util.List;

import tsuteto.tofu.item.ItemZundaBow.EnumArrowType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGelatin extends TcItem
{
    public enum Materials
    {
        gelatin,
        gelatinRaw,
    }
    
    private IIcon[] icons;

    public ItemGelatin()
    {
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int dmg = par1ItemStack.getItemDamage();
        if (dmg < Materials.values().length)
        {
            return "item.tofucraft:" + Materials.values()[dmg];
        }
        else
        {
            return super.getUnlocalizedName();
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        if (par1 < Materials.values().length)
        {
            return icons[par1];
        }
        else
        {
            return icons[0];
        }
    }

    @SideOnly(Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < Materials.values().length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.icons = new IIcon[Materials.values().length];

        for (int i = 0; i < Materials.values().length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:" + Materials.values()[i].name());
        }
    }
}
