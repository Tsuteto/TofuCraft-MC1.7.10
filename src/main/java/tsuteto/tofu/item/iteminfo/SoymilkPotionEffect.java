package tsuteto.tofu.item.iteminfo;

import com.google.common.collect.Lists;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.LinkedList;

public class SoymilkPotionEffect
{
    private LinkedList<Grade> grades = Lists.newLinkedList();

    private final Potion potion;

    public SoymilkPotionEffect(Potion potion)
    {
        this.potion = potion;
    }

    public SoymilkPotionEffect addGrade(int tierFrom, int level, int initial, double baseMax, int bonusInc, int bonusMax)
    {
        Grade newGrade = new Grade();
        newGrade.tierFrom = tierFrom;
        newGrade.level = level;
        newGrade.initialDuration = initial;
        newGrade.baseMaxDuration = baseMax;
        newGrade.bonusIncrement = bonusInc;
        newGrade.bonusMaxDuration = bonusMax;

        grades.add(newGrade);
        return this;
    }

    public PotionEffect getPotionEffect(SoymilkPlayerInfo info)
    {
        Grade currentGrade = null;
        for (Grade g : grades)
        {
            if (info.tier < g.tierFrom) break;
            currentGrade = g;
        }

        if (currentGrade != null)
        {
            int duration = currentGrade.initialDuration;
            duration += Math.min(info.daysTotal / 100D, 1) * currentGrade.baseMaxDuration;

            int bonusMax = currentGrade.bonusMaxDuration == -1 ? Integer.MAX_VALUE : currentGrade.bonusMaxDuration;
            duration += Math.min((info.tier - currentGrade.tierFrom) * currentGrade.bonusIncrement, bonusMax);

            return new PotionEffect(potion.getId(), duration * 20, currentGrade.level);
        }
        else
        {
            return null;
        }
    }

    private class Grade
    {
        public int tierFrom;
        public int level;
        public int initialDuration;
        public double baseMaxDuration;
        public int bonusIncrement;
        public int bonusMaxDuration;
    }
}
