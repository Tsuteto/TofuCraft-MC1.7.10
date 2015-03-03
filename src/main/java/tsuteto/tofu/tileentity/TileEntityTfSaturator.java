package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineBase;
import tsuteto.tofu.block.BlockTfSaturator;
import tsuteto.tofu.block.BlockTofu;
import tsuteto.tofu.data.ContainerParamBool;
import tsuteto.tofu.util.TileScanner;

import java.util.Random;

public class TileEntityTfSaturator extends TileEntityTfMachineBase implements ITfConsumer
{
    public static final double TF_CAPACITY = 1D;
    public static final double COST_TF_PER_TICK = 0.008D;
    public static final int RADIUS = 16;

    private final Random machineRand = new Random();
    public double tfPooled = 0D;
    public int interval = getNextInterval();
    public int tickCounter = 0;
    public int step = 0;
    public boolean isProcessingLastTick = false;
    public int saturatingTime = 0;
    public ContainerParamBool paramSuffocating = new ContainerParamBool(1, false);

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfSaturator";
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setFloat("amount", (float) tfPooled);
        nbtTagCompound.setShort("tick", (short) tickCounter);
        nbtTagCompound.setShort("intv", (short) interval);
        nbtTagCompound.setShort("step", (byte) step);
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        tfPooled = nbtTagCompound.getFloat("amount");
        tickCounter = nbtTagCompound.getShort("tick");
        interval = nbtTagCompound.getShort("intv");
        step = nbtTagCompound.getByte("step");
    }

    @SideOnly(Side.CLIENT)
    public double getProgressScaled()
    {
        return (double)this.tickCounter / (double)interval;
    }

    public boolean isProcessing()
    {
        return tfPooled >= COST_TF_PER_TICK && this.isRedstonePowered() && !this.paramSuffocating.get();
    }

    @SideOnly(Side.CLIENT)
    public double getTfCharged()
    {
        return this.tfPooled / TF_CAPACITY;
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote)
        {
            this.paramSuffocating.set(!worldObj.isAirBlock(xCoord, yCoord + 1, zCoord));

            boolean isProcessing = isProcessing();
            if (isProcessing)
            {
                if (tickCounter >= interval)
                {
                    saturateAround();
                    tickCounter = 0;
                    interval = getNextInterval();
                }

                tickCounter++;
                tfPooled -= COST_TF_PER_TICK;
                //ModLog.debug("t=%d, i=%d, s=%d", tickCounter, interval, step);
            }

            if (isProcessingLastTick != isProcessing())
            {
                BlockTfSaturator.updateMachineState(isProcessing(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
            isProcessingLastTick = isProcessing();
        }
        else
        {
            if (tickCounter >= interval)
            {
                saturatingTime = 30;
            }
            else if (saturatingTime > 0)
            {
                saturatingTime--;
            }
        }
    }

    public void saturateAround()
    {
        TileScanner scanner = new TileScanner(worldObj, xCoord, yCoord, zCoord);

        int len = Math.min(step * 2, RADIUS);
        scanner.scan(len, TileScanner.Method.full, new TileScanner.Impl()
        {
            public void apply(World world, int x, int y, int z)
            {
                Block b = world.getBlock(x, y, z);
                if (b instanceof BlockTofu)
                {
                    BlockTofu tofu = (BlockTofu)b;
                    if (tofu.canDrain() && tofu.isUnderWeight(worldObj, x, y, z))
                    {
                        tofu.drainOneStep(worldObj, x, y, z, machineRand);
                    }
                }
            }
        });

        if (++step * 2 > RADIUS) step = 1;
    }

    private int getNextInterval()
    {
        return 200 + machineRand.nextInt(400);
    }

    @Override
    public double getMaxTfCapacity()
    {
        return Math.min(0.1, TF_CAPACITY - tfPooled);
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return this.isRedstonePowered() ? COST_TF_PER_TICK : 0;
    }

    @Override
    public void chargeTf(double amount)
    {
        tfPooled += amount;
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }
}
