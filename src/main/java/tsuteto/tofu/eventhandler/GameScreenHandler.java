package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.BatchDigging;
import tsuteto.tofu.item.DiamondTofuToolHandler;

public class GameScreenHandler
{
    private static final int LINE_COLOR = 0x00ffff;

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event)
    {
        MovingObjectPosition mop = event.target;

        if (event.subID == 0 && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            ItemStack itemHeld = event.currentItem;

            if (itemHeld != null
                    && (itemHeld.getItem() == TcItems.toolDiamond[0] || itemHeld.getItem() == TcItems.toolDiamond[1] || itemHeld.getItem() == TcItems.toolDiamond[2]))
            {
                EntityPlayer player = event.player;
                World world = player.worldObj;
                Block block = world.getBlock(mop.blockX, mop.blockY, mop.blockZ);
                int meta = world.getBlockMetadata(mop.blockX, mop.blockY, mop.blockZ);

                if (block.getMaterial() != Material.air
                        && itemHeld.getItem().getDigSpeed(itemHeld, block, meta) > 1.0F)
                {
                    float partialTicks = event.partialTicks;

                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                    GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
                    GL11.glLineWidth(2.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(false);

                    float f1 = 0.002F;
                    double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                    double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                    double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                    DiamondTofuToolHandler.Area digArea = DiamondTofuToolHandler.getDigArea(player);
                    AxisAlignedBB box = BatchDigging.getDiggingArea(mop.blockX, mop.blockY, mop.blockZ, digArea.w, digArea.d, digArea.h, mop.sideHit);
                    RenderGlobal.drawOutlinedBoundingBox(box.expand((double) f1, (double) f1, (double) f1).getOffsetBoundingBox(-d0, -d1, -d2), LINE_COLOR);

                    GL11.glDepthMask(true);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
        }
    }
}
