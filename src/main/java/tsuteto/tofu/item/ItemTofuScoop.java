package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tsuteto.tofu.block.BlockTofuBase;
import tsuteto.tofu.block.ITofuScoopable;

public class ItemTofuScoop extends TcItem
{

    public ItemTofuScoop()
    {
        super();
        this.setMaxDamage(352);
        this.setMaxStackSize(1);
        this.setFull3D();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        MovingObjectPosition mpos = this.getMovingObjectPositionFromPlayer(world, player, false);

        if (mpos != null && mpos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            int i = mpos.blockX;
            int j = mpos.blockY;
            int k = mpos.blockZ;
            Block block = world.getBlock(i, j, k);

            if (world.canMineBlock(player, i, j, k) && block != null)
            {
                boolean isScoopable;
                if (block instanceof BlockTofuBase)
                {
                    isScoopable = ((BlockTofuBase) block).isScoopable();
                }
                else
                {
                    isScoopable = block instanceof ITofuScoopable;
                }
                if (isScoopable)
                {
                    itemstack.damageItem(1, player);
                    world.setBlockToAir(i, j, k);

                    if (!world.isRemote)
                    {
                        ItemStack stack;
                        if (block instanceof BlockTofuBase)
                        {
                            stack = ((BlockTofuBase) block).createScoopedBlockStack();
                        }
                        else
                        {
                            stack = new ItemStack(block);
                        }
                        EntityItem drop = new EntityItem(world, mpos.hitVec.xCoord, mpos.hitVec.yCoord, mpos.hitVec.zCoord, stack);
                        world.spawnEntityInWorld(drop);
                    }
                    player.playSound(block.stepSound.getBreakSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                }
            }
        }
        return itemstack;
    }
}
