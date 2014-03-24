package tsuteto.tofu.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemColoredBottle extends TcItem
{
    private int liquidColor;
    private IIcon iconContents;

    public ItemColoredBottle(int color)
    {
        this();
        this.liquidColor = color;
    }

    public ItemColoredBottle()
    {
        super();
        this.setNoRepair();
    }


    /**
     * Gets an icon index based on an item's damage value and the given render pass
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 0 ? this.iconContents : super.getIconFromDamageForRenderPass(par1, par2);
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
        return this.liquidColor;
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
        this.itemIcon = par1IconRegister.registerIcon("potion_bottle_drinkable");
        this.iconContents = par1IconRegister.registerIcon("potion_overlay");
    }
}
