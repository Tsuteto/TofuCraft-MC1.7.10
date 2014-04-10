package tsuteto.tofu.block;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.block.tileentity.TileEntityMorijio;
import tsuteto.tofu.block.tileentity.TileEntitySaltFurnace;
import tsuteto.tofu.block.tileentity.TileEntityTfStorage;
import tsuteto.tofu.fluids.TcFluids;
import tsuteto.tofu.item.*;
import tsuteto.tofu.material.TcMaterial;
import tsuteto.tofu.util.ItemUtils;
import tsuteto.tofu.util.ModLog;

public class TcBlocks
{
    public static Block soybean;
    public static Block leek;
    public static Block tofuTerrain;
    public static BlockTofuBase tofuKinu;
    public static BlockTofuBase tofuMomen;
    public static BlockTofuBase tofuIshi;
    public static BlockTofuBase tofuMetal;
    public static BlockTofuBase tofuGrilled;
    public static BlockTofuBase tofuDried;
    public static BlockTofuBase tofuFriedPouch;
    public static BlockTofuBase tofuFried;
    public static BlockTofuBase tofuEgg;
    public static BlockTofuBase tofuAnnin;
    public static BlockTofuBase tofuSesame;
    public static BlockTofuBase tofuZunda;
    public static BlockTofuBase tofuMiso;
    public static BlockTofuBase tofuStrawberry;
    public static BlockTofuBase tofuHell;
    public static BlockTofuBase tofuGlow;
    public static BlockTofuBase tofuDiamond;
    public static Block saltFurnaceIdle;
    public static Block saltFurnaceActive;
    public static Block tofuCake;
    public static BlockTofuPortal tofuPortal;
    public static Block morijio;
    public static BlockTcStationary soymilkStill;
    public static Block barrelMiso;
    public static BlockTcStationary soySauceStill;
    public static Block nattoBed;
    public static BlockTcLeaves tcLeaves;
    public static Block tcLog;
    public static Block tcSapling;
    public static Block sesame;
    public static Block natto;
    public static Block sprouts;
    public static Block tofuStairsKinu;
    public static Block tofuStairsMomen;
    public static Block tofuStairsIshi;
    public static Block tofuStairsMetal;
    public static Block tofuStairsGrilled;
    public static Block tofuStairsDried;
    public static Block tofuStairsFriedPouch;
    public static Block tofuStairsFried;
    public static Block tofuStairsEgg;
    public static Block tofuStairsAnnin;
    public static Block tofuStairsSesame;
    public static Block tofuStairsZunda;
    public static Block tofuStairsStrawberry;
    public static Block tofuStairsHell;
    public static Block tofuStairsGlow;
    public static Block tofuStairsDiamond;
    public static BlockSlab tofuSingleSlab1;
    public static BlockSlab tofuSingleSlab2;
    public static BlockSlab tofuSingleSlab3;
    public static BlockSlab tofuSingleSlabFaces;
    public static BlockSlab tofuSingleSlabGlow;
    public static BlockSlab tofuDoubleSlab1;
    public static BlockSlab tofuDoubleSlab2;
    public static BlockSlab tofuDoubleSlab3;
    public static BlockSlab tofuDoubleSlabFaces;
    public static BlockSlab tofuDoubleSlabGlow;
    public static BlockTcStationary soymilkHellStill;
    public static Block soybeanHell;
    public static BlockTcOre oreTofuDiamond;
    public static BlockTcOre oreTofu;
    public static Block salt;
    public static Block yuba;
    public static Block barrelMisoTofu;
    public static Block tofuStairsMiso;
    public static Block barrelGlowtofu;
    public static Block tfStorageIdle;
    public static Block tfStorageActive;
    public static Block tfMachineCase;

    // === External Mod Blocks ===
    public static Block sakuraLeaves; // from Bamboo Mod

