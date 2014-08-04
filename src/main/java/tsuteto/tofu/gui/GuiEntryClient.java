package tsuteto.tofu.gui;

public class GuiEntryClient extends GuiEntry

{
    public GuiEntryClient(int id)
    {
        super(id);
    }

    @Override
    public GuiEntry withName(String guiClass, String tileEntityClass, String containerClass)
    {
        super.withName(guiClass, tileEntityClass, containerClass);
        try
        {
            this.guiClass = Class.forName("tsuteto.tofu.gui.Gui" + guiClass);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return this;
    }
}
