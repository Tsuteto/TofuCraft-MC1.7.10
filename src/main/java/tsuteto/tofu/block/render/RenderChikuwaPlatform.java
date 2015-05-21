package tsuteto.tofu.block.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.block.BlockChikuwaPlatform;

public class RenderChikuwaPlatform implements ISimpleBlockRenderingHandler
{
    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();
    
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        GL11.glDisable(GL11.GL_LIGHTING);

        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getBlockColor());

        tessellator.startDrawingQuads();
        renderOutside(block, metadata, renderer);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getBlockColor());
        renderInside(-0.5D, -0.5D, -0.5D, block, metadata, renderer);
        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        renderer.renderStandardBlock(block, x, y, z);

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
        renderInside(x, y, z, block, blockAccess.getBlockMetadata(x, y, z), renderer);

        return true;
    }

    public static void renderOutside(Block block, int metadata, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        float offset = -0.5F;
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        tessellator.setColorOpaque_F(f, f, f);
        renderer.renderFaceYNeg(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
        tessellator.setColorOpaque_F(f1, f1, f1);
        renderer.renderFaceYPos(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
        tessellator.setColorOpaque_F(f2, f2, f2);
        renderer.renderFaceZNeg(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
        tessellator.setColorOpaque_F(f2, f2, f2);
        renderer.renderFaceZPos(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
        tessellator.setColorOpaque_F(f3, f3, f3);
        renderer.renderFaceXNeg(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
        tessellator.setColorOpaque_F(f3, f3, f3);
        renderer.renderFaceXPos(block, offset, offset, offset, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
    }

    public static void renderInside(double x, double y, double z, Block block, int meta, RenderBlocks renderer)
    {
        float f4 = 1.0F / 16 * 5;
        BlockChikuwaPlatform.Dir dir = BlockChikuwaPlatform.getDirection(meta);
        IIcon innerIcon = ((BlockChikuwaPlatform)block).getInnerIcon();

        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorOpaque_I((block.getBlockColor() & 16711422) >> 1);

        if (dir == BlockChikuwaPlatform.Dir.X)
        {
            renderer.renderFaceZPos(block, x, y, (double) ((float) z - 1.0F + f4), innerIcon);
            renderer.renderFaceZNeg(block, x, y, (double) ((float) z + 1.0F - f4), innerIcon);
        }
        else if (dir == BlockChikuwaPlatform.Dir.Z)
        {
            renderer.uvRotateEast = 2;
            renderer.uvRotateWest = 1;
            renderer.uvRotateTop = 1;
            renderer.uvRotateBottom = 2;

            renderer.renderFaceXPos(block, (double) ((float) x - 1.0F + f4), y, z, innerIcon);
            renderer.renderFaceXNeg(block, (double) ((float) x + 1.0F - f4), y, z, innerIcon);
        }
        renderer.renderFaceYPos(block, x, (double) ((float) y - 1.0F + f4), z, innerIcon);
        renderer.renderFaceYNeg(block, x, (double) ((float) y + 1.0F - f4), z, innerIcon);

        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
    }

    @Override
    public boolean shouldRender3DInInventory(int i)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return renderId;
    }
}
