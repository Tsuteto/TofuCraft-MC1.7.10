package tsuteto.tofu.data;

import net.minecraft.nbt.NBTTagCompound;
import tsuteto.tofu.util.ModLog;

import java.io.File;

/**
 * Handles save data for Spelunker
 *
 * @author Tsuteto
 *
 */
public class TcSaveHandler extends ModSaveHandler
{
    public static final String DIR_NAME = "TofuCraft";
    public static final String FILE_ANTENNA = "Antenna";
    public static final String FILE_MORIJIO = "Morijio";

    public TcSaveHandler(File worldDir)
    {
        super(new File(worldDir, DIR_NAME));
    }

    public TfAntennaInfoHandler loadTfAntennaData()
    {
        NBTTagCompound var2 = this.readData(FILE_ANTENNA);
        if (var2 != null)
        {
            return new TfAntennaInfoHandler(var2);
        }
        else
        {
            TfAntennaInfoHandler newHandler = new TfAntennaInfoHandler();
            this.saveAntennaData(newHandler);
            return newHandler;
        }
    }

    public MorijioManager loadMorijioData()
    {
        NBTTagCompound var2 = this.readData(FILE_MORIJIO);
        if (var2 != null)
        {
            return new MorijioManager(var2);
        }
        else
        {
            MorijioManager newHandler = new MorijioManager();
            this.saveMorijioData(newHandler);
            return newHandler;
        }
    }

    public void saveAntennaData(TfAntennaInfoHandler worldInfo)
    {
        super.saveData(worldInfo.getNBTTagCompound(), FILE_ANTENNA);
    }

    public void saveMorijioData(MorijioManager worldInfo)
    {
        if (worldInfo.isModified())
        {
            super.saveData(worldInfo.getNBTTagCompound(), FILE_MORIJIO);
            ModLog.debug("Saved morijio save handler");
        }
    }
}
