package tsuteto.tofu.init.block;

import net.minecraft.block.Block;
import tsuteto.tofu.block.*;
import tsuteto.tofu.data.TofuInfo;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.TofuMaterial;
import tsuteto.tofu.material.TcMaterial;

public class LoaderTofuBlock extends TcBlockLoader
{
    static
    {
        registerTofuInfo(new TofuInfo(TofuMaterial.kinu,
                0.1F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.momen,
                0.4F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.ishi,
                2.0F, 10.0F, TcMaterial.tofu, Block.soundTypeWood).setHarvestLevel("pickaxe", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.metal,
                6.0F, 15.0F, TcMaterial.iron, Block.soundTypeMetal).setHarvestLevel("pickaxe", 1));

        registerTofuInfo(new TofuInfo(TofuMaterial.grilled,
                1.0F, 50.0F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.dried,
                1.0F, 2.0F, TcMaterial.sponge, Block.soundTypeStone).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.friedPouch,
                1.0F, 8.0F, TcMaterial.tofu, Block.soundTypeSand).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.fried,
                2.0F, 10.0F, TcMaterial.tofu, Block.soundTypeGravel).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.egg,
                0.2F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.annin,
                0.2F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.sesame,
                0.2F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.zunda,
                0.3F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.strawberry,
                0.5F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.hell,
                0.5F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.glow,
                0.5F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

        registerTofuInfo(new TofuInfo(TofuMaterial.diamond,
                8.0F, 15.0F, TcMaterial.tofu, Block.soundTypeGlass).setHarvestLevel("pickaxe", 1));

        registerTofuInfo(new TofuInfo(TofuMaterial.miso,
                0.5F, TcMaterial.tofu, Block.soundTypeSnow).setHarvestLevel("shovel", 0));

    }

    public static void registerTofuInfo(TofuInfo info)
    {
        TcBlocks.tofuInfoMap.put(info.tofuMaterial, info);
    }

    @Override
    public void load()
    {
        TcBlocks.tofuTerrain = $("blockTofuTerrain", new BlockTofuTerrain())
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
                .setStepSound(Block.soundTypeSnow)
        ;

        TcBlocks.tofuFarmland = $("tofuFarmland", new BlockTofuFarmland())
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
                .setStepSound(Block.soundTypeSnow)
        ;

        TcBlocks.tofuKinu = $("blockTofuKinu", new BlockTofu(TofuMaterial.kinu))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setFragile()
                .setFreeze(5)
        ;

        TcBlocks.tofuMomen = $("blockTofuMomen", new BlockTofu(TofuMaterial.momen))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setDrain(3)
        ;

        TcBlocks.tofuIshi = $("blockTofuIshi", new BlockTofuIshi(TofuMaterial.ishi))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setDrain(8)
                .setScoopable(false)
        ;

        TcBlocks.tofuMetal = $("blockTofuMetal", new BlockTofu(TofuMaterial.metal))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuGrilled = $("blockTofuGrilled", new BlockTofuGrilled(TofuMaterial.grilled))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuDried = $("blockTofuDried", new BlockTofu(TofuMaterial.dried))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuFriedPouch = $("blockTofuFriedPouch", new BlockTofu(TofuMaterial.friedPouch))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuFried = $("blockTofuFried", new BlockTofu(TofuMaterial.fried))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuEgg = $("blockTofuEgg", new BlockTofu(TofuMaterial.egg))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuAnnin = $("blockTofuAnnin", new BlockTofuAnnin(TofuMaterial.annin))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuSesame = $("blockTofuSesame", new BlockTofu(TofuMaterial.sesame))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuZunda = $("blockTofuZunda", new BlockTofu(TofuMaterial.zunda))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuStrawberry = $("blockTofuStrawberry", new BlockTofu(TofuMaterial.strawberry))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuMiso = $("blockTofuMiso", new BlockTofu(TofuMaterial.miso))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuHell = $("blockTofuHell", new BlockTofu(TofuMaterial.hell))
                .wrappedByItemTcBlock()
                .registerBlock()
        ;

        TcBlocks.tofuGlow = (BlockTofuBase) $("blockTofuGlow", new BlockTofu(TofuMaterial.glow))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setLightLevel(0.9375F)
        ;

        TcBlocks.tofuDiamond = $("blockTofuDiamond", new BlockTofu(TofuMaterial.diamond))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setScoopable(false)
        ;

        TcBlocks.tofuMinced = $("blockTofuMinced", new BlockTofuMinced())
                .wrappedByItemTcBlock()
                .setHarvestLevel("shovel", 0)
                .registerBlock()
                .setHardness(0.4F)
                .setStepSound(Block.soundTypeSnow)
        ;

    }
}
