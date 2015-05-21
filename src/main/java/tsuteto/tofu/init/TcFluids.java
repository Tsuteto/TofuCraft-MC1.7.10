package tsuteto.tofu.init;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.item.ItemTcMaterials;

public class TcFluids
{
    public static final Fluid SOYMILK = new Fluid("Soy Milk");
    public static final Fluid SOYMILK_HELL = new Fluid("Hell Soy Milk");
    public static final Fluid SOY_SAUCE = new Fluid("Soy Sauce");

    public static final Fluid NIGARI = new Fluid("Nigari");
    public static final Fluid MINERAL_SOYMILK = new Fluid("MineralSoymilk");
    public static final Fluid STRAWBERRY_JAM = new Fluid("StrawberryJam");
    public static final Fluid SOUP_STOCK = new Fluid("SoupStock");
//    public static final Fluid MAGMA_CREAM = new Fluid("Magma Cream");

    static
    {
        FluidRegistry.registerFluid(SOYMILK);
        FluidRegistry.registerFluid(SOYMILK_HELL);
        FluidRegistry.registerFluid(SOY_SAUCE);

        FluidRegistry.registerFluid(NIGARI);
        FluidRegistry.registerFluid(MINERAL_SOYMILK);
        FluidRegistry.registerFluid(STRAWBERRY_JAM);
        FluidRegistry.registerFluid(SOUP_STOCK);
//        FluidRegistry.registerFluid(MAGMA_CREAM);
    }

    public static void register(Side side)
    {
        if (side == Side.CLIENT)
        {
            MinecraftForge.EVENT_BUS.register(new TcFluids());
        }

        // Soymilk
        SOYMILK.setBlock(TcBlocks.soymilkStill)
                .setUnlocalizedName(TcBlocks.soymilkStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOYMILK,
                new ItemStack(TcItems.bucketSoymilk), FluidContainerRegistry.EMPTY_BUCKET);

        // Hell Soymilk
        SOYMILK_HELL.setBlock(TcBlocks.soymilkHellStill)
                .setUnlocalizedName(TcBlocks.soymilkHellStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOYMILK_HELL,
                new ItemStack(TcItems.bucketSoymilkHell), FluidContainerRegistry.EMPTY_BUCKET);

        // Soy Sauce
        SOY_SAUCE.setBlock(TcBlocks.soySauceStill)
                .setUnlocalizedName(TcBlocks.soySauceStill.getUnlocalizedName());
        FluidContainerRegistry.registerFluidContainer(SOY_SAUCE,
                new ItemStack(TcItems.bucketSoySauce), FluidContainerRegistry.EMPTY_BUCKET);
        FluidContainerRegistry.registerFluidContainer(SOY_SAUCE,
                new ItemStack(TcItems.bottleSoySauce), FluidContainerRegistry.EMPTY_BOTTLE);

        // Nigari
        NIGARI.setUnlocalizedName("tofucraft:nigari");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(NIGARI, 10),
                new ItemStack(TcItems.nigari), FluidContainerRegistry.EMPTY_BOTTLE);

        // Mineral Soymilk
        MINERAL_SOYMILK.setUnlocalizedName("tofucraft:mineralSoymilk");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(MINERAL_SOYMILK, 100),
                new ItemStack(TcItems.materials, 1, ItemTcMaterials.mineralSoymilk.id), FluidContainerRegistry.EMPTY_BOTTLE);

        // Strawberry Jam
        STRAWBERRY_JAM.setUnlocalizedName("tofucraft:strawberryJam");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(STRAWBERRY_JAM, 500),
                new ItemStack(TcItems.strawberryJam), FluidContainerRegistry.EMPTY_BOTTLE);

        // Dashi
        SOUP_STOCK.setUnlocalizedName("tofucraft:soupStock");
        FluidContainerRegistry.registerFluidContainer(new FluidStack(SOUP_STOCK, 500),
                new ItemStack(TcItems.dashi), FluidContainerRegistry.EMPTY_BOTTLE);

        // Magma cream
//        MAGMA_CREAM.setUnlocalizedName(Items.magma_cream.getUnlocalizedName());
//
//        FluidContainerRegistry.registerFluidContainer(MAGMA_CREAM,
//                new ItemStack(Items.magma_cream), FluidContainerRegistry.EMPTY_BUCKET);
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event)
    {
        SOYMILK.setIcons(TcBlocks.soymilkStill.getIcon(0, 0), TcBlocks.soymilkStill.getIcon(2, 0));
        SOYMILK_HELL.setIcons(TcBlocks.soymilkHellStill.getIcon(0, 0), TcBlocks.soymilkHellStill.getIcon(2, 0));
        SOY_SAUCE.setIcons(TcBlocks.soySauceStill.getIcon(0, 0), TcBlocks.soySauceStill.getIcon(2, 0));

        if (event.map.getTextureType() == 0)
        {
            IIcon icon;
            icon = event.map.registerIcon(TofuCraftCore.resourceDomain + "nigari");
            NIGARI.setIcons(icon);
            icon = event.map.registerIcon(TofuCraftCore.resourceDomain + "soupStock");
            SOUP_STOCK.setIcons(icon);
            icon = event.map.registerIcon(TofuCraftCore.resourceDomain + "strawberryJam");
            STRAWBERRY_JAM.setIcons(icon);
            icon = event.map.registerIcon(TofuCraftCore.resourceDomain + "soymilkMineral");
            MINERAL_SOYMILK.setIcons(icon);
        }
    }
}
