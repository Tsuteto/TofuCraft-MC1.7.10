package tsuteto.tofu.block.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.entity.EntityFallingChikuwaPlatform;

@SideOnly(Side.CLIENT)
public class RenderFallingChikuwaPlatform extends Render
{
    private final RenderBlocks renderer = new RenderBlocks();

    public RenderFallingChikuwaPlatform()
    {
        this.shadowSize = 0.5F;
    }

    public void doRender(EntityFallingChikuwaPlatform entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
    {
        World world = entity.func_145807_e();
        Block block = entity.func_145805_f();
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);

        if (block != null && block != world.getBlock(i, j, k))
        {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)x, (float)y + 0.5F, (float)z);
            this.bindEntityTexture(entity);
            GL11.glDisable(GL11.GL_LIGHTING);

            Tessellator tessellator = Tessellator.instance;

            this.renderer.setRenderBoundsFromBlock(block);
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));

            tessellator.startDrawingQuads();
            RenderChikuwaPlatform.renderOutside(block, entity.blockMetadata, renderer);
            tessellator.draw();

            tessellator.startDrawingQuads();
            RenderChikuwaPlatform.renderInside(-0.5F, -0.5F, -0.5F, block, entity.blockMetadata, renderer);
            tessellator.draw();

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

        }
    }

    protected ResourceLocation getEntityTexture(EntityFallingChikuwaPlatform p_110775_1_)
    {
        return TextureMap.locationBlocksTexture;
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityFallingChikuwaPlatform)p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityFallingChikuwaPlatform)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}