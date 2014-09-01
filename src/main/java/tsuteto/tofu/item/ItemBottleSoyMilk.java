package tsuteto.tofu.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tsuteto.tofu.api.achievement.TcAchievementMgr;
import tsuteto.tofu.item.iteminfo.SoymilkPlayerInfo;
import tsuteto.tofu.item.iteminfo.SoymilkPotionEffect;
import tsuteto.tofu.item.iteminfo.TcFoodBase;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ItemBottleSoyMilk extends ItemBasicFoodSetBase<ItemBottleSoyMilk.Flavor>
{
    public static final Flavor[] flavorList = new Flavor[10];

    // !! NOTE !! Potion Effect Tier is up to 20.

    public static Flavor flvPlain = new Flavor(0, "plain", 0xf5f7df, 2, 0.5F)
            .setPotionEffect(Potion.regeneration)
            .addPotionEffectGrade(0, 0, 15, 15, 5)
            .addPotionEffectGrade(5, 0, 40, 30, 10)
            .addPotionEffectGrade(10, 0, 90, 60, 20)
            .addPotionEffectGrade(15, 0, 190, 100, 25);

    public static Flavor flvKinako = new Flavor(1, "kinako", 0xd6bc2d, 4, 0.6F)
            .setPotionEffect(Potion.moveSpeed)
            .addPotionEffectGrade(0, 0,  60, 60, 30)
            .addPotionEffectGrade(4, 1,  80, 60, 30)
            .addPotionEffectGrade(8, 2, 120, 60, 15, 90);

    public static Flavor flvCocoa = new Flavor(2, "cocoa", 0x8d3d0d, 4, 0.6F)
            .setPotionEffect(Potion.jump)
            .addPotionEffectGrade(0, 0,  60, 60, 30)
            .addPotionEffectGrade(4, 1,  80, 60, 30)
            .addPotionEffectGrade(8, 2, 120, 60, 15, 90);

    public static Flavor flvZunda = new Flavor(3, "zunda", 0x95e24a, 4, 0.6F)
            .setPotionEffect(Potion.nightVision)
            .addPotionEffectGrade(0, 0, 20, 20, 5, 40);

    public static Flavor flvApple = new Flavor(4, "apple", 0xf2e087, 4, 0.6F)
            .setPotionEffect(Potion.resistance)
            .addPotionEffectGrade(0, 0, 20, 20, 10)
            .addPotionEffectGrade(4, 1, 30, 20,  5)
            .addPotionEffectGrade(9, 2, 30, 15,  5, 20);

    public static Flavor flvPumpkin = new Flavor(5, "pumpkin", 0xffb504, 4, 0.6F)
            .setPotionEffect(Potion.damageBoost)
            .addPotionEffectGrade(0, 0, 45, 45, 15)
            .addPotionEffectGrade(4, 1, 60, 30, 15)
            .addPotionEffectGrade(8, 2, 80, 30,  5, 40);

    public static Flavor flvRamune = new Flavor(6, "ramune", 0xa1c7ff, 4, 0.6F)
            .setRandomPotionEffect();

    public static Flavor flvStrawberry = new Flavor(7, "strawberry", 0xf4a4b7, 4, 0.6F)
            .setPotionEffect(Potion.digSpeed)
            .addPotionEffectGrade(0, 0, 45, 45, 15)
            .addPotionEffectGrade(4, 1, 60, 30, 15)
            .addPotionEffectGrade(8, 2, 90, 30,  5, 40);

    public static Flavor flvSakura = new Flavor(8, "sakura", 0xffd1d7, 4, 0.6F)
            .setPotionEffect(Potion.resistance)
            .addPotionEffectGrade( 0, 0, 45, 30, 10)
            .addPotionEffectGrade( 4, 1, 60, 30, 10)
            .addPotionEffectGrade( 8, 2, 80, 30,  5)
            .addPotionEffectGrade(14, 3, 80, 20,  4, 20);

    public static Flavor flvAnnin = new Flavor(9, "annin", 0xf5f7f3, 4, 0.6F)
            .setPotionEffect(Potion.field_76444_x)
            .addPotionEffectGrade(0,  0,  60, 60, 30)
            .addPotionEffectGrade(4,  1, 100, 60, 30)
            .addPotionEffectGrade(8,  2, 150, 60, 20)
            .addPotionEffectGrade(13, 3, 180, 60, 10, 60);

    public static class Flavor extends TcFoodBase<Flavor>
    {
        private static List<SoymilkPotionEffect> potionEffectsForRandom = Lists.newArrayList();
        private SoymilkPotionEffect potionEffect = null;
        private boolean isRandomPotionEffect = false;
        private Random rand = null;

        public Flavor(int id, String name, int color, int healAmount, float saturationModifier)
        {
            super(id, healAmount, saturationModifier, true, name);
            this.asGlassBottle(color);
            this.setContainerItem(new ItemStack(Items.glass_bottle));
            ItemBottleSoyMilk.flavorList[id] = this;
        }

        public Flavor setPotionEffect(Potion potion)
        {
            this.potionEffect = new SoymilkPotionEffect(potion);
            potionEffectsForRandom.add(this.potionEffect);
            return this;
        }

        public Flavor addPotionEffectGrade(int tierFrom, int level, int initial, double baseMax, int bonusInc)
        {
            this.potionEffect.addGrade(tierFrom, level, initial, baseMax, bonusInc, -1);
            return this;
        }

        public Flavor addPotionEffectGrade(int tierFrom, int level, int initial, double baseMax, int bonusInc, int bonusMax)
        {
            this.potionEffect.addGrade(tierFrom, level, initial, baseMax, bonusInc, bonusMax);
            return this;
        }

        public PotionEffect getPotionEffect(SoymilkPlayerInfo info)
        {
            if (!isRandomPotionEffect)
            {
                if (this.potionEffect != null)
                {
                    return this.potionEffect.getPotionEffect(info);
                }
                else
                {
                    return null;
                }
            }
            else
            {
                SoymilkPotionEffect randomEffect = potionEffectsForRandom.get(rand.nextInt(potionEffectsForRandom.size()));
                return randomEffect.getPotionEffect(info);
            }
        }

        public Flavor setRandomPotionEffect()
        {
            this.isRandomPotionEffect = true;
            this.rand = new Random();
            this.rand.nextInt();
            return this;
        }
    }

    public ItemBottleSoyMilk()
    {
        super();
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public List<PotionEffect> getPotionEffect(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer)
    {
        List<PotionEffect> list = Lists.newArrayList();
        Flavor flavor = this.getItemInfo(par1ItemStack.getItemDamage());

        PotionEffect newEffect = flavor.getPotionEffect(SoymilkPlayerInfo.of(par2EntityPlayer).readNBTFromPlayer());
        if (newEffect != null)
        {
            list.add(newEffect);
        }
        return list;
    }

    @Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote)
        {
            SoymilkPlayerInfo info = SoymilkPlayerInfo.of(par3EntityPlayer).readNBTFromPlayer();
            info.onTaken();

            TcAchievementMgr.achieve(par3EntityPlayer, TcAchievementMgr.Key.soymilk1st);

            if (info.isFirstTime)
            {
                // First experience of soy milk!
                par3EntityPlayer.addChatMessage(new ChatComponentTranslation("tofucraft.soymilkTaken.greetings"));
            }
            else if (info.daysPassed > 0)
            {
                if (info.chain == 1)
                {
                    // Chain broken
                    par3EntityPlayer.addChatMessage(new ChatComponentTranslation("tofucraft.soymilkTaken.recovered",
                            info.daysPassed - 1, info.tier));
                } else
                {
                    // Keeping a chain
                    par3EntityPlayer.addChatMessage(new ChatComponentTranslation("tofucraft.soymilkTaken.chain",
                            info.chain, info.tier));

                    if (info.chain >= 7)
                    {
                        TcAchievementMgr.achieve(par3EntityPlayer, TcAchievementMgr.Key.soymilkWeek);
                    }
                    if (info.tier == 20)
                    {
                        TcAchievementMgr.achieve(par3EntityPlayer, TcAchievementMgr.Key.soymilkMax);
                    }
                }
            }
        }

        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
    }

    @Override
    public Flavor[] getItemList()
    {
        return flavorList;
    }

    @Override
    public String getItemSetName()
    {
        return "bottleSoymilk";
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        Flavor flavor = this.getItemInfo(par1ItemStack.getItemDamage());

        if (flavor.isRandomPotionEffect)
        {
            par3List.add(StatCollector.translateToLocal("tofucraft.soymilkInfo.randomEffect"));
            return;
        }

        List list1 = this.getPotionEffect(par1ItemStack, par2EntityPlayer);
        HashMultimap hashmultimap = HashMultimap.create();
        Iterator iterator1;

        if (list1 != null && !list1.isEmpty())
        {
            iterator1 = list1.iterator();

            while (iterator1.hasNext())
            {
                PotionEffect potioneffect = (PotionEffect)iterator1.next();
                String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                Map map = potion.func_111186_k();

                if (map != null && map.size() > 0)
                {
                    Iterator iterator = map.entrySet().iterator();

                    while (iterator.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)iterator.next();
                        AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.func_111183_a(potioneffect.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        hashmultimap.put(((IAttribute)entry.getKey()).getAttributeUnlocalizedName(), attributemodifier1);
                    }
                }

                if (potioneffect.getAmplifier() > 0)
                {
                    s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier()).trim();
                }

                if (potioneffect.getDuration() > 20)
                {
                    s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
                }

                if (potion.isBadEffect())
                {
                    par3List.add(EnumChatFormatting.RED + s1);
                }
                else
                {
                    par3List.add(EnumChatFormatting.GRAY + s1);
                }
            }
        }
        else
        {
            String s = StatCollector.translateToLocal("potion.empty").trim();
            par3List.add(EnumChatFormatting.GRAY + s);
        }

        if (!hashmultimap.isEmpty())
        {
            par3List.add("");
            par3List.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
            iterator1 = hashmultimap.entries().iterator();

            while (iterator1.hasNext())
            {
                Map.Entry entry1 = (Map.Entry)iterator1.next();
                AttributeModifier attributemodifier2 = (AttributeModifier)entry1.getValue();
                double d0 = attributemodifier2.getAmount();
                double d1;

                if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                {
                    d1 = attributemodifier2.getAmount();
                }
                else
                {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D)
                {
                    par3List.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), new Object[] {ItemStack.field_111284_a.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
                }
                else if (d0 < 0.0D)
                {
                    d1 *= -1.0D;
                    par3List.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), new Object[] {ItemStack.field_111284_a.format(d1), StatCollector.translateToLocal("attribute.name." + (String)entry1.getKey())}));
                }
            }
        }

        SoymilkPlayerInfo info = SoymilkPlayerInfo.of(par2EntityPlayer).readNBTFromPlayer();
        info.update();
        if (info.tier == 0)
        {
            par3List.add(StatCollector.translateToLocalFormatted("tofucraft.soymilkInfo.stat1"));
        }
        else
        {
            par3List.add(StatCollector.translateToLocalFormatted("tofucraft.soymilkInfo.stat2", info.tier));
        }
    }

}
