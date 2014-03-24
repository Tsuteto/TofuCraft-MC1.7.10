package tsuteto.tofu.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderTofuSlime extends RenderSlime
{
    private static final ResourceLocation texture = new ResourceLocation("tofucraft", "textures/mob/tofuslime.png");

    public RenderTofuSlime(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super(par1ModelBase, par2ModelBase, par3);
    }

    protected ResourceLocation getSlimeTextures(EntitySlime par1EntitySlime)
    {
        return texture;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getSlimeTextures((EntityTofuSlime) par1Entity);
    }
}
