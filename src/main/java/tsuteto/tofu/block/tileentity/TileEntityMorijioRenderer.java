package tsuteto.tofu.block.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import tsuteto.tofu.model.ModelMorijio;

@SideOnly(Side.CLIENT)
public class TileEntityMorijioRenderer extends TileEntitySpecialRenderer
{
    public static TileEntityMorijioRenderer renderer;
    private final ModelBase field_82396_c = new ModelMorijio(0, 0, 64, 32);
    private final ResourceLocation texture = new ResourceLocation("tofucraft", "textures/entity/morijio.png");

     public void renderTileEntityMorijioAt(TileEntityMorijio par1TileEntityMorijio, double par2, double par4, double par6, float par8)
    {
        this.func_82393_a((float)par2, (float)par4, (float)par6, par1TileEntityMorijio.getBlockMetadata() & 7, (par1TileEntityMorijio.getRotation() * 360) / 16.0F);
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

    public void func_82393_a(float par1, float par2, float par3, int par4, float par5)
    {
        ModelBase var8 = this.field_82396_c;
        float size = 0.3125F;
        float half = size / 2;

        this.bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (par4 != 1)
        {
            switch (par4)
            {
//                case 2:
//                    GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.74F);
//                    break;
//                case 3:
//                    GL11.glTranslatef(par1 + 0.5F, par2 + 0.25F, par3 + 0.26F);
//                    par5 = 180.0F;
//                    break;
//                case 4:
//                    GL11.glTranslatef(par1 + 0.74F, par2 + 0.25F, par3 + 0.5F);
//                    par5 = 270.0F;
//                    break;
//                case 5:
//                default:
//                    GL11.glTranslatef(par1 + 0.26F, par2 + 0.25F, par3 + 0.5F);
//                    par5 = 90.0F;
            case 2:
                GL11.glTranslatef(par1 + 0.5F, par2 + 0.5F - half , par3 + 0.99F - half);
                break;
            case 3:
                GL11.glTranslatef(par1 + 0.5F, par2 + 0.5F - half , par3 + half + 0.01F);
                par5 = 180.0F;
                break;
            case 4:
                GL11.glTranslatef(par1 + 0.99F - half, par2 + 0.5F - half , par3 + 0.5F);
                par5 = 270.0F;
                break;
            case 5:
            default:
                GL11.glTranslatef(par1 + half + 0.01F, par2 + 0.5F - half , par3 + 0.5F);
                par5 = 90.0F;
            }
        }
        else
        {
            GL11.glTranslatef(par1 + 0.5F, par2, par3 + 0.5F);
        }

        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        var8.render(null, 0.0F, 0.0F, 0.0F, par5, 0.0F, var10);
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityMorijioAt((TileEntityMorijio)par1TileEntity, par2, par4, par6, par8);
    }
}
