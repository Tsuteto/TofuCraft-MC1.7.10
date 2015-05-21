package tsuteto.tofu.block;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.util.TofuBlockUtils;

public class BlockTofuTrapdoor extends BlockTrapDoor implements IBlockTofuMaterial
{
    private TofuMaterial tofuMaterial;

    public BlockTofuTrapdoor(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.tofuMaterial = tofuMaterial;
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
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(TofuCraftCore.resourceDomain + "tofuTrapdoor_" + this.tofuMaterial.name());
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
