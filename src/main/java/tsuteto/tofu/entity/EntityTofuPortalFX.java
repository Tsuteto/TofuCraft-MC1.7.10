package tsuteto.tofu.entity;

import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTofuPortalFX extends EntityPortalFX
{
    public EntityTofuPortalFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        super(par1World, par2, par4, par6, par8, par10, par12);
        float f = this.rand.nextFloat() * 0.3F + 0.7F;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
        this.particleGreen *= 1.0F;
        this.particleRed *= 1.0F;
        this.particleBlue *= 0.9F;
    }
}
