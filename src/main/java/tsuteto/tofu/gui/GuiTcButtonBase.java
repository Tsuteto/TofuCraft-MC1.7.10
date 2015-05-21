package tsuteto.tofu.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;
import tsuteto.tofu.texture.TcTextures;

abstract public class GuiTcButtonBase<T extends GuiTcButtonBase> extends GuiButton
{
    protected final ResourceLocation texture = TcTextures.tfMachineGui;

    protected static final int TEX_DISABLED = 0;
    protected static final int TEX_ENABLED = 1;
    protected static final int TEX_HOVER = 2;

    protected TfMachineGuiParts[] guiParts = new TfMachineGuiParts[3];

    public GuiTcButtonBase(int id, int x, int y, TfMachineGuiParts guiPartEnabled, String str)
    {
        this(id, x, y, guiPartEnabled.xSize, guiPartEnabled.ySize, guiPartEnabled, str);
    }

    public GuiTcButtonBase(int id, int x, int y, int par4, int par5, TfMachineGuiParts guiPartEnabled, String str)
    {
        super(id, x, y, par4, par5, str);
        this.guiParts[TEX_DISABLED] = guiPartEnabled;
        this.guiParts[TEX_ENABLED] = guiPartEnabled;
        this.guiParts[TEX_HOVER] = guiPartEnabled;
    }

    public T setTextureDisabled(TfMachineGuiParts guiPart)
    {
        this.guiParts[TEX_DISABLED] = guiPart;
        return (T)this;
    }

    public T setTextureEnabled(TfMachineGuiParts guiPart)
    {
        this.guiParts[TEX_ENABLED] = guiPart;
        return (T)this;
    }

    public T setTextureHover(TfMachineGuiParts guiPart)
    {
        this.guiParts[TEX_HOVER] = guiPart;
        return (T)this;
    }

    abstract public void onMouseOver(int p_146111_1_, int p_146111_2_, GuiTfMachineBase gui);
}
