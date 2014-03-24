package tsuteto.tofu.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBottleSoyMilk extends ItemDrinkBottle
{
    public static final Flavor[] flavorList = new Flavor[16];

    public static Flavor flvPlain = new Flavor(0, null, 0xf5f7df, Items.melon);
    public static Flavor flvKinako = new Flavor(1, "kinako", 0xd6bc2d, Items.baked_potato);
    public static Flavor flvCocoa = new Flavor(2, "cocoa", 0x8d3d0d, Items.baked_potato);
    public static Flavor flvZunda = new Flavor(3, "zunda", 0x95e24a, Items.baked_potato);
    public static Flavor flvApple = new Flavor(4, "apple", 0xf2e087, Items.baked_potato);
    public static Flavor flvPumpkin = new Flavor(5, "pumpkin", 0xffb504, Items.baked_potato);
    public static Flavor flvRamune = new Flavor(6, "ramune", 0xa1c7ff, Items.baked_potato);
    public static Flavor flvStrawberry = new Flavor(7, "strawberry", 0xf4a4b7, Items.baked_potato);
    public static Flavor flvSakura = new Flavor(8, "sakura", 0xffd1d7, Items.baked_potato);

    public static class Flavor
    {
        public final int id;
        public final String name;
        public final int color;
        public final ItemFood food;

        public Flavor(int id, String name, int color, Item foodItem)
        {
            this.id = id;
            this.name = name;
            this.color = color;
            this.food = (ItemFood)foodItem;
            ItemBottleSoyMilk.flavorList[id] = this;
        }
    }


    public ItemBottleSoyMilk()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getHealAmount(int itemDamage)
    {
        ItemFood food = flavorList[itemDamage].food;
        return food.func_150905_g(new ItemStack(food));
    }

    public float getSaturationModifier(int itemDamage)
    {
        ItemFood food = flavorList[itemDamage].food;
        return food.func_150906_h(new ItemStack(food));
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected int getColorFromDamage(int itemDamage)
    {
        return flavorList[itemDamage].color;
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < flavorList.length; i++)
        {
            if (flavorList[i] != null)
            {
                par3List.add(new ItemStack(this, 1, i));
            }
        }
    }



    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int itemDamage = par1ItemStack.getItemDamage();
        return this.getUnlocalizedName()
                + (flavorList[itemDamage].name != null ? "." + flavorList[itemDamage].name : "");
    }
}
