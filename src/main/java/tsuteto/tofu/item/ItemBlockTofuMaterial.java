package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import tsuteto.tofu.block.IBlockTofuMaterial;
import tsuteto.tofu.util.Utils;

public class ItemBlockTofuMaterial extends ItemTcBlock
{
    public ItemBlockTofuMaterial(Block par1)
    {
        super(par1);
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        String tofuMaterialName = ((IBlockTofuMaterial)this.field_150939_a).getTofuMaterial().name();
        return ("" + StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name",
                StatCollector.translateToLocal("item.tofucraft:tofu" + Utils.capitalize(tofuMaterialName) + ".name"))).trim();
    }
}
