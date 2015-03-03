package tsuteto.tofu.item;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.util.ItemUtils;
import tsuteto.tofu.util.ModLog;

public class ItemTofuHoe extends ItemHoe
{

    public ItemTofuHoe(ToolMaterial p_i45343_1_)
    {
        super(p_i45343_1_);
        MinecraftForge.EVENT_BUS.register(this);
        ModLog.debug("TofuHoe event has been registered");
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return ItemUtils.getCreativeTabs(this);
    }

    @SubscribeEvent
    public void onUseTofuHoe(UseHoeEvent event)
    {
        ItemStack hoe = event.current;
        World world = event.world;
        EntityLivingBase living = event.entityLiving;
        int x = event.x;
        int y = event.y;
        int z = event.z;

        if (hoe.getItem() == TcItems.tofuHoe)
        {
            Block block = world.getBlock(x, y, z);

            if (world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && (block == TcBlocks.tofuMomen || block == TcBlocks.tofuTerrain))
            {
                Block block1 = TcBlocks.tofuFarmland;
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D,
                        block1.stepSound.getStepResourcePath(),
                        (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

                if (world.isRemote)
                {
                    event.setResult(Event.Result.DENY);
                }
                else
                {
                    world.setBlock(x, y, z, block1);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }

}
