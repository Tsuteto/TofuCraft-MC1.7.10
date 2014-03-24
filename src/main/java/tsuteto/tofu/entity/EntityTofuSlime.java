package tsuteto.tofu.entity;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import tsuteto.tofu.Settings;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.block.TcBlocks;import tsuteto.tofu.item.ItemTofuSword;
import tsuteto.tofu.item.TcItem;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.util.ModLog;

public class EntityTofuSlime extends EntitySlime
{
    public EntityTofuSlime(World par1World)
    {
        super(par1World);
    }
    
    @Override
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
            TcAchievementMgr.achieve(entityplayer, TcAchievementMgr.Key.tofuSlimeHunter);
        }
    }

    @Override
    protected Item getDropItem()
    {
        return this.getSlimeSize() == 1 ? TcItems.tofuKinu : null;
    }

    @Override
    protected void dropFewItems(boolean par1, int par2)
    {
        super.dropFewItems(par1, par2);

        if (this.getSlimeSize() == 1 && this.attackingPlayer != null)
        {
            ItemStack equipped = this.attackingPlayer.getCurrentEquippedItem();
            if (this.attackingPlayer.dimension == Settings.tofuDimNo
                    || equipped != null && equipped.getItem() instanceof ItemTofuSword)
            {
                if (this.rand.nextInt(10) == 0)
                {
                    this.dropItem(TcItems.tofuStick, 1);
                }
            }
        }
    }

    @Override
    protected EntitySlime createInstance()
    {
        return new EntityTofuSlime(this.worldObj);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting.getDifficultyId() > 0)
        {
            int lightValue = this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (BiomeDictionary.isBiomeOfType(biome, TofuCraftCore.BIOME_TYPE_TOFU) && this.rand.nextInt(20) == 0)
            {
                return this.baseGetCanSpawnHere();
            }

            if (this.dimension == 0
                    && isSpawnChunk(this.worldObj, this.posX, this.posZ)
                    && this.posY > 15.0D && this.posY < 40.0D
                    && lightValue <= this.rand.nextInt(8))
            {
                ModLog.debug("Tofu slime spawned at (%.1f, %.1f, %.1f)", this.posX, this.posY, this.posZ);
                return this.baseGetCanSpawnHere();
            }
        }

        return false;
    }

    /**
     * Must be the same as EntityLiving.getCanSpawnHere!
     */
    private boolean baseGetCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    /**
     * Returns the name of a particle effect that may be randomly created by EntitySlime.onUpdate()
     */
    @Override
    protected String getSlimeParticle()
    {
        return "snowballpoof";
    }

    public static boolean isSpawnChunk(World world, double x, double z)
    {
        Chunk var1 = world.getChunkFromBlockCoords(MathHelper.floor_double(x), MathHelper.floor_double(z));
        return var1.getRandomWithSeed(4611020141L).nextInt(24) == 0;
    }
}
