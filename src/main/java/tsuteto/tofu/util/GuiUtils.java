package tsuteto.tofu.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
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
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(r, g, b, a);
        tessellator.addVertexWithUV((double) (par1 + 0), (double) (par2 + par6), (double) zLevel, (double) ((float) (par3 + 0) * f), (double) ((float) (par4 + par6) * f1));
        tessellator.addVertexWithUV((double) (par1 + par5), (double) (par2 + par6), (double) zLevel, (double) ((float) (par3 + par5) * f), (double) ((float) (par4 + par6) * f1));
        tessellator.addVertexWithUV((double) (par1 + par5), (double) (par2 + 0), (double) zLevel, (double) ((float) (par3 + par5) * f), (double) ((float) (par4 + 0) * f1));
        tessellator.addVertexWithUV((double) (par1 + 0), (double) (par2 + 0), (double) zLevel, (double) ((float) (par3 + 0) * f), (double) ((float) (par4 + 0) * f1));
        tessellator.draw();
    }
    
    public static void drawBorderedRect(int ox, int oy, int w, int h, int color, float zLevel)
    {
        drawRectangle(ox, oy, ox + w, oy + 1, color, zLevel);
        drawRectangle(ox, oy + h - 1, ox + w, oy + h, color, zLevel);
        drawRectangle(ox, oy, ox + 1, oy + h, color, zLevel);
        drawRectangle(ox + w - 1, oy, ox + w, oy + h, color, zLevel);
    }
    
    public static void drawRectangle(int x1, int y1, int x2, int y2, int color, float zLevel)
    {
        float f = (float)(color >> 24 & 255) / 255.0F;
        float f1 = (float)(color >> 16 & 255) / 255.0F;
        float f2 = (float)(color >> 8 & 255) / 255.0F;
        float f3 = (float)(color & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)x2, (double)y1, (double)zLevel);
        tessellator.addVertex((double)x1, (double)y1, (double)zLevel);
        tessellator.addVertex((double)x1, (double)y2, (double)zLevel);
        tessellator.addVertex((double)x2, (double)y2, (double)zLevel);
        tessellator.draw();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

    }

    public static void drawFluidGuage(int x, int y, FluidTank fluidTank, int width, int height, GuiScreen gui)
    {

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        FluidStack fluidStack = fluidTank.getFluid();

        ResourceLocation resLoc;
        if (fluidStack.getFluid().getSpriteNumber() == 0)
        {
            resLoc = TextureMap.locationBlocksTexture;
        }
        else
        {
            resLoc = TextureMap.locationItemsTexture;
        }

        gui.mc.getTextureManager().bindTexture(resLoc);
        applyColorRGB(fluidStack.getFluid().getColor(fluidStack));

        int heightInd = (int) (height * ((float) fluidTank.getFluidAmount() / (float) fluidTank.getCapacity()));
        int texX = x;
        int texY = y + (height - heightInd);

        int texW, texH;
        IIcon icon = fluidStack.getFluid().getIcon(fluidStack);

        for (int i = 0; i < width; i += 16)
        {

            for (int j = 0; j < heightInd; j += 16)
            {
                texW = Math.min(width - i, 16);
                texH = Math.min(heightInd - j, 16);
                gui.drawTexturedModelRectFromIcon(texX + i, texY + j, icon, texW, texH);
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

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
        GL11.glColor3ub((byte) r, (byte) g, (byte) b);
    }

    public static int getGuiLeft(GuiContainer gui)
    {
        try
        {
            return fldGuiLeft.getInt(gui);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    public static int getGuiTop(GuiContainer gui)
    {
        try
        {
            return fldGuiTop.getInt(gui);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

}
