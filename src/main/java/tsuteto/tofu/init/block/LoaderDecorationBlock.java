package tsuteto.tofu.init.block;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tsuteto.tofu.block.*;
import tsuteto.tofu.block.render.RenderChikuwaPlatform;
import tsuteto.tofu.block.render.RenderSaltPan;
import tsuteto.tofu.block.render.RenderYubaBlock;
import tsuteto.tofu.creativetabs.TcCreativeTabs;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.item.ItemTcMultiTextureTile;
import tsuteto.tofu.tileentity.*;

public class LoaderDecorationBlock extends TcBlockLoader
{
    public void load()
    {
        /*
         * Plant
         */
        TcBlocks.soybean = $("soybeans", new BlockSoybean())
                .withResource("blockSoybeans")
                .registerBlock();

        TcBlocks.soybeanHell = $("soybeansHell", new BlockSoybeanHell())
                .withResource("blockSoybeansHell")
                .registerBlock();

        TcBlocks.sprouts = $("blockSprouts", new BlockSprouts())
                .registerBlock();

        TcBlocks.sesame = $("blockSesame", new BlockSesame())
                .registerBlock();

        TcBlocks.tcSapling = $("tcSapling", new BlockTcSapling(0))
                .withResource("sapling")
                .wrappedBy(ItemTcMultiTextureTile.class)
                .havingArgs(new Object[]{BlockTcSapling.WOOD_TYPES})
                .registerBlock()
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeGrass)
        ;

        /*
         * Salt Furnace
         */
        TcBlocks.saltFurnaceIdle = $("saltFurnaceIdle", new BlockSaltFurnace(false))
                .withResource("saltFurnace")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(3.5F)
                .setStepSound(Block.soundTypeStone)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.saltFurnaceActive = $("saltFurnaceActive", new BlockSaltFurnace(true))
                .withResource("saltFurnace")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(3.5F)
                .setStepSound(Block.soundTypeStone)
                .setLightLevel(0.875F)
        ;
        GameRegistry.registerTileEntity(TileEntitySaltFurnace.class, "SaltFurnace");

        /*
         * Barrels
         */
        TcBlocks.barrelMiso = $("blockBarrelMiso", new BlockMisoBarrel(Material.wood))
                .withResource("barrelMiso")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(3)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
        ;

        TcBlocks.barrelMisoTofu = $("blockBarrelMisoTofu", new BlockMisoTofuBarrel(Material.wood))
                .withResource("barrelMisoTofu")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(2)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
        ;

        TcBlocks.barrelGlowtofu = $("blockBarrelGlowtofu", new BlockGlowtofuBarrel(Material.wood))
                .withResource("barrelGlowtofu")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(4)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
        ;

        TcBlocks.barrelAdvTofuGem = $("blockBarrelAdvTofuGem", new BlockAdvTofuGemBarrel(Material.wood))
                .withResource("barrelAdvTofuGem")
                .setHarvestLevel("axe", 0)
                .registerBlock()
                .setFermRate(4)
                .setHardness(2.0F)
                .setStepSound(Block.soundTypeWood)
        ;

        /*
         * Misc
         */
        TcBlocks.tofuCake = $("blockTofuCake", new BlockTofuCake())
                .withResource("tofuCake")
                .registerBlock()
                .disableStats()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
        ;

        TcBlocks.tofuPortal = (BlockTofuPortal) $("blockTofuPortal", new BlockTofuPortal())
                .withResource("tofuPortal")
                .registerBlock()
                .setHardness(-1.0F)
                .setStepSound(Block.soundTypeGlass)
                .setLightLevel(0.75F);

        TcBlocks.morijio = $("blockMorijio", new BlockMorijio())
                .withResource("morijio")
                .withTileEntity(TileEntityMorijio.class, "Morijio")
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
        ;

        TcBlocks.nattoBed = $("nattoBed", new BlockNattoBed(Material.grass))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setFermRate(3)
                .setHardness(0.8F)
                .setStepSound(Block.soundTypeGrass)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.yuba = $("blockYuba", new BlockYuba())
                .registerBlock()
                .setHardness(0.0F)
                .setStepSound(Block.soundTypeSnow)
        ;

