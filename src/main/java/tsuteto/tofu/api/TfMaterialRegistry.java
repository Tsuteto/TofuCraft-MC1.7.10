package tsuteto.tofu.api;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.ItemTcMaterials;

import com.google.common.collect.Lists;
import tsuteto.tofu.item.TcItems;

/**
 * Collection of TF materials
 */
public class TfMaterialRegistry
{
    private static final List<TfMaterialRegistry> tfMaterialList = Lists.newArrayList();
    private static final Map<String, FluidEntry> fluidRegistry = Maps.newHashMap();

    public static void init()
    {
        registerMaterial(TcItems.bucketSoymilk, 40);
        registerMaterial(TcItems.bucketSoymilkHell, 1200);

        registerMaterial(TcItems.soybeans, 20);
        registerMaterial(TcItems.tofuKinu, 10);
        registerMaterial(TcBlocks.tofuKinu, 40);
        registerMaterial(TcItems.tofuMomen, 10);
        registerMaterial(TcBlocks.tofuMomen, 40);
        registerMaterial(TcBlocks.tofuMomen, 40);
        registerMaterial(TcItems.tofuIshi, 15);
        registerMaterial(TcBlocks.tofuIshi, 60);
        registerMaterial(TcItems.tofuMetal, 25);
        registerMaterial(TcBlocks.tofuMetal, 100);

        registerMaterial(TcItems.tofuDried, 17);
        registerMaterial(TcBlocks.tofuDried, 68);
        registerMaterial(TcItems.tofuGrilled, 12);
        registerMaterial(TcBlocks.tofuGrilled, 48);

        registerMaterial(TcItems.materials, ItemTcMaterials.tofuGem.id, 600);
        registerMaterial(TcItems.tofuHell, 300);
        registerMaterial(TcBlocks.tofuHell, 1200);
        registerMaterial(TcItems.materials, ItemTcMaterials.tofuDiamondNugget.id, 200);
        registerMaterial(TcItems.tofuDiamond, 1800);
        registerMaterial(TcBlocks.tofuDiamond, 7200);
    }
    
    public static TfMaterialRegistry registerMaterial(Item item, double tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(item, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }
    
    public static TfMaterialRegistry registerMaterial(Item item, int itemDmg, double tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(item, itemDmg, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static TfMaterialRegistry registerMaterial(Block block, double tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(block, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static TfMaterialRegistry registerMaterial(Block block, int itemDmg, double tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(block, itemDmg, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static double getTfAmount(ItemStack itemstack)
    {
        // For fluid container items
        FluidStack content = FluidContainerRegistry.getFluidForFilledItem(itemstack);
        if (content != null)
        {
            return calcTfAmountFrom(content);
        }
        else
        {
            for (TfMaterialRegistry entry : tfMaterialList)
            {
                if (entry.matches(itemstack))
                {
                    return entry.tfAmount;
                }
            }
        }
        return 0;
    }
    
    public static boolean isTfMaterial(ItemStack itemstack)
    {
        return getTfAmount(itemstack) > 0;
    }
    
    public static double calcTfAmountFrom(FluidStack fluidStack)
    {
        FluidEntry fluidEntry = fluidRegistry.get(FluidRegistry.getFluidName(fluidStack));
        return fluidEntry != null ? fluidStack.amount * fluidEntry.tfAmount / fluidEntry.fluidStack.amount : 0.0D;
    }
    
    public static int calcFluidAmountFrom(double tfAmount, Fluid fluid)
    {
        FluidEntry fluidEntry = fluidRegistry.get(fluid.getName());
        return fluidEntry != null ? (int)(tfAmount / fluidEntry.tfAmount * fluidEntry.fluidStack.amount) : 0;
    }

    public final Item item;
    public final int itemDmg;
    
    public final double tfAmount;

    private TfMaterialRegistry(Block block, double tfAmount)
    {
        this(block, Item.getItemFromBlock(block).getHasSubtypes() ? OreDictionary.WILDCARD_VALUE : 0, tfAmount);
    }

    private TfMaterialRegistry(Block block, int itemDmg, double tfAmount)
    {
        this(Item.getItemFromBlock(block), itemDmg, tfAmount);
    }

    private TfMaterialRegistry(Item item, double tfAmount)
    {
        this(item, item.getHasSubtypes() ? OreDictionary.WILDCARD_VALUE : 0, tfAmount);
    }
    
    private TfMaterialRegistry(Item item, int itemDmg, double tfAmount)
    {
        this.item = item;
        this.itemDmg = itemDmg;
        this.tfAmount = tfAmount;

        FluidStack fluidStack = FluidContainerRegistry.getFluidForFilledItem(new ItemStack(item, 1, itemDmg));
        if (fluidStack != null)
        {
            FluidEntry fluidEntry = new FluidEntry();
            fluidEntry.fluidStack = fluidStack;
            fluidEntry.tfAmount = tfAmount;
            fluidRegistry.put(FluidRegistry.getFluidName(fluidStack), fluidEntry);
        }
    }

    public boolean matches(ItemStack is)
    {
        return is != null && is.getItem() == this.item
                && (!is.getItem().getHasSubtypes() || this.itemDmg == OreDictionary.WILDCARD_VALUE || this.itemDmg == is.getItemDamage());
    }

    private static class FluidEntry
    {
        public FluidStack fluidStack;
        public double tfAmount;
    }
}
