package tsuteto.tofu.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityTofuSplash extends EntityThrowable
{
    public EntityLivingBase entity = null;
    public double dx = 0;
    public double dy = 0;
    public double dz = 0;
    public float dYaw = 0;

    public int tickCount = 0;

    public EntityTofuSplash(World par1World)
    {
        super(par1World);
    }

    public EntityTofuSplash(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);

        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;

        float yaw = rand.nextFloat() * 360F;
        float pitch = rand.nextFloat() * -90F;
        float f = 0.4F;
        this.motionX = (-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (-MathHelper.sin((pitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 0.5F);
    }

    public EntityTofuSplash(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(MovingObjectPosition mobj)
    {
        if (mobj.entityHit != null)
        {
            Entity entityHit = mobj.entityHit;
            if (entityHit instanceof EntityLivingBase)
            {
                EntityLivingBase entityLivivng = (EntityLivingBase)entityHit;
                this.entity = entityLivivng;

                dx = entityLivivng.posX - this.posX;
                dy = entityLivivng.posY - this.posY;
                dz = entityLivivng.posZ - this.posZ;

                dYaw = (float)(Math.atan2(dx, dz) * 180.0D / Math.PI);

            }
        }
        else
        {
            float var20;
            this.motionX = ((float)(mobj.hitVec.xCoord - this.posX));
            this.motionY = ((float)(mobj.hitVec.yCoord - this.posY));
            this.motionZ = ((float)(mobj.hitVec.zCoord - this.posZ));
            var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / var20 * 0.05000000074505806D;
            this.posY -= this.motionY / var20 * 0.05000000074505806D;
            this.posZ -= this.motionZ / var20 * 0.05000000074505806D;
            this.inGround = true;
            if (!worldObj.isRemote)
            {
                this.setDead();
            }
        }
    }

    @Override
    public void onUpdate()
    {
        if (entity != null)
        {
            this.lastTickPosX = this.posX;
            this.lastTickPosY = this.posY;
            this.lastTickPosZ = this.posZ;
            this.onEntityUpdate();

            Vec3 lookVec = entity.getLookVec();

            this.motionX = entity.motionX;
            this.motionZ = entity.motionY;
            this.motionZ = entity.motionZ;

            this.posX = entity.posX - (MathHelper.cos((entity.rotationYaw - dYaw) / 180.0F * (float)Math.PI) * 0.5F);
            this.posY = entity.posY - dy;
            this.posZ = entity.posZ - (MathHelper.sin((entity.rotationYaw - dYaw) / 180.0F * (float)Math.PI) * 0.5F);

            this.rotationYaw = -(entity.rotationYaw + dYaw);
            this.rotationPitch = 0;
            this.tickCount++;

            if (!entity.isEntityAlive() || this.tickCount > 1200)
            {
                this.setDead();
            }
        }
        else
        {
            super.onUpdate();
        }
    }

    @Override
    protected float func_70182_d()
    {
        return 0.6F;
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.06F;
    }
}
