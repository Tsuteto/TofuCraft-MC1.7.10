package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.block.BlockTofuDoor;
import tsuteto.tofu.util.Utils;

import java.util.List;

public class ItemTofuDoor extends TcItem
{
    private IIcon[] icons;

    public ItemTofuDoor()
    {
        this.maxStackSize = 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.icons[p_77617_1_];
    }

    public TofuMaterial getMaterial(ItemStack itemStack)
    {
        return TofuMaterial.get(MathHelper.clamp_int(itemStack.getItemDamage(), 0, TofuMaterial.values().length - 1));
    }

    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ != 1)
        {
            return false;
        }
        else
        {
            ++p_77648_5_;
            Block block = BlockTofuDoor.doorBlocks.get(this.getMaterial(p_77648_1_));

            if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_ + 1, p_77648_6_, p_77648_7_, p_77648_1_))
            {
                if (!block.canPlaceBlockAt(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
                {
                    return false;
                }
                else
                {
                    int i1 = MathHelper.floor_double((double) ((p_77648_2_.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
                    placeDoorBlock(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, i1, block);
                    --p_77648_1_.stackSize;
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public static void placeDoorBlock(World world, int x, int y, int z, int dir, Block door)
    {
        byte b0 = 0;
        byte b1 = 0;

        if (dir == 0)
        {
            b1 = 1;
        }

        if (dir == 1)
        {
            b0 = -1;
        }

        if (dir == 2)
        {
            b1 = -1;
        }

        if (dir == 3)
        {
            b0 = 1;
        }

        int i1 = (world.getBlock(x - b0, y, z - b1).isNormalCube() ? 1 : 0) + (world.getBlock(x - b0, y + 1, z - b1).isNormalCube() ? 1 : 0);
        int j1 = (world.getBlock(x + b0, y, z + b1).isNormalCube() ? 1 : 0) + (world.getBlock(x + b0, y + 1, z + b1).isNormalCube() ? 1 : 0);
        boolean flag = world.getBlock(x - b0, y, z - b1) == door || world.getBlock(x - b0, y + 1, z - b1) == door;
        boolean flag1 = world.getBlock(x + b0, y, z + b1) == door || world.getBlock(x + b0, y + 1, z + b1) == door;
        boolean flag2 = false;

        if (flag && !flag1)
        {
            flag2 = true;
        }
        else if (j1 > i1)
        {
            flag2 = true;
        }

        world.setBlock(x, y, z, door, dir, 2);
        world.setBlock(x, y + 1, z, door, 8 | (flag2 ? 1 : 0), 2);
        world.notifyBlocksOfNeighborChange(x, y, z, door);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, door);
    }

    @Override
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for (TofuMaterial material : BlockTofuDoor.doorBlocks.keySet())
        {
            p_150895_3_.add(new ItemStack(this, 1, material.ordinal()));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_)
    {
        return StatCollector.translateToLocalFormatted(this.getUnlocalizedNameInefficiently(p_77653_1_) + ".name",
                StatCollector.translateToLocal("item.tofucraft:tofu" + Utils.capitalize(this.getMaterial(p_77653_1_).name()) + ".name"));
    }

    @Override
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.icons = new IIcon[TofuMaterial.values().length];

        for (TofuMaterial material : BlockTofuDoor.doorBlocks.keySet())
        {
            this.icons[material.id()] = p_94581_1_.registerIcon(TofuCraftCore.resourceDomain + "tofuDoor_" + material.name());
        }
    }
}
