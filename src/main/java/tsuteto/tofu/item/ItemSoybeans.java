package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.util.ItemUtils;

public class ItemSoybeans extends TcItem implements IPlantable
{
    public ItemSoybeans()
    {
        super();
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
        {
            Block soil = par3World.getBlock(par4, par5, par6);

            if (soil != null && par3World.isAirBlock(par4, par5 + 1, par6))
            {
                boolean isPlanted = false;
                if (soil.canSustainPlant(par3World, par4, par5, par6, ForgeDirection.UP, this))
                {
                    par3World.setBlock(par4, par5 + 1, par6, TcBlocks.soybean);
                    isPlanted = true;
                }
                else if (soil == Blocks.wool)
                {
                    par3World.setBlock(par4, par5 + 1, par6, TcBlocks.sprouts);
                    TcAchievementMgr.achieve(par2EntityPlayer, Key.sproutPlanting);
                    isPlanted = true;
                }
                if (isPlanted)
                {
                    --par1ItemStack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z)
    {
        return TcBlocks.soybean;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
        return 0;
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        return ItemUtils.getCreativeTabs(this);
    }
}
