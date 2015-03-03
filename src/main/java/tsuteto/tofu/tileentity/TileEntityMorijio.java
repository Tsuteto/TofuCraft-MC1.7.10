package tsuteto.tofu.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.data.MorijioManager;
import tsuteto.tofu.util.TileCoord;

public class TileEntityMorijio extends TileEntity
{
    /** The morijio's rotation. */
    private int rotation;

    private boolean isInit = false;
    private MorijioInfo info;

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote && !this.isInit)
        {
            this.info = new MorijioInfo();
            this.info.coord = new TileCoord(this.xCoord, this.yCoord, this.zCoord, this.worldObj.getWorldInfo().getVanillaDimension());

            MorijioManager infoHandler = TofuCraftCore.getMorijioManager();
            if (infoHandler != null)
            {
                if (!infoHandler.isInfoRegistered(info))
                {
                    infoHandler.registerInfo(info);
                }
                this.isInit = true;
            }
        }
    }

    public void removeInfo()
    {
        MorijioManager infoHandler = TofuCraftCore.getMorijioManager();
        infoHandler.removeInfo(this.info);
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("Rot", (byte)(this.rotation & 255));
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.rotation = par1NBTTagCompound.getByte("Rot");
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.readFromNBT(pkt.func_148857_g());
    }

    /**
     * Overriden in a sign to provide the text.
     */
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 4, nbttagcompound);
    }

    /**
     * Set the morijio's rotation
     */
    public void setRotation(int par1)
    {
        this.rotation = par1;
    }

    @SideOnly(Side.CLIENT)
    public int getRotation()
    {
        return this.rotation;
    }

    public static class MorijioInfo
    {
        public TileCoord coord;

        @Override
        public boolean equals(Object obj)
        {
            if (coord == null || !(obj instanceof MorijioInfo)) return false;
            return coord.equals(((MorijioInfo)obj).coord);
        }

        @Override
        public int hashCode()
        {
            return coord.hashCode();
        }
    }
}
