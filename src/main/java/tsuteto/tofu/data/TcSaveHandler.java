package tsuteto.tofu.data;

import net.minecraft.nbt.NBTTagCompound;

import java.io.File;

/**
 * Handles save data for Spelunker
 *
 * @author Tsuteto
 *
 */
public class TcSaveHandler extends ModSaveHandler
{
    private static final String filename = "TofuCraft";

    public TcSaveHandler(File worldDir)
    {
        super(worldDir);
    }

    public TcWorldInfo loadData()
    {
        NBTTagCompound var2 = this.readData(filename);
        if (var2 != null)
        {
            return new TcWorldInfo(var2);
        }
        else
        {
            return null;
        }
    }

    public void saveData(TcWorldInfo worldInfo)
    {
        super.saveData(worldInfo.getNBTTagCompound(), filename);
    }
}
