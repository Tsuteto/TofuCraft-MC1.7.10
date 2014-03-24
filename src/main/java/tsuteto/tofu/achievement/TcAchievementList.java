package tsuteto.tofu.achievement;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.achievement.TcAchievementMgr.Key;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.*;
import tsuteto.tofu.util.ModLog;

/**
 * Loads achievements for TofuCraft
 */
public class TcAchievementList
{
    public static void load()
    {
        /*
         *   -F-E-D-C-B-A-9-8-7-6-5-4-3-2-1 0 1 2 3 4 5 6 7 8 9 A B C D E F X
         * -A - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         * -9 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         * -8 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         * -7 - - - - - - - - - A * - - - - - * A * A - - - - - - - - - - -
         * -6 - - - - - - - - - - A * * - * * A * * A - - - - - - - - - - -
         * -5 - - - - - - - - - A - - * - * - * A * A - - - - - - - - - - -
         * -4 - - - - - - - - - - - - * - A - - * * A - - * * A * A - - - -
         * -3 - - - - - A * A * - - - * - * - - - - - - - * - * * A - - - -
         * -2 - - - - - - A * A * A * A * A * A - A - * * A - - - - - - - -
         * -1 - - - - - - - A * - - - * - - * - - * - * - - - - - - - - - -
         *  0 - - - - - - - - - - - - * * * A * * A * A * A - - - - - - - -
         *  1 - - - - - - A * A * A * * B * * - - * A * * * * * A A A A - -
         *  2 - - - - - - - - - - - - * - B * - A * * A * A - - - - - - - -
         *  3 - - - - - - A * A * A * * B * * - * - - - - - - - - - - - - -
         *  4 - - - - - - - - - - - - * - * A - A - C - - D * D - - - - - -
         *  5 - - - - - - - - A * * - * - * * - - - - - - - - * - - - - - -
         *  6 - - - - - - A * A * A * * - * A - E - F - - - - D * * * - - -
         *  7 - - - - - - - - A * * - - B * - - * - - - - - - - - - * - - -
         *  8 - - - - - - - - - - - - - - * - - * - - - - - - - - - D - - -
         *  9 - - - - - - - - - - - B * * * - - * - - - - - - - - - * - - -
         *  A - - - - - - - - - - - - - - - - - * - - - - - - - D * * - - -
         *  B - - - - - - - - - - - - - - - - - E - - - - - - - - - * - - -
         *  C - - - - - - - - - - - - - - - - - - - - - - - - - - - D - - -
         *  Y
         */

        // === Soybeans -> Tofu
        TcAchievement.create(Key.soybeans, -3, 0, TcItems.soybeans, null)
                .setTriggerItemPickup(new ItemStack(TcItems.soybeans))
                .registerStat();

        TcAchievement.create(Key.soymilk, 0, 0, TcItems.bucketSoymilk, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcItems.bucketSoymilk))
                .registerStat();

