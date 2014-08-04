package tsuteto.tofu.gui;

abstract public class GuiEntry
{
    public final int id;

    protected Class<?> guiClass;
    protected Class<?> tileEntityClass;
    protected Class<?> containerClass;

    public GuiEntry(int id)
    {
        this.id = id;
    }

    public GuiEntry withName(String name)
    {
        this.withName(name, name, name);
        return this;
    }

    public GuiEntry withName(String guiClass, String tileEntityClass, String containerClass)
    {
        try
        {
            this.tileEntityClass = Class.forName("tsuteto.tofu.tileentity.TileEntity" + tileEntityClass);
            this.containerClass = Class.forName("tsuteto.tofu.tileentity.Container" + containerClass);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return this;
    }

    public Class<?> getGuiClass()
    {
        return this.guiClass;
    }

    public Class<?> getTileEntityClass()
    {
        return this.tileEntityClass;
    }

    public Class<?> getContainerClass()
    {
        return this.containerClass;
    }
}
