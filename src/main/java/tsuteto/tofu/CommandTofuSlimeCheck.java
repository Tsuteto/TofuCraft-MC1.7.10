package tsuteto.tofu;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import tsuteto.tofu.entity.EntityTofuSlime;

public class CommandTofuSlimeCheck extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "tofuslimecheck";
    }

    @Override
    public void processCommand(ICommandSender var1, String[] var2)
    {
        EntityPlayerMP entityPlayer = getCommandSenderAsPlayer(var1);

        boolean isSpawnChunk = EntityTofuSlime.isSpawnChunk(entityPlayer.worldObj, entityPlayer.posX, entityPlayer.posZ);
        if (isSpawnChunk)
        {
            var1.addChatMessage(new ChatComponentTranslation("commands.tofuslimecheck.found"));
        }
        else
        {
            var1.addChatMessage(new ChatComponentTranslation("commands.tofuslimecheck.notFound"));
        }

    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "commands.tofuslimecheck.usage";
    }
}
