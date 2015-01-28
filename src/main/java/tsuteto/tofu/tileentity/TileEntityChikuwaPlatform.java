package tsuteto.tofu.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import tsuteto.tofu.block.BlockChikuwaPlatform;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.Utils;

import java.util.List;

public class TileEntityChikuwaPlatform extends TileEntity
{
    private int timer = 20;
    private AxisAlignedBB fullBoundingBox;

    @Override
    public void updateEntity()
    {
        if (!this.worldObj.isRemote)
        {
            if (BlockChikuwaPlatform.canGoThrough(worldObj, xCoord, yCoord - 1, zCoord))
            {
                if (fullBoundingBox == null)
                {
                    fullBoundingBox = AxisAlignedBB.getBoundingBox((double) xCoord, (double) yCoord, (double) zCoord,
                            (double) xCoord + 1.0D, (double) yCoord + 1.0D, (double) zCoord + 1.0D);
                }

                List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, fullBoundingBox);
                if (list.size() > 0)
                {
                    timer--;
                }
                else
                {
                    timer = 20;
                }

                if (timer <= 0)
                {
                    int chunkUID = (int) (((worldObj.getTotalWorldTime() & 65535) << 16) | (Utils.generateRandomFromCoord(xCoord, yCoord, zCoord) & 65535));
                    ModLog.debug("Chikuwa Chunk UID: " + chunkUID);
                    this.triggerDropping(xCoord, yCoord, zCoord, chunkUID, 0);
                    timer = 20;
                }
            }
        }
    }

    private void triggerDropping(int x, int y, int z, int chunkUID, int depth)
    {
        if (depth > 127) return;

        BlockChikuwaPlatform block = (BlockChikuwaPlatform)worldObj.getBlock(x, y, z);
        int meta = this.getBlockMetadata();
        BlockChikuwaPlatform.Dir blockDir = BlockChikuwaPlatform.getDirection(meta);

        boolean dropped = block.dropBlock(worldObj, x, y, z, block, chunkUID);

        if (dropped)
        {
            for (ForgeDirection dir : blockDir.connectedTo)
            {
                if (block.canChikuwaConnectTo(worldObj, x, y, z, meta, dir))
                {
                    this.triggerDropping(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, chunkUID, depth + 1);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
        super.writeToNBT(p_145841_1_);

        p_145841_1_.setShort("Timer", (short)timer);
    }

    @Override
    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
        super.readFromNBT(p_145839_1_);

        this.timer = p_145839_1_.getShort("Timer");
    }
}
