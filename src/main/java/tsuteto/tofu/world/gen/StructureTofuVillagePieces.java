package tsuteto.tofu.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.material.TcMaterial;
import tsuteto.tofu.util.ModLog;

import java.util.List;
import java.util.Random;

public class StructureTofuVillagePieces extends StructureVillagePieces
{
    public static class Start extends StructureVillagePieces.Start
    {
        public Start()
        {
        }

        public Start(WorldChunkManager p_i2104_1_, int p_i2104_2_, Random p_i2104_3_, int p_i2104_4_, int p_i2104_5_, List p_i2104_6_, int p_i2104_7_)
        {
            super(p_i2104_1_, p_i2104_2_, p_i2104_3_, p_i2104_4_, p_i2104_5_, p_i2104_6_, p_i2104_7_);
        }
    }

    public abstract static class TofuVillage extends Village
    {
        public TofuVillage() {}

        protected TofuVillage(StructureTofuVillagePieces.Start p_i2107_1_, int p_i2107_2_)
        {
            super(p_i2107_1_, p_i2107_2_);
        }

        protected Block func_151558_b(Block original, int type)
        {
            Block replacement;
            
            if (original.getMaterial() == TcMaterial.tofu)
            {
                replacement = original;
            }
            else if (original == Blocks.oak_stairs)
            {
                replacement = TcBlocks.tofuStairsMomen;
            }
            else if (original == Blocks.stone_stairs)
            {
                replacement = TcBlocks.tofuStairsMomen;
            }
            else if (original == Blocks.gravel)
            {
                replacement = TcBlocks.tofuGrilled;
            }
            else if (original == Blocks.furnace)
            {
                replacement = TcBlocks.saltFurnaceIdle;
            }
            else if (original == Blocks.stone_slab)
            {
                replacement = TcBlocks.tofuSingleSlab1;
            }
            else if (original == Blocks.double_stone_slab)
            {
                replacement = TcBlocks.tofuMomen;
            }
            else if (original == Blocks.farmland)
            {
                replacement = TcBlocks.tofuFarmland;
            }
            else if (original == Blocks.chest)
            {
                replacement = Blocks.air; // not working...
            }
            else if (original instanceof BlockPressurePlate)
            {
                replacement = Blocks.air;
            }
            else if (original instanceof BlockPane)
            {
                replacement = Blocks.air;
            }
            else if (original instanceof BlockCrops)
            {
                replacement = TcBlocks.soybean;
            }
            else if (original.getMaterial() == Material.wood)
            {
                replacement = TcBlocks.tofuMomen;
            }
            else if (original.getMaterial() == Material.rock)
            {
                replacement = TcBlocks.tofuMomen;
            }
            else if (original.getMaterial() == Material.ground)
            {
                replacement = TcBlocks.tofuDried;
            }
            else if (original.getMaterial().isLiquid())
            {
                replacement = TcBlocks.soymilkStill;
            }
            else if (!original.getMaterial().isOpaque() || !original.getMaterial().isSolid())
            {
                replacement = Blocks.air;
            }
            else
            {
                replacement = TcBlocks.tofuMomen;
            }

            ModLog.debug("Village block replaced: %s -> %s", original.getLocalizedName(), replacement.getLocalizedName());

            return replacement;
        }
    }
}
