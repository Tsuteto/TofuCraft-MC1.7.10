package tsuteto.tofu.item;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tsuteto.tofu.enchantment.TcEnchantmentHelper;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketBatchDigging;

public class DiamondTofuToolHandler
{
    private ItemTool tool;

    public DiamondTofuToolHandler(ItemTool tool)
    {
        this.tool = tool;
    }

    public void onBlockStartBreak(ItemStack stack, World world, Block blockDestroyed, int bx, int by, int bz, EntityLivingBase owner)
    {
        int meta = world.getBlockMetadata(bx, by, bz);
        if (tool.getDigSpeed(stack, blockDestroyed, meta) > 1.0F)
        {
            MovingObjectPosition mop = FMLClientHandler.instance().getClient().objectMouseOver;
            if (mop == null) return;
            Area area = getDigArea(owner);

            PacketDispatcher.packet(new PacketBatchDigging(area.w, area.d, area.h, bx, by, bz, blockDestroyed, meta, mop.sideHit)).sendToServer();
        }
    }

    public static Area getDigArea(EntityLivingBase owner)
    {
        // Determine digging area depending on level
        int lvl = TcEnchantmentHelper.getBatchModifier(owner);
        Area area = new Area();
        area.w = 1 + lvl;
        area.d = 2 + lvl;
        area.h = 1 + lvl;
        return area;
    }

    public static class Area
    {
        public int w, d, h;
    }
}
