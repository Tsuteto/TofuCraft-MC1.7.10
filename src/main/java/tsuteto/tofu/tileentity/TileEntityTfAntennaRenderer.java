package tsuteto.tofu.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import tsuteto.tofu.model.ModelTfAntenna;

public class TileEntityTfAntennaRenderer extends TileEntitySpecialRenderer
{
    public static TileEntityTfAntennaRenderer renderer;
    private final ModelTfAntenna model = new ModelTfAntenna(0, 0, 64, 32);
    private final ResourceLocation texture = new ResourceLocation("tofucraft:textures/entity/tfAntenna.png");
    private final RenderManager renderManager = RenderManager.instance;

    public void renderTileEntityMorijioAt(TileEntityTfAntenna tileentity, double par2, double par4, double par6, float par8)
    {
        this.func_82393_a((float)par2, (float)par4, (float)par6);
    }
    
    /**
     * Associate a TileEntityRenderer with this TileEntitySpecialRenderer
     */
    @Override
    public void func_147497_a(TileEntityRendererDispatcher p_147497_1_)
    {
        super.func_147497_a(p_147497_1_);
        renderer = this;
    }

    public void func_82393_a(float par1, float par2, float par3)
    {
        float size = 0.3125F;
        float half = size / 2;

        this.bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);

        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        this.model.render(null, 0.0F, 0.0F, 0.0F, this.renderManager.playerViewY, 0.0F, var10);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityMorijioAt((TileEntityTfAntenna)tileentity, par2, par4, par6, par8);
    }
}
