package tsuteto.tofu.recipe.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.api.recipe.TfReformerRecipe;
import tsuteto.tofu.api.recipe.TfReformerRecipeRegistry;
import tsuteto.tofu.gui.GuiTfMachineBase;
import tsuteto.tofu.gui.guiparts.*;
import tsuteto.tofu.recipe.Ingredient;

import java.awt.*;
import java.util.List;

public class TfReformerRecipeHandler extends TfMachineRecipeHandlerBase
{
    public static final int GUI_OFFSET_X = 20;
    public static final int GUI_OFFSET_Y = 2;

    public final ItemSlotPosition[] slotsInput = new ItemSlotPosition[]{
            new ItemSlotPosition(61, 17),
            new ItemSlotPosition(80, 17),
            new ItemSlotPosition(99, 17)
    };
    public final ItemSlotPosition slotContainer = new ItemSlotPosition(12, 9);
    public final ItemSlotPosition slotOutput = new ItemSlotPosition(12, 39);

    protected GuiPartGaugeBase progressBar;
    protected GuiPartGaugeBase gaugeTfCharged =
            new GuiPartGaugeV(82, 38, TfMachineGuiParts.gaugeTfChargedBg, TfMachineGuiParts.gaugeTfCharged)
                    .setInfoTip(50, 22, HoverTextPosition.LOWER_CENTER);

    public TfReformerRecipeHandler()
    {
    }

    @Override
    public void init()
    {
        super.init();
        this.drawingHandler.setGuiPosition(GUI_OFFSET_X, GUI_OFFSET_Y);

        this.progressBar = new GuiPartGaugeRevH(32, 23, TfMachineGuiParts.progressArrowRevBg, TfMachineGuiParts.progressArrowRev);

        // Recipes
        Rectangle rect = new Rectangle(this.progressBar.getBoundingBox());
        rect.translate(GUI_OFFSET_X, GUI_OFFSET_Y);
        this.transferRects.add(new RecipeTransferRect(rect, this.getRecipeId()));
    }

    public List<TfReformerRecipe> getRecipeList()
    {
        return TfReformerRecipeRegistry.recipeRegistry;
    }

    public String getRecipeId()
    {
        return "tfReformer";
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("container.tofucraft.TfReformer.mix");
    }

    public void drawBackground(int i)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawingHandler.bindStandardTexture();
        drawingHandler.drawBasePanel(165 - GUI_OFFSET_X * 2, 65 - GUI_OFFSET_Y);

        drawingHandler.drawGuiPart(59, 15, TfMachineGuiParts.itemSlotL1);
        drawingHandler.drawGuiPart(59 + 19, 15, TfMachineGuiParts.itemSlotL1);
        drawingHandler.drawGuiPart(59 + 38, 15, TfMachineGuiParts.itemSlotL1);

        drawingHandler.drawGuiPart(10, 7, TfMachineGuiParts.itemSlotL1);
        drawingHandler.drawGuiPart(17, 29, TfMachineGuiParts.smallArrowDown);
        drawingHandler.drawGuiPart(8, 35, TfMachineGuiParts.itemSlotL2);
    }

    public void drawExtras(int i)
    {
        float f;
        f = this.ticks <= 20 ? (float) this.ticks / 20.0F : 1.0F;
        this.gaugeTfCharged.setPercentage(f).draw(drawingHandler);

        f = this.ticks >= 20 ? (float) ((this.ticks - 20) % 20) / 20.0F : 0.0F;
        this.progressBar.setPercentage(f).draw(drawingHandler);
    }

    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(this.getRecipeId()))
        {
            for (TfReformerRecipe entry : this.getRecipeList())
            {
                this.arecipes.add(new CachedTfReformerRecipe(entry));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (TfReformerRecipe entry : this.getRecipeList())
        {
            ItemStack output = entry.result;
            if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
            {
                this.arecipes.add(new CachedTfReformerRecipe(entry));
                break;
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (TfReformerRecipe entry : this.getRecipeList())
        {
            boolean matched = false;
            for (TfReformerRecipe.IngInfo ing : entry.getIngredients())
            {
                if (ing.ingredient.matches(ingredient))
                {
                    this.arecipes.add(new CachedTfReformerRecipe(entry));
                    matched = true;
                    break;
                }
            }
            if (!matched && entry.containerItem.matches(ingredient))
            {
                this.arecipes.add(new CachedTfReformerRecipe(entry));
            }
        }
    }

    @Override
    protected void handleGuiPartToolTip(int x, int y, final CachedRecipe recipe)
    {
        this.gaugeTfCharged.showInfoTip(drawingHandler, x, y, new GuiTfMachineBase.IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                drawingHandler.drawStringInTip(StatCollector.translateToLocal("tofucraft.tfInput"), ox + 2, oy + 2);
                String s;
                s = String.format("%.0f", ((CachedTfReformerRecipe) recipe).getTfAmount());
                drawingHandler.drawStringInTip(s, ox + 2, oy + 12);
                drawingHandler.printTfSignInTip(ox + 2 + drawingHandler.getStringWidth(s), oy + 12);
            }
        });
    }

    public class CachedTfReformerRecipe extends CachedRecipe
    {
        private final List<PositionedStack> ingredients = Lists.newArrayList();
        private final PositionedStack output;
        private final List<PositionedStack> otherStacks = Lists.newArrayList();
        private final double tfAmount;

        public List<PositionedStack> getIngredients()
        {
            return this.getCycledIngredients(TfReformerRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult()
        {
            return this.output;
        }

        public List<PositionedStack> getOtherStacks()
        {
            return otherStacks;
        }

        public double getTfAmount()
        {
            return this.tfAmount;
        }

        public CachedTfReformerRecipe(TfReformerRecipe recipe)
        {
            TfReformerRecipe.IngInfo[] input = recipe.getIngredients();
            Ingredient container = recipe.containerItem;
            double tfAmount = recipe.tfAmountNeeded;
            ItemStack output1 = recipe.result;

            for (int i = 0; i < input.length; i++)
            {
                this.ingredients.add(new PositionedStack(input[i].ingredient.getApplicableItems(), slotsInput[i].x, slotsInput[i].y));
            }
            this.otherStacks.add(new PositionedStack(container.getApplicableItems(), slotContainer.x, slotContainer.y));
            this.tfAmount = tfAmount;
            this.output = new PositionedStack(output1, slotOutput.x, slotOutput.y);
        }
    }
}
