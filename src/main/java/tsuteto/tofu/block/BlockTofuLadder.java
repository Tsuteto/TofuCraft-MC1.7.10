package tsuteto.tofu.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.client.renderer.texture.IIconRegister;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.TofuMaterial;

public class BlockTofuLadder extends BlockLadder implements IBlockTofuMaterial
{
    private TofuMaterial tofuMaterial;

    public BlockTofuLadder(TofuMaterial tofuMaterial)
    {
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.tofuMaterial = tofuMaterial;
    }

    public int getRenderBlockPass()
    {
        return this.tofuMaterial.getBlock().getRenderBlockPass();
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(TofuCraftCore.resourceDomain + "tofuLadder_" + this.tofuMaterial.name());
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
