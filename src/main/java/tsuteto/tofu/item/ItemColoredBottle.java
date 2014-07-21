package tsuteto.tofu.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import tsuteto.tofu.item.iteminfo.TcItemInfoBase;
import tsuteto.tofu.item.iteminfo.TcItemType;

public class ItemColoredBottle extends ItemColoredBottleImpl<TcItemInfoBase>
{
    private final TcItemInfoBase itemInfo = new TcItemInfoBase(TcItemType.BOTTLE);

    public ItemColoredBottle(int color)
    {
        this();
        this.itemInfo.setLiquidColor(color);
    }

    public ItemColoredBottle()
    {
        super();
        this.setNoRepair();
        // Don't set any container items!
    }

    @Override
    public TcItemInfoBase getItemInfo(int itemDmg)
    {
        return itemInfo;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.itemIcon = par1IconRegister.registerIcon("potion_bottle_drinkable");
    }

}
