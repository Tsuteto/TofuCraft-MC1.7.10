package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.util.TofuBlockUtils;

public class BlockTofuFenceGate extends BlockFenceGate implements IBlockTofuMaterial
{
    private TofuMaterial tofuMaterial;

    public BlockTofuFenceGate(TofuMaterial tofuMaterial)
    {
        this.tofuMaterial = tofuMaterial;
        this.tofuMaterial.getBlockInfo().setBasicFeature(this);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return tofuMaterial.getBlock().getBlockTextureFromSide(p_149691_1_);
    }

    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (tofuMaterial == TofuMaterial.kinu)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }

    public int getRenderBlockPass()
    {
        return this.tofuMaterial.getBlock().getRenderBlockPass();
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
