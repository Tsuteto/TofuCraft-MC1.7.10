package tsuteto.tofu.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import tsuteto.tofu.block.BlockMorijio;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.tileentity.TileEntityMorijio;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.TileCoord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages Morijio info and checks if a coord is in range
 *
 * @author Tsuteto
 *
 */
public class MorijioManager
{
    public static final String NBT_MORIJIO = "Morijio";
    public static final String NBT_MORIJIO_COORD = "Coord";

    public List<TileEntityMorijio.MorijioInfo> morijioInfoList = Lists.newArrayList();
    public Map<Integer, Map<Integer, Map<Integer, List<TileEntityMorijio.MorijioInfo>>>> morijioInfoMapping = Maps.newHashMap();
    private boolean modified = false;

    public MorijioManager()
    {
    }

    public MorijioManager(NBTTagCompound nbt)
    {
        // Load Morijio info from NBT
        NBTTagList nbttaglist = nbt.getTagList(NBT_MORIJIO, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
            TileEntityMorijio.MorijioInfo morijio = new TileEntityMorijio.MorijioInfo();
            morijio.coord = TileCoord.readFromNBT(tag.getCompoundTag(NBT_MORIJIO_COORD));
            this.addInfo(morijio);
        }
    }

    public boolean isInfoRegistered(TileEntityMorijio.MorijioInfo info)
    {
        DataSectorCoord sectorCoord = DataSectorCoord.getFromCoord(info.coord.x, info.coord.z, info.coord.dim);
        List<TileEntityMorijio.MorijioInfo> list = this.getInfo(sectorCoord);
        return list != null && list.contains(info);
    }

    public void registerInfo(TileEntityMorijio.MorijioInfo info)
    {
        this.addInfo(info);
        this.modified = true;
        ModLog.debug("Registered morijio info at: (%d, %d, %d), %d left", info.coord.x, info.coord.y, info.coord.z, morijioInfoList.size());
    }

    public boolean isInRangeOfMorijio(World world, int tileX, int tileY, int tileZ, int dim)
    {
        int range = BlockMorijio.EFFECTIVE_RANGE;
        List<TileEntityMorijio.MorijioInfo> infoList = this.getInfo(
                tileX - range, tileZ - range, tileX + range, tileZ + range, dim);

        for (TileEntityMorijio.MorijioInfo morijio : infoList)
        {
            if (Math.abs(tileX - morijio.coord.x) <= range
                    && Math.abs(tileY - morijio.coord.y) <= range
                    && Math.abs(tileZ - morijio.coord.z) <= range)
            {
                Block block = world.getBlock(morijio.coord.x, morijio.coord.y, morijio.coord.z);
                if (block == TcBlocks.morijio)
                {
                    ModLog.debug("In range of Morishio at relatively (%d, %d, %d)",
                            tileX - morijio.coord.x, tileY - morijio.coord.y, tileZ - morijio.coord.z);
                    return true;
                }
                else if (world.blockExists(morijio.coord.x, morijio.coord.y, morijio.coord.z))
                {
                    this.removeInfo(morijio);
                }
            }
        }
        return false;
    }

    private void addInfo(TileEntityMorijio.MorijioInfo info)
    {
        DataSectorCoord sectorCoord = DataSectorCoord.getFromCoord(info.coord.x, info.coord.z, info.coord.dim);

        if (!morijioInfoMapping.containsKey(sectorCoord.dim))
        {
            morijioInfoMapping.put(sectorCoord.dim, new HashMap<Integer, Map<Integer, List<TileEntityMorijio.MorijioInfo>>>());
        }

        Map<Integer, Map<Integer, List<TileEntityMorijio.MorijioInfo>>> mappingX = morijioInfoMapping.get(sectorCoord.dim);
        if (!mappingX.containsKey(sectorCoord.xSector))
        {
            mappingX.put(sectorCoord.xSector, new HashMap<Integer, List<TileEntityMorijio.MorijioInfo>>());
        }

        Map<Integer, List<TileEntityMorijio.MorijioInfo>> mappingZ = mappingX.get(sectorCoord.xSector);
        if (!mappingZ.containsKey(sectorCoord.zSector))
        {
            mappingZ.put(sectorCoord.zSector, new ArrayList<TileEntityMorijio.MorijioInfo>());
        }

        mappingZ.get(sectorCoord.zSector).add(info);
        morijioInfoList.add(info);
    }

