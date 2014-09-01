package tsuteto.tofu.item.iteminfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import tsuteto.tofu.util.ModLog;

public class SoymilkPlayerMultiInfo extends SoymilkPlayerInfo
{
    protected static final String NBT_LOGIN = "Login";
    protected static final String NBT_LOGOUT = "Logout";


    public long lastLoginTime;
    public long lastLogoutTime;

    protected SoymilkPlayerMultiInfo(EntityPlayer player)
    {
        super(player);
    }

    @Override
    public SoymilkPlayerInfo readNBTFrom(NBTTagCompound nbtTag)
    {
        super.readNBTFrom(nbtTag);

        NBTTagCompound root = nbtTag.getCompoundTag(NBT_ROOT);
        this.lastLoginTime = root.getLong(NBT_LOGIN);
        this.lastLogoutTime = root.getLong(NBT_LOGOUT);

        return this;
    }

    @Override
    public void writeNBTTo(NBTTagCompound nbtTag)
    {
        super.writeNBTTo(nbtTag);

        NBTTagCompound root = nbtTag.getCompoundTag(NBT_ROOT);
        root.setLong(NBT_LOGIN, this.lastLoginTime);
        root.setLong(NBT_LOGOUT, this.lastLogoutTime);
    }

    public void onLogin()
    {
        this.lastLoginTime = player.getEntityWorld().getWorldTime();
        this.writeNBTToPlayer();
        super.onLogin();
    }

    public void onLogout()
    {
        this.lastLogoutTime = player.getEntityWorld().getWorldTime();
        this.writeNBTToPlayer();
    }

    @Override
    protected void calcDaysPassed()
    {
        super.calcDaysPassed();

        if (this.lastTakenTime < this.lastLoginTime)
        {
            long logoutDuration = (this.lastLoginTime - this.lastLogoutTime) / 24000;
            ModLog.debug("logoutDuration=%d", logoutDuration);
            this.daysPassed -= (int)logoutDuration;
        }
    }
}
