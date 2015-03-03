package tsuteto.tofu.recipe.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.client.FMLClientHandler;
import tsuteto.tofu.TcTextures;
import tsuteto.tofu.util.GuiUtils;

import java.awt.*;
import java.util.List;

public abstract class TfMachineRecipeHandlerBase extends TemplateRecipeHandler
{
    protected TfMachineDrawingHandler drawingHandler;
    protected int ticks;

    public void init()
    {
        this.drawingHandler = new TfMachineDrawingHandler();
    }

    @Override
    public String getGuiTexture()
    {
        return TcTextures.tfMachineGui.toString();
    }

    public void loadTransferRects()
    {
        // initialize here, called from TemplateRecipeHandler earlier than constructor
        this.init();
    }

    public void onUpdate()
    {
        super.onUpdate();
        ++this.ticks;
        this.drawingHandler.setParentGui(FMLClientHandler.instance().getClient().currentScreen);
    }

    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe)
    {
        final int guiLeft = GuiUtils.getGuiLeft(gui);
        final int guiTop = GuiUtils.getGuiTop(gui);

        TemplateRecipeHandler.CachedRecipe recipeObj = this.arecipes.get(recipe);
        Point mousepos = GuiDraw.getMousePosition();
        final Point offset = gui.getRecipePosition(recipe);
        Point relMouse = new Point(mousepos.x - guiLeft - offset.x, mousepos.y - guiTop - offset.y);

        drawingHandler.setParentGui(gui);
        drawingHandler.bindStandardTexture();
        drawingHandler.toolTipOffsetX = guiLeft + offset.x;
        drawingHandler.toolTipOffsetY = guiTop + offset.y;

        this.handleGuiPartToolTip(relMouse.x, relMouse.y, recipeObj);

        return super.handleTooltip(gui, currenttip, recipe);
    }

    protected void handleGuiPartToolTip(int x, int y, final CachedRecipe recipe) {}

    public class ItemSlotPosition
    {
        public final int x;
        public final int y;

        public ItemSlotPosition(int x, int y)
        {
            this.x = x + drawingHandler.getGuiLeft();
            this.y = y + drawingHandler.getGuiTop();
        }
    }
}
