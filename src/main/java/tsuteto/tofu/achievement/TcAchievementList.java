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
         * -7 - - - - - - - - - A * - - - - - * A * A - # # - - - - - - - -
         * -6 - - - - - - - - - - A * * - * * A * * A - # # - - - - - - - -
         * -5 - - - - - - - - - A - - * - * - * A * A - - - - - - - - - - -
         * -4 - - - - - - - - - - - - * - A - - * * A - - * * A * A - - - -
         * -3 - - - - - A * A * - - - * - * - - - - - - - * - * * A - - - -
         * -2 - - - - - - A * A * A * A * A * A - A - * * A - - - - - - - -
         * -1 - - - - - - - A * - - - * - - * - - * - * A - - - - - - - - -
         *  0 - - - - - - - - - - - - * * * A * * A * A * A - - - - - - - -
         *  1 - - - - - - A * A * A * * B * * - - * A * * * * * A A A A - -
         *  2 - - - - - - - - - - - - * - B * - A * * A * A - - - - - - - -
         *  3 - - - - - - A * A * A * * B * * - * - - - - - - - - - - - - -
         *  4 - - - - - - - - - - - - * - * A - A - C - - D * D - - - - - -
         *  5 - - - - - - - - A * * - * - * * - - - - - - * - * - - - - - -
         *  6 - - - - - - A * A * A * * - * A - E - F - - * - D * * * - - -
         *  7 - - - - - - - - A * * - - B * - - * - - - - D - - - - * - - -
         *  8 - - - - - - - - - - - - - - * - - * - - - - * - - - - D - - -
         *  9 - - - - - - - - - - - B * * * - - * - - - D * - - - - * - - -
         *  A - - - - - - - - - - - - - - - - - * - - - * - - - D * * - - -
         *  B - - - - - - - - - - - - - - - - - E - - - * * D - - - * - - -
         *  C - - - - - - - - - - - - - - - - - - - - - - - * - - - D - - -
         *  D - - - - - - - - - - - - - - - - - - - - - - - D - - - - - - -
         *  E - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
         *  F - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
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

        TcAchievement.create(Key.momenTofu, 5, 0, TcItems.tofuMomen, Key.tofu)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tofuMomen))
                .registerStat();

        TcAchievement.create(Key.ishiTofu, 7, -2, TcItems.tofuMomen, Key.momenTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuIshi))
                .registerStat();

        TcAchievement.create(Key.metalTofu, 9, -4, TcItems.tofuMomen, Key.ishiTofu)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuMetal))
                .registerStat();

        // === Kinu Tofu ===
        TcAchievement.create(Key.tofuCake, 3, -2, TcItems.tofuCake, Key.tofu)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuCake))
                .registerStat();

        // When: Rock block or entityLiving put on tofu
        TcAchievement.create(Key.tofuMental, 4, 1, TcBlocks.tofuKinu, Key.tofu)
                .registerStat();

        // When: Scattering bone meal and leek comes up
        TcAchievement.create(Key.leek, 2, 2, TcBlocks.leek, Key.tofu)
                .registerStat();

        TcAchievement.create(Key.hiyayakko, 2, 4, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.hiyayakko.id), Key.leek)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.hiyayakko.id))
                .registerStat();

        // === Momen Tofu ===
        TcAchievement.create(Key.tofuGrilled, 7, 0, TcItems.tofuGrilled, Key.momenTofu)
                .setTriggerSmelting(new ItemStack(TcItems.tofuGrilled))
                .registerStat();

        TcAchievement.create(Key.dengaku, 6, -1, new ItemStack(TcItems.misoDengaku), Key.momenTofu)
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
        TcAchievement.create(Key.sesameTofu, 7, -7, TcItems.tofuSesame, null)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuSesame))
                .registerStat();

        TcAchievement.create(Key.eggTofu, 6, -7, TcItems.tofuEgg, null)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuEgg))
                .registerStat();

        TcAchievement.create(Key.anninTofu, 6, -6, TcItems.tofuAnnin, null)
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
        TcAchievement.create(Key.nattoMaking, -5, 3, Items.wheat, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.nattoBed))
                .registerStat();

        // When: Natto bed is placed on the ground
        TcAchievement.create(Key.nattoFarm, -7, 3, TcBlocks.nattoBed, Key.nattoMaking)
                .registerStat();

        TcAchievement.create(Key.natto, -9, 3, TcItems.natto, Key.nattoFarm)
                .setTriggerItemPickup(new ItemStack(TcItems.natto))
                .registerStat();

        TcAchievement.create(Key.nattoRice, -11, 3, TcItems.riceNatto, Key.natto)
                .setTriggerItemCrafting(new ItemStack(TcItems.riceNatto))
                .registerStat();


        // === Okara ===
        TcAchievement.create(Key.okara, 0, 4, TcItems.okara, Key.soymilk)
                .registerStat();

        TcAchievement.create(Key.okaraStick, 0, 6, TcItems.okaraStick, Key.okara)
                .setTriggerItemCrafting(new ItemStack(TcItems.okaraStick))
                .registerStat();


        // === Miso ===
        TcAchievement.create(Key.koujiBase, -1, -4, TcItems.koujiBase, Key.soybeans)
                .setTriggerItemCrafting(new ItemStack(TcItems.koujiBase))
                .registerStat();

        // When: Koujibase changes into kouji
        TcAchievement.create(Key.kouji, -1, -6, TcItems.kouji, Key.koujiBase)
                .registerStat();

        TcAchievement.create(Key.misoBarrel, 1, -8, TcItems.barrelMiso, Key.kouji)
                .setTriggerItemCrafting(new ItemStack(TcItems.barrelMiso))
                .registerStat();

        TcAchievement.create(Key.miso, 2, -9, TcItems.miso, Key.misoBarrel)
                .setTriggerItemPickup(new ItemStack(TcItems.miso))
                .registerStat();

        TcAchievement.create(Key.misoSoup, 4, -9, TcItems.misoSoup, Key.miso)
                .setTriggerItemCrafting(new ItemStack(TcItems.misoSoup))
                .registerStat();

        TcAchievement.create(Key.yakionigiriMiso, 4, -8, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriMiso.id), Key.miso)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriMiso.id))
                .registerStat();

        TcAchievement.create(Key.goheimochi, 6, -8, new ItemStack(TcItems.foodSetStick, 1, ItemFoodSetStick.goheimochi.id), Key.yakionigiriMiso)
                .setTriggerItemCrafting(new ItemStack(TcItems.foodSetStick, 1, ItemFoodSetStick.goheimochi.id))
                .registerStat();

        // === Soy Sauce ===
        // When: Soysauce comes out of miso barrel
        TcAchievement.create(Key.soySauce, 2, -7, TcItems.bucketSoySauce, Key.misoBarrel)
                .registerStat();

        TcAchievement.create(Key.nikujaga, 4, -7, TcItems.nikujaga, Key.soySauce)
                .setTriggerItemCrafting(new ItemStack(TcItems.nikujaga))
                .registerStat();

        TcAchievement.create(Key.yakionigiriShoyu, 4, -6, new ItemStack(TcItems.foodSet, 1, ItemFoodSet.yakionigiriShoyu.id), Key.soySauce)
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

        TcAchievement.create(Key.soymilk1st, 1, -4, new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvPlain.id), Key.soymilk)
                .registerStat();

        TcAchievement.create(Key.soymilkWeek, 3, -4, new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvCocoa.id), Key.soymilk1st)
                .registerStat();

        TcAchievement.create(Key.soymilkMax, 5, -4, new ItemStack(TcItems.bottleSoymilk, 1, ItemBottleSoyMilk.flvZunda.id), Key.soymilkWeek)
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
        TcAchievement.create(Key.tofuGem, 8, 4, new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id), null)
                .setTriggerItemPickup(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id))
                .registerStat();

        TcAchievement.create(Key.tofuSlimeRadar, 12, 4, TcItems.tofuRadar, Key.tofuGem)
                .setTriggerItemCrafting(new ItemStack(TcItems.tofuRadar))
                .registerStat();

        TcAchievement.create(Key.tofuSlimeHunter, 14, 4, TcItems.swordKinu, Key.tofuSlimeRadar)
                .registerStat();

        TcAchievement.create(Key.tofuStick, 14, 7, TcItems.tofuStick, Key.tofuSlimeHunter)
                .setTriggerItemPickup(new ItemStack(TcItems.tofuStick))
                .registerStat();

        // When: The player traveled to the Tofu World
        TcAchievement.create(Key.tofuWorld, 15, 8, TcBlocks.tofuMomen, Key.tofuStick)
                .registerStat();

        TcAchievement.create(Key.tofuFishing, 17, 10, Items.fishing_rod, Key.tofuWorld)
                .setTriggerItemPickup(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofufishRow.id))
                .registerStat();

        TcAchievement.create(Key.tofunian, 15, 12, TcItems.tofuKinu, Key.tofuWorld)
                .setSpecial()
                .registerStat();

        // === Tofu Force ===
        TcAchievement.create(Key.tfCapacitor, 6, 7, new ItemStack(TcItems.materials, 1, ItemTcMaterials.tfCapacitor.id), Key.tofuGem)
                .setTriggerItemCrafting(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tfCapacitor.id))
                .registerStat();

        TcAchievement.create(Key.tfAntenna, 6, 5, TcBlocks.tfAntennaMedium, Key.tfCapacitor)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfAntennaMedium))
                .registerStat();

        TcAchievement.create(Key.highPowerGem, 8, 10, new ItemStack(TcItems.materials, 1, ItemTcMaterials.advTofuGem.id), Key.tofuGem)
                .setTriggerItemPickup(new ItemStack(TcItems.materials, 1, ItemTcMaterials.advTofuGem.id))
                .registerStat();

        TcAchievement.create(Key.tfStorage, 6, 9, TcBlocks.tfStorageIdle, Key.tfCapacitor)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfStorageIdle))
                .registerStat();

        TcAchievement.create(Key.tfReformer, 5, 9, TcBlocks.tfReformerActive, Key.koyatofu)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfReformerIdle))
                .registerStat();

        // When: Put an element item of TF on the input slot of TF Storage
        TcAchievement.create(Key.tofuForce, 6, 11, TcBlocks.tfStorageActive, Key.tfStorage)
                .registerStat();

        TcAchievement.create(Key.tfSaturator, 10, 10, TcBlocks.tfSaturatorActive, Key.highPowerGem)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfSaturatorIdle))
                .registerStat();

        TcAchievement.create(Key.tfCondenser, 9, 11, TcBlocks.tfCondenserActive, Key.tofuForce)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfCondenserIdle))
                .registerStat();

        TcAchievement.create(Key.strawberryTofu, 11, 11, TcItems.tofuStrawberry, Key.tfCondenser)
                .setTriggerTfCondenser(new ItemStack(TcBlocks.tofuStrawberry))
                .registerStat();

        TcAchievement.create(Key.tofuActivation, 8, 13, new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedTofuGem.id), Key.tfCondenser)
                .setTriggerTfCondenser(new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedTofuGem.id))
                .registerStat();

        TcAchievement.create(Key.hellTofuActivation, 10, 13, new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedHellTofu.id), Key.tfCondenser)
                .setTriggerTfCondenser(new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedHellTofu.id))
                .registerStat();

        TcAchievement.create(Key.tfOven, 10, 14, TcBlocks.tfOvenActive, Key.hellTofuActivation)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfOvenIdle))
                .registerStat();

        TcAchievement.create(Key.ultrawave, 5, 13, TcBlocks.tfAntennaUltra, Key.tofuActivation)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfAntennaUltra))
                .registerStat();

        TcAchievement.create(Key.tfCollector, 8, 14, TcBlocks.tfCollector, Key.tofuActivation)
                .setTriggerItemCrafting(new ItemStack(TcBlocks.tfCollector))
                .registerStat();

        // When: 64 activated hell tofu in TF Oven's slot
        TcAchievement.create(Key.ultimateOven, 10, 16, new ItemStack(TcItems.materials, 1, ItemTcMaterials.activatedHellTofu.id), Key.tfOven)
                .setSpecial()
                .registerStat();

        // Add a new achievement page for the mod
        Achievement[] array = TcAchievementMgr.getAllAsArray();
        AchievementPage.registerAchievementPage(new AchievementPage("TofuCraft", array));

        ModLog.info("%d achievements for TofuCraft has been registered.", array.length);
        if (!TcAchievementMgr.unregisteredKeys.isEmpty())
        {
            ModLog.log(Level.WARN, "Unregistered Achievements Found: %s", TcAchievementMgr.unregisteredKeys);
        }
    }
}
