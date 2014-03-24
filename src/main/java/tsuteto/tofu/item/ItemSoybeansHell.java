package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import tsuteto.tofu.block.TcBlocks;

public class ItemSoybeansHell extends ItemTcSeeds implements IPlantable
{
    public ItemSoybeansHell()
    {
        super(TcBlocks.soybeanHell, Blocks.soul_sand);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Nether;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z)
    {
        return TcBlocks.soybeanHell;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
        return 0;
    }
}
