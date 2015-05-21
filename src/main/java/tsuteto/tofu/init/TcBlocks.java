package tsuteto.tofu.init;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import tsuteto.tofu.block.*;
import tsuteto.tofu.data.TofuInfo;
import tsuteto.tofu.item.TofuMaterial;

import java.util.EnumMap;

public class TcBlocks
{
    public static EnumMap<TofuMaterial, BlockTofuBase> tofuBlockMap = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, TofuInfo> tofuInfoMap = Maps.newEnumMap(TofuMaterial.class);

    public static Block soybean;
    public static Block leek;

    public static Block tofuTerrain;
    public static Block tofuFarmland;
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
    public static Block tofuMinced;

    public static Block saltFurnaceIdle;
    public static Block saltFurnaceActive;
    public static Block tofuCake;
    public static BlockTofuPortal tofuPortal;
    public static Block morijio;
    public static Block chikuwaPlatformTofu;
    public static Block chikuwaPlatformPlain;

    public static BlockTcStationary soymilkStill;
    public static BlockTcStationary soySauceStill;

    public static Block barrelMiso;
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

    public static EnumMap<TofuMaterial, Block> tofuDoors = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, Block> tofuWalls = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, Block> tofuTorches = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, Block> tofuFenceGates = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, Block> tofuTrapdoors = Maps.newEnumMap(TofuMaterial.class);
    public static EnumMap<TofuMaterial, Block> tofuLadders = Maps.newEnumMap(TofuMaterial.class);

    public static BlockTcStationary soymilkHellStill;
    public static Block soybeanHell;
    public static BlockTcOre oreTofuDiamond;
    public static BlockTcOre oreTofu;
    public static Block salt;
    public static Block yuba;
    public static Block saltPan;

    public static Block barrelMisoTofu;
    public static Block tofuStairsMiso;
    public static Block barrelGlowtofu;
    public static Block barrelAdvTofuGem;
    public static Block advTofuGem;

    public static Block tfStorageIdle;
    public static Block tfStorageActive;
    public static Block tfMachineCase;
    public static Block tfAntennaMedium;
    public static Block tfAntennaUltra;
    public static Block tfAntennaSuper;
    public static Block tfSaturatorIdle;
    public static Block tfSaturatorActive;
    public static Block tfCollector;
    public static Block tfCondenserIdle;
    public static Block tfCondenserActive;
    public static Block tfOvenIdle;
    public static Block tfOvenActive;
    public static Block tfReformerIdle;
    public static Block tfReformerActive;

    // === External Mod Blocks ===
    public static Block sakuraLeaves; // from Bamboo Mod

}
