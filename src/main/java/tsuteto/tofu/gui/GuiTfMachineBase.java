package tsuteto.tofu.gui;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

abstract public class GuiTfMachineBase extends GuiContainer
{
    public static final ResourceLocation tfSignResource = new ResourceLocation("tofucraft", "textures/gui/tfsign.png");
    
    protected enum HoverTextPosition
    {
        MC_STANDARD,
        UPPER_CENTER,
        LOWER_CENTER,
    }

    public GuiTfMachineBase(Container par1Container)
    {
        super(par1Container);
    }
    
    protected void printTfSign(int x, int y, int color)
    {
        this.mc.getTextureManager().bindTexture(tfSignResource);
        if ((color & -67108864) == 0)
        {
            color |= -16777216;
        }
        float red = (color >> 16 & 255) / 255.0F;
        float blue = (color >> 8 & 255) / 255.0F;
        float green = (color & 255) / 255.0F;
        float alpha = (color >> 24 & 255) / 255.0F;
        GL11.glColor4f(red, blue, green, alpha);
        
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0d, y + 8d, this.zLevel, 0d, 1d);
        tessellator.addVertexWithUV(x + 8d, y + 8d, this.zLevel, 1d, 1d);
        tessellator.addVertexWithUV(x + 8d, y + 0d, this.zLevel, 1d, 0d);
        tessellator.addVertexWithUV(x + 0d, y + 0d, this.zLevel, 0d, 0d);
        tessellator.draw();
    }

    protected void drawTfHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        this.drawTfHoveringText(par1List, par2, par3, font, HoverTextPosition.UPPER_CENTER);
    }
    
    protected void drawTfHoveringText(List par1List, int par2, int par3, FontRenderer font, HoverTextPosition pos)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }
            
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            this.zLevel = 300.0F;
            this.itemRender.zLevel = 300.0F;
            
            int i1 = calcHoverOffsetX(par2, k, pos);
            int j1 = calcHoverOffsetY(par3, k1, pos);
            
            this.doDrawTfHoveringTextBg(i1, j1, k, k1, 1);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, i1, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    protected void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh)
    {
        this.drawTfHoveringTipFixedSize(ox, oy, fw, fh, HoverTextPosition.UPPER_CENTER, null);
    }
    
    protected void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh, HoverTextPosition pos)
    {
        this.drawTfHoveringTipFixedSize(ox, oy, fw, fh, pos, null);
    }
    
    protected void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh, HoverTextPosition pos, IHoverDrawingHandler handler)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        this.zLevel = 300.0F;
        this.itemRender.zLevel = 300.0F;
        
        int i1 = calcHoverOffsetX(ox, fw, pos);
        int j1 = calcHoverOffsetY(oy, fh, pos);

        this.doDrawTfHoveringTextBg(i1, j1, fw, fh, 0);
        
        if (handler != null)
        {
            handler.draw(i1, j1, fw, fh);
        }
        
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }
    
    private void doDrawTfHoveringTextBg(int ox, int oy, int fw, int fh, int p)
    {
        if (ox + fw > this.width)
        {
            ox -= 28 + fw;
        }

        if (oy + fh + 6 > this.height)
        {
            oy = this.height - fh - 6;
        }

        int color;
        // Border
        color = 0xff878a7c;
        this.drawGradientRect(ox - p,      oy - p - 1,  ox + fw + p,     oy - p,          color, color);
        this.drawGradientRect(ox - p,      oy + fh + p, ox + fw + p,     oy + fh + p + 1, color, color);
        this.drawGradientRect(ox - p - 1,  oy - p,      ox - p,          oy + fh + p,     color, color);
        this.drawGradientRect(ox + fw + p, oy - p,      ox + fw + p + 1, oy + fh + p,     color, color);
        // Background
        color = 0xff5c5e54;
        this.drawGradientRect(ox - p,      oy - p,      ox + fw + p, oy + fh + p, color, color);
    }
    
    private int calcHoverOffsetX(int mouseX, int fw, HoverTextPosition pos)
    {
        switch (pos)
        {
        case MC_STANDARD:
            return mouseX + 12;
        case UPPER_CENTER:
            return mouseX - fw / 2;
        case LOWER_CENTER:
            return mouseX - fw / 2;
        default:
            return mouseX;
        }
    }
    
    private int calcHoverOffsetY(int mouseY, int fh, HoverTextPosition pos)
    {
        switch (pos)
        {
        case MC_STANDARD:
            return mouseY - 12;
        case UPPER_CENTER:
            return mouseY - fh - 5;
        case LOWER_CENTER:
            return mouseY + 8;
        default:
            return mouseY;
        }
    }

    protected interface IHoverDrawingHandler
    {
        void draw(int ox, int oy, int fw, int fh);
    }
}
