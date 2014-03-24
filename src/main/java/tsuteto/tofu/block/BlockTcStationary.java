package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockTcStationary extends BlockFluidClassic
{
    private final String[] iconNames;
    private IIcon[] theIcon;
    private int fluidColor = 0xffffff;

    public BlockTcStationary(Fluid fluid, Material material, String[] iconNames)
    {
        super(fluid, material);
        this.iconNames = iconNames;
        disableStats();
    }

    public BlockTcStationary setColor(int color)
    {
        this.fluidColor = color;
        return this;
    }
    
    @Override
    public int getBlockColor()
    {
        return 16777215;
    }
    
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return fluidColor;
    }
    
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        return par1 != 0 && par1 != 1 ? this.theIcon[1] : this.theIcon[0];
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.theIcon = new IIcon[] {par1IconRegister.registerIcon(iconNames[0]), par1IconRegister.registerIcon(iconNames[1])};
    }


    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        Block b = world.getBlock(x, y, z);
        if (!(b instanceof BlockFluidBase))
        {
            if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        Block b = world.getBlock(x, y, z);
        if (!(b instanceof BlockFluidBase))
        {
            if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        }
        return super.displaceIfPossible(world, x, y, z);
    }
}
