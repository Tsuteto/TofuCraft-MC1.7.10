package tsuteto.tofu.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.material.TcMaterial;
import tsuteto.tofu.util.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTofuStep extends BlockTofuStepBase
{
    /** The list of the types of step blocks. */
    public static final String[] blockStepTypes1 = new String[] {"kinu", "momen", "ishi", "metal", "grilled", "dried", "pouchFried", "fried"};
    public static final boolean[] typesObsolete1 = new boolean[] {false, false, false, false, true, false, false, false};
    public static final String[] blockStepTypes2 = new String[] {"egg", "annin", "sesame", "zunda", "strawberry", "hell", "glow", "diamond"};
    public static final boolean[] typesObsolete2 = new boolean[] {false, false, false, false, false, false, true, false};
    public static final String[] blockStepTypes3 = new String[] {"yamauni"};
    public static final boolean[] typesObsolete3 = new boolean[] {false};
    
    private final String[] blockStepTypes;
    private final boolean[] typesObsolete;
    private IIcon[] icons;

    public BlockTofuStep(boolean par2, String[] types, boolean[] obsolete)
    {
        super(par2, TcMaterial.tofu);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.blockStepTypes = types;
        this.typesObsolete = obsolete;
        this.setLightOpacity(0);
        this.setTickRandomly(true);
    }
    
    private boolean isFragile(int meta)
    {
        return (meta & 7) == 0 && (this == TcBlocks.tofuSingleSlab1 || this == TcBlocks.tofuDoubleSlab1);
    }
    
    /**
     * Block's chance to react to an entity falling on it.
     */
    @Override
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        
        if (!par1World.isRemote && isFragile(meta))
        {
            if (par6 > 0.5F)
            {
                if (!(par5Entity instanceof EntityPlayer) && !par1World.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                    return;
                }
                this.onEntityWeightedBlock(par1World, par5Entity);
            }
        }
    }

    private void onEntityWeightedBlock(World world, Entity entity)
    {
        int i = MathHelper.floor_double(entity.boundingBox.minX + 0.001D);
        int j = MathHelper.floor_double(entity.boundingBox.minY + 0.001D);
        int k = MathHelper.floor_double(entity.boundingBox.minZ + 0.001D);
        int l = MathHelper.floor_double(entity.boundingBox.maxX - 0.001D);
        int i1 = MathHelper.floor_double(entity.boundingBox.maxY - 0.001D);
        int j1 = MathHelper.floor_double(entity.boundingBox.maxZ - 0.001D);

        if (entity.worldObj.checkChunksExist(i, j, k, l, i1, j1))
        {
            for (int k1 = i; k1 <= l; ++k1)
            {
                for (int l1 = j; l1 <= i1; ++l1)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        int bx = k1;
                        int by = l1 - 1;
                        int bz = i2;
                        if (world.getBlock(bx, by, bz) == this)
                        {
                            this.collapseBlock(entity, world, bx, by, bz);
                        }
                    }
                }
            }
        }
    }

    private void collapseBlock(Entity entity, World world, int x, int y, int z)
    {
        dropBlockAsItem(world, x, y, z, 0, 0);
        world.setBlockToAir(x, y, z);

        if (entity instanceof EntityPlayer)
        {
            TcAchievementMgr.achieve((EntityPlayer)entity, Key.tofuMental);
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        int meta = par1World.getBlockMetadata(par2, par3, par4);

        if (isFragile(meta) && this.field_150004_a)
        {
            Block weightBlock = par1World.getBlock(par2, par3 + 1, par4);

            if (weightBlock != null)
            {
               if (weightBlock.getMaterial() == Material.rock || weightBlock.getMaterial() == Material.iron)
               {
                   dropBlockAsItem(par1World, par2, par3, par4, 0, 0);
                   par1World.setBlockToAir(par2, par3, par4);
               }
            }
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 >= this.icons.length)
        {
            par2 = 0;
        }
        return icons[par2 & 7];
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        Block block = isBlockSingleSlab(this) ? this
                : (this == TcBlocks.tofuDoubleSlab1 ? TcBlocks.tofuSingleSlab1
                        : (this == TcBlocks.tofuDoubleSlab2 ? TcBlocks.tofuSingleSlab2
                                : (this == TcBlocks.tofuDoubleSlab3 ? TcBlocks.tofuSingleSlab3
                                        : TcBlocks.tofuSingleSlab1)));
        return Item.getItemFromBlock(block);
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks register do not support subtypes. Blocks register cannot be harvested should return null.
     */
    @Override
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(this, 2, par1 & 7);
    }

    /**
     * Returns the slab block name with step type.
     */
    @Override
    public String func_150002_b(int par1) // getFullSlabName
    {
        if (par1 < 0 || par1 >= blockStepTypes.length)
        {
            par1 = 0;
        }

        return super.getUnlocalizedName() + "." + blockStepTypes[par1] + (typesObsolete[par1] ? "_old" : "");
    }
    
    protected boolean isBlockSingleSlab(Block par0)
    {
        return par0 == TcBlocks.tofuSingleSlab1 || par0 == TcBlocks.tofuSingleSlab2 || par0 == TcBlocks.tofuSingleSlab3;
    }
    
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4)
    {
        Block block = isBlockSingleSlab(this) ? this
                : (this == TcBlocks.tofuDoubleSlab1 ? TcBlocks.tofuSingleSlab1
                        : (this == TcBlocks.tofuDoubleSlab2 ? TcBlocks.tofuSingleSlab2
                                : (this == TcBlocks.tofuDoubleSlab3 ? TcBlocks.tofuSingleSlab3
                                        : TcBlocks.tofuSingleSlab1)));
        return Item.getItemFromBlock(block);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (isBlockSingleSlab(Block.getBlockFromItem(par1)))
        {
            for (int var4 = 0; var4 < blockStepTypes.length; var4++)
            {
                if (!typesObsolete[var4])
                {
                    par3List.add(new ItemStack(par1, 1, var4));
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
    	this.icons = new IIcon[blockStepTypes.length];
        for (int i = 0; i < blockStepTypes.length; ++i)
        {
            this.icons[i] = par1IconRegister.registerIcon("tofucraft:blockTofu" + Utils.capitalize(blockStepTypes[i]));
        }
    }
}
