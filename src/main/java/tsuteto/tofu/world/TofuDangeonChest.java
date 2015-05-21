package tsuteto.tofu.world;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.item.ItemTcMaterials;

import java.util.Random;

public class TofuDangeonChest
{
    public static final String TOFU_DANGEON = "tofuDangeon";

    public static final WeightedRandomChestContent[] chestContent = new WeightedRandomChestContent[] {
        new WeightedRandomChestContent(TcItems.tofuKinu, 0, 1, 64, 20),
        new WeightedRandomChestContent(TcItems.tofuDiamond, 0, 1, 4, 3),
        new WeightedRandomChestContent(TcItems.materials, ItemTcMaterials.tofuGem.id, 1, 32, 15),
        new WeightedRandomChestContent(TcItems.materials, ItemTcMaterials.tofuDiamondNugget.id, 1, 8, 5),
        new WeightedRandomChestContent(Item.getItemFromBlock(TcBlocks.tcSapling), 1, 8, 16, 10),
        new WeightedRandomChestContent(TcItems.armorDiamond[0], 0, 1, 1, 1),
        new WeightedRandomChestContent(TcItems.armorDiamond[1], 0, 1, 1, 1),
        new WeightedRandomChestContent(TcItems.armorDiamond[2], 0, 1, 1, 1),
        new WeightedRandomChestContent(TcItems.armorDiamond[3], 0, 1, 1, 1),
        new WeightedRandomChestContent(TcItems.swordDiamond, 0, 1, 1, 1),
        new WeightedRandomChestContent(TcItems.armorMetal[0], 0, 1, 1, 5),
        new WeightedRandomChestContent(TcItems.armorMetal[1], 0, 1, 1, 5),
        new WeightedRandomChestContent(TcItems.armorMetal[2], 0, 1, 1, 5),
        new WeightedRandomChestContent(TcItems.armorMetal[3], 0, 1, 1, 5),
        new WeightedRandomChestContent(TcItems.swordMetal, 0, 1, 1, 8),
        new WeightedRandomChestContent(TcItems.tofuCake, 0, 1, 1, 10),
        new WeightedRandomChestContent(TcItems.zundaBow, 0, 1, 1, 2),
   };

    public static final ChestGenHooks chestInfo = new ChestGenHooks(TOFU_DANGEON, chestContent, 8, 15);

    public static void generateDangeonChestContent(Random rand, TileEntityChest chest)
    {
        WeightedRandomChestContent.generateChestContents(rand, chestInfo.getItems(rand), chest, chestInfo.getCount(rand));
    }

}