    public void removeInfo(TileEntityMorijio.MorijioInfo info)
    {
        DataSectorCoord sectorCoord = DataSectorCoord.getFromCoord(info.coord.x, info.coord.z, info.coord.dim);
        List<TileEntityMorijio.MorijioInfo> list = this.getInfo(sectorCoord);
        if (list != null && list.contains(info))
        {
            list.remove(info);
            morijioInfoList.remove(info);
            this.modified = true;
            ModLog.debug("Removed morijio info at: (%d, %d, %d), %d left", info.coord.x, info.coord.y, info.coord.z, morijioInfoList.size());
        }
    }

    private List<TileEntityMorijio.MorijioInfo> getInfo(DataSectorCoord sectorCoord)
    {
        if (morijioInfoMapping.containsKey(sectorCoord.dim))
        {
            Map<Integer, Map<Integer, List<TileEntityMorijio.MorijioInfo>>> mappingX = morijioInfoMapping.get(sectorCoord.dim);
            if (mappingX.containsKey(sectorCoord.xSector))
            {
                Map<Integer, List<TileEntityMorijio.MorijioInfo>> mappingZ = mappingX.get(sectorCoord.xSector);
                if (mappingZ.containsKey(sectorCoord.zSector))
                {
                    return mappingZ.get(sectorCoord.zSector);
                }
            }
        }
        return null;
    }

    public List<TileEntityMorijio.MorijioInfo> getInfo(int xCoord1, int zCoord1, int xCoord2, int zCoord2, int dim)
    {
        List<TileEntityMorijio.MorijioInfo> list = Lists.newArrayList();
        DataSectorCoord sector1 = DataSectorCoord.getFromCoord(Math.min(xCoord1, xCoord2), Math.min(zCoord1, zCoord2), dim);
        DataSectorCoord sector2 = DataSectorCoord.getFromCoord(Math.max(xCoord1, xCoord2), Math.max(zCoord1, zCoord2), dim);

        for (int sectX = sector1.xSector; sectX <= sector2.xSector; sectX++)
        {
            for (int sectZ = sector1.zSector; sectZ <= sector2.zSector; sectZ++)
            {
                List<TileEntityMorijio.MorijioInfo> sectorInfo = this.getInfo(new DataSectorCoord(sectX, sectZ, dim));
                if (sectorInfo != null)
                {
                    list.addAll(sectorInfo);
                }
            }
        }
        return list;
    }

    public boolean isModified()
    {
        return modified;
    }

    public NBTTagCompound getNBTTagCompound()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToTagCompound(nbt);
        return nbt;
    }

    private void writeToTagCompound(NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();
        for (TileEntityMorijio.MorijioInfo morijio : morijioInfoList)
        {
            NBTTagCompound entry = new NBTTagCompound();
            NBTTagCompound coord = new NBTTagCompound();
            morijio.coord.writeToNBT(coord);
            entry.setTag(NBT_MORIJIO_COORD, coord);
            tagList.appendTag(entry);
        }
        nbt.setTag(NBT_MORIJIO, tagList);
        this.modified = false;
    }

    public static class DataSectorCoord
    {
        public final int xSector;
        public final int zSector;
        public final int dim;

        public DataSectorCoord(int xSector, int zSector, int dim)
        {
            this.xSector = xSector;
            this.zSector = zSector;
            this.dim = dim;
        }

        public static DataSectorCoord getFromCoord(int xCoord, int zCoord, int dim)
        {
            return new DataSectorCoord(xCoord >> 6, zCoord >> 6, dim);
        }
    }
}
