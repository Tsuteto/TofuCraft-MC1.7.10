package tsuteto.tofu.fishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tsuteto.tofu.fishing.TofuFishingLoot.TofuFishingEntryEntity.ISpawnImpl;

/**
 * Handles loot of tofu fishing
 *
 * @author Tsuteto
 *
 */
public class TofuFishingLoot
{
    private static List<TofuFishingLoot> lootList = new ArrayList<TofuFishingLoot>();
    private static Random rand = new Random();

    public static void addItem(ItemStack item, int weight)
    {
        TofuFishingEntryItem entry = new TofuFishingEntryItem();
        entry.item = item;
        entry.weight = weight;
        lootList.add(entry);
    }

    public static void addEntity(int weight, ISpawnImpl proc)
    {
        TofuFishingEntryEntity entry = new TofuFishingEntryEntity();
        entry.weight = weight;
        entry.spawnImpl = proc;
        lootList.add(entry);
    }

    public static TofuFishingLoot lot()
    {
        int totalWeight = getTotalWeight();
        int threshold = rand.nextInt(totalWeight);
        for (TofuFishingLoot entry : lootList)
        {
            threshold -= entry.weight;

            if (threshold < 0)
            {
                return entry;
            }
        }

        return null;
    }

    private static int getTotalWeight()
    {
        int total = 0;
        for (TofuFishingLoot entry : lootList)
        {
            total += entry.weight;
        }

        return total;
    }

    public int weight;

    public static class TofuFishingEntryItem extends TofuFishingLoot
    {
        public ItemStack item;
    }
    public static class TofuFishingEntryEntity extends TofuFishingLoot
    {
        public ISpawnImpl spawnImpl;

        public static interface ISpawnImpl
        {
            Entity spawnEntity(World world);
        }
    }
}
