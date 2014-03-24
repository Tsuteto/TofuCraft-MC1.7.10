package tsuteto.tofu.world.tofuvillage;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.world.biome.BiomeGenTofuBase;

public class GetVillageBlockIDEventHandler
{

	@SubscribeEvent
	public void GetVillageBlockID(BiomeEvent.GetVillageBlockID event)
	{
		if(event.biome instanceof BiomeGenTofuBase)
		{
            if (event.original == Blocks.log)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.cobblestone)
            {
            	event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.planks)
            {
            	event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.oak_stairs)
            {
            	event.replacement = TcBlocks.tofuStairsMomen;
            }
            else if (event.original == Blocks.stone_stairs)
            {
            	event.replacement = TcBlocks.tofuStairsMomen;
            }
            else if (event.original == Blocks.gravel)
            {
            	event.replacement = TcBlocks.tofuGrilled;
            }
            else if (event.original == Blocks.dirt)
            {
            	event.replacement = TcBlocks.tofuDried;
            }
            else if (event.original == Blocks.furnace)
            {
            	event.replacement = TcBlocks.saltFurnaceIdle;
            }
            else if (event.original == Blocks.carrots)
            {
            	event.replacement = TcBlocks.soybean;
            }
            else if (event.original == Blocks.potatoes)
            {
            	event.replacement = TcBlocks.soybean;
            }
            else if (event.original == Blocks.wheat)
            {
            	event.replacement = TcBlocks.soybean;
            }
            else if (event.original == Blocks.water)
            {
                event.replacement = TcBlocks.soymilkStill;
            }
            else if (event.original == Blocks.flowing_water)
            {
                event.replacement = TcBlocks.soymilkStill;
            }
            else if (event.original == Blocks.lava)
            {
                event.replacement = TcBlocks.soymilkStill;
            }
            else if (event.original == Blocks.flowing_lava)
            {
                event.replacement = TcBlocks.soymilkStill;
            }
            else if (event.original == Blocks.wool)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.stone_slab)
            {
                event.replacement = TcBlocks.tofuSingleSlab1;
            }
            else if (event.original == Blocks.double_stone_slab)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.glass_pane)
            {
                event.replacement = Blocks.air;
            }
            else if (event.original == Blocks.iron_bars)
            {
                event.replacement = Blocks.air;
            }
            else if (event.original == Blocks.carpet)
            {
                event.replacement = Blocks.air;
            }
            else if (event.original == Blocks.chest)
            {
                event.replacement = Blocks.air; // not working...
            }
            else
            {
            	return;
            }

            event.setResult(Event.Result.DENY);
		}
	}
}
