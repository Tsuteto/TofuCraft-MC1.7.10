package tsuteto.tofu.item;

import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import tsuteto.tofu.util.Utils;

import java.util.Set;

public class ItemTcAxe extends ItemAxe
{

    public ItemTcAxe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        Utils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("axe");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return Utils.getCreativeTabs(this);
    }

}
