package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TofuMaterial;

public class BlockTofuAnnin extends BlockTofu
{

    public BlockTofuAnnin(TofuMaterial material)
    {
        super(material);
        this.setLightOpacity(2);
    }

    @SideOnly(Side.CLIENT)
    
    /**
     * Returns register pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
