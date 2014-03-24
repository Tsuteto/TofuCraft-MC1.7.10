package tsuteto.tofu.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class NameEntry
{
    enum Lang
    {
        en("en_US"), ja("ja_JP");

        private String code;
        Lang(String code)
        {
            this.code = code;
        }
    }

    public static NameEntry of(Item item)
    {
        return new NameEntry(new ItemStack(item));
    }

    public static NameEntry of(Block block)
    {
        return new NameEntry(new ItemStack(block));
    }

    private ItemStack itemstack;

    private NameEntry(ItemStack itemstack)
    {
        this.itemstack = itemstack;
    }

    public NameEntry forDmg(int meta)
    {
        itemstack.setItemDamage(meta);
        return this;
    }

    public NameEntry nameEn(String name)
    {
        return this.name(Lang.en, name);
    }

    public NameEntry nameJa(String name)
    {
        return this.name(Lang.ja, name);
    }

    public NameEntry name(Lang lang, String name)
    {
        LanguageRegistry.instance().addNameForObject(itemstack, lang.code, name);
        return this;
    }
}