        TcBlocks.saltPan = $("blockSaltPan", new BlockSaltPan())
                .wrappedByItemTcBlock()
                .registerBlock()
                .setStepSound(Block.soundTypeWood)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        /*
         * TF machine
         */
        TcBlocks.tfStorageIdle = $("tfStorageIdle", new BlockTfStorage(false))
                .withResource("tfStorage")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfStorageActive = $("tfStorageActive", new BlockTfStorage(true))
                .withResource("tfStorage")
                .withTileEntity(TileEntityTfStorage.class, "TfStorage")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
        ;

        TcBlocks.tfAntennaMedium = $("tfAntenna", new BlockTfAntenna(BlockTfAntenna.MEDIUMWAVE))
                .wrappedByItemTcBlock()
                .withTileEntity(TileEntityTfAntenna.class, "TfAntenna")
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfAntennaUltra = $("tfAntennaU", new BlockTfAntenna(BlockTfAntenna.ULTRAWAVE))
                .wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        // This is hidden now
        TcBlocks.tfAntennaSuper = $("tfAntennaS", new BlockTfAntenna(BlockTfAntenna.SUPERWAVE))
                //.wrappedByItemTcBlock()
                .registerBlock()
                .setHardness(0.5F)
                .setStepSound(Block.soundTypeCloth)
                //.setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfSaturatorIdle = $("tfSaturatorIdle", new BlockTfSaturator(false))
                .withResource("tfSaturator")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfSaturatorActive = $("tfSaturatorActive", new BlockTfSaturator(true))
                .withResource("tfSaturator")
                .withTileEntity(TileEntityTfSaturator.class, "TfSaturator")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
        ;

        TcBlocks.tfCollector = $("tfCollector", new BlockTfCollector())
                .withTileEntity(TileEntityTfCollector.class, "TfCollector")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfCondenserIdle = $("tfCondenserIdle", new BlockTfCondenser(false))
                .withResource("tfCondenser")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfCondenserActive = $("tfCondenserActive", new BlockTfCondenser(true))
                .withResource("tfCondenser")
                .withTileEntity(TileEntityTfCondenser.class, "TfCondenser")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
        ;

        TcBlocks.tfOvenIdle = $("tfOvenIdle", new BlockTfOven(false))
                .withResource("tfOven")
                .wrappedByItemTcBlock()
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfOvenActive = $("tfOvenActive", new BlockTfOven(true))
                .withResource("tfOven")
                .withTileEntity(TileEntityTfOven.class, "TfOven")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
        ;

        TcBlocks.tfReformerIdle = $("tfReformerIdle", new BlockTfReformer(false))
                .withResource("tfReformer")
                .wrappedBy(ItemTcMultiTextureTile.class)
                .havingArgs(new Object[]{BlockTfReformer.blockNames})
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(TcCreativeTabs.DECORATIONS)
        ;

        TcBlocks.tfReformerActive = $("tfReformerActive", new BlockTfReformer(true))
                .withResource("tfReformer")
                .withTileEntity(TileEntityTfReformer.class, "tfReformer")
                .setHarvestLevel("pickaxe", 0)
                .registerBlock()
                .setHardness(2.5F)
                .setStepSound(Block.soundTypeMetal)
                .setLightLevel(0.875F)
        ;

    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockRenderer()
    {
        RenderingRegistry.registerBlockHandler(new RenderYubaBlock());
        RenderingRegistry.registerBlockHandler(new RenderChikuwaPlatform());
        RenderingRegistry.registerBlockHandler(new RenderSaltPan());

        ClientRegistry.registerTileEntity(TileEntityMorijio.class, "TmMorijio", new TileEntityMorijioRenderer());
        ClientRegistry.registerTileEntity(TileEntityTfAntenna.class, "TcTfAntenna", new TileEntityTfAntennaRenderer());
    }

}
