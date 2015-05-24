package tsuteto.tofu.block.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.block.BlockSaltPan;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcFluids;

public class RenderSaltPan implements ISimpleBlockRenderingHandler
{
    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();

    public static final double frameHeight = 0.0625D * 6;
    public static final double frameWeight = 0.0625D * 2;
    public static final double bottomThickness = 0.0625D;
    public static final double waterHeight = 0.0625D * 3;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        double yOffset = 0.25D;
        renderer.setRenderBounds(0.0D, yOffset, 0.0D, 1.0D, yOffset + bottomThickness, 1.0D);
        this.renderBlock(Blocks.cobblestone, renderer);

        renderer.setRenderBounds(0.0D, yOffset + bottomThickness, 0.0D, frameWeight, yOffset + frameHeight, 1.0D);
        this.renderBlock(block, renderer);

        renderer.setRenderBounds(1.0D - frameWeight, yOffset + bottomThickness, 0.0D, 1.0D, yOffset + frameHeight, 1.0D);
        this.renderBlock(block, renderer);

        renderer.setRenderBounds(0.0D, yOffset + bottomThickness, 0.0D, 1.0D, yOffset + frameHeight, frameWeight);
        this.renderBlock(block, renderer);

        renderer.setRenderBounds(0.0D, yOffset + bottomThickness, 1.0D - frameWeight, 1.0D, yOffset + frameHeight, 1.0D);
        this.renderBlock(block, renderer);
    }

    private void renderBlock(Block block, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 0));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 1));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 2));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 3));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 4));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 5));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        BlockSaltPan panFrame = (BlockSaltPan)block;
        BlockSaltPan.Stat stat = panFrame.getStat(world, x, y, z);
        boolean blockNegX = panFrame.canConnectTo(world, x - 1, y, z);
        boolean blockPosX = panFrame.canConnectTo(world, x + 1, y, z);
        boolean blockNegZ = panFrame.canConnectTo(world, x, y, z - 1);
        boolean blockPosZ = panFrame.canConnectTo(world, x, y, z + 1);

        // Bottom
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, bottomThickness, 1.0D);
        renderer.renderStandardBlock(Blocks.cobblestone, x, y, z);

        // Frame
        if (!blockNegX)
        {
            renderer.setRenderBounds(0.0D, bottomThickness, 0.0D, frameWeight, frameHeight, 1.0D);
            renderer.renderStandardBlock(block, x, y, z);
        }

        if (!blockPosX)
        {
            renderer.setRenderBounds(1.0D - frameWeight, bottomThickness, 0.0D, 1.0D, frameHeight, 1.0D);
            renderer.renderStandardBlock(block, x, y, z);
        }

        if (!blockNegZ)
        {
            renderer.setRenderBounds(0.0D, bottomThickness, 0.0D, 1.0D, frameHeight, frameWeight);
            renderer.renderStandardBlock(block, x, y, z);
        }

        if (!blockPosZ)
        {
            renderer.setRenderBounds(0.0D, bottomThickness, 1.0D - frameWeight, 1.0D, frameHeight, 1.0D);
            renderer.renderStandardBlock(block, x, y, z);
        }

        // Surface of content
        IIcon iicon = null;

        if (stat == BlockSaltPan.Stat.WATER)
        {
            iicon = BlockLiquid.getLiquidIcon("water_still");
        }

        if (stat == BlockSaltPan.Stat.SALT)
        {
            iicon = renderer.getBlockIconFromSide(TcBlocks.salt, 0);
        }

        if (stat == BlockSaltPan.Stat.BITTERN)
        {
            iicon = TcFluids.NIGARI.getStillIcon();
        }

        if (iicon != null)
        {
            boolean sideNegX = this.shouldSideBeRendered(world, panFrame, x - 1, y, z);
            boolean sidePosX = this.shouldSideBeRendered(world, panFrame, x + 1, y, z);
            boolean sideNegZ = this.shouldSideBeRendered(world, panFrame, x, y, z - 1);
            boolean sidePosZ = this.shouldSideBeRendered(world, panFrame, x, y, z + 1);

            double uNeg = blockNegX ? 0.0D : frameWeight;
            double uPos = blockPosX ? 1.0D : 1.0D - frameWeight;
            double vNeg = blockNegZ ? 0.0D : frameWeight;
            double vPos = blockPosZ ? 1.0D : 1.0D - frameWeight;

            renderer.setRenderBounds(uNeg, bottomThickness, vNeg, uPos, waterHeight, vPos);

            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
            tessellator.setColorOpaque_I((block.getBlockColor() & 16711422));
            renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, iicon);

            tessellator.setColorOpaque_F(0.95F, 0.95F, 0.95F);

            if (sideNegX)
            {
                renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, iicon);
            }
            if (sidePosX)
            {
                renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, iicon);
            }

            if (sideNegZ)
            {
                renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, iicon);
            }

            if (sidePosZ)
            {
                renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, iicon);
            }
        }

        block.setBlockBoundsBasedOnState(world, x, y, z);
        return true;
    }

    private boolean shouldSideBeRendered(IBlockAccess world, BlockSaltPan panFrame, int x, int y, int z)
    {
        BlockSaltPan.Stat stat = panFrame.getStat(world, x, y, z);
        return stat != BlockSaltPan.Stat.WATER && stat != BlockSaltPan.Stat.BITTERN;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return renderId;
    }
}
