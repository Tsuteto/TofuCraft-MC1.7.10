package tsuteto.tofu.gui;

public class GuiEntry
{
    final int id;
    final String baseName;

    public GuiEntry(int id, String name)
    {
        this.id = id;
        this.baseName = name;
    }

    public Class<?> getGuiClass()
    {
        try
        {
            return Class.forName("tsuteto.tofu.gui.Gui" + this.baseName);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Couldn't get a GUI class.", e);
        }
    }

    public Class<?> getTileEntityClass()
    {
        try
        {
            return Class.forName("tsuteto.tofu.tileentity.TileEntity" + this.baseName);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Couldn't get a TileEntity class.", e);
        }
    }
    public Class<?> getContainerClass()
    {
        try
        {
            return Class.forName("tsuteto.tofu.tileentity.Container" + this.baseName);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Couldn't get a Container class.", e);
        }
    }
}
