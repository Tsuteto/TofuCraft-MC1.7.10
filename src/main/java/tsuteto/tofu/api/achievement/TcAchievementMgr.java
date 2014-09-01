package tsuteto.tofu.api.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

public class TcAchievementMgr
{
    public static enum Key
    {
        soybeans(0), soymilk(1), tofu(2), saltFurnace(3), salt(4), nigari(5), zunda(6), edamame(7), irimame(8), kinako(9),
        fukumame(10), soymilkFv(11), hellsoybeans(12), zundaArrow(13), zundaBow(14), zundaAttack(15), momenTofu(16), ishiTofu(17), metalTofu(18), koujiBase(19),
        kouji(20), misoBarrel(21), miso(22), misoSoup(23), soySauce(24), tofuWarrior(25), tofuCake(26), koyatofu(27), tofuFried(28), tofuGrilled(29),
        soyOil(30), okara(31), okaraStick(32), friedTofuPouch(33), oage(34), leek(35), ttt(36), sproutPlanting(37), sprout(38), sproutMeal(39),
        hiyayakko(40), nikujaga(41), yakionigiriShoyu(42), yakionigiriMiso(43), nattoMaking(44), nattoFarm(45), natto(46), nattoRice(47), tofuMining(48), koyaStew(49),
        dengaku(50), goheimochi(51), tofuCook(52), hellTofu(53), tofuGem(54), tofuSlimeRadar(55), tofuSlimeHunter(56), tofuStick(57), tofuWorld(58), tofuFishing(59),
        tofuMental(60), kiyome(61), strawberryTofu(62), morijio(63), sesameTofu(64), zundaTofu(65), eggTofu(66), anninTofu(67), misoTofu(68), glowtofu(69),
        tofunian(70), oinarisan(71), tfCapacitor(72), tfStorage(73), tofuForce(74), tfAntenna(75), tfCondenser(76), tofuActivation(77), highPowerGem(78), hellTofuActivation(79),
        tfOven(80), tfCollector(81), ultimateOven(82), tfReformer(83), tfSaturator(84), ultrawave(85), soymilk1st(86), soymilkWeek(87), soymilkMax(88);

        Key(int localId)
        {
        }
    }

    public static Map<Key, TcAchievement> achievementList = new EnumMap<Key, TcAchievement>(Key.class);
    public static ArrayList<TcAchievement> itemPickupMap = new ArrayList<TcAchievement>();
    public static ArrayList<TcAchievement> itemCraftingMap = new ArrayList<TcAchievement>();
    public static ArrayList<TcAchievement> itemSmeltingMap = new ArrayList<TcAchievement>();

    public static EnumSet<Key> unregisteredKeys = EnumSet.allOf(Key.class);

    public static void add(TcAchievement ach)
    {
        achievementList.put(ach.key, ach);
        unregisteredKeys.remove(ach.key);
    }

    public static TcAchievement[] getAllAsArray()
    {
        return achievementList.values().toArray(new TcAchievement[0]);
    }

    public static TcAchievement get(Key key)
    {
        return achievementList.get(key);
    }

    public static void achieveCraftingItem(ItemStack itemstack, EntityPlayer player)
    {
        for (TcAchievement ach : itemCraftingMap)
        {
            AchievementTrigger trigger = ach.getTrigger();
            if (trigger.equals(itemstack))
            {
                player.triggerAchievement(ach);
            }
        }
    }

    public static void achieveItemPickup(ItemStack itemstack, EntityPlayer player)
    {
        for (TcAchievement ach : itemPickupMap)
        {
            AchievementTrigger trigger = ach.getTrigger();
            if (trigger.equals(itemstack))
            {
                player.triggerAchievement(ach);
            }
        }
    }

    public static void achieveSmeltingItem(ItemStack itemstack, EntityPlayer player)
    {
        for (TcAchievement ach : itemSmeltingMap)
        {
            AchievementTrigger trigger = ach.getTrigger();
            if (trigger.equals(itemstack))
            {
                player.triggerAchievement(ach);
            }
        }
    }

    public static void achieve(EntityPlayer player, Key key)
    {
        player.triggerAchievement(get(key));
    }
}
