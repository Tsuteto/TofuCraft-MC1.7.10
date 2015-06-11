package tsuteto.tofu.recipe.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.api.recipe.TfCondenserRecipe;
import tsuteto.tofu.api.recipe.TfCondenserRecipeRegistry;
import tsuteto.tofu.fluids.FluidUtils;
import tsuteto.tofu.gui.GuiTfCondenser;
import tsuteto.tofu.gui.GuiTfMachineBase;
import tsuteto.tofu.gui.guiparts.*;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.tileentity.TileEntityTfCondenser;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class TfCondenserRecipeHandler extends TfMachineRecipeHandlerBase
{
    public static final int GUI_OFFSET_X = 20;
    public static final int GUI_OFFSET_Y = 0;

    private static final FluidStack NIGARI = new FluidStack(TcFluids.NIGARI, TileEntityTfCondenser.NIGARI_COST_MB);

    public final ItemSlotPosition slotOutput = new ItemSlotPosition(99, 25);
    protected GuiPartGaugeBase progressBar;
    private GuiPartGaugeV nigariGauge = new GuiPartGaugeV(9, 2, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setIndicatorColor(0xc0d0ff)
            .setItemDisplay(TfMachineGuiParts.gaugeVItemDisplay)
            .setItemStack(new ItemStack(TcItems.nigari))
            .setFluidStack(new FluidStack(TcFluids.NIGARI, 0))
            .setInfoTip(51, 12, HoverTextPosition.LOWER_CENTER);
    private GuiPartGaugeV ingredientGauge = new GuiPartGaugeV(30, 2, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setIndicatorColor(0xb0f0a7)
            .setItemDisplay(TfMachineGuiParts.gaugeVItemDisplay)
            .setInfoTip(51, 12, HoverTextPosition.LOWER_CENTER);
    private GuiPartGaugeV tfGauge = new GuiPartGaugeV(50, 2, TfMachineGuiParts.gaugeV2Frame, TfMachineGuiParts.gaugeV2)
            .setInfoTip(51, 12, HoverTextPosition.LOWER_CENTER);

    public TfCondenserRecipeHandler()
    {
    }

    @Override
    public void init()
    {
        super.init();
        this.drawingHandler.setGuiPosition(GUI_OFFSET_X, GUI_OFFSET_Y);

        this.progressBar = new GuiPartGaugeH(67, 24, TfMachineGuiParts.progressArrowBg, TfMachineGuiParts.progressArrow);
        this.progressBar.setInfoTip(HoverTextPosition.LOWER_CENTER);

        // Recipes
        this.transferRects.add(new RecipeTransferRect(new Rectangle(110, 24, 24, 17), this.getRecipeId()));
    }

    public List<TfCondenserRecipe> getRecipeList()
    {
        return TfCondenserRecipeRegistry.recipeRegistry;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiTfCondenser.class;
    }

    public String getRecipeId()
    {
        return "tfCondenser";
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("container.tofucraft.TfCondenser");
    }

    public void drawBackground(int i)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawingHandler.bindStandardTexture();
        drawingHandler.drawBasePanel(165 - GUI_OFFSET_X * 2, 65 - GUI_OFFSET_Y);

        drawingHandler.drawGuiPart(95, 21, TfMachineGuiParts.itemSlotL2);

        CachedTfCondenserRecipe recipe = (CachedTfCondenserRecipe) this.arecipes.get(i);

        // Nigari guage
        nigariGauge
                .setPercentage((double) NIGARI.amount / (double) TileEntityTfCondenser.TANK_CAPACITY_NIGARI)
                .draw(drawingHandler);

        // Ingredient guage
        ingredientGauge
                .setPercentage((double) recipe.ingredientFluid.amount / (double) TileEntityTfCondenser.TANK_CAPACITY_INGREDIENT)
                .draw(drawingHandler);

        tfGauge
                .setPercentage(1.0D)
                .draw(drawingHandler);
        drawingHandler.drawGuiPart(50, 46, TfMachineGuiParts.tfMark);

    }

    public void drawExtras(int i)
    {
        CachedTfCondenserRecipe recipe = (CachedTfCondenserRecipe) this.arecipes.get(i);
        // Item Stacks
        nigariGauge.drawItem(drawingHandler);
        ingredientGauge.setItemStack(recipe.getIngredientItem()).drawItem(drawingHandler);

        GL11.glEnable(GL11.GL_BLEND);
        drawingHandler.bindStandardTexture();
        float f;
        f = this.ticks >= 20 ? (float) ((this.ticks - 20) % 20) / 20.0F : 0.0F;
        this.progressBar.setPercentage(f).draw(drawingHandler);
    }

    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(this.getRecipeId()))
        {
            for (TfCondenserRecipe entry : this.getRecipeList())
            {
                this.arecipes.add(new CachedTfCondenserRecipe(entry));
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
        for (TfCondenserRecipe entry : this.getRecipeList())
        {
            ItemStack output = entry.result;
            if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
            {
                this.arecipes.add(new CachedTfCondenserRecipe(entry));
                break;
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (TfCondenserRecipe entry : this.getRecipeList())
        {
            if (entry.ingredient.isFluidEqual(ingredient) || NIGARI.isFluidEqual(ingredient))
            {
                this.arecipes.add(new CachedTfCondenserRecipe(entry));
            }
        }
    }

    @Override
    protected void handleGuiPartToolTip(int x, int y, final CachedRecipe recipe)
    {
        final CachedTfCondenserRecipe condenserRecipe = (CachedTfCondenserRecipe) recipe;

        this.nigariGauge.showInfoTip(drawingHandler, x, y, new GuiTfMachineBase.IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%dmB", NIGARI.amount);
                drawingHandler.drawStringInTip(s, ox + 46 - drawingHandler.getStringWidth(s), oy + 2);
            }
        });
        this.ingredientGauge.setFluidStack(condenserRecipe.ingredientFluid)
                .showInfoTip(drawingHandler, x, y, new GuiTfMachineBase.IHoverDrawingHandler()
                {
                    @Override
                    public void draw(int ox, int oy, int fw, int fh)
                    {
                        String s;
                        s = String.format("%dmB", condenserRecipe.getIngredientFluid().amount);
                        drawingHandler.drawStringInTip(s, ox + 46 - drawingHandler.getStringWidth(s), oy + 2);
                    }
                });
        this.tfGauge.showInfoTip(drawingHandler, x, y, new GuiTfMachineBase.IHoverDrawingHandler()
        {
            @Override
            public void draw(int ox, int oy, int fw, int fh)
            {
                String s;
                s = String.format("%.0f", condenserRecipe.getTfAmount());
                drawingHandler.drawStringInTip(s, ox + 41 - drawingHandler.getStringWidth(s), oy + 2);
                drawingHandler.printTfSignInTip(ox + 41, oy + 2);
            }
        });

        this.progressBar.showInfoTip(drawingHandler, x, y,
                StatCollector.translateToLocalFormatted("tofucraft.seconds", (double) condenserRecipe.getTicksNeeded() / 20.0D));
    }

    public class CachedTfCondenserRecipe extends CachedRecipe
    {
        final long offset = System.currentTimeMillis();
        private final FluidStack ingredientFluid;
        private final List<ItemStack> ingredientItem;
        private final PositionedStack output;
        private final List<PositionedStack> otherStacks = Lists.newArrayList();
        private final double tfAmount;
        private final int ticksNeeded;

        public CachedTfCondenserRecipe(TfCondenserRecipe recipe)
        {
            this.ingredientFluid = recipe.ingredient;
            if (FluidUtils.fluidToFilledItemMap.containsKey(recipe.ingredient.getFluid().getID()))
            {
                this.ingredientItem = FluidUtils.getFilledItemFromFluid(recipe.ingredient.getFluid());
            }
            else
            {
                this.ingredientItem = Lists.newArrayList();
            }
            this.tfAmount = recipe.tfAmountNeeded;
            this.ticksNeeded = recipe.ticksNeeded;
            this.output = new PositionedStack(recipe.result, slotOutput.x, slotOutput.y);
        }

        public ItemStack getIngredientItem()
        {
            return this.getCycledItemStacks(TfCondenserRecipeHandler.this.cycleticks / 20, this.ingredientItem);
        }

        public FluidStack getIngredientFluid()
        {
            return this.ingredientFluid;
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

        public int getTicksNeeded()
        {
            return ticksNeeded;
        }

        public ItemStack getCycledItemStacks(int cycle, List<ItemStack> stacks)
        {
            Random rand = new Random(cycle + this.offset);
            return stacks.get(Math.abs(rand.nextInt()) % stacks.size());
        }
    }
}
