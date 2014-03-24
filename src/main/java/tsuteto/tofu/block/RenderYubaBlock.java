package tsuteto.tofu.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RenderYubaBlock implements ISimpleBlockRenderingHandler
{
    public static final int renderId = RenderingRegistry.getNextAvailableRenderId();
    
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        IIcon icon = renderer.getBlockIconFromSide(block, 1);

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.overrideBlockTexture;
        }

        float f = 0.015625F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = (x * 3129871) ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int)(l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.setColorOpaque_I(block.getBlockColor());
        tessellator.addVertexWithUV((f1 + f3 - f4), (y + f), (f2 + f3 + f4), d0, d1);
        tessellator.addVertexWithUV((f1 + f3 + f4), (y + f), (f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((f1 - f3 + f4), (y + f), (f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((f1 - f3 - f4), (y + f), (f2 + f3 - f4), d0, d3);
        tessellator.setColorOpaque_I((block.getBlockColor() & 16711422) >> 1);
        tessellator.addVertexWithUV((f1 - f3 - f4), (y + f), (f2 + f3 - f4), d0, d3);
        tessellator.addVertexWithUV((f1 - f3 + f4), (y + f), (f2 - f3 - f4), d2, d3);
        tessellator.addVertexWithUV((f1 + f3 + f4), (y + f), (f2 - f3 + f4), d2, d1);
        tessellator.addVertexWithUV((f1 + f3 - f4), (y + f), (f2 + f3 + f4), d0, d1);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int i)
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return renderId;
    }
}
