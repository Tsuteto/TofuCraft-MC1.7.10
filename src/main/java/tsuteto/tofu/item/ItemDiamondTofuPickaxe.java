package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemDiamondTofuPickaxe extends ItemTcPickaxe
{
    private DiamondTofuToolImpl impl;

    public ItemDiamondTofuPickaxe(ToolMaterial par2EnumToolMaterial)
    {
        super(par2EnumToolMaterial);
        this.impl = new DiamondTofuToolImpl(this);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int bx, int by, int bz, EntityPlayer owner)
    {
        if (owner.worldObj.isRemote)
        {
            Block blockDestroyed = owner.getEntityWorld().getBlock(bx, by, bz);
            this.impl.onBlockStartBreak(stack, owner.worldObj, blockDestroyed, bx, by, bz, owner);
        }
        return false;
    }
}
