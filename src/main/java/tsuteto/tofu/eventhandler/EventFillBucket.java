package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.BlockMisoBarrel;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;

public class EventFillBucket
{
    @SubscribeEvent
    public void onFillBucket(FillBucketEvent event)
    {
        World par2World = event.world;
        ItemStack par1ItemStack = event.current;
        EntityPlayer par3EntityPlayer = event.entityPlayer;
        MovingObjectPosition var12 = event.target;

        int var13 = var12.blockX;
        int var14 = var12.blockY;
        int var15 = var12.blockZ;

        if (!par2World.canMineBlock(par3EntityPlayer, var13, var14, var15))
        {
            return;
        }

        if (par1ItemStack.getItem() == Items.bucket)
        {
            if (var12.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                Block block = par2World.getBlock(var13, var14, var15);
                ItemStack filledBucket = this.pickUpFluid(par3EntityPlayer, par2World, var13, var14, var15, block);
                if (filledBucket != null)
                {
                    event.result = filledBucket;
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }

    private ItemStack pickUpFluid(EntityPlayer player, World world, int var13, int var14, int var15, Block block)
    {
        if (block == TcBlocks.soymilkStill)
        {
        	world.setBlockToAir(var13, var14, var15); // replace with an air block
            return new ItemStack(TcItems.bucketSoymilk);
        }

        if (block == TcBlocks.soymilkHellStill)
        {
            world.setBlockToAir(var13, var14, var15);
            return new ItemStack(TcItems.bucketSoymilkHell);
        }

        if (block == TcBlocks.soySauceStill)
        {
        	world.setBlockToAir(var13, var14, var15);
            return new ItemStack(TcItems.bucketSoySauce);
        }

        if (block == TcBlocks.barrelMiso)
        {
            if (((BlockMisoBarrel) TcBlocks.barrelMiso).removeSoySauce(world, var13, var14, var15))
            {
                TcAchievementMgr.achieve(player, Key.soySauce);
                return new ItemStack(TcItems.bucketSoySauce);
            }

        }

        return null;
    }
}
