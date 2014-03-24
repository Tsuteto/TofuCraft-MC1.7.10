package tsuteto.tofu.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class PotionFilling extends Potion
{
    int[] intervalList = new int[]{200, 100, 60, 20, 5};
    
    protected PotionFilling(int par1, boolean par2, int par3)
    {
        super(par1, par2, par3);
    }

    @Override
    public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
    {
        if (par1EntityLivingBase instanceof EntityPlayer)
        {
            if (!par1EntityLivingBase.worldObj.isRemote)
            {
                ((EntityPlayer)par1EntityLivingBase).getFoodStats().addStats(par2 + 1, 0.2F);
            }
        }
    }

    @Override
    public boolean isReady(int par1, int par2)
    {
        int intv = intervalList[MathHelper.clamp_int(par2, 0, intervalList.length - 1)];
        return par1 % intv == 0;
    }
}
