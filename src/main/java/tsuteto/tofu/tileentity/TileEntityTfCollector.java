package tsuteto.tofu.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import tsuteto.tofu.Settings;
import tsuteto.tofu.api.tileentity.ITfSupplier;
import tsuteto.tofu.api.tileentity.TileEntityTfMachineBase;
import tsuteto.tofu.api.util.TfJitterControl;

import java.util.EnumSet;

public class TileEntityTfCollector extends TileEntityTfMachineBase implements ITfSupplier
{
    private static final EnumSet<ForgeDirection> ENABLED_SIDES = EnumSet.of(
            ForgeDirection.UP,
            ForgeDirection.NORTH,
            ForgeDirection.WEST,
            ForgeDirection.SOUTH,
            ForgeDirection.EAST);

    private double output = 0.0D;
    private final TfJitterControl jitterControl = new TfJitterControl(1.0D, 1.2, 0.5D, 24000 / 4, 63L);

    @Override
    protected String getInventoryNameTranslate()
    {
        return null;
    }

    public void updateEntity()
    {
        if (worldObj.isRemote) return;

        if (worldObj.getHeightValue(xCoord, zCoord) - 10 < yCoord)
        {
            double weatherRate = worldObj.isRaining() ? 1.5D : 1.0D;

            int workingSides = 0;
            for (ForgeDirection dir : ENABLED_SIDES)
            {
                if (!worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ).isNormalCube())
                {
                    workingSides++;
                }
            }

            double jitter = jitterControl.getCurrentValue(worldObj, xCoord, yCoord, zCoord);
            output = (worldObj.getWorldInfo().getVanillaDimension() == Settings.tofuDimNo ? 2000D : 100D) / 24000D
                    * weatherRate * jitter * ((double) workingSides / ENABLED_SIDES.size());
        }
    }

    @Override
    public double getMaxTfOffered()
    {
        return output;
    }

    @Override
    public void drawTf(double amount)
    {
        output -= amount;
    }

    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        return false;
    }
}
