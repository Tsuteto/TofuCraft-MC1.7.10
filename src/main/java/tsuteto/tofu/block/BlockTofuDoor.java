package tsuteto.tofu.block;

import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.util.BlockUtils;
import tsuteto.tofu.util.TofuBlockUtils;

import java.util.EnumMap;
import java.util.Random;

public class BlockTofuDoor extends BlockDoor implements IBlockTofuMaterial
{
    public static final EnumMap<TofuMaterial, BlockTofuDoor> doorBlocks = Maps.newEnumMap(TofuMaterial.class);

    @SideOnly(Side.CLIENT)
    private IIcon[] iconUpper;
    @SideOnly(Side.CLIENT)
    private IIcon[] iconLower;

    private TofuMaterial tofuMaterial;

    public BlockTofuDoor(TofuMaterial tofuMaterial)
    {
        super(tofuMaterial.getBlockInfo().material);
        tofuMaterial.getBlockInfo().setBasicFeature(this);
        this.tofuMaterial = tofuMaterial;
        doorBlocks.put(tofuMaterial, this);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return this.iconLower[0];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess p_149673_1_, int p_149673_2_, int p_149673_3_, int p_149673_4_, int p_149673_5_)
    {
        if (p_149673_5_ != 1 && p_149673_5_ != 0)
        {
            int i1 = this.func_150012_g(p_149673_1_, p_149673_2_, p_149673_3_, p_149673_4_);
            int j1 = i1 & 3;
            boolean flag = (i1 & 4) != 0;
            boolean flag1 = false;
            boolean flag2 = (i1 & 8) != 0;

            if (flag)
            {
                if (j1 == 0 && p_149673_5_ == 2)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && p_149673_5_ == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && p_149673_5_ == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && p_149673_5_ == 4)
                {
                    flag1 = !flag1;
                }
            }
            else
            {
                if (j1 == 0 && p_149673_5_ == 5)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 1 && p_149673_5_ == 3)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 2 && p_149673_5_ == 4)
                {
                    flag1 = !flag1;
                }
                else if (j1 == 3 && p_149673_5_ == 2)
                {
                    flag1 = !flag1;
                }

                if ((i1 & 16) != 0)
                {
                    flag1 = !flag1;
                }
            }

            return flag2 ? this.iconUpper[flag1?1:0] : this.iconLower[flag1?1:0];
        }
        else
        {
            return this.iconLower[0];
        }
    }

    // Put 1 for rendering with alpha!
    public int getRenderBlockPass()
    {
        return this.tofuMaterial.getBlock().getRenderBlockPass();
    }

    private static BlockUtils.IEntityWeightingBlockHandler tofuWeightingHandler = new BlockUtils.IEntityWeightingBlockHandler()
    {
        @Override
        public void apply(World world, Entity entity, Block block, int x, int y, int z)
        {
            world.setBlockToAir(x, y, z);

            if (entity instanceof EntityPlayer)
            {
                TcAchievementMgr.achieve((EntityPlayer) entity, TcAchievementMgr.Key.tofuMental);
            }
        }
    };

    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6)
    {
        if (this.tofuMaterial == TofuMaterial.kinu)
        {
            TofuBlockUtils.onFallenUponFragileTofu(par1World, par5Entity, this, par6, tofuWeightingHandler);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.iconUpper = new IIcon[2];
        this.iconLower = new IIcon[2];
        this.iconUpper[0] = p_149651_1_.registerIcon(this.getTextureName() + "_" + this.tofuMaterial.name() + "_upper");
        this.iconLower[0] = p_149651_1_.registerIcon(this.getTextureName() + "_" + this.tofuMaterial.name() + "_lower");
        this.iconUpper[1] = new IconFlipped(this.iconUpper[0], true, false);
        this.iconLower[1] = new IconFlipped(this.iconLower[0], true, false);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return (p_149650_1_ & 8) != 0 ? null : TcItems.tofuDoor;
    }

    public int damageDropped(int p_149692_1_)
    {
        return this.tofuMaterial.id();
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return TcItems.tofuDoor;
    }

    @Override
    public TofuMaterial getTofuMaterial()
    {
        return this.tofuMaterial;
    }
}
