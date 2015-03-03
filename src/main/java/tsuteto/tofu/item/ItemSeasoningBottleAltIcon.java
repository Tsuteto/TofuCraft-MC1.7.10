package tsuteto.tofu.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.item.iteminfo.TcItemInfoBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

public class ItemSeasoningBottleAltIcon extends ItemWithState
{
    protected final TcItemInfoBase itemInfo = new TcItemInfoBase(TcItemType.NORMAL);

    public ItemSeasoningBottleAltIcon(int durability)
    {
        super();
        this.setMaxDamage(durability);
        itemInfo.setCraftingDurabilityTool();
        itemInfo.setContainerItem(new ItemStack(Items.glass_bottle));
        this.setMaxStackSize(1);
    }

    @Override
    public TcItemInfoBase getItemInfo(int itemDmg)
    {
        return itemInfo;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
    }
}
