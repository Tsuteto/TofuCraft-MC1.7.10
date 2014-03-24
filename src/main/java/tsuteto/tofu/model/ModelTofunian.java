package tsuteto.tofu.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTofunian extends ModelBiped
{
    public ModelTofunian()
    {
        this(0.0F, false);
    }

    protected ModelTofunian(float par1, float par2, int par3, int par4)
    {
        super(par1, par2, par3, par4);
    }

    public ModelTofunian(float par1, boolean par2)
    {
        super(par1, 0.0F, 64, par2 ? 32 : 64);

        this.bipedCloak = new ModelRenderer(this, 0, 0);
        this.bipedCloak.addBox(-5.0F, 8.0F, -1.0F, 8, 11, 1, par1);
        this.bipedEars = new ModelRenderer(this, 24, 0);
        this.bipedEars.addBox(-3.0F, 1.0F, -1.0F, 6, 6, 1, par1);

        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1);
        this.bipedHead.addBox(-1.5F, -11.0F, -0.0F, 3, 3, 0, par1);
        this.bipedHead.setRotationPoint(0.0F, 12.0F, 0.0F);

        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, par1 + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 11.0F, 0.0F);

        this.bipedBody = new ModelRenderer(this, 8, 16);
        this.bipedBody.addBox(-3.0F, 0.0F, -2.0F, 6, 6, 4, par1);
        this.bipedBody.setRotationPoint(0.0F, 13.0F, 0.0F);

        this.bipedRightArm = new ModelRenderer(this, 28, 16);
        this.bipedRightArm.addBox(-0.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedRightArm.setRotationPoint(-5.0F, 15.0F, 0.0F);

        this.bipedLeftArm = new ModelRenderer(this, 28, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-2.0F, -2.0F, -1.0F, 2, 5, 2, par1);
        this.bipedLeftArm.setRotationPoint(5.0F, 15.0F, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedRightLeg.setRotationPoint(-1.4F, 14.0F, 0.0F);

        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, par1);
        this.bipedLeftLeg.setRotationPoint(1.4F, 14.0F, 0.0F);
    }

    @Override
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);

        if (this.isChild)
        {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            //GL11.glTranslatef(0.0F, 12.0F * par7, 0.0F);
            GL11.glTranslatef(0.0F, 16.0F * par7, 0.0F);
            this.bipedHead.render(par7);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24F * par7, 0.0F);
            //this.bipedBody.render(par7);
            //this.bipedRightArm.render(par7);
            //this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
            GL11.glPopMatrix();
        }
        else
        {
            this.bipedHead.render(par7);
            this.bipedBody.render(par7);
            this.bipedRightArm.render(par7);
            this.bipedLeftArm.render(par7);
            this.bipedRightLeg.render(par7);
            this.bipedLeftLeg.render(par7);
            this.bipedHeadwear.render(par7);
        }
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);

        float f6 = 12.0f;

        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointZ = 0.0F;
        this.bipedLeftLeg.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointY = 6.0F + f6;
        this.bipedLeftLeg.rotationPointY = 6.0F + f6;
        this.bipedHead.rotationPointZ = -0.0F;
        this.bipedHead.rotationPointY = f6 + 1.0F;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
    }
}
