package tsuteto.tofu.entity;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import tsuteto.tofu.TofuCraftCore;
import tsuteto.tofu.achievement.TcAchievementMgr;
import tsuteto.tofu.block.TcBlocks;
import tsuteto.tofu.item.TcItems;
import tsuteto.tofu.network.packet.PacketZundaArrowHit;
import tsuteto.tofu.util.SimplePacketDispatcher;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.SidedProxy;

import java.util.Random;


public class EntityZundaArrow extends EntityArrowBase implements IEntityAdditionalSpawnData
{
    @SidedProxy(serverSide = "tsuteto.tofu.entity.EntityZundaArrow$CommonProxy", clientSide = "tsuteto.tofu.entity.EntityZundaArrow$ClientProxy")
    public static CommonProxy sidedProxy;

    public EntityZundaArrow(World par1World)
    {
        super(par1World);
    }

    public EntityZundaArrow(World par1World, double par2, double par3, double par4)
    {
        super(par1World, par2, par3, par4);
    }

    public EntityZundaArrow(World par1World, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving, float par4, float par5)
    {
        super(par1World, par2EntityLiving, par3EntityLiving, par4, par5);
    }

    public EntityZundaArrow(World par1World, EntityLivingBase par2EntityLiving, float par3)
    {
        super(par1World, par2EntityLiving, par3);
    }

    @Override
    protected void onHitEntity(MovingObjectPosition var4)
    {
        if (var4.entityHit instanceof EntitySlime)
        {
            EntitySlime slime = (EntitySlime)var4.entityHit;
            if (!worldObj.isRemote)
            {
                for (int i = 0; i < slime.getSlimeSize(); i++)
                {
                    slime.dropItem(TcItems.tofuZunda, 1);
                }
            }
            slime.setDead();

            if (this.shootingEntity instanceof EntityPlayer)
            {
                TcAchievementMgr.achieve((EntityPlayer)this.shootingEntity, TcAchievementMgr.Key.zundaAttack);
            }
        }
        else if (var4.entityHit instanceof EntityLivingBase)
        {
            EntityLivingBase living = (EntityLivingBase)var4.entityHit;
            living.addPotionEffect(new PotionEffect(Potion.regeneration.id, this.getIsCritical() ? 200 : 100, 1));
        }

        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

        if (!(var4.entityHit instanceof EntityEnderman))
        {
            this.setDead();
        }

        SimplePacketDispatcher.packet(new PacketZundaArrowHit(var4.entityHit.posX, var4.entityHit.posY, var4.entityHit.posZ))
                .sendToAllInDimension(var4.entityHit.dimension);
    }


    @Override
    protected void emitCriticalEffect()
    {
        sidedProxy.emitCriticalEffect(this);
    }

    public static void emitArrowHitEffect(double x, double y, double z)
    {
        sidedProxy.emitArrowHitEffect(x, y, z);
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        if (this.shootingEntity != null)
        {
            data.writeInt(this.shootingEntity.getEntityId());
        }
        else
        {
            data.writeInt(-1);
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        int entityId = data.readInt();
        if (entityId != -1)
        {
            this.shootingEntity = this.worldObj.getEntityByID(entityId);
        }
    }

    @Override
    protected ItemStack getArrowItemStack()
    {
        return new ItemStack(TcItems.zundaArrow, 1);
    }
    public static class CommonProxy
    {
        public void emitCriticalEffect(EntityZundaArrow arrow)
        {
        }
        public void emitArrowHitEffect(double var8, double var10, double var12)
        {
        }
    }

    public static class ClientProxy extends CommonProxy
    {
        @Override
        public void emitCriticalEffect(EntityZundaArrow arrow)
        {
            if (arrow.worldObj.isRemote)
            {
                Minecraft mc = FMLClientHandler.instance().getClient();
                for (int var9 = 0; var9 < 4; ++var9)
                {
                    EntityFX fx = new EntityCritFX(arrow.worldObj, arrow.posX + arrow.motionX * var9 / 4.0D,
                            arrow.posY + arrow.motionY * var9 / 4.0D,
                            arrow.posZ + arrow.motionZ * var9 / 4.0D,
                            -arrow.motionX, -arrow.motionY + 0.2D, -arrow.motionZ);
                    fx.setRBGColorF(0.4f, 1.0f, 0.2f);
                    mc.effectRenderer.addEffect(fx);
                }
            }
        }

        @Override
        public void emitArrowHitEffect(double var8, double var10, double var12)
        {
            Minecraft mc = FMLClientHandler.instance().getClient();
            Random rand = mc.theWorld.rand;
            int var15 = 0x8dd746;
            float var16 = (float)(var15 >> 16 & 255) / 255.0F;
            float var17 = (float)(var15 >> 8 & 255) / 255.0F;
            float var18 = (float)(var15 >> 0 & 255) / 255.0F;
            int var20;
            double var23;
            double var25;
            double var27;
            double var29;
            double var39;

            for (var20 = 0; var20 < 100; ++var20)
            {
                var39 = rand.nextDouble() * 4.0D;
                var23 = rand.nextDouble() * Math.PI * 2.0D;
                var25 = Math.cos(var23) * var39;
                var27 = 0.01D + rand.nextDouble() * 0.5D;
                var29 = Math.sin(var23) * var39;
                EntityFX var31 = new EntitySpellParticleFX(mc.theWorld, var8 + var25 * 0.1D, var10 + 0.3D, var12 + var29 * 0.1D, var25, var27, var29);

                float var32 = 0.75F + rand.nextFloat() * 0.25F;
                var31.setRBGColorF(var16 * var32, var17 * var32, var18 * var32);
                var31.multiplyVelocity((float)var39);
                mc.effectRenderer.addEffect(var31);
            }
        }
    }
}
