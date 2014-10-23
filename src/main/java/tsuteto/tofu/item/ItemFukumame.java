package tsuteto.tofu.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import tsuteto.tofu.entity.EntityFukumame;

public class ItemFukumame extends TcItem
{
    private IIcon iconEmpty;

    public ItemFukumame()
    {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(62);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new DispenserBehaviorFukumame());
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        if (flag || par1ItemStack.getItemDamage() <= par1ItemStack.getMaxDamage())
        {
            float f = 0.8F;

            for (int i = 0; i < 8; i++) {
                EntityFukumame fukumame = new EntityFukumame(par2World, par3EntityPlayer);

                applyEffect(fukumame, par1ItemStack);

                if (!par2World.isRemote)
                {
                    par2World.spawnEntityInWorld(fukumame);
                }
            }
            par1ItemStack.attemptDamageItem(1, par3EntityPlayer.getRNG());
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        }
        return par1ItemStack;
    }

    public static void applyEffect(EntityFukumame fukumame, ItemStack itemstack)
    {
        int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

        if (k > 0)
        {
            fukumame.setDamage(fukumame.getDamage() + (double)k * 0.25D + 0.25D);
        }

//        int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);
//
//        if (l > 0)
//        {
//            fukumame.setKnockbackStrength(l);
//        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
        {
            fukumame.setFire(100);
        }

    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("tofucraft:fukumame");
        this.iconEmpty = par1IconRegister.registerIcon("tofucraft:masu");
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return par1 > this.getMaxDamage() ? iconEmpty : itemIcon;
    }

    private class DispenserBehaviorFukumame implements IBehaviorDispenseItem
    {
        @Override
        public ItemStack dispense(IBlockSource iblocksource, ItemStack itemstack)
        {
            if (itemstack.getItemDamage() > itemstack.getMaxDamage()) return itemstack;

            EnumFacing enumfacing = BlockDispenser.func_149937_b(iblocksource.getBlockMetadata()); // getFacing
            World world = iblocksource.getWorld();
            int tileX = iblocksource.getXInt() + enumfacing.getFrontOffsetX();
            int tileY = iblocksource.getYInt() + enumfacing.getFrontOffsetY();
            int tileZ = iblocksource.getZInt() + enumfacing.getFrontOffsetZ();

            for (int i = 0; i < 8; i++) {
                EntityFukumame fukumame = new EntityFukumame(world, tileX, tileY, tileZ);
                fukumame.setThrowableHeading(enumfacing.getFrontOffsetX(), (enumfacing.getFrontOffsetY() + 0.1F), enumfacing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());

                applyEffect(fukumame, itemstack);

                if (!world.isRemote)
                {
                    world.spawnEntityInWorld(fukumame);
                }
            }

            if (itemstack.isItemStackDamageable())
            {
                itemstack.attemptDamageItem(1, itemstack.getItem().itemRand);
            }
            iblocksource.getWorld().playAuxSFX(1000, iblocksource.getXInt(), iblocksource.getYInt(), iblocksource.getZInt(), 0);
            return itemstack;
        }

        protected float func_82498_a()
        {
            return 6.0F;
        }

        protected float func_82500_b()
        {
            return 1.1F;
        }
    }
}
