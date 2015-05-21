package tsuteto.tofu.texture;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import tsuteto.tofu.TofuCraftCore;

public class TcTextures
{
    public static final ResourceLocation tfSign = new ResourceLocation("tofucraft", "textures/gui/tfsign.png");
    public static final ResourceLocation tfMachineGui = new ResourceLocation("tofucraft", "textures/gui/tfMachine.png");

    public static IIcon tofuSmoke;

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Pre event)
    {
        int type = event.map.getTextureType();

        if (type == 1)
        {
            registerIcons(event.map);
        }
    }

    public void registerIcons(IIconRegister par1IconRegister)
    {
        tofuSmoke = par1IconRegister.registerIcon(TofuCraftCore.resourceDomain + "smokeTofu");
    }


}
