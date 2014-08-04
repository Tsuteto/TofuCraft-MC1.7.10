package tsuteto.tofu.gui;

import cpw.mods.fml.common.event.FMLLoadEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
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
    public static final int GUIID_TF_REFORMER2 = 6;
    public static final int GUIID_TF_SATURATOR = 7;

    private HashMap<Integer, GuiEntry> guiRegistry = new HashMap<Integer, GuiEntry>();

    public TcGuiHandler()
    {
        this.registerGuiEntry(createEntry(GUIID_SALT_FURNACE).withName("SaltFurnace"));
        this.registerGuiEntry(createEntry(GUIID_TF_STORAGE).withName("TfStorage"));
        this.registerGuiEntry(createEntry(GUIID_TF_ANTENNA).withName("TfAntenna"));
        this.registerGuiEntry(createEntry(GUIID_TF_CONDENSER).withName("TfCondenser"));
        this.registerGuiEntry(createEntry(GUIID_TF_OVEN).withName("TfOven"));
        this.registerGuiEntry(createEntry(GUIID_TF_REFORMER).withName("TfReformer"));
        this.registerGuiEntry(createEntry(GUIID_TF_REFORMER2).withName("TfReformer2", "TfReformer", "TfReformer2"));
        this.registerGuiEntry(createEntry(GUIID_TF_SATURATOR).withName("TfSaturator"));
    }

    private GuiEntry createEntry(int id)
    {
        if (FMLLaunchHandler.side() == Side.CLIENT)
        {
            return new GuiEntryClient(id);
        }
        else
        {
            return new GuiEntryServer(id);
        }
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
