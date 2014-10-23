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

public class DiamondTofuToolImpl
{
    private ItemTool tool;

    public DiamondTofuToolImpl(ItemTool tool)
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

            // Determine digging area depending on level
            int lvl = TcEnchantmentHelper.getBatchModifier(owner);
            int w = 1 + lvl;
            int d = 2 + lvl;
            int h = 1 + lvl;

            PacketDispatcher.packet(new PacketBatchDigging(w, d, h, bx, by, bz, blockDestroyed, meta, mop.sideHit)).sendToServer();
        }
    }
}
