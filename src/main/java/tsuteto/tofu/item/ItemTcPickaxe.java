package tsuteto.tofu.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.util.ItemUtils;

import java.util.Set;

public class ItemTcPickaxe extends ItemPickaxe
{

    public ItemTcPickaxe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        this.setCreativeTab(TcCreativeTabs.TOOLS);
        ItemUtils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("pickaxe");
    }
}
