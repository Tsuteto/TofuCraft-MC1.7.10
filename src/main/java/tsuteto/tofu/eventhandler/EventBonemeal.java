package tsuteto.tofu.eventhandler;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.BonemealEvent;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.BlockLeek;
import tsuteto.tofu.block.BlockTcSapling;
import tsuteto.tofu.block.BlockTofuBase;
import tsuteto.tofu.block.TcBlocks;

import java.util.Random;

public class EventBonemeal
{
    @SubscribeEvent
    public void onBonemeal(BonemealEvent event)
    {
        EntityPlayer player = event.entityPlayer;
        World world = event.world;
        Random rand = world.rand;
        Block block = event.block;
        int posX = event.x;
        int posY = event.y;
        int posZ = event.z;
        int var11;
        int var12;
        int var13;

        // Saplings
        if (block == TcBlocks.tcSapling)
        {
            if (!world.isRemote)
            {
                ((BlockTcSapling) TcBlocks.tcSapling).growTree(world, posX, posY, posZ, world.rand);
            }
            event.setResult(Event.Result.ALLOW);
        }

        // Leek
        if (block instanceof BlockTofuBase)
        {
            if (!world.isRemote)
            {
                label133:

                for (var12 = 0; var12 < 32; ++var12)
                {
                    var13 = posX;
                    int var14 = posY + 1;
                    int var15 = posZ;

                    for (int var16 = 0; var16 < var12 / 16; ++var16)
                    {
                        var13 += rand.nextInt(3) - 1;
                        var14 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
                        var15 += rand.nextInt(3) - 1;

                        if (!(world.getBlock(var13, var14 - 1, var15) instanceof BlockTofuBase) || world.getBlock(var13, var14, var15).isNormalCube())
                        {
                            continue label133;
                        }
                    }

                    if (world.isAirBlock(var13, var14, var15))
                    {
                        if (rand.nextInt(10) < 5)
                        {
                            if (TcBlocks.leek.canBlockStay(world, var13, var14, var15))
                            {
                                world.setBlock(var13, var14, var15, TcBlocks.leek, BlockLeek.META_NATURAL, 3);
                                TcAchievementMgr.achieve(player, Key.leek);
                            }
                        }
                        else
                        {
                            world.getBiomeGenForCoords(var13, var15).plantFlower(world, rand, var13, var14, var15);
                        }
                    }
                }
            }
            event.setResult(Event.Result.ALLOW);
        }

        //event.setCanceled(false);
    }
}
