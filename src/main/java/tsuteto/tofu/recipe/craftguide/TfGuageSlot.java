package tsuteto.tofu.recipe.craftguide;

import org.lwjgl.opengl.GL11;
import uristqwerty.CraftGuide.api.NamedTexture;
import uristqwerty.CraftGuide.api.Renderer;
import uristqwerty.CraftGuide.api.Util;

public class TfGuageSlot extends TfAmountSlot
{
    private static NamedTexture containerTexture = null;

    public TfGuageSlot(int x, int y)
    {
        super(x, y);

        if(containerTexture == null)
        {
            containerTexture = Util.instance.getTexture("liquidFilterContainer");
        }
    }

    @Override
    public int getWidth()
    {
        return 16;
    }

    @Override
    public int getHeight()
    {
        return 16;
    }

    @Override
    public void draw(Renderer renderer, int recipeX, int recipeY, Object[] data, int dataIndex, boolean isMouseOver)
    {
        int x = recipeX + this.x;
        int y = recipeY + this.y;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2i(x + 3, y + 1);
        GL11.glVertex2i(x + 3, y + 15);
        GL11.glVertex2i(x + 13, y + 15);
        GL11.glVertex2i(x + 13, y + 1);
        GL11.glEnd();

        renderer.renderRect(x - 1, y - 1, 18, 18, containerTexture);
    }
}
