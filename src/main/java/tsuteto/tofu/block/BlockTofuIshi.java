package tsuteto.tofu.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTofuIshi extends BlockTofu
{

    public BlockTofuIshi(Material material)
    {
        super(material);
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
}
