package tsuteto.tofu.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemTcMaterials extends TcItem
{
    public static TcMaterial[] materialList = new TcMaterial[4];

    public static final TcMaterial tofuGem = new TcMaterial(0, "tofuGem");
    public static final TcMaterial tofuDiamondNugget = new TcMaterial(1, "tofuDiamondNugget");
    public static final TcMaterial tofuHamburgRaw = new TcMaterial(2, "tofuHamburgRaw");
    public static final TcMaterial tfCapacitor = new TcMaterial(3, "tfCapacitor");

    public static class TcMaterial
    {
        public final int id;
        public final String name;

        public TcMaterial(int id, String name)
        {
            this.id = id;
            this.name = name;
            ItemTcMaterials.materialList[id] = this;
        }
    }

    private IIcon[] icons;

    public ItemTcMaterials()
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
        if (dmg < materialList.length)
        {
            return "item.tofucraft:" + materialList[dmg].name;
        }
        else
        {
            return super.getUnlocalizedName();
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        if (par1 < materialList.length)
        {
            return icons[par1];
        }
        else
        {
            return icons[0];
        }
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < materialList.length; ++i)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.icons = new IIcon[materialList.length];

        for (int i = 0; i < materialList.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:" + materialList[i].name);
        }
    }
}
