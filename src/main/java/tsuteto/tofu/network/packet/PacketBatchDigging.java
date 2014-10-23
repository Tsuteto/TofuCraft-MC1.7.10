package tsuteto.tofu.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import tsuteto.tofu.network.AbstractPacket;
import tsuteto.tofu.network.MessageToServer;
import tsuteto.tofu.util.ModLog;

public class PacketBatchDigging extends AbstractPacket implements MessageToServer
{

    public int w;
    public int d;
    public int h;
    public int bx;
    public int by;
    public int bz;
    public int blockId;
    public int blockMeta;
    public int sideHit;

    public PacketBatchDigging() {}

    public PacketBatchDigging(int w, int d, int h, int bx, int by, int bz, Block block, int meta, int sideHit)
    {
        this.w = w;
        this.d = d;
        this.h = h;
        this.bx = bx;
        this.by = by;
        this.bz = bz;
        this.blockId = Block.getIdFromBlock(block);
        this.blockMeta = meta;
        this.sideHit = sideHit;
    }

    @Override
    public void encodeInto(ByteBuf buffer)
    {
        buffer.writeShort(w);
        buffer.writeShort(d);
        buffer.writeShort(h);
        buffer.writeInt(bx);
        buffer.writeInt(by);
        buffer.writeInt(bz);
        buffer.writeShort(blockId);
        buffer.writeByte(blockMeta);
        buffer.writeByte(sideHit);
    }

    @Override
    public void decodeInto(ByteBuf buffer)
    {
        this.w = buffer.readShort();
        this.d = buffer.readShort();
        this.h = buffer.readShort();
        this.bx = buffer.readInt();
        this.by = buffer.readInt();
        this.bz = buffer.readInt();
        this.blockId = buffer.readShort();
        this.blockMeta = buffer.readByte();
        this.sideHit = buffer.readByte();
    }

    @Override
    public IMessage handleServerSide(EntityPlayer player)
    {
        this.batchDigging(player);
        return null;
    }

    public int batchDigging(EntityLivingBase owner)
    {
        World world = owner.worldObj;
        Block blockDestroyed = Block.getBlockById(blockId);
        int blockMetaDestroyed = this.blockMeta;

        // Convert WDH to practical XYZ
        ForgeDirection dir = ForgeDirection.getOrientation(sideHit).getOpposite();
        ModLog.debug("sideHit: %s", dir);
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
                return 0;
        }

        int numBlocksDestroyed = 0;
        for (int x = x1; x <= x2; x++)
        {
            for (int y = y1; y <= y2; y++)
            {
                for (int z = z1; z <= z2; z++)
                {
                    if (this.destroyBlock(world, blockDestroyed, blockMetaDestroyed, bx + x, by + y, bz + z, owner))
                    {
                        numBlocksDestroyed++;
                    }
                }
            }
        }
        return numBlocksDestroyed;
    }

    private boolean destroyBlock(World world, Block blockDestroyed, int blockMetaDestroyed, int x, int y, int z, EntityLivingBase owner)
    {
        Block block = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (block == blockDestroyed && meta == blockMetaDestroyed)
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
                        flag = this.removeBlock(world, x, y, z, player);
                        player.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                    }
                    else
                    {
                        flag = this.removeBlock(world, x, y, z, player);

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

    private boolean removeBlock(World world, int par1, int par2, int par3, EntityPlayer owner)
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
}
