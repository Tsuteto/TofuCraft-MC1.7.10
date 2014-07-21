package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import tsuteto.tofu.item.iteminfo.TcFoodBase;

public class ItemBottleSoyMilk extends ItemFoodSetBase<ItemBottleSoyMilk.Flavor>
{
    public static final Flavor[] flavorList = new Flavor[10];

    public static Flavor flvPlain = new Flavor(0, "plain", 0xf5f7df, 2, 0.5F);

    public static Flavor flvKinako = new Flavor(1, "kinako", 0xd6bc2d, 4, 0.6F)
            .addPotionEffect(Potion.digSpeed, 45, 0, 1.0f);
    public static Flavor flvCocoa = new Flavor(2, "cocoa", 0x8d3d0d, 4, 0.6F)
            .addPotionEffect(Potion.jump, 60, 0, 1.0f);
    public static Flavor flvZunda = new Flavor(3, "zunda", 0x95e24a, 4, 0.6F)
            .addPotionEffect(Potion.nightVision, 20, 0, 1.0f);
    public static Flavor flvApple = new Flavor(4, "apple", 0xf2e087, 4, 0.6F)
            .addPotionEffect(Potion.fireResistance, 45, 0, 1.0f);
    public static Flavor flvPumpkin = new Flavor(5, "pumpkin", 0xffb504, 4, 0.6F)
            .addPotionEffect(Potion.damageBoost, 45, 0, 1.0f);
    public static Flavor flvRamune = new Flavor(6, "ramune", 0xa1c7ff, 4, 0.6F)
            .addPotionEffect(Potion.digSpeed, 45, 0, 1.0f)
            .addPotionEffect(Potion.jump, 60, 0, 1.0f)
            .addPotionEffect(Potion.nightVision, 45, 0, 1.0f)
            .addPotionEffect(Potion.fireResistance, 45, 0, 1.0f)
            .addPotionEffect(Potion.damageBoost, 45, 0, 1.0f)
            .addPotionEffect(Potion.moveSpeed, 60, 0, 1.0f)
            .addPotionEffect(Potion.resistance, 45, 0, 1.0f)
            .addPotionEffect(Potion.field_76444_x, 45, 0, 1.0f)
            .setRandomPotionEffect(1.0f);
    public static Flavor flvStrawberry = new Flavor(7, "strawberry", 0xf4a4b7, 4, 0.6F)
            .addPotionEffect(Potion.moveSpeed, 60, 0, 1.0f);
    public static Flavor flvSakura = new Flavor(8, "sakura", 0xffd1d7, 4, 0.6F)
            .addPotionEffect(Potion.resistance, 45, 0, 1.0f);
    public static Flavor flvAnnin = new Flavor(9, "annin", 0xf5f7f3, 4, 0.6F)
            .addPotionEffect(Potion.field_76444_x, 45, 0, 1.0f);

    public static class Flavor extends TcFoodBase<Flavor>
    {
        public Flavor(int id, String name, int color, int healAmount, float saturationModifier)
        {
            super(id, healAmount, saturationModifier, true, name);
            this.asGlassBottle(color);
            this.setContainerItem(new ItemStack(Items.glass_bottle));
            ItemBottleSoyMilk.flavorList[id] = this;
        }
    }

    public ItemBottleSoyMilk()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public Flavor[] getItemList()
    {
        return flavorList;
    }

    @Override
    public String getItemSetName()
    {
        return "bottleSoymilk";
    }
}
