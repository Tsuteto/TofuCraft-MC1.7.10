package tsuteto.tofu.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.util.ItemUtils;

import java.util.Set;

public class ItemTcAxe extends ItemAxe
{

    public ItemTcAxe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        ItemUtils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("axe");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return ItemUtils.getCreativeTabs(this);
    }

}
