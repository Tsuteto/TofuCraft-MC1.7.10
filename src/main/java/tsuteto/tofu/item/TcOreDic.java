package tsuteto.tofu.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tsuteto.tofu.block.TcBlocks;

public enum TcOreDic
{
    // === Blocks ===
    blockTofu(TcBlocks.tofuMomen),
    blockSalt(TcBlocks.salt),
    blockNatto(TcBlocks.natto),

    blockTofuKinu(TcBlocks.tofuKinu),
    blockTofuMomen(TcBlocks.tofuMomen),
    blockTofuIshi(TcBlocks.tofuIshi),
    blockTofuMetal(TcBlocks.tofuMetal),
    blockTofuGrilled(TcBlocks.tofuGrilled),
    blockTofuDried(TcBlocks.tofuDried),
    blockTofuFriedPouch(TcBlocks.tofuFriedPouch),
    blockTofuFried(TcBlocks.tofuFried),
    blockTofuEgg(TcBlocks.tofuEgg),
    blockTofuAnnin(TcBlocks.tofuAnnin),
    blockTofuSesame(TcBlocks.tofuSesame),
    blockTofuZunda(TcBlocks.tofuZunda),
    blockTofuStrawberry(TcBlocks.tofuStrawberry),
    blockTofuMiso(TcBlocks.tofuMiso),
    blockTofuHell(TcBlocks.tofuHell),
    blockTofuGlow(TcBlocks.tofuGlow),
    blockTofuDiamond(TcBlocks.tofuDiamond),

    oreTofu(TcBlocks.oreTofu),
    oreTofuDiamond(TcBlocks.oreTofuDiamond),

    leavesApricot(new ItemStack(TcBlocks.tcLeaves, 1, 0)),
    leavesApricotF(new ItemStack(TcBlocks.tcLeaves, 1, 1)),
    leavesTofu(new ItemStack(TcBlocks.tcLeaves, 1, 2)),
//    logApricot(new ItemStack(TcBlock.tcLog, 1, 0)),
    saplingApricot(new ItemStack(TcBlocks.tcSapling, 1, 0)),
    saplingTofu(new ItemStack(TcBlocks.tcSapling, 1, 1)),

    blockTfMachineCase(TcBlocks.tfMachineCase),

    // === Items ===
    tofu(TcItems.tofuMomen),
    salt(TcItems.salt),
    nigari(TcItems.nigari),
    leek(TcItems.leek),
    miso(TcItems.miso),
    natto(TcItems.natto),

    soybeans(TcItems.soybeans),
    soybeansParched(TcItems.soybeansParched),
    soybeansHell(TcItems.soybeansHell),
    kinako(TcItems.kinako),
    edamame(TcItems.edamame),
    edamameBoiled(TcItems.edamameBoiled),
    zunda(TcItems.zunda),
    okara(TcItems.okara),


    tofuKinu(TcItems.tofuKinu),
    tofuMomen(TcItems.tofuMomen),
    tofuIshi(TcItems.tofuIshi),
    tofuMetal(TcItems.tofuMetal),
    tofuGrilled(TcItems.tofuGrilled),
    tofuDried(TcItems.tofuDried),
    tofuFriedPouch(TcItems.tofuFriedPouch),
    tofuFried(TcItems.tofuFried),
    tofuEgg(TcItems.tofuEgg),
    tofuAnnin(TcItems.tofuAnnin),
    tofuSesame(TcItems.tofuSesame),
    tofuZunda(TcItems.tofuZunda),
    tofuStrawberry(TcItems.tofuStrawberry),
    tofuMiso(TcItems.tofuMiso),
    tofuHell(TcItems.tofuHell),
    tofuGlow(TcItems.tofuGlow),
    tofuDiamond(TcItems.tofuDiamond),

    bucketSoySauce(TcItems.bucketSoySauce),
    bucketSoymilk(TcItems.bucketSoymilk),
    bucketSoymilkHell(TcItems.bucketSoymilkHell),

    defattingPotion(TcItems.defattingPotion),
    dashi(new ItemStack(TcItems.dashi, 1, OreDictionary.WILDCARD_VALUE)),
    soyOil(new ItemStack(TcItems.soyOil, 1, OreDictionary.WILDCARD_VALUE)),
    bottleSoySauce(new ItemStack(TcItems.bottleSoySauce, 1, OreDictionary.WILDCARD_VALUE)),
    
    barrel(TcItems.barrelEmpty),
    barrelEmpty(TcItems.barrelEmpty),
    barrelMiso(TcItems.barrelMiso),
    barrelMisoTofu(TcItems.barrelMisoTofu),
    barrelGlowtofu(TcItems.barrelGlowtofu),

    kouji(TcItems.kouji),
    mincedPotato(TcItems.mincedPotato),
    filterCloth(TcItems.filterCloth),
    apricot(TcItems.apricot),
    apricotSeed(TcItems.apricotSeed),
    gelatin(new ItemStack(TcItems.gelatin, 1, ItemGelatin.Materials.gelatin.ordinal())),
    kyoninso(TcItems.kyoninso),
    sesame(TcItems.sesame),
    starch(TcItems.starch),
    doubanjiang(new ItemStack(TcItems.doubanjiang, 1, OreDictionary.WILDCARD_VALUE)),
    strawberryJam(new ItemStack(TcItems.strawberryJam, 1, OreDictionary.WILDCARD_VALUE)),
    onigiri(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.onigiri.id)),
    sprouts(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.sprouts.id)),
    oage(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.oage.id)),
    tofuChikuwa(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuChikuwa.id)),
    tofuDiamondNugget(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuDiamondNugget.id)),
    tofuGem(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuGem.id)),
    yuba(TcItems.yuba),
    tofuHamburgRaw(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tofuHamburgRaw.id)),
    tofuHamburg(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuHamburg.id)),
    hamburg(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuHamburg.id)),
    tofuCookie(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuCookie.id)),
    cookie(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuCookie.id)),
    tofufishRaw(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofufishRow.id)),
    tofufishCooked(new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofufishCooked.id)),

    tfCapacitor(new ItemStack(TcItems.materials, 1, ItemTcMaterials.tfCapacitor.id)),
    ;

    TcOreDic(Item... items)
    {
        for (Item item : items)
        {
            OreDictionary.registerOre(this.name(), item);
        }
    }

    TcOreDic(Block... blocks)
    {
        for (Block block : blocks)
        {
            OreDictionary.registerOre(this.name(), block);
        }
    }

    TcOreDic(ItemStack... itemstacks)
    {
        for (ItemStack itemstack : itemstacks)
        {
            OreDictionary.registerOre(this.name(), itemstack);
        }
    }
}
