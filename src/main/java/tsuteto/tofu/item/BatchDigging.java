package tsuteto.tofu.item;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import tsuteto.tofu.network.packet.PacketBatchDigging;

import java.util.Collections;
import java.util.List;

public class BatchDigging
{
    private static List<List<ItemStack>> blockGroupingRegistry = Lists.newArrayList();

    private ItemStack itemStackDestroyed;

    static
    {
        addBlockGroup(new ItemStack(Blocks.dirt, 1, 0), new ItemStack(Blocks.dirt, 1, 2), new ItemStack(Blocks.grass, 1, 0));
    }

    public static void addBlockGroup(ItemStack... stacks)
    {
        List<ItemStack> group = Lists.newArrayList();
        Collections.addAll(group, stacks);
        blockGroupingRegistry.add(group);
    }

    private static Area calcArea(int bx, int by, int bz, int w, int d, int h, int sideHit)
    {
        // Convert WDH to practical XYZ
        ForgeDirection dir = ForgeDirection.getOrientation(sideHit).getOpposite();
        int x1, y1, z1, x2, y2, z2;
        switch (dir)
        {
            case UP:
                x1 = -w;
                x2 = w;
                y1 = 0;
                y2 = d;
                z1 = -h;
                z2 = h;
                break;
            case DOWN:
                x1 = -w;
                x2 = w;
                y1 = -d;
                y2 = 0;
                z1 = -h;
                z2 = h;
                break;

            case WEST:
                x1 = -d;
                x2 = 0;
                y1 = -h;
                y2 = h;
                z1 = -w;
                z2 = w;
                break;
            case EAST:
                x1 = 0;
                x2 = d;
                y1 = -h;
                y2 = h;
                z1 = -w;
                z2 = w;
                break;

            case NORTH:
                x1 = -w;
                x2 = w;
                y1 = -h;
                y2 = h;
                z1 = -d;
                z2 = 0;
                break;

            case SOUTH:
                x1 = -w;
                x2 = w;
                y1 = -h;
                y2 = h;
                z1 = 0;
                z2 = d;
                break;

            default:
                return null;
        }

        // Add base coordinate
        return new Area(bx + x1, by + y1, bz + z1, bx + x2, by + y2, bz + z2);
    }

    public static AxisAlignedBB getDiggingArea(int bx, int by, int bz, int w, int d, int h, int sideHit)
    {
        Area area = calcArea(bx, by, bz, w, d, h, sideHit);
        return AxisAlignedBB.getBoundingBox(area.x1, area.y1, area.z1, area.x2 + 1.0D, area.y2 + 1.0D, area.z2 + 1.0D);
    }

    private PacketBatchDigging packet;
    private EntityPlayer owner;

    public BatchDigging(PacketBatchDigging packet, EntityPlayer owner)
    {
        this.packet = packet;
        this.owner = owner;
        this.itemStackDestroyed = new ItemStack(Block.getBlockById(packet.blockId), 1, packet.blockMeta);
    }

    public static boolean isUnitedBlock(ItemStack block1, ItemStack block2)
    {
        if (block1.isItemEqual(block2)) return true;

        for (List<ItemStack> group : blockGroupingRegistry)
        {
            boolean matched1 = false;
            boolean matched2 = false;
            for (ItemStack stack : group)
            {
                if (OreDictionary.itemMatches(stack, block1, false)) matched1 = true;
                if (OreDictionary.itemMatches(stack, block2, false)) matched2 = true;
            }
            if (matched1 && matched2) return true;
        }
        return false;
    }

    public int execute()
    {
        World world = owner.worldObj;

        Area area = calcArea(packet.bx, packet.by, packet.bz, packet.w, packet.d, packet.h, packet.sideHit);

        int numBlocksDestroyed = 0;
        for (int x = area.x1; x <= area.x2; x++)
        {
            for (int y = area.y1; y <= area.y2; y++)
            {
                for (int z = area.z1; z <= area.z2; z++)
                {
                    if (this.destroyBlock(world, x, y, z))
                    {
                        numBlocksDestroyed++;
                    }
                }
            }
        }
        return numBlocksDestroyed;
    }

    private boolean destroyBlock(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (isUnitedBlock(itemStackDestroyed, new ItemStack(block, 1, meta)))
        {
            if (owner instanceof EntityPlayerMP)
            {
                EntityPlayerMP player = (EntityPlayerMP) owner;
                BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(
                        world, player.theItemInWorldManager.getGameType(), player, x, y, z);
                if (event.isCanceled())
                {
                    return false;
                }
                else
                {
                    boolean isCreative = player.capabilities.isCreativeMode;
                    boolean flag;
                    if (isCreative)
                    {
                        flag = this.removeBlock(world, x, y, z);
                        player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                    }
                    else
                    {
                        flag = this.removeBlock(world, x, y, z);

                        if (flag)
                        {
                            block.harvestBlock(world, player, x, y, z, meta);
                        }
                    }

                    // Drop experience
                    if (!isCreative && flag)
                    {
                        block.dropXpOnBlockBreak(world, x, y, z, event.getExpToDrop());
                    }
                    return flag;
                }
            }
            else
            {
                int fortune = EnchantmentHelper.getFortuneModifier(owner);
                world.setBlockToAir(x, y, z);
                block.dropBlockAsItem(world, x, y, z, meta, fortune);
                return true;
            }
        }
        return false;
    }

    private boolean removeBlock(World world, int par1, int par2, int par3)
    {
        Block block = world.getBlock(par1, par2, par3);
        int l = world.getBlockMetadata(par1, par2, par3);
        block.onBlockHarvested(world, par1, par2, par3, l, owner);
        boolean flag = block.removedByPlayer(world, owner, par1, par2, par3);

        if (flag)
        {
            block.onBlockDestroyedByPlayer(world, par1, par2, par3, l);
        }

        return flag;
    }

    private static class Area
    {
        final int x1, y1, z1, x2, y2, z2;

        public Area(int x1, int y1, int z1, int x2, int y2, int z2)
        {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }
    }
}
