package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.util.TofuBlockUtils;

import java.util.List;

public class BlockTofuWall extends BlockWall implements IBlockTofuMaterial
{
    private TofuMaterial tofuMaterial;

    public BlockTofuWall(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlock());
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.tofuMaterial = tofuMaterial;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return tofuMaterial.getBlock().getBlockTextureFromSide(p_149691_1_);
    }

    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }

    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (tofuMaterial == TofuMaterial.kinu)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6);
        }
    }

    public boolean canConnectWallTo(IBlockAccess p_150091_1_, int p_150091_2_, int p_150091_3_, int p_150091_4_)
    {
        Block block = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
        return !(block != this && block != TcBlocks.tofuFenceGates.get(tofuMaterial))
                || (block.getMaterial().isOpaque() && block.renderAsNormalBlock() && block.getMaterial() != Material.gourd);
    }

    public int getRenderBlockPass()
    {
        return this.tofuMaterial.getBlock().getRenderBlockPass();
    }

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
