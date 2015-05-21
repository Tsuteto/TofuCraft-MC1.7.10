package tsuteto.tofu.item;

import com.google.common.collect.ImmutableSet;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.util.ItemUtils;

import java.util.Set;

public class ItemTcSpade extends ItemSpade
{

    public ItemTcSpade(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        this.setCreativeTab(TcCreativeTabs.TOOLS);
        ItemUtils.tweakToolAttackDamage(this);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        return ImmutableSet.of("shovel");
    }
}
