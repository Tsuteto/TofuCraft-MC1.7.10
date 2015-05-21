package tsuteto.tofu.world.tofuvillage;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.material.TcMaterial;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.world.biome.BiomeGenTofuBase;

public class GetVillageBlockIDEventHandler
{

	@SubscribeEvent
	public void onVillageBlockID(BiomeEvent.GetVillageBlockID event)
	{
		if(event.biome instanceof BiomeGenTofuBase)
		{
            if (event.original.getMaterial() == TcMaterial.tofu)
            {
                event.replacement = event.original;
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
            else if (event.original == Blocks.furnace)
            {
            	event.replacement = TcBlocks.saltFurnaceIdle;
            }
            else if (event.original == Blocks.stone_slab)
            {
                event.replacement = TcBlocks.tofuSingleSlab1;
            }
            else if (event.original == Blocks.double_stone_slab)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original == Blocks.farmland)
            {
                event.replacement = TcBlocks.tofuFarmland;
            }
            else if (event.original == Blocks.fence)
            {
                event.replacement = TcBlocks.tofuWalls.get(TofuMaterial.momen);
            }
            else if (event.original == Blocks.chest)
            {
                event.replacement = Blocks.air; // not working...
            }
            else if (event.original instanceof BlockPressurePlate)
            {
                event.replacement = Blocks.air;
            }
            else if (event.original instanceof BlockPane)
            {
                event.replacement = Blocks.air;
            }
            else if (event.original instanceof BlockCrops)
            {
                event.replacement = TcBlocks.soybean;
            }
            else if (event.original.getMaterial() == Material.wood)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original.getMaterial() == Material.rock)
            {
                event.replacement = TcBlocks.tofuMomen;
            }
            else if (event.original.getMaterial() == Material.ground)
            {
                event.replacement = TcBlocks.tofuDried;
            }
            else if (event.original.getMaterial().isLiquid())
            {
                event.replacement = TcBlocks.soymilkStill;
            }
            else if (!event.original.getMaterial().isOpaque() || !event.original.getMaterial().isSolid())
            {
                event.replacement = Blocks.air;
            }
            else
            {
                event.replacement = TcBlocks.tofuMomen;
            }

            ModLog.debug("Village block replaced: %s -> %s", event.original.getLocalizedName(), event.replacement.getLocalizedName());
            event.setResult(Event.Result.DENY);
		}
	}

    @SubscribeEvent
    public void onVillageBlockMeta(BiomeEvent.GetVillageBlockMeta event)
    {
        if(event.biome instanceof BiomeGenTofuBase)
        {
            if (event.original == Blocks.stone_slab)
            {
                event.replacement = 1; // momen tofu slab
            }
        }
    }
}
