package tsuteto.tofu.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import tsuteto.tofu.api.tileentity.ITfConsumer;
import tsuteto.tofu.api.tileentity.ITfTank;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineBase;
import tsuteto.tofu.block.BlockTfAntenna;
import tsuteto.tofu.util.TileCoord;

import java.util.List;

public class TileEntityTfDistanceAntenna extends TileEntityTfMachineBase implements ITfTank, ITfConsumer
{
    public static final String NBT_NAME = "Name";
    public static final String NBT_SRC = "Src";

    public String antennaName = null;
    /** Another antenna that this antenna receives TF from */
    public TileCoord antennaSrcLoc = null;

    public List<AntennaInfo> antennaFound = Lists.newArrayList();
    public int searchRange = 0;

    /** TF to send */
    private double tfBuffer = 0D;

    @Override
    protected String getInventoryNameTranslate()
    {
        return "";
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);

        nbtTagCompound.setString(NBT_NAME, antennaName);

        if (antennaSrcLoc != null)
        {
            if (!nbtTagCompound.hasKey(NBT_SRC))
            {
                nbtTagCompound.setTag(NBT_SRC, new NBTTagCompound());
            }

            antennaSrcLoc.writeToNBT(nbtTagCompound.getCompoundTag(NBT_SRC));
        }
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);

        this.antennaName = nbtTagCompound.getString(NBT_NAME);

        if (nbtTagCompound.hasKey(NBT_SRC))
        {
            antennaSrcLoc = TileCoord.readFromNBT(nbtTagCompound.getCompoundTag(NBT_SRC));
        }
    }

    public void setAntenna(int x, int y, int z, int dim)
    {
        antennaSrcLoc = new TileCoord(x, y, z, dim);
    }

    public void clearAntenna()
    {
        antennaSrcLoc = null;
    }

    public TileEntityTfDistanceAntenna getSourceAntenna()
    {
        if (antennaSrcLoc != null)
        {
            World world = DimensionManager.getWorld(antennaSrcLoc.dim);
            if (world != null)
            {
                TileEntity te = this.getWorldObj().getTileEntity(antennaSrcLoc.x, antennaSrcLoc.y, antennaSrcLoc.z);
                if (te instanceof TileEntityTfDistanceAntenna)
                {
                    return (TileEntityTfDistanceAntenna) te;
                }
                else
                {
                    this.clearAntenna();
                }
            }
        }
        return null;
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }

    @Override
    public double getMaxTfOffered()
    {
        TileEntityTfDistanceAntenna srcAntenna = this.getSourceAntenna();
        if (srcAntenna != null)
        {
            return this.tfBuffer;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public void drawTf(double amount)
    {
        TileEntityTfDistanceAntenna srcAntenna = this.getSourceAntenna();
        if (srcAntenna != null)
        {
            srcAntenna.tfBuffer -= amount;
        }
    }

    @Override
    public double getMaxTfCapacity()
    {
        return this.tfBuffer == 0 ? Integer.MAX_VALUE : 0;
    }

    @Override
    public double getCurrentTfConsumed()
    {
        return 0;
    }

    @Override
    public void chargeTf(double amount)
    {
        this.tfBuffer += amount;
    }

    public static class AntennaInfo
    {
        public TileCoord coord;
        public boolean isDistanceAntenna = false;
        public BlockTfAntenna.WaveFreq antennaType;
    }
}
