package tsuteto.tofu.village;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.BiomeDictionary;
import tsuteto.tofu.Settings;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.block.BlockLeek;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

import java.util.Random;

/**
 * Tofu cook's House in village
 * 
 * @author Tsuteto
 *
 */
public class ComponentVillageHouseTofu extends StructureVillagePieces.Road
{
    public static final Item[] displayedItemList = new Item[]{
            TcItems.tofuKinu, TcItems.tofuMomen, TcItems.tofuMetal,
            TcItems.tofuZunda, TcItems.tofuMiso, TcItems.tofuStrawberry};

    private StructureVillagePieces.Start startPiece;

    public ComponentVillageHouseTofu() {}

    public ComponentVillageHouseTofu(StructureVillagePieces.Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.startPiece = par1ComponentVillageStartPiece;
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }

    public static StructureBoundingBox getStructureBoundingBox(int par3, int par4, int par5, int par6)
    {
        return StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, -4, 0, 9, 6, 7, par6);
    }

    @Override
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        ModLog.debug(startPiece);
        if (startPiece != null)
        {
            if (BiomeDictionary.isBiomeOfType(startPiece.biome, TofuCraftCore.BIOME_TYPE_TOFU)) return false;
        }
        
        if (this.field_143015_k < 0) // averageGroundLevel
        {
            this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);

            if (this.field_143015_k < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }
        
        int width = 8;
        int height = 4;
        int length = 6;

        // Floor
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, width, 0, length, TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        // Ceiling
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 0, width, height, length, TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        
        // Wall
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0,     1, 0,      0,     height - 1, length, TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, width, 1, 0,      width, height - 1, length, TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0,     1, length, width, height - 1, length, TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0,     1, 0,      width, height - 1, 0,      TcBlocks.tofuIshi, TcBlocks.tofuIshi, false);
        
        // Window
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, 0, 2, length / 2 - 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, 0, 2, length / 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, 0, 2, length / 2 + 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width / 2 - 1, 2, length, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width / 2, 2, length, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width / 2 + 1, 2, length, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width, 2, length / 2 - 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width, 2, length / 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.glass_pane, 0, width, 2, length / 2 + 1, par3StructureBoundingBox);
                
        // Door
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, width / 2, 1, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));

        if (this.getBlockAtCurrentPosition(par1World, 2, 0, -1, par3StructureBoundingBox) == Blocks.air && this.getBlockAtCurrentPosition(par1World, 2, -1, -1, par3StructureBoundingBox) != Blocks.air)
        {
            this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuStairsIshi, this.getMetadataWithOffset(Blocks.stone_stairs, 3), width / 2, 0, -1, par3StructureBoundingBox);
        }

        // Cleaning
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, width - 1, height - 1, length - 1, Blocks.air, Blocks.air, false);
        
        /*
         * Basement
         */
        // Room
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, -3, 1, width - 1, -1, length - 1, Blocks.air, Blocks.air, false);
        // Floor
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, -4, 0, width, -4, length, Blocks.planks, Blocks.planks, false);
        // Wall
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, -3, 0, 0, -1, length, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, width, -3, 0, width, -1, length, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, -3, length, width, -1, length, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, -3, 0, width, -1, 0, Blocks.planks, Blocks.planks, false);
        
        // Stairs
        this.placeBlockAtCurrentPosition(par1World, Blocks.air, 0, width - 1, 0, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.air, 0, width - 1, 0, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.air, 0, width - 1, 0, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.air, 0, width - 1, 0, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), width - 1, 0, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), width - 1, -1, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 2), width - 1, -2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -1, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -2, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -3, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -3, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -3, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.planks, 0, width - 1, -3, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.oak_stairs, this.getMetadataWithOffset(Blocks.oak_stairs, 0), width - 2, -3, 5, par3StructureBoundingBox);
        
        /*
         * Interior
         */
        
        int i, j, k;
        
        // Carpet
        for (i = 1; i <= length - 1; ++i)
        {
            for (j = 1; j <= width - 2; ++j)
            {
                this.placeBlockAtCurrentPosition(par1World, Blocks.carpet, 0, j, 1, i, par3StructureBoundingBox);
            }
        }
        this.placeBlockAtCurrentPosition(par1World, Blocks.carpet, 0, width - 1, 1, 1, par3StructureBoundingBox);
        
        // Torch
        this.placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, width / 2, 3, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, width - 2, -2, length / 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 1, -2, length / 2, par3StructureBoundingBox);

        // Workbench
        this.placeBlockAtCurrentPosition(par1World, Blocks.crafting_table, 0, 2, 1, 5, par3StructureBoundingBox);
        // Salt Furnace
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.saltFurnaceIdle, 0, 1, 1, 5, par3StructureBoundingBox);
        // Cauldron
        this.placeBlockAtCurrentPosition(par1World, Blocks.cauldron, 3, 1, 2, 5, par3StructureBoundingBox);
        // Book shelf
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 1, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 1, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.bookshelf, 0, 6, 2, 5, par3StructureBoundingBox);
        
        // Item frame
        k = 0;
        for (j = 3; j >= 2; j--)
        {
            for (i = 1; i <= 3; i++)
            {
                this.hangItemFrame(par1World, par3StructureBoundingBox, i, j, 0, new ItemStack(displayedItemList[k++]));
            }
        }

        // Tofu
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 2, -3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 3, -3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 4, -3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 5, -3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 2, -3, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 3, -3, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, TcBlocks.tofuMomen, 0, 4, -3, 4, par3StructureBoundingBox);
        // Weight
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 2, -2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 3, -2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 4, -2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 5, -2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 2, -2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 3, -2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Blocks.cobblestone, 0, 4, -2, 4, par3StructureBoundingBox);
        
        // Leek
        for (i = 0; i < 20; i++)
        {
            j = par2Random.nextInt(width - 1) + 1;
            k = par2Random.nextInt(length - 1) + 1;

            this.placeBlockAtCurrentPosition(par1World, TcBlocks.leek, BlockLeek.META_NATURAL, j, height + 1, k, par3StructureBoundingBox);
        }
        
        for (i = 0; i <= length; ++i)
        {
            for (j = 0; j <= width; ++j)
            {
                this.clearCurrentPositionBlocksUpwards(par1World, j, height + 2, i, par3StructureBoundingBox);
                this.func_151554_b(par1World, Blocks.cobblestone, 0, j, -5, i, par3StructureBoundingBox); // fillCurrentPositionBlocksDownwards
            }
        }

        this.spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
    
    protected void hangItemFrame(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, ItemStack item)
    {
        int j1 = this.getXWithOffset(par3, par5);
        int k1 = this.getYWithOffset(par4);
        int l1 = this.getZWithOffset(par3, par5);

        if (!par2StructureBoundingBox.isVecInside(j1, k1, l1))
        {
            return;
        }

        int i1 = this.coordBaseMode;
        EntityItemFrame itemFrame = new EntityItemFrame(par1World, j1, k1, l1, i1);
        itemFrame.setDisplayedItem(item);
        
        if (itemFrame.onValidSurface())
        {
            par1World.spawnEntityInWorld(itemFrame);
        }
    }
    
    @Override
    protected int getVillagerType(int par1)
    {
        return Settings.professionIdTofucook;
    }
}
