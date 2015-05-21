package tsuteto.tofu.item;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketSomenScooping;

import java.util.List;

public class ItemSomenTsuyuBowl extends ItemSoupBase
{
    public ItemSomenTsuyuBowl(int par2, float par3, boolean par4)
    {
        super(par2, par3, par4);
        this.setAlwaysEdible();
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        return new ItemStack(TcItems.materials, 1, ItemTcMaterials.glassBowl.id);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par2World.isRemote)
        {
            MovingObjectPosition mop = this.getEntityItemPlayerPointing(1.0F);
            //ModLog.debug(mop == null ? null : mop.entityHit);
            if (mop != null && mop.entityHit != null)
            {
                PacketDispatcher.packet(new PacketSomenScooping(mop.entityHit.getEntityId())).sendToServer();
                par1ItemStack = scoopSomen(mop.entityHit, par1ItemStack, par3EntityPlayer, true);
            }
            else
            {
                super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
            }
        }
        else
        {
            super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
        }
        return par1ItemStack;
    }

    public static ItemStack scoopSomen(Entity entity, ItemStack itemHeld, EntityPlayer player, boolean isRemote)
    {
        if (entity instanceof EntityItem)
        {
            ItemStack scooped = ((EntityItem) entity).getEntityItem();

            if (itemHeld != null && itemHeld.getItem() == TcItems.somenTsuyuBowl
                    && scooped.getItem() == TcItems.materials && scooped.getItemDamage() == ItemTcMaterials.tofuSomen.id)
            {
                if (!isRemote)
                {
                    --scooped.stackSize;

                    if (scooped.stackSize <= 0)
                    {
                        entity.setDead();
                    }
                }

                --itemHeld.stackSize;

                ItemStack newItem = new ItemStack(TcItems.foodSet, 1, ItemFoodSet.tofuSomen.id);

                if (itemHeld.stackSize <= 0)
                {
                    return newItem;
                }

                if (!player.inventory.addItemStackToInventory(newItem))
                {
                    player.dropPlayerItemWithRandomChoice(newItem, false);
                }
            }
        }
        return itemHeld;
    }

    public MovingObjectPosition getEntityItemPlayerPointing(float par1)
    {
        Minecraft mc = FMLClientHandler.instance().getClient();

        mc.pointedEntity = null;
        double d0 = (double) mc.playerController.getBlockReachDistance();
        mc.objectMouseOver = mc.renderViewEntity.rayTrace(d0, par1);
        double d1 = d0;
        Vec3 vec3 = mc.renderViewEntity.getPosition(par1);

        if (mc.playerController.extendedReach())
        {
            d0 = 6.0D;
            d1 = 6.0D;
        } else
        {
            if (d0 > 3.0D)
            {
                d1 = 3.0D;
            }

            d0 = d1;
        }

        if (mc.objectMouseOver != null)
        {
            d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
        }

        Vec3 vec31 = mc.renderViewEntity.getLook(par1);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
        Entity pointedEntity = null;
        Vec3 vec33 = null;
        float f1 = 1.0F;
        List list = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double) f1, (double) f1, (double) f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = (Entity) list.get(i);

            float f2 = entity.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

            if (axisalignedbb.isVecInside(vec3))
            {
                if (0.0D < d2 || d2 == 0.0D)
                {
                    pointedEntity = entity;
                    vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                    d2 = 0.0D;
                }
            } else if (movingobjectposition != null)
            {
                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                if (d3 < d2 || d2 == 0.0D)
                {
                    pointedEntity = entity;
                    vec33 = movingobjectposition.hitVec;
                    d2 = d3;
                }
            }
        }

        if (pointedEntity != null && (d2 < d1))
        {
            return new MovingObjectPosition(pointedEntity, vec33);
        }
        return null;
    }

}