        TcAchievement.create(Key.tofu, 3, 0, TcItems.tofuKinu, Key.soymilk)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuKinu))
                .registerStat();

        //[fixed]
        TcAchievement.create(Key.momenTofu, 5, 0, TcItems.tofuMomen, Key.tofu)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tofuMomen))
                .registerStat();

        // [fixed]
        TcAchievement.create(Key.ishiTofu, 7, -2, TcItems.tofuMomen, Key.momenTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuIshi))
                .registerStat();

        TcAchievement.create(Key.metalTofu, 9, -4, TcItems.tofuMomen, Key.ishiTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuMetal))
                .registerStat();

        // === Kinu Tofu ===
        // [fixed]
        TcAchievement.create(Key.tofuCake, 3, -2, TcItems.tofuCake, Key.tofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuCake))
                .registerStat();

        // When: Rock block or entityLiving put on tofu [fixed]
        TcAchievement.create(Key.tofuMental, 4, 1, TcBlocks.tofuKinu, Key.tofu)
                .registerStat();

        // When: Scattering bone meal and leek comes up [fixed]
        TcAchievement.create(Key.leek, 2, 2, TcBlocks.leek, Key.tofu)
                .registerStat();

        TcAchievement.create(Key.hiyayakko, 2, 4, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.hiyayakko.id), Key.leek)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.hiyayakko.id))
                .registerStat();

        // === Momen Tofu ===
        // [fixed]
        TcAchievement.create(Key.tofuGrilled, 7, 0, TcItems.tofuGrilled, Key.momenTofu)
                .setTriggerSmelting(new ItemStack(TcItems.tofuGrilled))
                .registerStat();

        // [fixed]
        TcAchievement.create(Key.dengaku, 7, 0, new ItemStack(TcItems.misoDengaku), Key.momenTofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.misoDengaku))
                .registerStat();

        // === Metal Tofu ===
        TcAchievement.create(Key.tofuWarrior, 11, -4, TcItems.swordMetal, Key.metalTofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.swordMetal))
                .registerStat();

        TcAchievement.create(Key.tofuMining, 11, -3, TcItems.toolMetal[1], Key.metalTofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.toolMetal[1]))
                .registerStat();

        // === Koya-tofu ===
        TcAchievement.create(Key.koyatofu, 5, 2, TcItems.tofuDried, Key.tofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuDried))
                .registerStat();

        TcAchievement.create(Key.koyaStew, 7, 2, TcItems.koyadofuStew, Key.koyatofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.koyadofuStew))
                .registerStat();

        // === Various tofu ===
        TcAchievement.create(Key.strawberryTofu, 10, 1, TcItems.tofuStrawberry, Key.momenTofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuStrawberry))
                .registerStat();

        TcAchievement.create(Key.sesameTofu, 5, 6, TcItems.tofuSesame, null)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuSesame))
                .registerStat();

        TcAchievement.create(Key.eggTofu, 4, 7, TcItems.tofuEgg, null)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuEgg))
                .registerStat();

        TcAchievement.create(Key.anninTofu, 5, 7, TcItems.tofuAnnin, null)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuAnnin))
                .registerStat();

        TcAchievement.create(Key.misoTofu, 11, 1, TcItems.tofuMiso, Key.momenTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuMiso))
                .registerStat();

        TcAchievement.create(Key.glowtofu, 12, 1, TcItems.tofuGlow, Key.momenTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuGlow))
                .registerStat();

        // === salt ===
        TcAchievement.create(Key.saltFurnace, -2, 1, TcBlocks.saltFurnaceIdle, null)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.saltFurnaceIdle))
                .registerStat();

        TcAchievement.create(Key.salt, -1, 2, TcItems.salt, Key.saltFurnace)
                .registerStat();

        TcAchievement.create(Key.nigari, -2, 3, TcItems.nigari, Key.salt)
                .setTriggerItemCrafting(new ItemStack(TcItems.nigari))
                .registerStat();

        TcAchievement.create(Key.kiyome, -2, 7, Items.glowstone_dust, Key.salt)
                .setTriggerItemCrafting(new ItemStack(TcItems.goldenSalt))
                .registerStat();

        TcAchievement.create(Key.morijio, -3, 9, TcItems.morijio, Key.salt)
                .setTriggerItemCrafting(new ItemStack(TcItems.morijio))
                .registerStat();

        // === Moyashi ===
        // When: Soybeans are planted on wool
        TcAchievement.create(Key.sproutPlanting, -5, 1, Blocks.wool, Key.soybeans)
                .registerStat();

        TcAchievement.create(Key.sprout, -7, 1, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.sprouts.id), Key.sproutPlanting)
                .setTriggerItemPickup(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.sprouts.id))
                .registerStat();

        TcAchievement.create(Key.sproutMeal, -9, 1, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.sproutSaute.id), Key.sprout)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.sproutSaute.id))
                .registerStat();

        // === Natto ===
        // [fixed]
        TcAchievement.create(Key.nattoMaking, -5, 3, TcBlocks.nattoBed, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.nattoBed))
                .registerStat();

        TcAchievement.create(Key.natto, -7, 3, TcItems.natto, Key.nattoMaking)
                .setTriggerItemPickup(new ItemStack(TcItems.natto))
                .registerStat();

        TcAchievement.create(Key.nattoRice, -9, 3, TcItems.riceNatto, Key.natto)
                .setTriggerItemCrafting(new ItemStack(TcItems.riceNatto))
                .registerStat();


        // === Okara ===
        // [fixed]
        TcAchievement.create(Key.okara, 0, 4, TcItems.okara, Key.soymilk)
                .registerStat();

        // [1.5.4 fixed]
        TcAchievement.create(Key.okaraStick, 0, 6, TcItems.okaraStick, Key.okara)
                .setTriggerItemCrafting(new ItemStack(TcItems.okaraStick))
                .registerStat();


        // === Miso ===
        TcAchievement.create(Key.koujiBase, -1, -2, TcItems.koujiBase, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcItems.koujiBase))
                .registerStat();

        // When: Koujibase changes into kouji
        TcAchievement.create(Key.kouji, -1, -4, TcItems.kouji, Key.koujiBase)
                .registerStat();

        TcAchievement.create(Key.misoBarrel, 1, -6, TcItems.barrelMiso, Key.kouji)
                .setTriggerItemCrafting(new ItemStack(TcItems.barrelMiso))
                .registerStat();

        TcAchievement.create(Key.miso, 2, -7, TcItems.miso, Key.misoBarrel)
                .setTriggerItemPickup(new ItemStack(TcItems.miso))
                .registerStat();

        TcAchievement.create(Key.misoSoup, 4, -7, TcItems.misoSoup, Key.miso)
                .setTriggerItemCrafting(new ItemStack(TcItems.misoSoup))
                .registerStat();

        TcAchievement.create(Key.yakionigiriMiso, 4, -6, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriMiso.id), Key.miso)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriMiso.id))
                .registerStat();

        TcAchievement.create(Key.goheimochi, 6, -6, new ItemStack(TcItems.foodSetStick, 1, ItemFoodSetStick.goheimochi.id), Key.yakionigiriMiso)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSetStick, 1, ItemFoodSetStick.goheimochi.id))
                .registerStat();

        // === Soy Sauce ===
        // When: Soysauce comes out of miso barrel [fixed]
        TcAchievement.create(Key.soySauce, 2, -5, TcItems.bucketSoySauce, Key.misoBarrel)
                .registerStat();

        TcAchievement.create(Key.nikujaga, 4, -5, TcItems.nikujaga, Key.soySauce)
                .setTriggerItemCrafting(new ItemStack(TcItems.nikujaga))
                .registerStat();

        TcAchievement.create(Key.yakionigiriShoyu, 4, -4, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriShoyu.id), Key.soySauce)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriShoyu.id))
                .registerStat();

        // === Edamame -> Zunda ===
        TcAchievement.create(Key.edamame, -5, -2, TcItems.edamame, Key.soybeans)
                .setTriggerItemPickup(new ItemStack(TcItems.edamame))
                .registerStat();

        TcAchievement.create(Key.zunda, -7, -2, TcItems.zunda, Key.edamame)
                .setTriggerItemCrafting(new ItemStack(TcItems.zunda))
                .registerStat();

        TcAchievement.create(Key.zundaTofu, -9, -2, TcItems.tofuZunda, Key.zunda)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuZunda))
                .registerStat();

        // === Irimame -> Kinako ===
        // [1.5.4 absolutely fixed!!!]
        TcAchievement.create(Key.irimame, -5, -6, TcItems.soybeansParched, Key.soybeans)
                .setTriggerSmelting(new ItemStack(TcItems.soybeansParched))
                .registerStat();

        TcAchievement.create(Key.kinako, -6, -5, TcItems.kinako, Key.irimame)
                .setTriggerItemCrafting(new ItemStack(TcItems.kinako))
                .registerStat();

        TcAchievement.create(Key.fukumame, -6, -7, TcItems.fukumame, Key.irimame)
                .setTriggerItemCrafting(new ItemStack(TcItems.fukumame))
                .registerStat();

        // === Soymilk ===
        TcAchievement.create(Key.soymilkFv, 1, -2, new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvRamune.id), Key.soymilk)
                .setTriggerItemCrafting(new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvRamune.id))
                .registerStat();

        // === Zunda Arrow ===
        TcAchievement.create(Key.zundaBow, -8, -1, TcItems.zundaBow, Key.zunda)
                .setTriggerItemCrafting(new ItemStack(TcItems.zundaBow, 1, TriggerItem.DMG_WILDCARD))
                .registerStat();

        TcAchievement.create(Key.zundaArrow, -8, -3, TcItems.zundaArrow, Key.zunda)
                .setTriggerItemCrafting(new ItemStack(TcItems.zundaArrow))
                .registerStat();

        TcAchievement.create(Key.zundaAttack, -10, -3, TcItems.tofuZunda, Key.zundaArrow)
                .setSpecial()
                .registerStat();

        // === Soy Oil ===
        TcAchievement.create(Key.soyOil, -5, 6, TcItems.soyOil, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcItems.soyOil))
                .registerStat();

        TcAchievement.create(Key.tofuFried, -7, 5, TcItems.tofuFried, Key.soyOil)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuFried))
                .registerStat();

        TcAchievement.create(Key.friedTofuPouch, -7, 6, TcItems.tofuFriedPouch, Key.soyOil)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuFriedPouch))
                .registerStat();

        // [fixed]
        TcAchievement.create(Key.ttt, -9, 6, TcItems.tttBurger, Key.friedTofuPouch)
                .setTriggerItemCrafting(new ItemStack(TcItems.tttBurger))
                .registerStat();

        TcAchievement.create(Key.oage, -7, 7, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.oage.id), Key.soyOil)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.oage.id))
                .registerStat();

        TcAchievement.create(Key.oinarisan, -9, 7, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.inari.id), Key.oage)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.inari.id))
                .registerStat();

        // === Tofu cook ===
        TcAchievement.create(Key.tofuCook, 4, 4, TcItems.tofuKinu, null)
                .registerStat();

        // === Hell Soybeans ===
        TcAchievement.create(Key.hellsoybeans, 2, 6, TcItems.soybeansHell, null)
                .setTriggerItemPickup(new ItemStack(TcItems.soybeansHell))
                .registerStat();

        TcAchievement.create(Key.hellTofu, 2, 11, TcItems.tofuHell, Key.hellsoybeans)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuHell))
                .setSpecial()
                .registerStat();

        // === Tofu World ===
        TcAchievement.create(Key.tofuGem, 7, 4, new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id), null)
                .setTriggerItemPickup(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id))
                .registerStat();

        TcAchievement.create(Key.tofuSlimeRadar, 9, 4, TcItems.tofuRadar, Key.tofuGem)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuRadar))
                .registerStat();

        // [fixed]
        TcAchievement.create(Key.tofuSlimeHunter, 9, 6, TcItems.swordKinu, Key.tofuSlimeRadar)
                .registerStat();

        TcAchievement.create(Key.tofuStick, 11, 7, TcItems.tofuStick, Key.tofuSlimeHunter)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuStick))
                .registerStat();

        // When: The player traveled to the Tofu World
        TcAchievement.create(Key.tofuWorld, 12, 8, TcBlocks.tofuMomen, Key.tofuStick)
                .registerStat();

        TcAchievement.create(Key.tofuFishing, 10, 10, Items.fishing_rod, Key.tofuWorld)
                .setTriggerItemPickup(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofufishRow.id))
                .registerStat();

        TcAchievement.create(Key.tofunian, 12, 12, TcItems.tofuKinu, Key.tofuWorld)
                .setSpecial()
                .registerStat();

        // Add a new achievement page for the mod
        Achievement[] array = TcAchievementMgr.getAllAsArray();
        AchievementPage.registerAchievementPage(new AchievementPage("TofuCraft", array));

        ModLog.log(Level.INFO, "%d achievements for TofuCraft has been registered.", array.length);
    }
}
