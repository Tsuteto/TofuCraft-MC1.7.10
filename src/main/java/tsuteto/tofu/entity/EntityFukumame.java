package tsuteto.tofu.entity;

import java.util.Calendar;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFukumame extends EntityThrowable
{
    private boolean isCrit;

    public EntityFukumame(World par1World)
    {
        super(par1World);
        this.isCrit = this.chkdate();
    }

    public EntityFukumame(World par1World, EntityLivingBase par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
        this.isCrit = this.chkdate();

        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;

        float yaw = this.rotationYaw + rand.nextFloat() * 30F - 15F;
        float pitch = this.rotationPitch + rand.nextFloat() * 10F - 5F;
        float f = 0.2F;
        this.motionX = (-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (-MathHelper.sin((pitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
    }

    public EntityFukumame(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        this.isCrit = this.chkdate();
    }

    private boolean chkdate()
    {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) == Calendar.FEBRUARY && cal.get(Calendar.DATE) == 3;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            int d = this.isCrit ? 4 : 1;

            Entity entityHit = par1MovingObjectPosition.entityHit;
            entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), d);
            if (entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivivng = (EntityLivingBase)entityHit;
                entityLivivng.hurtResistantTime = entityLivivng.maxHurtResistantTime / 2;
            }
            for (int i = 0; i < 3; ++i)
            {
                this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.inGround && this.isCrit)
        {
            for (int l = 0; l < 2; ++l)
            {
                this.worldObj.spawnParticle("crit", this.posX + this.motionX * l / 2.0D, this.posY + this.motionY * l / 2.0D, this.posZ + this.motionZ * l / 2.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }
    }
}
