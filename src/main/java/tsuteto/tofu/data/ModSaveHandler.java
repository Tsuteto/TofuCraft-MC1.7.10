package tsuteto.tofu.data;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.util.ModLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Handles save data file
 *
 * @author Tsuteto
 *
 */
public class ModSaveHandler
{
    private final File saveDirectory;

    private final long now = System.currentTimeMillis();

    public ModSaveHandler(File worldDir)
    {
        this.saveDirectory = worldDir;
        this.saveDirectory.mkdirs();
    }

    protected File getSaveDirectory()
    {
        return saveDirectory;
    }

    public NBTTagCompound readData(String par1Str)
    {
        try
        {
            File var2 = new File(this.saveDirectory, par1Str + ".dat");

            if (var2.exists())
            {
                return CompressedStreamTools.readCompressed(new FileInputStream(var2));
            }
        }
        catch (Exception var3)
        {
            ModLog.log(Level.WARN, var3, "Failed to load NBT data: %s", par1Str + ".dat");
        }

        return null;
    }

    public void saveData(NBTTagCompound var2, String filename)
    {
        try
        {
            File var3 = new File(this.saveDirectory, filename + ".dat.tmp");
            File var4 = new File(this.saveDirectory, filename + ".dat");
            CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));

            if (var4.exists())
            {
                var4.delete();
            }

            var3.renameTo(var4);
        }
        catch (Exception var5)
        {
            ModLog.log(Level.WARN, var5, "Failed to save NBT data: %s", filename + ".dat");
        }
    }

    /**
     * Returns an array of usernames for which player.dat exists.
     */
    public String[] getAvailableDatList()
    {
        String[] var1 = this.saveDirectory.list();

        for (int var2 = 0; var2 < var1.length; ++var2)
        {
            if (var1[var2].endsWith(".dat"))
            {
                var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
            }
        }

        return var1;
    }
}
