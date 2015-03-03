package tsuteto.tofu.recipe.nei;

import codechicken.nei.PositionedStack;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.gui.GuiTfMachineBase;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeBase;
import tsuteto.tofu.gui.guiparts.GuiPartGaugeH;
import tsuteto.tofu.gui.guiparts.HoverTextPosition;
import tsuteto.tofu.gui.guiparts.TfMachineGuiParts;

import java.awt.*;
import java.util.List;

public class TfStorageRecipeHandler extends TfMachineRecipeHandlerBase
{
    public static final int GUI_OFFSET_X = 20;
    public static final int GUI_OFFSET_Y = 2;

    public final ItemSlotPosition slotInput = new ItemSlotPosition(11, 23);

    private GuiPartGaugeBase tfGauge = (GuiPartGaugeBase)new GuiPartGaugeH(58, 25, TfMachineGuiParts.gaugeFrame, TfMachineGuiParts.gauge)
            .setInfoTip(45, 12, HoverTextPosition.LOWER_CENTER);

    protected GuiPartGaugeBase progressBar;

    @Override
    public void init()
    {
        super.init();
        this.drawingHandler.setGuiPosition(GUI_OFFSET_X, GUI_OFFSET_Y);

        this.progressBar = new GuiPartGaugeH(31, 22, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);

        // Recipes
        Rectangle rect = new Rectangle(this.progressBar.getBoundingBox());
        rect.translate(GUI_OFFSET_X, GUI_OFFSET_Y);
        this.transferRects.add(new RecipeTransferRect(rect, this.getRecipeId()));
    }

    public List<TfMaterialRegistry> getRecipeList()
    {
        return TfMaterialRegistry.getTfMaterialList();
    }

    public String getRecipeId()
    {
        return "tfStorage";
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("container.tofucraft.TfStorage");
    }

    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(this.getRecipeId()))
        {
            for (TfMaterialRegistry entry : this.getRecipeList())
            {
                this.arecipes.add(new CachedTfStorageRecipe(entry));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (TfMaterialRegistry entry : this.getRecipeList())
        {
            if (entry.matches(ingredient))
            {
                this.arecipes.add(new CachedTfStorageRecipe(entry));
            }
        }
    }

    public void drawBackground(int i)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawingHandler.bindStandardTexture();
        drawingHandler.drawBasePanel(165 - GUI_OFFSET_X * 2, 65 - GUI_OFFSET_Y);

        CachedTfStorageRecipe recipe = (CachedTfStorageRecipe) this.arecipes.get(i);

        drawingHandler.drawGuiPart(9, 21, TfMachineGuiParts.itemSlotL1);
        tfGauge.setPercentage(recipe.getTfAmount() / 5000.0D).draw(drawingHandler);
    }

    public void drawExtras(int i)
    {
        float f;
        f = (float) ((this.ticks - 20) % 20) / 20.0F;
        this.progressBar.setPercentage(f).draw(drawingHandler);
    }

    @Override
    protected void handleGuiPartToolTip(int x, int y, final CachedRecipe recipe)
    {
        this.tfGauge.showInfoTip(drawingHandler, x, y, new GuiTfMachineBase.IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.0f", ((CachedTfStorageRecipe) recipe).getTfAmount());
                drawingHandler.drawStringInTip(s, ox + fw - 10 - drawingHandler.getStringWidth(s), oy + 2);
                drawingHandler.printTfSignInTip(ox + fw - 10, oy + 2);
            }
        });
    }

    public class CachedTfStorageRecipe extends CachedRecipe
    {
        private final List<PositionedStack> ingredients = Lists.newArrayList();
        private final double tfAmount;

        public List<PositionedStack> getIngredients()
        {
            return this.getCycledIngredients(TfStorageRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult()
        {
            return null;
        }

        public double getTfAmount()
        {
            return this.tfAmount;
        }

        public CachedTfStorageRecipe(TfMaterialRegistry recipe)
        {
            this.ingredients.add(new PositionedStack(recipe.getItemStack(), slotInput.x, slotInput.y));
            this.tfAmount = recipe.tfAmount;
        }
    }

}
