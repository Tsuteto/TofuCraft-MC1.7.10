package tsuteto.tofu.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import tsuteto.tofu.model.ModelTofunian;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTofunian extends RenderBiped
{
    private static final ResourceLocation field_110865_p = new ResourceLocation("tofucraft", "textures/mob/tofunian.png");
    private final ModelBiped field_82434_o;
    private final int field_82431_q = 1;

    public RenderTofunian()
    {
        super(new ModelTofunian(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
    }

    @Override
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelTofunian(0.8F, true);
        this.field_82425_h = new ModelTofunian(0.4F, true);
     }

    protected int shouldRenderPass(EntityTofunian par1EntityTofunian, int par2, float par3)
    {
        this.func_82427_a(par1EntityTofunian);
        return super.shouldRenderPass(par1EntityTofunian, par2, par3);
    }

    public void doRender(EntityTofunian par1EntityTofunian, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82427_a(par1EntityTofunian);
        super.doRender(par1EntityTofunian, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(EntityTofunian par1EntityTofunian)
    {
        return field_110865_p;
    }

    protected void renderEquippedItems(EntityTofunian par1EntityTofunian, float par2)
    {
        this.func_82427_a(par1EntityTofunian);
        super.renderEquippedItems(par1EntityTofunian, par2);
    }

    private void func_82427_a(EntityTofunian par1EntityTofunian)
    {
        this.mainModel = this.field_82434_o;

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void rotateCorpse(EntityTofunian par1EntityTofunian, float par2, float par3, float par4)
    {
        super.rotateCorpse(par1EntityTofunian, par2, par3, par4);
    }

    @Override
    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        this.renderEquippedItems((EntityTofunian) par1EntityLiving, par2);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
    {
        return this.getEntityTexture((EntityTofunian) par1EntityLiving);
    }

    @Override
    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityTofunian) par1EntityLiving, par2, par4, par6, par8, par9);
    }

    @Override
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.shouldRenderPass((EntityTofunian) par1EntityLiving, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
    {
        return this.shouldRenderPass((EntityTofunian) par1EntityLivingBase, par2, par3);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.renderEquippedItems((EntityTofunian) par1EntityLivingBase, par2);
    }

    @Override
    protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        this.rotateCorpse((EntityTofunian) par1EntityLivingBase, par2, par3, par4);
    }

    @Override
    public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityTofunian) par1EntityLivingBase, par2, par4, par6, par8, par9);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getEntityTexture((EntityTofunian) par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function register does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRender((EntityTofunian) par1Entity, par2, par4, par6, par8, par9);
    }
}
