package tsuteto.tofu.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTfAntenna extends ModelBase
{
    public ModelRenderer box1;
    public ModelRenderer box2;

    public float rotateAngleX;

    public ModelTfAntenna()
    {
        this(0, 32, 64, 64);
    }

    public ModelTfAntenna(int par1, int par2, int par3, int par4)
    {
        this.textureWidth = par3;
        this.textureHeight = par4;
        
        box1 = new ModelRenderer(this, 10, 0);
        box1.addBox(-3.5F, -2.0F, -3.5F, 7, 2, 7, 0.0F);

        box2 = new ModelRenderer(this, 0, 0);
        box2.mirror = true;
        box2.addBox(-2.5F, -16.0F, 0.0F, 5, 14, 0, 0.0F);

        this.box1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.box2.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        this.box1.render(par7);
        this.box2.render(par7);
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
        //this.box1.rotateAngleY = par4 / (180F / (float)Math.PI);
        //this.box1.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.box2.rotateAngleY = (par4 + rotateAngleX) / (180F / (float)Math.PI);
        this.box2.rotateAngleX = par5 / (180F / (float)Math.PI);
    }

}
