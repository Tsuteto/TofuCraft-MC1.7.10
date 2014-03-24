package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.material.TcMaterial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTofuStepFaces extends BlockTofuStepBase
{
    private IIcon iconSide;

    public BlockTofuStepFaces(boolean par2)
    {
        super(par2, TcMaterial.tofu);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        int direction = (metadata & 4) >> 2;

        if (direction == 1 && side == 0 || direction == 0 && side == 1)
        {
            return this.blockIcon;
        }
        else
        {
            return this.iconSide;
        }
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        int var6 = determineOrientation(par1World, par2, par3, par4, par5EntityLiving) | par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
    }
    
    /**
     * gets the way this piston should face for that entity that placed it.
     */
    public static int determineOrientation(World par0World, int par1, int par2, int par3, EntityLivingBase par4EntityPlayer)
    {
            double var5 = par4EntityPlayer.posY + 1.82D - par4EntityPlayer.yOffset;

            if (var5 - par2 > 0.0D)
            {
                return 0;
            }
            else
            {
                return 4;
            }
    }

    @Override
    protected boolean isBlockSingleSlab(Block par0)
    {
        return par0 == TcBlocks.tofuSingleSlabFaces;
    }

    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(TcBlocks.tofuSingleSlabFaces);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        Block block = isBlockSingleSlab(this) ? this : TcBlocks.tofuSingleSlabFaces;
        return Item.getItemFromBlock(block);
    }

    @Override
    public String func_150002_b(int par1)
    {
        return super.getUnlocalizedName();
    }
    
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (isBlockSingleSlab(getBlockFromItem(par1)))
        {
            super.getSubBlocks(par1, par2CreativeTabs, par3List);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("tofucraft:blockTofuGrilled");
        this.iconSide = par1IconRegister.registerIcon("tofucraft:blockTofuMomen");
    }
}
