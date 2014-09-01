package tsuteto.tofu.item.iteminfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import tsuteto.tofu.network.PacketDispatcher;
import tsuteto.tofu.network.packet.PacketSoymilkInfo;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.Utils;

public class SoymilkPlayerInfo
{
    public static final byte NBT_REV_NO = 1;

    protected static final String NBT_ROOT = "TcSoymilk";
    protected static final String NBT_REV = "Rev";
    protected static final String NBT_DAYS = "Days"; // # of days accumulated
    protected static final String NBT_TIER = "Tier"; // Tier of effect
    protected static final String NBT_TAKEN = "Taken"; // World time at the last time of taken
    protected static final String NBT_CHAIN = "Chain"; // # of days in a row

    protected final EntityPlayer player;

    public int tier = 0;
    public int daysTotal = 0;
    public int chain = 0;
    public long lastTakenTime = -1;

    // followings available after update
    public int daysPassed;
    public boolean isFirstTime = false;

    public static SoymilkPlayerInfo of(EntityPlayer player)
    {
        if (player.getEntityWorld().isRemote || MinecraftServer.getServer().isSinglePlayer())
        {
            return new SoymilkPlayerInfo(player);
        }
        else
        {
            return new SoymilkPlayerMultiInfo(player);
        }
    }

    protected SoymilkPlayerInfo(EntityPlayer player)
    {
        this.player = player;
    }

    public SoymilkPlayerInfo readNBTFromPlayer()
    {
        NBTTagCompound persisted = Utils.getNBTPlayerPersisted(player);

        if (persisted.hasKey(NBT_ROOT))
        {
            this.readNBTFrom(persisted);
        }
        return this;
    }

    public SoymilkPlayerInfo readNBTFrom(NBTTagCompound nbtTag)
    {
        NBTTagCompound root = nbtTag.getCompoundTag(NBT_ROOT);
        this.daysTotal = root.getInteger(NBT_DAYS);
        this.tier = root.getInteger(NBT_TIER);
        this.chain = root.getInteger(NBT_CHAIN);
        this.lastTakenTime = root.getLong(NBT_TAKEN);
        return this;
    }

    public void writeNBTToPlayer()
    {
        NBTTagCompound persisted = Utils.getNBTPlayerPersisted(player);
        this.writeNBTTo(persisted);
    }

    public void writeNBTTo(NBTTagCompound nbtTag)
    {
        if (!nbtTag.hasKey(NBT_ROOT))
        {
            nbtTag.setTag(NBT_ROOT, new NBTTagCompound());
        }

        NBTTagCompound root = nbtTag.getCompoundTag(NBT_ROOT);

        root.setByte(NBT_REV, NBT_REV_NO);
        root.setInteger(NBT_DAYS, this.daysTotal);
        root.setInteger(NBT_TIER, this.tier);
        root.setInteger(NBT_CHAIN, this.chain);
        root.setLong(NBT_TAKEN, this.lastTakenTime);
    }

    public void update()
    {
        if (lastTakenTime == -1)
        {
            this.isFirstTime = true;
            this.tier = 0;
            this.daysTotal = 1;
            this.chain = 1;
            this.daysPassed = 0;
        }
        else
        {
            this.calcDaysPassed();
            ModLog.debug("daysPassed=%d", daysPassed);

            if (daysPassed > 0)
            {
                this.daysTotal++;

                if (daysPassed == 1)
                {
                    this.chain++;
                    if (this.tier < 20) this.tier++;
                } else
                {
                    this.chain = 1;
                    this.tier = Math.max(0, this.tier - 2 * (daysPassed - 1));
                }
            }
            ModLog.debug("chain=%d, tier=%d, ", chain, tier);
        }
    }

    protected void calcDaysPassed()
    {
        long lastDay = this.lastTakenTime / 24000;
        long currentDay = player.getEntityWorld().getWorldTime() / 24000;
        this.daysPassed = (int)(currentDay - lastDay);
    }

    public void onTaken()
    {
        this.update();
        this.lastTakenTime = player.getEntityWorld().getWorldTime();
        this.writeNBTToPlayer();

        PacketDispatcher.packet(new PacketSoymilkInfo(this)).sendToPlayer(player);
    }

    public void onLogin()
    {
        PacketDispatcher.packet(new PacketSoymilkInfo(this)).sendToPlayer(player);
    }

    public void onLogout() {}
}
