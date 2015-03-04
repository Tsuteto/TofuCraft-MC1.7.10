package tsuteto.tofu.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;

public class GuiUtils
{
    private static Field fldGuiLeft = ReflectionHelper.findField(GuiContainer.class, "field_147003_i", "guiLeft");
    private static Field fldGuiTop = ReflectionHelper.findField(GuiContainer.class, "field_147009_r", "guiTop");

    public static void drawColorTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6, int color, float zLevel)
    {
        if ((color & -67108864) == 0)
        {
            color |= -16777216;
        }
        drawColorTexturedModalRectRGBA(par1, par2, par3, par4, par5, par6, color, zLevel);
    }

    public static void drawColorTexturedModalRectRGBA(int par1, int par2, int par3, int par4, int par5, int par6, int color, float zLevel)
    {
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(r, g, b, a);
        tessellator.addVertexWithUV((double) (par1 + 0), (double) (par2 + par6), (double) zLevel, (double) ((float) (par3 + 0) * f), (double) ((float) (par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.draw();
    }

    public static void applyColorRGBA(int color)
    {
        int a = color >> 24 & 255;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        GL11.glColor4b((byte) r, (byte) g, (byte) b, (byte) a);
    }

    public static void applyColorRGB(int color)
    {
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        GL11.glColor4b((byte)r, (byte)g, (byte)b, (byte)255);
    }

    public static int getGuiLeft(GuiContainer gui)
    {
        try {
            return fldGuiLeft.getInt(gui);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    public static int getGuiTop(GuiContainer gui)
    {
        try {
            return fldGuiTop.getInt(gui);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

}
