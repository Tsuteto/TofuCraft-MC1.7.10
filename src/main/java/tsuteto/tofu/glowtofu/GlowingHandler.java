package tsuteto.tofu.glowtofu;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import tsuteto.tofu.params.DataType;
import tsuteto.tofu.params.EntityInfo;
import tsuteto.tofu.util.TilePos;

public class GlowingHandler
{
    private static final GlowingHandler instance = new GlowingHandler();
    private int lightLevel;
    
    private GlowingHandler() {
        this.lightLevel = 12;
    }
    
    public static boolean lightTarget(World var1, EntityLivingBase var2)
    {
        return instance.doLightTarget(var1, var2);
    }
    
    public static void removeLight(World var1, EntityLivingBase var2)
    {
        instance.doRemoveLight(var1, var2);
    }
    
    public static void setHeadLightValue(int headLightValue)
    {
        instance.lightLevel = headLightValue;
    }
    
    private boolean doLightTarget(World world, EntityLivingBase living)
    {
        TilePos prevPos = this.getLightPosition(world, living);
        int hx = (int)living.posX;
        int hy = (int)(living.posY + living.getEyeHeight());
        int hz = (int)living.posZ;
        TilePos currPos = new TilePos(hx, hy, hz);

        if (prevPos != null && !prevPos.equals(currPos))
        {
            this.setLightLevel(world, prevPos, 0);
        }

        this.setLightLevel(world, currPos, lightLevel);
       
        this.setLightPosition(world, living, currPos);
        return true;
    }
    
    private void setLightLevel(World world, TilePos pos, int newVal)
    {
        int x = pos.x;
        int y = pos.y;
        int z = pos.z;
        int currVal = world.getSavedLightValue(EnumSkyBlock.Block, x, y, z);

        if (currVal < newVal || newVal == 0)
        {
            world.setLightValue(EnumSkyBlock.Block, x, y, z, newVal);
            world.updateLightByType(EnumSkyBlock.Block, x - 1, y, z);
            world.updateLightByType(EnumSkyBlock.Block, x + 1, y, z);
            world.updateLightByType(EnumSkyBlock.Block, x, y - 1, z);
            world.updateLightByType(EnumSkyBlock.Block, x, y + 1, z);
            world.updateLightByType(EnumSkyBlock.Block, x, y, z - 1);
            world.updateLightByType(EnumSkyBlock.Block, x, y, z + 1);
        }
    }

    private void doRemoveLight(World var1, EntityLivingBase var2)
    {
        TilePos var3 = this.getLightPosition(var1, var2);

        if (var3 != null)
        {
            this.setLightLevel(var1, var3, 0);
            DataType dataType = var1.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
            EntityInfo.instance().remove(var2.getEntityId(), dataType);
        }
    }

    private TilePos getLightPosition(World world, EntityLivingBase living)
    {
        DataType dataType = world.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
        return EntityInfo.instance().get(living.getEntityId(), dataType);
    }
    
    private void setLightPosition(World world, EntityLivingBase living, TilePos pos)
    {
        DataType dataType = world.isRemote ? DataType.GlowTofuLightPositionCl : DataType.GlowTofuLightPositionSv;
        EntityInfo.instance().set(living.getEntityId(), dataType, pos);
    }
}
