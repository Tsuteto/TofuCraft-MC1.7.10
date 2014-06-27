package tsuteto.tofu.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import tsuteto.tofu.TcTextures;
import tsuteto.tofu.api.tileentity.SlotTfMachine;
import tsuteto.tofu.util.GuiUtils;

import java.util.Iterator;
import java.util.List;

abstract public class GuiTfMachineBase extends GuiContainer
{
    protected static final ResourceLocation texture = TcTextures.tfMachineGui;
    public static final int COLOR_TIP_TEXT = 0xf1f2e6;

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

    public void drawStandardBasePanel()
    {
        this.drawBasePanel(this.xSize, this.ySize - 101);
        this.drawGuiPart(0, this.ySize - 98, TfMachineGuiParts.playerInventory);
    }

    public void drawBasePanel(int w, int h)
    {
        int sx = this.guiLeft;
        int sy = this.guiTop;
        // corner size
        int cw = 5;
        int ch = 5;

        this.drawGuiPart(0, 0, TfMachineGuiParts.baseLeftTop);
        this.drawGuiPart(w - cw, 0, TfMachineGuiParts.baseRightTop);
        this.drawGuiPart(0, h - ch, TfMachineGuiParts.baseLeftBottom);
        this.drawGuiPart(w - cw, h - ch, TfMachineGuiParts.baseRightBottom);

        int color;
        // Border
        color = 0xffb5b7a5;
        this.drawGradientRect(sx + cw,    sy        , sx + w - cw, sy + 1     , color, color);
        this.drawGradientRect(sx + cw,    sy + h - 1, sx + w - cw, sy + h     , color, color);
        this.drawGradientRect(sx,         sy + ch   , sx + 1,      sy + h - ch, color, color);
        this.drawGradientRect(sx + w - 1, sy + ch   , sx + w,      sy + h - ch, color, color);

        // BG
        color = 0xfff1f2e6;
        this.drawGradientRect(sx + cw, sy + 1,  sx + w - cw, sy + h - 1,  color, color);
        this.drawGradientRect(sx + 1 , sy + ch, sx + w - 1 , sy + h - ch, color, color);
    }

    public void drawTfMachineSlot()
    {
        for (Object slot : inventorySlots.inventorySlots)
        {
            if (slot instanceof SlotTfMachine)
            {
                SlotTfMachine machineSlot = (SlotTfMachine)slot;
                this.drawGuiPart(machineSlot.xBgPosition, machineSlot.yBgPosition, machineSlot.getGuiPart());
            }
        }
    }

    public void drawGuiPart(int x, int y, TfMachineGuiParts part)
    {
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, part.ox, part.oy, part.xSize, part.ySize);
    }

    public void drawGuiPartFg(int x, int y, TfMachineGuiParts part)
    {
        this.drawTexturedModalRect(x, y, part.ox, part.oy, part.xSize, part.ySize);
    }

    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6, int color)
    {
        GuiUtils.drawColorTexturedModalRect(par1, par2, par3, par4, par5, par6, color, this.zLevel);
    }

    public void drawItemStack(ItemStack p_146982_1_, int x, int y)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (p_146982_1_ != null) font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, x, y);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, x, y, null);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

    public void printTfSign(int x, int y, int color)
    {
        this.mc.getTextureManager().bindTexture(TcTextures.tfSign);
        if ((color & -67108864) == 0)
        {
            color |= -16777216;
        }
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        float a = (color >> 24 & 255) / 255.0F;

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(r, g, b, a);
        tessellator.addVertexWithUV(x + 0d, y + 8d, this.zLevel, 0d, 1d);
        tessellator.addVertexWithUV(x + 8d, y + 8d, this.zLevel, 1d, 1d);
        tessellator.addVertexWithUV(x + 8d, y + 0d, this.zLevel, 1d, 0d);
        tessellator.addVertexWithUV(x + 0d, y + 0d, this.zLevel, 0d, 0d);
        tessellator.draw();
    }

    public int getGuiLeft()
    {
        return this.guiLeft;
    }

    public int getGuiTop()
    {
        return this.guiTop;
    }

    public void drawTfHoveringText(String str, int par2, int par3)
    {
        this.drawTfHoveringText(str, par2, par3, HoverTextPosition.UPPER_CENTER);
    }

    public void drawTfHoveringText(String str, int par2, int par3, HoverTextPosition pos)
    {
        this.drawTfHoveringText(str, par2, par3, pos, COLOR_TIP_TEXT);
    }

    public void drawTfHoveringText(String str, int par2, int par3, HoverTextPosition pos, int color)
    {
        List<String> list = Lists.newArrayList();
        list.add(str);
        this.drawTfHoveringText(list, par2, par3, pos, color);
    }

    public void drawTfHoveringText(List par1List, int par2, int par3)
    {
        this.drawTfHoveringText(par1List, par2, par3, HoverTextPosition.UPPER_CENTER);
    }

    public void drawTfHoveringText(List par1List, int par2, int par3, HoverTextPosition pos)
    {
        this.drawTfHoveringText(par1List, par2, par3, pos, COLOR_TIP_TEXT);
    }

    public void drawTfHoveringText(List par1List, int par2, int par3, HoverTextPosition pos, int color)
    {
        par2 -= this.guiLeft;
        par3 -= this.guiTop;

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
                int l = fontRendererObj.getStringWidth(s);

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
            
            this.doDrawTfHoveringTextBg(i1, j1, k, k1, 2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                fontRendererObj.drawString(s1, i1, j1, color);

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

    public void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh)
    {
        this.drawTfHoveringTipFixedSize(ox, oy, fw, fh, HoverTextPosition.UPPER_CENTER, null);
    }

    public void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh, HoverTextPosition pos)
    {
        this.drawTfHoveringTipFixedSize(ox, oy, fw, fh, pos, null);
    }

    public void drawTfHoveringTipFixedSize(int ox, int oy, int fw, int fh, HoverTextPosition pos, IHoverDrawingHandler handler)
    {
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        this.zLevel = 300.0F;
        this.itemRender.zLevel = 300.0F;

        ox -= this.guiLeft;
        oy -= this.guiTop;
        
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
            return mouseY - fh - 6;
        case LOWER_CENTER:
            return mouseY + 8;
        default:
            return mouseY;
        }
    }

    public boolean isPointInRegion(int ox, int oy, int w, int h, int x, int y)
    {
        return this.func_146978_c(ox, oy, w, h, x, y);
    }

    protected interface IHoverDrawingHandler
    {
        void draw(int ox, int oy, int fw, int fh);
    }
}
