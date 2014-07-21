package tsuteto.tofu.api.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.ItemFoodSet;
import tsuteto.tofu.item.ItemGelatin;
import tsuteto.tofu.item.ItemTcMaterials;
import tsuteto.tofu.item.TcItems;

public enum TcOreDic
{
    // === Blocks ===
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
    blockAdvTofuGem(new ItemStack(TcBlocks.advTofuGem)),

    // === Items ===
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
    gelatin(TcItems.gelatin.getItemStack(ItemGelatin.gelatin)),
    kyoninso(TcItems.kyoninso),
    sesame(TcItems.sesame),
    starch(TcItems.starch),
    doubanjiang(new ItemStack(TcItems.doubanjiang, 1, OreDictionary.WILDCARD_VALUE)),
    strawberryJam(new ItemStack(TcItems.strawberryJam, 1, OreDictionary.WILDCARD_VALUE)),
    onigiri(TcItems.foodSet.getItemStack(ItemFoodSet.onigiri)),
    sprouts(TcItems.foodSet.getItemStack(ItemFoodSet.sprouts)),
    oage(TcItems.foodSet.getItemStack(ItemFoodSet.oage)),
    tofuChikuwa(TcItems.foodSet.getItemStack(ItemFoodSet.tofuChikuwa)),
    tofuDiamondNugget(TcItems.materials.getItemStack(ItemTcMaterials.tofuDiamondNugget)),
    tofuGem(TcItems.materials.getItemStack(ItemTcMaterials.tofuGem)),
    yuba(TcItems.yuba),
    tofuHamburgRaw(TcItems.materials.getItemStack(ItemTcMaterials.tofuHamburgRaw)),
    tofuHamburg(TcItems.foodSet.getItemStack(ItemFoodSet.tofuHamburg)),
    hamburg(TcItems.foodSet.getItemStack(ItemFoodSet.tofuHamburg)),
    tofuCookie(TcItems.foodSet.getItemStack(ItemFoodSet.tofuCookie)),
    cookie(TcItems.foodSet.getItemStack(ItemFoodSet.tofuCookie)),
    tofufishRaw(TcItems.foodSet.getItemStack(ItemFoodSet.tofufishRow)),
    tofufishCooked(TcItems.foodSet.getItemStack(ItemFoodSet.tofufishCooked)),
    rollingPin(TcItems.materials.getItemStack(ItemTcMaterials.rollingPin)),
    tofuSomen(TcItems.materials.getItemStack(ItemTcMaterials.tofuSomen)),
    glassBowl(TcItems.materials.getItemStack(ItemTcMaterials.glassBowl)),
    somenTsuyuBowl(TcItems.somenTsuyuBowl),

    tfCapacitor(TcItems.materials.getItemStack(ItemTcMaterials.tfCapacitor)),
    tfCoil(TcItems.materials.getItemStack(ItemTcMaterials.tfCoil)),
    tfOscillator(TcItems.materials.getItemStack(ItemTcMaterials.tfOscillator)),
    tfCircuitBoard(TcItems.materials.getItemStack(ItemTcMaterials.tfCircuit)),
    advTofuGem(TcItems.materials.getItemStack(ItemTcMaterials.advTofuGem)),
    activatedTofuGem(TcItems.materials.getItemStack(ItemTcMaterials.activatedTofuGem)),
    activatedHellTofu(TcItems.materials.getItemStack(ItemTcMaterials.activatedHellTofu)),
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
