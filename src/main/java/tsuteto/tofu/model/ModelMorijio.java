package tsuteto.tofu.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMorijio extends ModelBase
{
    public ModelRenderer morijio;

    public ModelMorijio()
    {
        this(0, 35, 64, 64);
    }

    public ModelMorijio(int par1, int par2, int par3, int par4)
    {
        this.textureWidth = par3;
        this.textureHeight = par4;
        this.morijio = new ModelRenderer(this, par1, par2);
        this.morijio.addBox(-2.5F, -5.0F, -2.5F, 5, 5, 5, 0.0F);
        this.morijio.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.morijio.render(par7);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.morijio.rotateAngleY = par4 / (180F / (float)Math.PI);
        this.morijio.rotateAngleX = par5 / (180F / (float)Math.PI);
    }
}
