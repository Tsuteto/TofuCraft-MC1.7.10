package tsuteto.tofu.entity;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tsuteto.tofu.block.BlockChikuwaPlatform;

import java.util.List;

public class EntityFallingChikuwaPlatform extends Entity implements IEntityAdditionalSpawnData
{
    private Block blockContained;
    public int blockMetadata;
    public int fallingTime;
    private int chikuwaChunkUID;
    private ChunkCoordinates homeCoord = null;

    @SideOnly(Side.CLIENT)
    private double velocityX;
    @SideOnly(Side.CLIENT)
    private double velocityY;
    @SideOnly(Side.CLIENT)
    private double velocityZ;

    public EntityFallingChikuwaPlatform(World p_i1706_1_)
    {
        super(p_i1706_1_);
        this.preventEntitySpawning = true;
        this.setSize(1.0F, 0.875F);
    }

    public EntityFallingChikuwaPlatform(World p_i45319_1_, double p_i45319_2_, double p_i45319_4_, double p_i45319_6_, Block p_i45319_8_, int p_i45319_9_, int chikuwaChunkUID)
    {
        this(p_i45319_1_);
        this.blockContained = p_i45319_8_;
        this.blockMetadata = p_i45319_9_;
        this.yOffset = this.height / 2.0F;
        this.setPosition(p_i45319_2_, p_i45319_4_, p_i45319_6_);
        this.motionX = 0.0D;
        this.motionY = -0.1D;
        this.motionZ = 0.0D;
        this.prevPosX = p_i45319_2_;
        this.prevPosY = p_i45319_4_;
        this.prevPosZ = p_i45319_6_;
        this.chikuwaChunkUID = chikuwaChunkUID;
    }

    public void setHome(ChunkCoordinates coord)
    {
        this.homeCoord = new ChunkCoordinates(coord);
    }

    protected void entityInit() {}

