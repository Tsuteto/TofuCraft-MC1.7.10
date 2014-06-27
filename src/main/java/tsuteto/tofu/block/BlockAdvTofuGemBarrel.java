package tsuteto.tofu.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.Utils;

import java.util.List;
import java.util.Random;

public class BlockAdvTofuGemBarrel extends BlockFermentable
{
	private IIcon iconTop;
	private IIcon iconBottom;

    public BlockAdvTofuGemBarrel(Material par3Material)
    {
        super(par3Material);
    }

    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        return TcItems.barrelAdvTofuGem;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return TcItems.barrelEmpty;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par1 == 1) // top
        {
            return this.iconTop;
        }
        else if (par1 == 0) // bottom
        {
            return this.iconBottom;
        }
        else
        {
            return this.blockIcon;
        }
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int metadata = this.getFermStep(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        return metadata == 7 ? 0x885511 : this.doCheckEnvironment(par1IBlockAccess, par2, par3, par4)? 0xffd399 : 0xffffff;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
        this.iconTop = par1IconRegister.registerIcon("tofucraft:barrel_top");
        this.iconBottom = par1IconRegister.registerIcon("tofucraft:barrel_bottom");
    }

    @Override
    public void addFermentedItem(List list)
    {
        list.add(new ItemStack(TcItems.materials, 1, ItemTcMaterials.advTofuGem.id));
    }

    @Override
    public void addIngredients(List list)
    {
        list.add(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id));
        list.add(new ItemStack(Blocks.redstone_block, 1));
    }

    @Override
    public boolean checkEnvironment(World world, int x, int y, int z)
    {
        ModLog.debug("advtofugem: %d", this.getFermStep(world.getBlockMetadata(x, y, z)));
        return this.doCheckEnvironment(world, x, y, z);
    }

    private boolean doCheckEnvironment(IBlockAccess blockAccess, int x, int y, int z)
    {
        return blockAccess.getBlock(x, y + 1, z) == Blocks.anvil
                && (blockAccess.getBlockMetadata(x, y, z) & 8) == 8;
    }

    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        Utils.onNeighborBlockChange_RedstoneSwitch(this, p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
    }
}
