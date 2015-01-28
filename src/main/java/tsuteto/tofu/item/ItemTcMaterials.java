package tsuteto.tofu.item;

import com.google.common.collect.Lists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.iteminfo.TcItemSetInfo;
import tsuteto.tofu.item.iteminfo.TcItemType;

import java.util.List;

/**
 * Material items with no function
 */
public class ItemTcMaterials extends ItemSetBase<ItemTcMaterials.TcMaterial>
{
    public static List<TcMaterial> materialList = Lists.newArrayList();

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

    public static class TcMaterial extends TcItemSetInfo<TcMaterial>
    {
        public TcMaterial(int id, String name)
        {
            super(id, TcItemType.NORMAL, name);
            materialList.add(id, this);
        }

        public boolean isItemEqual(ItemStack itemStack)
        {
            if (itemStack != null)
            {
                return itemStack.getItem() == TcItems.materials && itemStack.getItemDamage() == this.id;
            }
            return false;
        }
    }

    public ItemTcMaterials()
    {
        super();
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public List<TcMaterial> getItemList()
    {
        return materialList;
    }
}
