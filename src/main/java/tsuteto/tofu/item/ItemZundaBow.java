package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import tsuteto.tofu.data.DataType;
import tsuteto.tofu.data.EntityInfo;
import tsuteto.tofu.entity.EntityZundaArrow;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketZundaArrowType;
import tsuteto.tofu.util.RenderUtils;

public class ItemZundaBow extends ItemBow implements IItemRenderer
{
    public enum EnumArrowType
    {
        NORMAL(Items.arrow), ZUNDA(TcItems.zundaArrow);

        public final Item item;
        public IIcon[] icons;

        EnumArrowType(Item item)
        {
            this.item = item;

        }
    }

    public static final String[] iconNameSuffix = new String[]{"pull_0", "pull_1", "pull_2"};

    public ItemZundaBow()
    {
        super();
        this.setMaxDamage(1345);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (usingItem != null)
        {
            EnumArrowType arrowType = EntityInfo.instance().get(player.getEntityId(), DataType.ZundaArrowType);
            if (arrowType == null) arrowType = EnumArrowType.NORMAL;

            int k = usingItem.getMaxItemUseDuration() - useRemaining;
            if (k >= 18) return arrowType.icons[2];
            if (k > 13) return arrowType.icons[1];
            if (k > 0) return arrowType.icons[0];
        }
        return this.getIconIndex(stack);
    }

    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;

        boolean var5 = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        EnumArrowType arrowType = EntityInfo.instance().get(par3EntityPlayer.getEntityId(), DataType.ZundaArrowType);

        if (arrowType != null)
        {
            float var7 = var6 / 20.0F;
            var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

            if (var7 < 0.1D)
            {
                return;
            }

            if (var7 > 1.0F)
            {
                var7 = 1.0F;
            }

            Entity var8;

            if (arrowType == EnumArrowType.NORMAL)
            {
                var8 = this.getNormalArrow(par1ItemStack, par2World, par3EntityPlayer, var7);

                if (var5)
                {
                    ((EntityArrow) var8).canBePickedUp = 2;
                }
            }
            else
            {
                var8 = this.getZundaArrow(par1ItemStack, par2World, par3EntityPlayer, var7);

                if (var5)
                {
                    ((EntityZundaArrow) var8).canBePickedUp = 2;
                }
            }

            if (!var5)
            {
                par3EntityPlayer.inventory.consumeInventoryItem(arrowType.item);
            }
            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(var8);
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        EnumArrowType arrowType = this.getArrowType(par1ItemStack, par3EntityPlayer);
        if (arrowType != null)
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

            PacketDispatcher.packet(
                    new PacketZundaArrowType(par3EntityPlayer.getEntityId(), arrowType))
                    .sendPacketToAllPlayers();
            EntityInfo.instance().set(par3EntityPlayer.getEntityId(), DataType.ZundaArrowType, arrowType);
        }

        return par1ItemStack;
    }

    public EntityArrow getNormalArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, float var7)
    {
        EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, var7 * 2.0F);

        if (var7 == 1.0F)
        {
            var8.setIsCritical(true);
        }

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

        if (var9 > 0)
        {
            var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

        if (var10 > 0)
        {
            var8.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
        {
            var8.setFire(100);
        }

        return var8;
    }

    public EntityZundaArrow getZundaArrow(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, float var7)
    {
        EntityZundaArrow var8 = new EntityZundaArrow(par2World, par3EntityPlayer, var7 * 2.0F);

        if (var7 == 1.0F)
        {
            var8.setIsCritical(true);
        }

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

        if (var9 > 0)
        {
            var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

        if (var10 > 0)
        {
            var8.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
        {
            var8.setFire(100);
        }

        return var8;
    }

    private EnumArrowType getArrowType(ItemStack par1ItemStack, EntityPlayer player)
    {
        boolean var5 = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (var5 || player.inventory.hasItem(EnumArrowType.ZUNDA.item))
        {
            return EnumArrowType.ZUNDA;
        }
        else if (player.inventory.hasItem(EnumArrowType.NORMAL.item))
        {
            return EnumArrowType.NORMAL;
        }
        else
        {
            return null;
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("tofucraft:zundaBow");

        for (EnumArrowType type : EnumArrowType.values())
        {
            type.icons = new IIcon[iconNameSuffix.length];

            for (int i = 0; i < type.icons.length; ++i)
            {
                type.icons[i] = par1IconRegister.registerIcon(String.format("tofucraft:zundaBow_%s_%s", type.toString().toLowerCase(), iconNameSuffix[i]));
            }
        }
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
        GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(-130.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        RenderUtils.renderStandardItem((EntityLivingBase) data[1], item, 0);
    }
}
