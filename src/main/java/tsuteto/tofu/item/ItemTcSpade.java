package tsuteto.tofu.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.util.Utils;

import java.util.Set;

public class ItemTcSpade extends ItemSpade
{

    public ItemTcSpade(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        Utils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("shovel");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return Utils.getCreativeTabs(this);
    }
}
