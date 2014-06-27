package tsuteto.tofu.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.ITfSupplier;
import tsuteto.tofu.api.tileentity.ITfTank;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineBase;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.TileCoord;
import tsuteto.tofu.util.TileScanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityTfAntenna extends TileEntityTfMachineBase
{
    public int radius = 10;

    private int step = 1;
    private boolean isInit = false;

    private final List<TileCoord> supplierList = new ArrayList<TileCoord>();
    private final List<TileCoord> consumerList = new ArrayList<TileCoord>();

    public double statTfBalance;
    public double statTfDemand;
    public double statTfSupply;

    public TileEntityTfAntenna() {}

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("step", (short)step);
        nbtTagCompound.setShort("r", (short)radius);
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        step = nbtTagCompound.getShort("step");
        radius = nbtTagCompound.getShort("r");
    }

    @Override
    protected String getInventoryNameTranslate()
    {
        return "container.tofucraft.TfAntenna";
    }

    /**
     * Overriden in a sign to provide the text.
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbttagcompound);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    /**
     * Update processing by a tick
     */
    @Override
    public void updateEntity()
    {
        if (worldObj.isRemote) return;

        if (!isInit)
        {
            TileScanner scanner = new TileScanner(worldObj, xCoord, yCoord, zCoord);
            this.scanWholeArea(scanner);
            isInit = true;
        }

        StringBuffer log = new StringBuffer();
        try
        {
            Iterator<TileCoord> itr;

            /*
             * Sum TF needed
             */
            double tfNeeded = 0.0D;
            double tfConsumed = 0.0D;
            itr = consumerList.iterator();
            while (itr.hasNext())
            {
                TileCoord coord = itr.next();
                TileEntity tileEntity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
                if (tileEntity instanceof ITfConsumer)
                {
                    tfNeeded += ((ITfConsumer)tileEntity).getMaxTfCapacity();
                    tfConsumed += ((ITfConsumer)tileEntity).getCurrentTfConsumed();
                }
                else if (worldObj.blockExists(coord.x, coord.y, coord.z)) // Checks if the block is loaded
                {
                    itr.remove();
                }
            }

            statTfDemand = tfConsumed;
            log.append(String.format("tfNeeded=%.1f, ", tfNeeded));

            /*
             * Draw TF from suppliers
             */
            double tfInput = 0D;
            itr = supplierList.iterator();
            while (itr.hasNext())
            {
                TileCoord coord = itr.next();
                TileEntity tileEntity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
                if (tileEntity instanceof ITfTank)
                {
                    ITfTank supplier = (ITfTank)tileEntity;
                    if (tfNeeded > 0.0D)
                    {
                        if (tfNeeded <= supplier.getMaxTfOffered())
                        {
                            supplier.drawTf(tfNeeded);
                            tfInput += tfNeeded;
                            tfNeeded = 0.0D;
                            break;
                        } else
                        {
                            double tfDrawn = supplier.getMaxTfOffered();
                            supplier.drawTf(tfDrawn);
                            tfInput += tfDrawn;
                            tfNeeded -= tfDrawn;
                        }
                    }
                }
                else if (tileEntity instanceof ITfSupplier)
                {
                    ITfSupplier supplier = (ITfSupplier)tileEntity;
                    double tfDrawn = supplier.getMaxTfOffered();
                    supplier.drawTf(tfDrawn);
                    tfInput += tfDrawn;
                    tfNeeded -= tfDrawn;
                }
                else if (worldObj.blockExists(coord.x, coord.y, coord.z))
                {
                    itr.remove();
                }
            }

            statTfSupply = tfInput;
            log.append(String.format("tfInput=%.1f, ", tfInput));
            if (tfInput == 0D) return;

            /*
             * Supply TF to consumers
             */
            double tfLeft = tfInput;
            itr = consumerList.iterator();
            while (itr.hasNext())
            {
                TileCoord coord = itr.next();
                TileEntity tileEntity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
                if (tileEntity instanceof ITfConsumer)
                {
                    ITfConsumer consumer = (ITfConsumer) tileEntity;
                    double tfOutput = consumer.getMaxTfCapacity();
                    if (tfLeft < tfOutput)
                    {
                        consumer.chargeTf(tfLeft);
                        tfLeft = 0D;
                        break;
                    }
                    else
                    {
                        tfLeft -= tfOutput;
                        consumer.chargeTf(consumer.getMaxTfCapacity());
                    }
                }
            }

            log.append(String.format("tfLeft=%.1f, ", tfLeft));
            if (tfLeft == 0D) return;

            /*
             * Charge extra TF to TF tank
             */
            itr = supplierList.iterator();
            while (itr.hasNext())
            {
                TileCoord coord = itr.next();
                TileEntity tileEntity = worldObj.getTileEntity(coord.x, coord.y, coord.z);
                if (tileEntity instanceof ITfTank)
                {
                    ITfTank tank = (ITfTank)tileEntity;
                    if (tfLeft > 0.0D && tfLeft <= tank.getMaxTfCapacity())
                    {
                        tank.drawTf(-tfLeft);
                        tfLeft = 0.0D;
                        break;
                    }
                    else
                    {
                        double tfCapacity = tank.getMaxTfCapacity();
                        tank.drawTf(-tfCapacity);
                        tfLeft -= tfCapacity;
                    }
                }
            }

            statTfBalance = tfLeft;
        }
        finally
        {
            //ModLog.debug(log);
            if (++step > radius) step = 1;
        }
    }

    public void scanWholeArea(TileScanner scanner)
    {

        scanner.scan(radius, TileScanner.Method.whole, new TileScanner.Impl()
        {
            public void apply(World world, int x, int y, int z)
            {
                TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
                registerMachine(tileEntity);
            }
        });
    }

    public void scanPartialArea(TileScanner scanner)
    {

        scanner.scan(step, TileScanner.Method.partial, new TileScanner.Impl()
        {
            public void apply(World world, int x, int y, int z)
            {
                TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
                if (tileEntity != null)
                {
                    registerMachine(tileEntity);
                }
            }
        });
    }

    public void registerMachine(TileEntity tileEntity)
    {
        if (tileEntity instanceof ITfSupplier)
        {
            supplierList.add(new TileCoord(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
            ModLog.debug("%s connected to antenna as supplier", tileEntity.getBlockType().getUnlocalizedName());
        }

        if (tileEntity instanceof ITfConsumer)
        {
            consumerList.add(new TileCoord(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
            ModLog.debug("%s connected to antenna as consumer", tileEntity.getBlockType().getUnlocalizedName());
        }
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }
}
