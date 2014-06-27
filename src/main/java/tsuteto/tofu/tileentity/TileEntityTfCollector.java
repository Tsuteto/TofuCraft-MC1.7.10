package tsuteto.tofu.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tsuteto.tofu.Settings;
import tsuteto.tofu.api.tileentity.ITfSupplier;
import tsuteto.tofu.api.util.TfJitterControl;

public class TileEntityTfCollector extends TileEntity implements ITfSupplier
{
    private double output = 0.0D;
    private final TfJitterControl jitterControl = new TfJitterControl(1.0D, 1.2, 0.5D, 24000 / 4, 63L);

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
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

    public void updateEntity()
    {
        if (worldObj.isRemote) return;

        double weatherRate = worldObj.isRaining() ? 1.5 : 1.0;

        double jitter = jitterControl.getCurrentValue(worldObj, xCoord, yCoord, zCoord);
        output = (worldObj.getWorldInfo().getVanillaDimension() == Settings.tofuDimNo ? 2000D : 100D) / 24000D * weatherRate * jitter;
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
}
