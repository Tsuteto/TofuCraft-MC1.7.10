package tsuteto.tofu;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItem;

import com.google.common.collect.Lists;
import tsuteto.tofu.item.TcItems;

/**
 * Collection of TF materials
 * 
 * TODO: Registration of fluids
 * 
 * @author Tsuteto
 *
 */
public class TfMaterialRegistry
{
    private static final List<TfMaterialRegistry> tfMaterialList = Lists.newArrayList();
    private static TfMaterialRegistry soymilkBucket;
    
    public static void init()
    {
        soymilkBucket = registerMaterial(TcItems.bucketSoymilk, 40);
        registerMaterial(TcItems.soybeans, 20);
        registerMaterial(TcItems.tofuKinu, 10);
        registerMaterial(TcBlocks.tofuKinu, 40);
        registerMaterial(TcItems.tofuMomen, 10);
        registerMaterial(TcBlocks.tofuMomen, 40);
        registerMaterial(TcItems.tofuIshi, 20);
        registerMaterial(TcBlocks.tofuIshi, 80);
        registerMaterial(TcItems.tofuMetal, 30);
        registerMaterial(TcBlocks.tofuMetal, 120);
        registerMaterial(TcItems.materials, ItemTcMaterials.tofuGem.id, 600);
        registerMaterial(TcItems.tofuHell, 100);
        registerMaterial(TcBlocks.tofuHell, 400);
        registerMaterial(TcItems.materials, ItemTcMaterials.tofuDiamondNugget.id, 200);
        registerMaterial(TcItems.tofuDiamond, 1800);
        registerMaterial(TcBlocks.tofuDiamond, 8000);
    }
    
    public static TfMaterialRegistry registerMaterial(Item item, float tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(item, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }
    
    public static TfMaterialRegistry registerMaterial(Item item, int itemDmg, float tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(item, itemDmg, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static TfMaterialRegistry registerMaterial(Block block, float tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(block, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static TfMaterialRegistry registerMaterial(Block block, int itemDmg, float tfAmount)
    {
        TfMaterialRegistry newEntry = new TfMaterialRegistry(block, itemDmg, tfAmount);
        tfMaterialList.add(newEntry);
        return newEntry;
    }

    public static float getTfAmount(ItemStack itemstack)
    {
        // For soymilk container items
        FluidStack content = FluidContainerRegistry.getFluidForFilledItem(itemstack);
        if (content != null && content.fluidID == TcFluids.SOYMILK.getID())
        {
            return calcTfAmountFrom(content.amount);
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
    
    public static float getSoymilkTfAmount()
    {
        return soymilkBucket.tfAmount;
    }
    
    public static float calcTfAmountFrom(int millibucketsOfSoymilk)
    {
        return millibucketsOfSoymilk * TfMaterialRegistry.getSoymilkTfAmount() / FluidContainerRegistry.BUCKET_VOLUME;
    }
    
    public static int calcSoymilkAmountFrom(float tfAmount)
    {
        return (int)(tfAmount / TfMaterialRegistry.getSoymilkTfAmount() * FluidContainerRegistry.BUCKET_VOLUME);
    }
    
    public final Item item;
    public final int itemDmg;
    
    public final float tfAmount;

    private TfMaterialRegistry(Block block, float tfAmount)
    {
        this(block, OreDictionary.WILDCARD_VALUE, tfAmount);
    }

    private TfMaterialRegistry(Block block, int itemDmg, float tfAmount)
    {
        this(Item.getItemFromBlock(block), itemDmg, tfAmount);
    }

    private TfMaterialRegistry(Item item, float tfAmount)
    {
        this(item, OreDictionary.WILDCARD_VALUE, tfAmount);
    }
    
    private TfMaterialRegistry(Item item, int itemDmg, float tfAmount)
    {
        this.item = item;
        this.itemDmg = itemDmg;
        this.tfAmount = tfAmount;
    }

    public boolean matches(ItemStack is)
    {
        if (is == null) return false;
        return is.getItem() == this.item
                && (this.itemDmg == OreDictionary.WILDCARD_VALUE || this.itemDmg == is.getItemDamage());
    }
}
