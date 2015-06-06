package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketBugle;
import tsuteto.tofu.util.RenderUtils;

public class ItemTofuBugle extends TcItem implements IItemRenderer
{

    public ItemTofuBugle()
    {
        super();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            PacketDispatcher.packet(
                    new PacketBugle((float) player.posX, (float) player.posY, (float) player.posZ, player.getEntityId()))
                    .sendToAllAround(player.posX, player.posY, player.posZ, 64, player.dimension);
        }
        
        player.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        return itemstack;
    }
    
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    /*
     * === IItemRenderer ===
     */
    @SideOnly(Side.CLIENT)
    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
    {
        return type == IItemRenderer.ItemRenderType.EQUIPPED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glTranslatef(0.9F, -0.5F, 0.5F);
        GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        RenderUtils.renderStandardItem((EntityLivingBase) data[1], item, 0);
    }

}
