package tsuteto.tofu.init.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.block.*;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.item.*;
import tsuteto.tofu.tileentity.TileEntityChikuwaPlatform;
import tsuteto.tofu.util.Utils;

public class LoaderConstructionBlock extends TcBlockLoader
{
    public void load()
    {
        /*
         * Ore
         */
        // Contained ore is set in TcItem.
        TcBlocks.oreTofu = (BlockTcOre) $("blockOreTofu", new BlockTcOre(2, 5))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock()
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setStepSound(Block.soundTypePiston)
        ;

        TcBlocks.oreTofuDiamond = (BlockTcOre) $("blockOreTofuDiamond", new BlockTcOreDiamond(3, 7))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                .setHardness(1.0F)
                .setResistance(5.0F)
                .setStepSound(Block.soundTypeSnow)
        ;

        /*
         * Fluid
         */
        TcBlocks.soymilkStill = (BlockTcStationary) $("soymilkStill",
                new BlockSoymilkStill(Material.water,
                        new String[]{"tofucraft:soymilk", "tofucraft:soymilk_flow"}))
                .withResource("soymilk")
                .registerBlock()
                .setHardness(100.0F)
                .setLightOpacity(8)
        ;

        TcBlocks.soymilkHellStill = (BlockTcStationary) $("soymilkHellStill",
                new BlockTcStationary(TcFluids.SOYMILK_HELL, Material.water,
                        new String[]{"tofucraft:soymilkHell", "tofucraft:soymilkHell_flow"}))
                .withResource("soymilkHell")
                .registerBlock()
                .setHardness(100.0F)
                .setLightOpacity(8)
        ;

        TcBlocks.soySauceStill = (BlockTcStationary) $("soySauceStill",
                new BlockTcStationary(TcFluids.SOY_SAUCE, Material.water,
                        new String[]{"tofucraft:soySauce", "tofucraft:soySauce_flow"}))
                .withResource("soySauce")
                .registerBlock()
                .setHardness(100.0F)
                .setLightOpacity(8)
        ;

        /*
         * Trees
         */
        TcBlocks.tcLeaves = (BlockTcLeaves) $("tcLeaves", new BlockTcLeaves())
                .withResource("leaves")
                .wrappedBy(ItemTcLeaves.class)
                .registerBlock()
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                .setHardness(0.2F)
                .setLightOpacity(1)
                .setStepSound(Block.soundTypeGrass)
        ;

        TcBlocks.tcLog = $("tcWood", new BlockTcLog())
                .withResource("log")
                .wrappedBy(ItemTcMultiTextureTile.class)
                .havingArgs(new Object[]{BlockTcLog.woodType})
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
        ;

        /*
         * TF machine
         */
        TcBlocks.tfMachineCase = $("tfMachineCase", new BlockTfMachineCase())
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
        ;

        /*
         * Misc
         */
        TcBlocks.leek = $("blockLeek", new BlockLeek())
                .wrappedBy(ItemLeekDense.class)
                .registerBlock()
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeGrass)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
        ;

