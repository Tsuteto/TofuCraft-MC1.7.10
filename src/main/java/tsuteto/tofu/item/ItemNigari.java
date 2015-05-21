package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tsuteto.tofu.init.TcBlocks;

public class ItemNigari extends ItemColoredBottle
{
    public ItemNigari()
    {
        super(0x809cff);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

        if (var4 == null)
        {
            return par1ItemStack;
        }
        else
        {
            if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int var5 = var4.blockX;
                int var6 = var4.blockY;
                int var7 = var4.blockZ;
                Block var11 = par2World.getBlock(var5, var6, var7);
                Block var13 = null;
                
                if (var11 == TcBlocks.soymilkStill)
                {
                    var13 = TcBlocks.tofuKinu;
                }
                else if (var11 == TcBlocks.soymilkHellStill)
                {
                    var13 = TcBlocks.tofuHell;
                }
                
                if (var13 != null)
                {
                    par2World.playSoundEffect((double)((float)var5 + 0.5F), (double)((float)var6 + 0.5F), (double)((float)var7 + 0.5F),
                            var13.stepSound.getBreakSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);
    
                    par2World.setBlock(var5, var6, var7, var13);
                    
                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        --par1ItemStack.stackSize;

                        ItemStack container = new ItemStack(this.getContainerItem());
        
                        if (par1ItemStack.stackSize <= 0)
                        {
                            return container;
                        }
        
                        if (!par3EntityPlayer.inventory.addItemStackToInventory(container))
                        {
                            par3EntityPlayer.dropPlayerItemWithRandomChoice(container, false);
                        }
                    }
                }
            }
            return par1ItemStack;
        }
    }
}
