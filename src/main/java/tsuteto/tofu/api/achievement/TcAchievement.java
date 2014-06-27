package tsuteto.tofu.api.achievement;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.api.achievement.TcAchievementMgr.Key;

public class TcAchievement extends Achievement
{
    public final TcAchievementMgr.Key key;
    private AchievementTrigger trigger;

    public static TcAchievement create(Key key, int par3, int par4, Object obj, Key relation)
    {
        if (obj instanceof Block)
        {
            return new TcAchievement(key, par3, par4, (Block)obj, relation);
        }
        else if (obj instanceof Item)
        {
            return new TcAchievement(key, par3, par4, (Item)obj, relation);
        }
        else if (obj instanceof ItemStack)
        {
            return new TcAchievement(key, par3, par4, (ItemStack)obj, relation);
        }

        throw new RuntimeException("Caught an error in achievement registration.");
    }

    private TcAchievement(Key key, int par3, int par4, Block block, Key relation)
    {
        super(getId(key.name()), getId(key.name()), par3, par4, block, TcAchievementMgr.get(relation));
        this.key = key;
    }

    private TcAchievement(Key key, int par3, int par4, Item item, Key relation)
    {
        super(getId(key.name()), getId(key.name()), par3, par4, item, TcAchievementMgr.get(relation));
        this.key = key;
    }

    private TcAchievement(Key key, int par3, int par4, ItemStack itemstack, Key relation)
    {
        super(getId(key.name()), getId(key.name()), par3, par4, itemstack, TcAchievementMgr.get(relation));
        this.key = key;
    }

    private static String getId(String name)
    {
        return TofuCraftCore.resourceDomain + name;
    }

    public TcAchievement setTriggerItemPickup(ItemStack item)
    {
        this.trigger = new TriggerItem(item);
        TcAchievementMgr.itemPickupMap.add(this);
        return this;
    }

    public TcAchievement setTriggerItemCrafting(ItemStack item)
    {
        this.trigger = new TriggerItem(item);
        TcAchievementMgr.itemCraftingMap.add(this);
        return this;
    }

    public TcAchievement setTriggerSmelting(ItemStack item)
    {
        this.trigger = new TriggerItem(item);
        TcAchievementMgr.itemSmeltingMap.add(this);
        return this;
    }

    public TcAchievement setTriggerTfCondenser(ItemStack item)
    {
        this.trigger = new TriggerItem(item);
        TcAchievementMgr.itemCraftingMap.add(this);
        return this;
    }

    @Override
    public TcAchievement initIndependentStat()
    {
        super.initIndependentStat();
        return this;
    }

    @Override
    public TcAchievement setSpecial()
    {
        super.setSpecial();
        return this;
    }

    @Override
    public TcAchievement registerStat()
    {
        super.registerStat();
        TcAchievementMgr.add(this);
        return this;
    }

    public AchievementTrigger getTrigger()
    {
        return this.trigger;
    }
}

