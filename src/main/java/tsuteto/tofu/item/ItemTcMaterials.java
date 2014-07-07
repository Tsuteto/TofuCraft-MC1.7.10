package tsuteto.tofu.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Material items with no function
 */
public class ItemTcMaterials extends TcItem
{
    public static TcMaterial[] materialList = new TcMaterial[14];

    public static final TcMaterial tofuGem = new TcMaterial(0, "tofuGem");
    public static final TcMaterial tofuDiamondNugget = new TcMaterial(1, "tofuDiamondNugget");
    public static final TcMaterial tofuHamburgRaw = new TcMaterial(2, "tofuHamburgRaw");
    public static final TcMaterial tfCapacitor = new TcMaterial(3, "tfCapacitor");
    public static final TcMaterial tfCircuit = new TcMaterial(4, "tfCircuit");
    public static final TcMaterial tfCoil = new TcMaterial(5, "tfCoil");
    public static final TcMaterial tfOscillator = new TcMaterial(6, "tfOscillator");
    public static final TcMaterial advTofuGem = new TcMaterial(7, "advTofuGem");
    public static final TcMaterial activatedTofuGem = new TcMaterial(8, "activatedTofuGem");
    public static final TcMaterial activatedHellTofu = new TcMaterial(9, "activatedHellTofu");
    public static final TcMaterial mineralSoymilk = new TcMaterial(10, "mineralSoymilk").asGlassBottle(0xeaad72);
    public static final TcMaterial tofuSomen = new TcMaterial(11, "tofuSomen");
    public static final TcMaterial glassBowl = new TcMaterial(12, "glassBowl");
    public static final TcMaterial rollingPin = new TcMaterial(13, "rollingPin").setNonDurabilityTool();

    public static IIcon bottleIconContent;

    public boolean isNonDurabilityTool(ItemStack itemStack)
    {
        TcMaterial m = getMaterial(itemStack.getItemDamage());
        return m.isNonDurabilityTool;
    }

    public static class TcMaterial
    {
        public final int id;
        public final String name;
        public ItemStack container = null;
        public boolean isGlassBottle = false;
        public int liquidColor;
        public boolean hasEnchantEffect = false;
        public boolean isNonDurabilityTool = false;

        public TcMaterial(int id, String name)
        {
            this.id = id;
            this.name = name;
            ItemTcMaterials.materialList[id] = this;
        }

        public TcMaterial setContainerItem(ItemStack stack)
        {
            this.container = stack;
            return this;
        }

        public ItemStack getContainerItem()
        {
            if (container != null)
            {
                return container.copy();
            }
            else
            {
                return null;
            }
        }

        public boolean hasContainerItem()
        {
            return container != null;
        }

        public TcMaterial asGlassBottle(int liquidColor)
        {
            this.liquidColor = liquidColor;
            setContainerItem(new ItemStack(Items.glass_bottle));
            this.isGlassBottle = true;
            return this;
        }

        public TcMaterial setEnchantEffect()
        {
            this.hasEnchantEffect = true;
            return this;
        }

        public TcMaterial setNonDurabilityTool()
        {
            this.isNonDurabilityTool = true;
            return this;
        }

        public boolean isItemEqual(ItemStack itemStack)
        {
            if (itemStack != null)
            {
                return itemStack.getItem() == TcItems.materials && itemStack.getItemDamage() == id;
            }
            return false;
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
            TcMaterial m = getMaterial(i);
            if (m.isGlassBottle)
            {
                this.icons[i] = par1IconRegister.registerIcon("potion_bottle_drinkable");
            }
            else
            {
                this.icons[i] = par1IconRegister.registerIcon("tofucraft:" + materialList[i].name);
            }
        }

        this.bottleIconContent = par1IconRegister.registerIcon("potion_overlay");
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        TcMaterial m = getMaterial(itemStack.getItemDamage());
        return m.getContainerItem();
    }

    @Override
    public boolean hasContainerItem(ItemStack itemStack)
    {
        TcMaterial m = getMaterial(itemStack.getItemDamage());
        return m.hasContainerItem();
    }

    public TcMaterial getMaterial(int dmg)
    {
        return materialList[dmg < materialList.length ? dmg : 0];
    }

    /* === */

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        TcMaterial m = getMaterial(par1ItemStack.getItemDamage());
        if (m.isGlassBottle && pass == 0 || !m.isGlassBottle)
        {
            return m.hasEnchantEffect;
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
        TcMaterial m = getMaterial(par1);
        if (m.isGlassBottle)
        {
            return par2 == 0 ? this.bottleIconContent : super.getIconFromDamageForRenderPass(par1, par2);
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
        TcMaterial m = getMaterial(itemDamage);
        if (m.isGlassBottle)
        {
            return m.liquidColor;
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

}
