package tsuteto.tofu.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import tsuteto.tofu.item.iteminfo.TcEffectFoodBase;

public class ItemFoodSet extends ItemFoodSetBase<ItemFoodSet.Food>
{
    public static Food[] foodList = new Food[22];

    public static Food tofuChikuwa =
            new Food( 0,  6, 0.4f,  true, "tofuChikuwa");
    public static Food oage =
            new Food( 1,  5, 0.3f,  true, "oage");
    public static Food mabodofu =
            new Food( 2, 16, 0.5f, false, "mabodofu").setContainer(new ItemStack(Items.bowl));
    public static Food onigiri =
            new Food( 3,  4, 0.4f, false, "onigiri");
    public static Food onigiriSalt =
            new Food( 4,  5, 0.6f, false, "onigiriSalt");
    public static Food yakionigiriMiso =
            new Food( 5,  6, 0.8f, false, "yakionigiriMiso");
    public static Food yakionigiriShoyu =
            new Food( 6,  6, 0.8f, false, "yakionigiriShoyu");
    public static Food sproutSaute =
            new Food( 7,  8, 0.4f, false, "moyashiitame").setContainer(new ItemStack(Items.bowl));
    public static Food moyashiOhitashi =
            new Food( 8,  5, 0.3f, false, "moyashiohitashi").setContainer(new ItemStack(Items.bowl));
    public static Food sprouts =
            new Food( 9,  2, 0.5f, false, "sprouts");
    public static Food hiyayakko =
            new Food(10,  6, 0.5f, false, "hiyayakko");
    public static Food riceTofu =
            new Food(11, 10, 0.2f, false, "riceTofu"); // For External Mod
    public static Food tofuHamburg =
            new Food(12, 12, 0.8f, false, "tofuHamburg");
    public static Food tofuCookie =
            new Food(13, 2, 0.15f, false, "tofuCookie");
    public static Food inari =
            new Food(14, 5, 0.6f, false, "inari");
    public static Food tofufishRow =
            new Food(15, 4, 0.4f, false, "tofufishRaw");
    public static Food tofufishCooked =
            new Food(16, 6, 0.6f, false, "tofufishCooked");
    public static Food hiyayakkoGl =
            new Food(17, 6, 0.5f, false, "hiyayakko_glass").setContainer(TcItems.materials.getItemStack(ItemTcMaterials.glassBowl));
    public static Food nattoHiyayakkoGl =
            new Food(18, 8, 0.8f, false, "nattoHiyayakko_glass").setContainer(TcItems.materials.getItemStack(ItemTcMaterials.glassBowl));
    public static Food tofuSomen =
            new Food(19, 3, 0.3f, true, "tofuSomenBowl_glass").setContainer(new ItemStack(TcItems.somenTsuyuBowl));
    public static Food zundaMochi = (Food)
            new Food(20, 3, 0.8f, false, "zundaMochi").addPotionEffect(Potion.regeneration, 3, 0, 1.0F).setMaxItemUseDuration(64); // For External Mod
    public static Food kinakoMochi = (Food)
            new Food(21, 5, 0.8f, false, "kinakoMochi").setMaxItemUseDuration(64); // For External Mod

    public ItemFoodSet()
    {
        super();
    }

    public static class Food extends TcEffectFoodBase<Food>
    {

        Food(int id, int healAmount, float saturationModifier, boolean alwaysEdible, String name)
        {
            super(id, healAmount, saturationModifier, alwaysEdible, name);
            foodList[id] = this;
        }
    }

    @Override
    public Food[] getItemList()
    {
        return foodList;
    }
}