    public static void register()
    {
        soybean = $("soybeans", new BlockSoybean())
                .withResource("blockSoybeans")
                .registerBlock();

        soybeanHell = $("soybeansHell", new BlockSoybeanHell())
                .withResource("blockSoybeansHell")
                .registerBlock();

        /*
         * Tofu
         */
        tofuTerrain = $("blockTofuTerrain", new BlockTofuTerrain())
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuKinu = (BlockTofuBase) $("blockTofuKinu", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setFragile()
                .setFreeze(5)
                .setHardness(0.1F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuMomen = (BlockTofuBase) $("blockTofuMomen", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setDrain(3)
                .setHardness(0.4F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuIshi = (BlockTofuBase) $("blockTofuIshi", new BlockTofuIshi(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setDrain(8)
                .setScoopable(false)
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setStepSound(Block.soundTypeStone)
                ;

        tofuMetal = (BlockTofuBase) $("blockTofuMetal", new BlockTofu(Material.iron))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock()
                .setScoopable(false)
                .setHardness(6.0F)
                .setResistance(15.0F)
                .setStepSound(Block.soundTypeMetal)
                ;

        tofuGrilled = (BlockTofuBase) $("blockTofuGrilled", new BlockTofuGrilled())
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(50.0F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuDried = (BlockTofuBase) $("blockTofuDried", new BlockTofu(Material.sponge))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setScoopable(false)
                .setHardness(1.0F)
                .setResistance(2.0F)
                .setStepSound(Block.soundTypeStone)
                ;

        tofuFriedPouch = (BlockTofuBase) $("blockTofuFriedPouch", new BlockTofu(TcMaterial.tofu))
                .withResource("blockTofuPouchFried")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(8.0F)
                .setStepSound(Block.soundTypeSand)
                ;

        tofuFried = (BlockTofuBase) $("blockTofuFried", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(2.0F)
                .setResistance(10.0F)
                .setStepSound(Block.soundTypeGravel)
                ;

        tofuEgg = (BlockTofuBase) $("blockTofuEgg", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.2F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuAnnin = (BlockTofuBase) $("blockTofuAnnin", new BlockTofuAnnin(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.2F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuSesame = (BlockTofuBase) $("blockTofuSesame", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.2F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuZunda = (BlockTofuBase) $("blockTofuZunda", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.3F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuStrawberry = (BlockTofuBase) $("blockTofuStrawberry", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuMiso = (BlockTofuBase) $("blockTofuMiso", new BlockTofu(TcMaterial.tofu))
                .withResource("blockTofuYamauni")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuHell = (BlockTofuBase) $("blockTofuHell", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuGlow = (BlockTofuBase) $("blockTofuGlow", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setLightLevel(0.9375F)
                .setStepSound(Block.soundTypeSnow)
                ;

        tofuDiamond = (BlockTofuBase) $("blockTofuDiamond", new BlockTofu(TcMaterial.tofu))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock()
                .setScoopable(false)
                .setHardness(8.0F)
                .setResistance(15.0F)
                .setStepSound(Block.soundTypeGlass)
                ;

        /*
         * Misc
         */
        leek = $("blockLeek", new BlockLeek())
                .wrappedBy(ItemLeekDense.class)
                .registerBlock()
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeGrass)
                .setCreativeTab(CreativeTabs.tabDecorations);

        saltFurnaceIdle = $("saltFurnaceIdle", new BlockSaltFurnace(false))
                .withResource("saltFurnace")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(3.5F)
                .setStepSound(Block.soundTypeStone)
                .setCreativeTab(CreativeTabs.tabDecorations)
                ;

        saltFurnaceActive = $("saltFurnaceActive", new BlockSaltFurnace(true))
                .withResource("saltFurnace")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(3.5F)
                .setStepSound(Block.soundTypeStone)
                .setLightLevel(0.875F)
                ;
        GameRegistry.registerTileEntity(TileEntitySaltFurnace.class, "tofucraft:SaltFurnace");

        tofuCake = $("blockTofuCake", new BlockTofuCake())
                .withResource("tofuCake")
                .registerBlock()
                .disableStats()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
                ;

        tofuPortal = (BlockTofuPortal) $("blockTofuPortal", new BlockTofuPortal())
                .withResource("tofuPortal")
                .registerBlock()
                .setHardness(-1.0F)
                .setStepSound(Block.soundTypeGlass)
                .setLightLevel(0.75F);

        morijio = $("blockMorijio", new BlockMorijio())
                .withResource("morijio")
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
                ;
        GameRegistry.registerTileEntity(TileEntityMorijio.class, "tofucraft:Morijio");

        barrelMiso = $("blockBarrelMiso", new BlockMisoBarrel(Material.wood))
                .withResource("barrelMiso")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(3)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                ;

        nattoBed = $("nattoBed", new BlockNattoBed(Material.grass))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setFermRate(3)
                .setHardness(0.8F)
                .setStepSound(Block.soundTypeGrass)
                .setCreativeTab(CreativeTabs.tabDecorations);

        sesame = $("blockSesame", new BlockSesame())
                .registerBlock();

        natto = $("blockNatto", new BlockNatto())
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.3F)
                .setStepSound(Block.soundTypeSnow)
                .setCreativeTab(CreativeTabs.tabBlock);

        sprouts = $("blockSprouts", new BlockSprouts())
                .registerBlock();

        salt = $("blockSalt", new BlockFalling())
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeSand)
                .setCreativeTab(CreativeTabs.tabBlock);

        yuba = $("blockYuba", new BlockYuba())
                .registerBlock()
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeSnow)
                ;

        barrelMisoTofu = $("blockBarrelMisoTofu", new BlockMisoTofuBarrel(Material.wood))
                .withResource("barrelMisoTofu")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(2)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                ;

        barrelGlowtofu = $("blockBarrelGlowtofu", new BlockGlowtofuBarrel(Material.wood))
                .withResource("barrelGlowtofu")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(4)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                ;

        /*
         * TF machine
         */
        tfMachineCase = $("tfMachineCase", new BlockTfMachineCase())
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(CreativeTabs.tabBlock)
                ;

        tfStorageIdle = $("tfStorageIdle", new BlockTfStorage(false))
                .withResource("tfStorage")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(CreativeTabs.tabDecorations)
                ;

        tfStorageActive = $("tfStorageActive", new BlockTfStorage(true))
                .withResource("tfStorage")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
                ;
        GameRegistry.registerTileEntity(TileEntityTfStorage.class, "tofucraft:TfStorage");

        /*
         * Ore
         */
        // Contained ore is set in TcItem.
        oreTofu = (BlockTcOre)$("blockOreTofu", new BlockTcOre(1, 5))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock()
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setStepSound(Block.soundTypeStone)
                ;

        oreTofuDiamond = (BlockTcOre)$("blockOreTofuDiamond", new BlockTcOreDiamond(3, 7))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(5.0F)
                .setStepSound(Block.soundTypeSnow)
                ;

        /*
         * Fluid
         */
        soymilkStill = (BlockTcStationary)$("soymilkStill",
                new BlockSoymilkStill(Material.water,
                        new String[]{"tofucraft:soymilk", "tofucraft:soymilk_flow"}))
                .withResource("soymilk")
                .registerBlock()
                .setHardness(100.0F)
                .setLightOpacity(8)
                ;

        soymilkHellStill = (BlockTcStationary)$("soymilkHellStill",
                new BlockTcStationary(TcFluids.SOYMILK_HELL, Material.water,
                        new String[]{"tofucraft:soymilkHell", "tofucraft:soymilkHell_flow"}))
                .withResource("soymilkHell")
                .registerBlock()
                .setHardness(100.0F)
                .setLightOpacity(8)
                ;

        soySauceStill = (BlockTcStationary)$("soySauceStill",
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
        tcLeaves = (BlockTcLeaves)$("tcLeaves", new BlockTcLeaves())
                .withResource("leaves")
                .wrappedBy(ItemTcLeaves.class)
                .registerBlock()
                .setHardness(0.2F)
                .setLightOpacity(1)
                .setStepSound(Block.soundTypeGrass)
                ;

        tcLog = $("tcWood", new BlockTcLog())
                .withResource("log")
                .wrappedBy(ItemTcMultiTextureTile.class)
                .havingArgs(new Object[]{BlockTcLog.woodType})
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
                ;

        tcSapling = $("tcSapling", new BlockTcSapling(0))
                .withResource("sapling")
                .wrappedBy(ItemTcMultiTextureTile.class)
                .havingArgs(new Object[]{BlockTcSapling.WOOD_TYPES})
                .registerBlock()
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeGrass)
                ;

        /*
         * Stairs
         */
        tofuStairsKinu = $("tofuStairsKinu", new BlockTofuStairs(tofuKinu, 0))
                .withResource("stairsTofuKinu")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setFragile()
                ;

        tofuStairsMomen = $("tofuStairsMomen", new BlockTofuStairs(tofuMomen, 0))
                .withResource("stairsTofuMomen")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsIshi = $("tofuStairsIshi", new BlockTofuStairs(tofuIshi, 0))
                .withResource("stairsTofuIshi")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock();

        tofuStairsMetal = $("tofuStairsMetal", new BlockTofuStairs(tofuMetal, 0))
                .withResource("stairsTofuMetal")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock();

        tofuStairsGrilled = $("tofuStairsGrilled", new BlockTofuStairs(tofuGrilled, 0))
                .withResource("stairsTofuGrilled")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsDried = $("tofuStairsDried", new BlockTofuStairs(tofuDried, 0))
                .withResource("stairsTofuDried")
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsFriedPouch = $("tofuStairsFriedPouch", new BlockTofuStairs(tofuFriedPouch, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsFried = $("tofuStairsFried", new BlockTofuStairs(tofuFried, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsEgg = $("tofuStairsEgg", new BlockTofuStairs(tofuEgg, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsAnnin = $("tofuStairsAnnin", new BlockTofuStairs(tofuAnnin, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsSesame = $("tofuStairsSesame", new BlockTofuStairs(tofuSesame, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsZunda = $("tofuStairsZunda", new BlockTofuStairs(tofuZunda, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsStrawberry = $("tofuStairsStrawberry", new BlockTofuStairs(tofuStrawberry, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsHell = $("tofuStairsHell", new BlockTofuStairs(tofuHell, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        tofuStairsGlow = $("tofuStairsGlow", new BlockTofuStairs(tofuGlow, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setLightLevel(1.0F);

        tofuStairsDiamond = $("tofuStairsDiamond", new BlockTofuStairs(tofuDiamond, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 1)
                .registerBlock();

        tofuStairsMiso = $("tofuStairsMiso", new BlockTofuStairs(tofuMiso, 0))
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock();

        /*
         * Slabs
         */
        tofuSingleSlab1 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes1, BlockTofuStep.typesObsolete1);
        tofuDoubleSlab1 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes1, BlockTofuStep.typesObsolete1);
        $("tofuSingleSlab1", tofuSingleSlab1)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab1, tofuDoubleSlab1, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab1", tofuDoubleSlab1)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab1, tofuDoubleSlab1, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        tofuSingleSlab2 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes2, BlockTofuStep.typesObsolete2);
        tofuDoubleSlab2 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes2, BlockTofuStep.typesObsolete2);
        $("tofuSingleSlab2", tofuSingleSlab2)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab2, tofuDoubleSlab2, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab2", tofuDoubleSlab2)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab2, tofuDoubleSlab2, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        tofuSingleSlab3 = new BlockTofuStep(false, BlockTofuStep.blockStepTypes3, BlockTofuStep.typesObsolete3);
        tofuDoubleSlab3 = new BlockTofuStep(true, BlockTofuStep.blockStepTypes3, BlockTofuStep.typesObsolete3);
        $("tofuSingleSlab3", tofuSingleSlab3)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab3, tofuDoubleSlab3, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlab3", tofuDoubleSlab3)
                .withResource("tofuSlab")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlab3, tofuDoubleSlab3, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setStepSound(Block.soundTypeSnow);

        tofuSingleSlabFaces = new BlockTofuStepFaces(false);
        tofuDoubleSlabFaces = new BlockTofuStepFaces(true);
        $("tofuSingleSlabFaces", tofuSingleSlabFaces)
                .withResource("tofuSlab.grilled")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlabFaces, tofuDoubleSlabFaces, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(50.0F)
                .setStepSound(Block.soundTypeSnow);

        $("tofuDoubleSlabFaces", tofuDoubleSlabFaces)
                .withResource("tofuSlab.grilled")
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlabFaces, tofuDoubleSlabFaces, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(1.0F)
                .setResistance(50.0F)
                .setStepSound(Block.soundTypeSnow);

        tofuSingleSlabGlow = new BlockTofuStepSimple(false);
        tofuDoubleSlabGlow = new BlockTofuStepSimple(true);
        $("tofuSingleSlabGlow", tofuSingleSlabGlow)
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlabGlow, tofuDoubleSlabGlow, false)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setLightLevel(0.9375F)
                .setStepSound(Block.soundTypeSnow)
                .setBlockTextureName("tofucraft:blockTofuGlow")
                .setBlockName("tofucraft:tofuSlab.glow");

        $("tofuDoubleSlabGlow", tofuDoubleSlabGlow)
                .wrappedBy(ItemTcSlab.class)
                .havingArgs(tofuSingleSlabGlow, tofuDoubleSlabGlow, true)
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.5F)
                .setLightLevel(0.9375F)
                .setStepSound(Block.soundTypeSnow)
                .setBlockTextureName("tofucraft:blockTofuGlow")
                .setBlockName("tofucraft:tofuSlab.glow");

    }

    /**
     * === External Mod Items ===
     */
    public static void registerExternalModBlocks()
    {
        if (Loader.isModLoaded("BambooMod"))
        {
            sakuraLeaves = ItemUtils.getExternalModBlockWithRegex("(?i)sakuraleaves");
            ModLog.debug("sakuraLeaves: " + sakuraLeaves);
        }
    }

    public static <T extends Block> BlockRegister<T> $(String name, T block)
    {
        return new BlockRegister<T>(name, block);
    }

    private static class BlockRegister<T extends Block>
    {
        private T block;
        private Class<? extends ItemBlock> itemBlock = ItemBlock.class;
        private Object[] itemCtorArgs;
        private String uniqueName;
        private String resourceName;

        public BlockRegister(String name, T block)
        {
            this.block = block;
            this.uniqueName = name;
            this.resourceName = name;
        }

        public BlockRegister<T> withResource(String name)
        {
            this.resourceName = name;
            return this;
        }

        public BlockRegister<T> wrappedByItemTcBlock()
        {
            this.itemBlock = ItemTcBlock.class;
            return this;
        }

        public BlockRegister<T> wrappedBy(Class<? extends ItemBlock> itemBlock)
        {
            this.itemBlock = itemBlock;
            return this;
        }

        public BlockRegister<T> havingArgs(Object... itemCtorArgs)
        {
            this.itemCtorArgs = itemCtorArgs;
            return this;
        }

        public BlockRegister<T> setHarvestLevel(String tool, int level)
        {
            block.setHarvestLevel(tool, level);
            return this;
        }

        public T registerBlock()
        {
            block.setBlockName(TofuCraftCore.resourceDomain + resourceName);
            block.setBlockTextureName(TofuCraftCore.resourceDomain + resourceName);
            if (itemCtorArgs != null)
            {
                GameRegistry.registerBlock(block, itemBlock, uniqueName, TofuCraftCore.modid, itemCtorArgs);
            }
            else
            {
                GameRegistry.registerBlock(block, itemBlock, uniqueName, TofuCraftCore.modid);
            }
            return block;
        }
    }
}
