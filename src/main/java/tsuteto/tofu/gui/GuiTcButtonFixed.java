package tsuteto.tofu.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class GuiTcButtonFixed extends GuiTcButtonBase<GuiTcButtonFixed>
{
    protected GuiTfMachineBase.HoverTextPosition tipPosition = GuiTfMachineBase.HoverTextPosition.UPPER_CENTER;

    public GuiTcButtonFixed(int id, int x, int y, TfMachineGuiParts guiPartEnabled, String str)
    {
        super(id, x, y, guiPartEnabled, str);
    }

    public GuiTcButtonFixed setTipPosition(GuiTfMachineBase.HoverTextPosition pos)
    {
        this.tipPosition = pos;
        return this;
    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (this.visible)
        {
            p_146112_1_.getTextureManager().bindTexture(texture);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, guiParts[k].ox, guiParts[k].oy, this.width, this.height);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
        }
    }

    public void onMouseOver(int p_146111_1_, int p_146111_2_, GuiTfMachineBase gui)
    {
        gui.drawTfHoveringText(this.displayString, p_146111_1_, p_146111_2_, tipPosition);
    }

}
