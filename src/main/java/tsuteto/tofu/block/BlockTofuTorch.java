package tsuteto.tofu.block;

import net.minecraft.block.BlockTorch;
import net.minecraft.client.renderer.texture.IIconRegister;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.TofuMaterial;

public class BlockTofuTorch extends BlockTorch implements IBlockTofuMaterial
{
    private TofuMaterial tofuMaterial;

    public BlockTofuTorch(TofuMaterial tofuMaterial)
    {
        this.tofuMaterial = tofuMaterial;
        this.tofuMaterial.getBlockInfo().setBasicFeature(this);
    }

    public int getRenderBlockPass()
    {
        return this.tofuMaterial.getBlock().getRenderBlockPass();
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(TofuCraftCore.resourceDomain + "tofuTorch_" + this.tofuMaterial.name());
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
