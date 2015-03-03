package tsuteto.tofu.data;

import com.google.common.collect.Lists;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import tsuteto.tofu.block.BlockTfAntenna;
import tsuteto.tofu.tileentity.TileEntityTfDistanceAntenna;
import tsuteto.tofu.util.TileCoord;

import java.util.List;

/**
 * Handles world info of TofuCraft
 *
 * @author Tsuteto
 *
 */
public class TfAntennaInfoHandler
{
    // For TF Distance Antenna
    public static final String NBT_ANTENNA = "Antenna";
    public static final String NBT_ANTENNA_ENTRY = "Entry";
    public static final String NBT_ANTENNA_DANT = "DAnt";
    public static final String NBT_ANTENNA_TYPE = "Type";
    public List<TileEntityTfDistanceAntenna.AntennaInfo> antennaInfoList = Lists.newArrayList();

    public TfAntennaInfoHandler()
    {
    }

    public TfAntennaInfoHandler(NBTTagCompound nbt)
    {
        // Load info from NBT
        NBTTagList nbttaglist = nbt.getTagList(NBT_ANTENNA, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound tag = nbttaglist.getCompoundTagAt(i);
            NBTTagCompound entry = tag.getCompoundTag(NBT_ANTENNA_ENTRY);

            TileEntityTfDistanceAntenna.AntennaInfo antenna = new TileEntityTfDistanceAntenna.AntennaInfo();
            antenna.isDistanceAntenna = entry.getBoolean(NBT_ANTENNA_DANT);
            antenna.antennaType = BlockTfAntenna.WAVE_LIST[entry.getInteger(NBT_ANTENNA_TYPE)];
            antenna.coord = TileCoord.readFromNBT(entry);

            antennaInfoList.add(antenna);
        }
    }

    public NBTTagCompound getNBTTagCompound()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToTagCompound(nbt);
        return nbt;
    }

    public void writeToTagCompound(NBTTagCompound nbt)
    {
        NBTTagList tagList = new NBTTagList();
        for (TileEntityTfDistanceAntenna.AntennaInfo antenna : antennaInfoList)
        {
            NBTTagCompound entry = new NBTTagCompound();
            entry.setBoolean(NBT_ANTENNA_DANT, antenna.isDistanceAntenna);
            entry.setInteger(NBT_ANTENNA_TYPE, antenna.antennaType.id);
            antenna.coord.writeToNBT(entry);
            tagList.appendTag(entry);
        }
        nbt.setTag(NBT_ANTENNA, tagList);
    }
}