        TcBlocks.natto = $("blockNatto", new BlockNatto())
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.3F)
                .setStepSound(Block.soundTypeSnow)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);

        TcBlocks.salt = $("blockSalt", new BlockFalling())
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeSand)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);

        TcBlocks.advTofuGem = $("blockAdvTofuGem", new TcBlock(Material.iron))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setLightLevel(0.625F)
                .setHardness(5.0F)
                .setResistance(10.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
        ;

        TcBlocks.chikuwaPlatformTofu = $("chikuwaPlatformTofu", new BlockChikuwaPlatform("tofu"))
                .wrappedByItemTcBlock()
                .withTileEntity(TileEntityChikuwaPlatform.class, "ChikuwaPlatform")
                .registerBlock()
                .setHardness(0.6F)
                .setStepSound(Block.soundTypeSnow)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);

        TcBlocks.chikuwaPlatformPlain = $("chikuwaPlatformPlain", new BlockChikuwaPlatform("plain"))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.6F)
                .setStepSound(Block.soundTypeSnow)
                .setCreativeTab(TcCreativeTabs.CONSTRUCTION);

        /*
         * Stairs
         */
        TcBlocks.tofuStairsKinu = $("tofuStairsKinu", new BlockTofuStairs(TcBlocks.tofuKinu, 0))
                .withResource("stairsTofuKinu")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setFragile()
        ;

        TcBlocks.tofuStairsMomen = $("tofuStairsMomen", new BlockTofuStairs(TcBlocks.tofuMomen, 0))
                .withResource("stairsTofuMomen")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsIshi = $("tofuStairsIshi", new BlockTofuStairs(TcBlocks.tofuIshi, 0))
                .withResource("stairsTofuIshi")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock();

        TcBlocks.tofuStairsMetal = $("tofuStairsMetal", new BlockTofuStairs(TcBlocks.tofuMetal, 0))
                .withResource("stairsTofuMetal")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock();

        TcBlocks.tofuStairsGrilled = $("tofuStairsGrilled", new BlockTofuStairs(TcBlocks.tofuGrilled, 0))
                .withResource("stairsTofuGrilled")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsDried = $("tofuStairsDried", new BlockTofuStairs(TcBlocks.tofuDried, 0))
                .withResource("stairsTofuDried")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsFriedPouch = $("tofuStairsFriedPouch", new BlockTofuStairs(TcBlocks.tofuFriedPouch, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsFried = $("tofuStairsFried", new BlockTofuStairs(TcBlocks.tofuFried, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsEgg = $("tofuStairsEgg", new BlockTofuStairs(TcBlocks.tofuEgg, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsAnnin = $("tofuStairsAnnin", new BlockTofuStairs(TcBlocks.tofuAnnin, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsSesame = $("tofuStairsSesame", new BlockTofuStairs(TcBlocks.tofuSesame, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsZunda = $("tofuStairsZunda", new BlockTofuStairs(TcBlocks.tofuZunda, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsStrawberry = $("tofuStairsStrawberry", new BlockTofuStairs(TcBlocks.tofuStrawberry, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsHell = $("tofuStairsHell", new BlockTofuStairs(TcBlocks.tofuHell, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        TcBlocks.tofuStairsGlow = $("tofuStairsGlow", new BlockTofuStairs(TcBlocks.tofuGlow, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setLightLevel(1.0F);

        TcBlocks.tofuStairsDiamond = $("tofuStairsDiamond", new BlockTofuStairs(TcBlocks.tofuDiamond, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock();

        TcBlocks.tofuStairsMiso = $("tofuStairsMiso", new BlockTofuStairs(TcBlocks.tofuMiso, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        /*
         * Slabs
         */
        TcBlocks.tofuSingleSlab1 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes1, BlockTofuStep.typesObsolete1);
        TcBlocks.tofuDoubleSlab1 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes1, BlockTofuStep.typesObsolete1);
        $("tofuSingleSlab1", TcBlocks.tofuSingleSlab1)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab1, TcBlocks.tofuDoubleSlab1, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab1", TcBlocks.tofuDoubleSlab1)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab1, TcBlocks.tofuDoubleSlab1, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        TcBlocks.tofuSingleSlab2 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes2, BlockTofuStep.typesObsolete2);
        TcBlocks.tofuDoubleSlab2 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes2, BlockTofuStep.typesObsolete2);
        $("tofuSingleSlab2", TcBlocks.tofuSingleSlab2)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab2, TcBlocks.tofuDoubleSlab2, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab2", TcBlocks.tofuDoubleSlab2)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab2, TcBlocks.tofuDoubleSlab2, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        TcBlocks.tofuSingleSlab3 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes3, BlockTofuStep.typesObsolete3);
        TcBlocks.tofuDoubleSlab3 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes3, BlockTofuStep.typesObsolete3);
        $("tofuSingleSlab3", TcBlocks.tofuSingleSlab3)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab3, TcBlocks.tofuDoubleSlab3, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab3", TcBlocks.tofuDoubleSlab3)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlab3, TcBlocks.tofuDoubleSlab3, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        TcBlocks.tofuSingleSlabFaces = new BlockTofuStepFaces(false);
        TcBlocks.tofuDoubleSlabFaces = new BlockTofuStepFaces(true);
        $("tofuSingleSlabFaces", TcBlocks.tofuSingleSlabFaces)
                .withResource("tofuSlab.grilled")
                .wrappedBy(ItemTcSlabFaces.class)
                .havingArgs(TcBlocks.tofuSingleSlabFaces, TcBlocks.tofuDoubleSlabFaces, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(50.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlabFaces", TcBlocks.tofuDoubleSlabFaces)
                .withResource("tofuSlab.grilled")
                .wrappedBy(ItemTcSlabFaces.class)
                .havingArgs(TcBlocks.tofuSingleSlabFaces, TcBlocks.tofuDoubleSlabFaces, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(50.0F)
                .setStepSound(Block.soundTypeSnow);

        TcBlocks.tofuSingleSlabGlow = new BlockTofuStepSimple(false);
        TcBlocks.tofuDoubleSlabGlow = new BlockTofuStepSimple(true);
        $("tofuSingleSlabGlow", TcBlocks.tofuSingleSlabGlow)
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlabGlow, TcBlocks.tofuDoubleSlabGlow, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setLightLevel(0.9375F)
                .setStepSound(Block.soundTypeSnow)
                .setBlockTextureName("tofucraft:blockTofuGlow")
                .setBlockName("tofucraft:tofuSlab.glow");

        $("tofuDoubleSlabGlow", TcBlocks.tofuDoubleSlabGlow)
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(TcBlocks.tofuSingleSlabGlow, TcBlocks.tofuDoubleSlabGlow, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setLightLevel(0.9375F)
                .setStepSound(Block.soundTypeSnow)
                .setBlockTextureName("tofucraft:blockTofuGlow")
                .setBlockName("tofucraft:tofuSlab.glow");

        /*
         * Tofu Doors
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuDoor" + Utils.capitalize(material.name());
            Block tofuDoor = $(blockName, new BlockTofuDoor(material))
                    .withResource("tofuDoor")
                    .registerBlock();

            TcBlocks.tofuDoors.put(material, tofuDoor);
        }

        /*
         * Walls
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuWall" + Utils.capitalize(material.name());
            Block tofuWall = $(blockName, new BlockTofuWall(material))
                    .wrappedBy(ItemBlockTofuMaterial.class)
                    .registerBlock()
                    .setBlockName(TofuCraftCore.resourceDomain + "tofuWall")
                    .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                    ;

            TcBlocks.tofuWalls.put(material, tofuWall);
        }

        /*
         * Torches
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            if (material != TofuMaterial.glow)
            {
                String blockName = "tofuTorch" + Utils.capitalize(material.name());
                Block tofuFence = $(blockName, new BlockTofuTorch(material))
                        .wrappedBy(ItemBlockTofuMaterial.class)
                        .registerBlock()
                        .setBlockName(TofuCraftCore.resourceDomain + "tofuTorch")
                        .setLightLevel(0.9375F)
                        .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                        ;

                TcBlocks.tofuTorches.put(material, tofuFence);
            }
        }

        /*
         * Fence Gates
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuFenceGate" + Utils.capitalize(material.name());
            Block tofuFence = $(blockName, new BlockTofuFenceGate(material))
                    .wrappedBy(ItemBlockTofuMaterial.class)
                    .registerBlock()
                    .setBlockName(TofuCraftCore.resourceDomain + "tofuFenceGate")
                    .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                    ;

            TcBlocks.tofuFenceGates.put(material, tofuFence);
        }

        /*
         * Trapdoors
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuTrapdoor" + Utils.capitalize(material.name());
            Block tofuFence = $(blockName, new BlockTofuTrapdoor(material))
                    .wrappedBy(ItemBlockTofuMaterial.class)
                    .registerBlock()
                    .setBlockName(TofuCraftCore.resourceDomain + "tofuTrapdoor")
                    .setCreativeTab(TcCreativeTabs.CONSTRUCTION) // Redstone tab in vanilla
                    ;

            TcBlocks.tofuTrapdoors.put(material, tofuFence);
        }

        /*
         * Ladders
         */
        for (TofuMaterial material : TofuMaterial.values())
        {
            String blockName = "tofuLadder" + Utils.capitalize(material.name());
            Block tofuFence = $(blockName, new BlockTofuLadder(material))
                    .wrappedBy(ItemBlockTofuMaterial.class)
                    .registerBlock()
                    .setBlockName(TofuCraftCore.resourceDomain + "tofuLadder")
                    .setCreativeTab(TcCreativeTabs.CONSTRUCTION)
                    ;

            TcBlocks.tofuLadders.put(material, tofuFence);
        }
    }
}
