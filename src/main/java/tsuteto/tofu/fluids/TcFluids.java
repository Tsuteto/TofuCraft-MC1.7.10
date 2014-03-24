package tsuteto.tofu.fluids;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;

public class TcFluids
{
    public static final Fluid SOYMILK = new Fluid("Soy Milk");
    public static final Fluid SOYMILK_HELL = new Fluid("Hell Soy Milk");
    public static final Fluid SOY_SAUCE = new Fluid("Soy Sauce");

    static
    {
        FluidRegistry.registerFluid(SOYMILK);
        FluidRegistry.registerFluid(SOYMILK_HELL);
        FluidRegistry.registerFluid(SOY_SAUCE);
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
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Post event)
    {
        SOYMILK.setIcons(TcBlocks.soymilkStill.getIcon(0, 0), TcBlocks.soymilkStill.getIcon(2, 0));
        SOYMILK_HELL.setIcons(TcBlocks.soymilkHellStill.getIcon(0, 0), TcBlocks.soymilkHellStill.getIcon(2, 0));
        SOY_SAUCE.setIcons(TcBlocks.soySauceStill.getIcon(0, 0), TcBlocks.soySauceStill.getIcon(2, 0));
    }
}