    public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
    {
        return p_70114_1_.boundingBox;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.blockContained.getMaterial() == Material.air)
        {
            this.setDead();
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY = -0.1D;
            ++this.fallingTime;
            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (!this.worldObj.isRemote)
            {
                int tileX = MathHelper.floor_double(this.posX);
                int tileY = MathHelper.floor_double(this.posY);
                int tileZ = MathHelper.floor_double(this.posZ);

                if (this.onGround)
                {
                    if (this.onLanding(tileX, tileY, tileZ))
                    {
                        this.triggerLanding();
                    }
                }
                else if (this.fallingTime > 100 && !this.worldObj.isRemote && (tileY < 1 || tileY > 256) || this.fallingTime > 600)
                {
                    if (this.homeCoord != null)
                    {
                        if (this.onLanding(tileX, tileY, tileZ))
                        {
                            this.triggerLanding();
                        }
                    }
                    else
                    {
                        this.entityDropItem(new ItemStack(this.blockContained, 1, this.blockContained.damageDropped(this.blockMetadata)), 0.0F);
                        this.setDead();
                    }
                }
            }
        }
    }

    public void triggerLanding()
    {
        List<EntityFallingChikuwaPlatform> chikuwaChunk = Lists.newArrayList();

        for (Object entry : this.worldObj.loadedEntityList)
        {
            if (entry instanceof EntityFallingChikuwaPlatform)
            {
                EntityFallingChikuwaPlatform chikuwa = (EntityFallingChikuwaPlatform)entry;
                if (!chikuwa.equals(this) && chikuwa.chikuwaChunkUID == this.chikuwaChunkUID)
                {
                    chikuwaChunk.add(chikuwa);
                }
            }
        }

        for (EntityFallingChikuwaPlatform entity : chikuwaChunk)
        {
            int tileX = MathHelper.floor_double(entity.posX);
            int tileY = MathHelper.floor_double(entity.posY);
            int tileZ = MathHelper.floor_double(entity.posZ);
            entity.onLanding(tileX, tileY, tileZ);
        }
    }

    private boolean onLanding(int tileX, int tileY, int tileZ)
    {
        boolean landed;
        if (this.homeCoord != null)
        {
            tileX = this.homeCoord.posX;
            tileY = this.homeCoord.posY;
            tileZ = this.homeCoord.posZ;

            this.setDead();
            landed = this.worldObj.setBlock(tileX, tileY, tileZ, this.blockContained, this.blockMetadata, 3);
        }
        else
        {
            if (this.worldObj.getBlock(tileX, tileY, tileZ) != Blocks.piston_extension)
            {
                this.setDead();
                landed = this.worldObj.canPlaceEntityOnSide(this.blockContained, tileX, tileY, tileZ, true, 1, (Entity) null, (ItemStack) null)
                        && !BlockChikuwaPlatform.canGoThrough(this.worldObj, tileX, tileY - 1, tileZ)
                        && this.worldObj.setBlock(tileX, tileY, tileZ, this.blockContained, this.blockMetadata, 3);
            }
            else
            {
                // Consider landing is successful
                landed = true;
            }
        }

        if (!landed)
        {
            this.entityDropItem(new ItemStack(this.blockContained, 1, this.blockContained.damageDropped(this.blockMetadata)), 0.0F);
        }
        return landed;
    }

    protected void fall(float p_70069_1_) {}

    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("TileID", Block.getIdFromBlock(this.blockContained));
        nbt.setByte("Data", (byte) this.blockMetadata);
        nbt.setShort("Time", (short) this.fallingTime);
        nbt.setInteger("ChunkUID", chikuwaChunkUID);
        if (this.homeCoord != null)
        {
            NBTTagCompound nbtHome = new NBTTagCompound();
            nbtHome.setInteger("X", this.homeCoord.posX);
            nbtHome.setInteger("Y", this.homeCoord.posY);
            nbtHome.setInteger("Z", this.homeCoord.posZ);
            nbt.setTag("Home", nbtHome);
        }
    }

    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        this.blockContained = Block.getBlockById(nbt.getInteger("TileID"));
        this.blockMetadata = nbt.getByte("Data") & 255;
        this.fallingTime = nbt.getShort("Time");
        this.chikuwaChunkUID = nbt.getInteger("ChunkUID");
        if (nbt.hasKey("Home"))
        {
            NBTTagCompound nbtHome = nbt.getCompoundTag("Home");
            this.homeCoord = new ChunkCoordinates(nbtHome.getInteger("X"), nbtHome.getInteger("Y"), nbtHome.getInteger("Z"));
        }

        if (this.blockContained.getMaterial() == Material.air)
        {
            this.blockContained = Blocks.sand;
        }

    }

    public void addEntityCrashInfo(CrashReportCategory p_85029_1_)
    {
        super.addEntityCrashInfo(p_85029_1_);
        p_85029_1_.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(this.blockContained)));
        p_85029_1_.addCrashSection("Immitating block data", Integer.valueOf(this.blockMetadata));
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    public World func_145807_e()
    {
        return this.worldObj;
    }

    /**
     * Return whether this entity should be rendered as on fire.
     */
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

    public Block func_145805_f()
    {
        return this.blockContained;
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
    {
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
    {
        this.velocityX = this.motionX = p_70016_1_;
        this.velocityY = this.motionY = p_70016_3_;
        this.velocityZ = this.motionZ = p_70016_5_;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        buffer.writeInt(Block.getIdFromBlock(blockContained));
        buffer.writeByte(blockMetadata);

        buffer.writeFloat(((float)motionX));
        buffer.writeFloat(((float)motionY));
        buffer.writeFloat(((float)motionZ));
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        blockContained = Block.getBlockById(additionalData.readInt());
        blockMetadata = additionalData.readByte();

        motionX = additionalData.readFloat();
        motionY = additionalData.readFloat();
        motionZ = additionalData.readFloat();
        setVelocity(motionX, motionY, motionZ);
    }
}
