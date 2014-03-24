package tsuteto.tofu.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import tsuteto.tofu.Settings;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import tsuteto.tofu.item.TcItems;

public class EntityTofuCreeper extends EntityMob
{
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private float explosionRadius = 1.5F;
    private long es;

    public EntityTofuCreeper(World par1World)
    {
        super(par1World);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAITofuCreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    public int getMaxSafePointTries()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
    protected void fall(float par1)
    {
        super.fall(par1);
        this.timeSinceIgnited = (int)(this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
        {
            par1NBTTagCompound.setBoolean("powered", true);
        }

        par1NBTTagCompound.setShort("Fuse", (short)this.fuseTime);
        par1NBTTagCompound.setFloat("ExplosionRadius", this.explosionRadius);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
        {
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");
        }

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
        {
            this.explosionRadius = par1NBTTagCompound.getFloat("ExplosionRadius");
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate()
    {
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("random.fuse", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;

                //for (i = 0; i < 200; i++)
                //{
                    //Entity splash = new EntityTofuSplash(worldObj, this);
                    //worldObj.spawnEntityInWorld(splash);
                //}

                int r = getPowered() ? 6 : 2;

                this.buildTofu((int)this.posX, (int)this.posY, (int)this.posZ, r, this.worldObj);
                this.worldObj.spawnParticle("hugeexplosion", this.posX + r, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("hugeexplosion", this.posX - r, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY, this.posZ + r, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("hugeexplosion", this.posX, this.posY, this.posZ - r, 1.0D, 0.0D, 0.0D);
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
                this.setDead();
            }
        }

        super.onUpdate();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (this.dimension == Settings.tofuDimNo)
        {
            return super.getCanSpawnHere();
        }
        else
        {
            int[] idx = TofuCreeperSeed.instance().getSpawnId(this.worldObj, TcEntity.allBiomesList.length);
            int bid1 = TcEntity.allBiomesList[idx[0]].biomeID;
            int bid2 = TcEntity.allBiomesList[idx[1]].biomeID;
            int bid3 = worldObj.getBiomeGenForCoords((int)this.posX, (int)this.posZ).biomeID;
            if (bid3 == bid1 || bid3 == bid2)
            {
                return super.getCanSpawnHere();
            }
            else
            {
                return false;
            }
        }
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    public String getHurtSound()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    public String getDeathSound()
    {
        return "mob.creeper.death";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton)
        {
            this.dropItem(TcItems.tofuCake, 1);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    public float getCreeperFlashIntensity(float par1)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / (this.fuseTime - 2);
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    @Override
    protected Item getDropItem()
    {
        return TcItems.tofuKinu;
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int par1)
    {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    @Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        //super.onStruckByLightning(par1EntityLightningBolt);
        this.dealFireDamage(1);

        this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    }

    protected void buildTofu(int ox, int oy, int oz, int height, World par1World)
    {
        int blockY, radius, blockX, relX;
        radius = 1 + height / 2;

        for (blockY = oy; blockY <= oy + height; ++blockY)
        {
            for (blockX = ox - radius; blockX <= ox + radius; ++blockX)
            {
                relX = blockX - ox;

                for (int blockZ = oz - radius; blockZ <= oz + radius; ++blockZ)
                {
                    int relZ = blockZ - oz;

                    if (par1World.getBlock(blockX, blockY, blockZ) != Blocks.mob_spawner)
                    {
                        par1World.setBlock(blockX, blockY, blockZ, TcBlocks.tofuMomen, 0, 3);
                    }
                }
            }
        }
    }
}
