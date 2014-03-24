package tsuteto.tofu.gui;

import tsuteto.tofu.block.tileentity.ContainerSaltFurnace;
import tsuteto.tofu.block.tileentity.ContainerTfStorage;
import tsuteto.tofu.block.tileentity.TileEntitySaltFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import tsuteto.tofu.block.tileentity.TileEntityTfStorage;

public class TcGuiHandler implements IGuiHandler
{
    public static int GUIID_SALT_FURNACE = 0;
    public static int GUIID_TF_STORAGE = 1;

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        if (guiId == GUIID_SALT_FURNACE)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntitySaltFurnace)
            {
                return new ContainerSaltFurnace(player.inventory, (TileEntitySaltFurnace) tile);
            }
        }

        if (guiId == GUIID_TF_STORAGE)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityTfStorage)
            {
                return new ContainerTfStorage(player.inventory, (TileEntityTfStorage) tile);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z)
    {
        if (guiId == GUIID_SALT_FURNACE)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntitySaltFurnace)
            {
                return new GuiSaltFurnace(player.inventory, (TileEntitySaltFurnace) tile);
            }
        }

        if (guiId == GUIID_TF_STORAGE)
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityTfStorage)
            {
                return new GuiTfStorage(player.inventory, (TileEntityTfStorage) tile);
            }
        }
        return null;
    }
}
