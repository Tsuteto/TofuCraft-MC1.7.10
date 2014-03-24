package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemDrinkBucket extends ItemTcBucket
{
    private boolean alwaysEdible = false;
    
    private final int healAmount;
    private final Float saturationModifier;

    private int potionId;
    private int potionDuration;
    private int potionAmplifier;
    private float potionEffectProbability;

    public ItemDrinkBucket(Block par2, int healAmount, float saturationModifier)
    {
        super(par2);
        this.healAmount = healAmount;
        this.saturationModifier = saturationModifier;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;

        par3EntityPlayer.getFoodStats().addStats(healAmount, saturationModifier);
        this.func_77849_c(par1ItemStack, par2World, par3EntityPlayer);
        
        if (par1ItemStack.stackSize <= 0)
        {
            return new ItemStack(Items.bucket);
        }

        par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.bucket));

        return par1ItemStack;
    }
    
    protected void func_77849_c(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote && this.potionId > 0 && par2World.rand.nextFloat() < this.potionEffectProbability)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par1ItemStack = super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
        
        MovingObjectPosition var12 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);
        if (var12 == null && par3EntityPlayer.canEat(this.alwaysEdible))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }
    
    public ItemDrinkBucket setPotionEffect(int par1, int par2, int par3, float par4)
    {
        this.potionId = par1;
        this.potionDuration = par2;
        this.potionAmplifier = par3;
        this.potionEffectProbability = par4;
        return this;
    }

    public ItemDrinkBucket setAlwaysEdible()
    {
        this.alwaysEdible = true;
        return this;
    }
}
