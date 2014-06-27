package tsuteto.tofu.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.util.ModLog;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class TcGuiHandler implements IGuiHandler
{
    public static final int GUIID_SALT_FURNACE = 0;
    public static final int GUIID_TF_STORAGE = 1;
    public static final int GUIID_TF_ANTENNA = 2;
    public static final int GUIID_TF_CONDENSER = 3;
    public static final int GUIID_TF_OVEN = 4;
    public static final int GUIID_TF_REFORMER = 5;

    private HashMap<Integer, GuiEntry> guiRegistry = new HashMap<Integer, GuiEntry>();

    public TcGuiHandler()
    {
        this.registerGuiEntry(new GuiEntry(GUIID_SALT_FURNACE, "SaltFurnace"));
        this.registerGuiEntry(new GuiEntry(GUIID_TF_STORAGE, "TfStorage"));
        this.registerGuiEntry(new GuiEntry(GUIID_TF_ANTENNA, "TfAntenna"));
        this.registerGuiEntry(new GuiEntry(GUIID_TF_CONDENSER, "TfCondenser"));
        this.registerGuiEntry(new GuiEntry(GUIID_TF_OVEN, "TfOven"));
        this.registerGuiEntry(new GuiEntry(GUIID_TF_REFORMER, "TfReformer"));
    }

    public void registerGuiEntry(GuiEntry entry)
    {
        guiRegistry.put(entry.id, entry);
    }

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        GuiEntry entry = guiRegistry.get(guiId);

        if (entry != null)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
            {
                try
                {
                    Constructor constructor = entry.getContainerClass().getConstructor(player.inventory.getClass(), entry.getTileEntityClass());
                    return constructor.newInstance(player.inventory, tile);
                } catch (Exception e)
                {
                    ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
                }
            }
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        GuiEntry entry = guiRegistry.get(guiId);

        if (entry != null)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (entry.getTileEntityClass().isAssignableFrom(tile.getClass()))
            {
                try
                {
                    Constructor constructor = entry.getGuiClass().getConstructor(player.inventory.getClass(), entry.getTileEntityClass());
                    return constructor.newInstance(player.inventory, tile);
                } catch (Exception e)
                {
                    ModLog.log(Level.WARN, e, "Failed to open a gui screen!");
                }
            }
        }

        return null;
    }
}
