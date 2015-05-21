package tsuteto.tofu.block;

import net.minecraft.world.IBlockAccess;
import tsuteto.tofu.item.TofuMaterial;

public class BlockTofuIshi extends BlockTofu
{

    public BlockTofuIshi(TofuMaterial material)
    {
        super(material);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
}
