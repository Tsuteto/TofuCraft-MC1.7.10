package tsuteto.tofu.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.biome.BiomeGenBase;
import tsuteto.tofu.entity.EntityTofuCreeper;

public class CommandTofuCreeperSpawn extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "tofucreeperspawn";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_)
    {
        return "commands.tofucreeperspawn.usage";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
    {
        StringBuilder sb = new StringBuilder();
        int[] biomeIds = EntityTofuCreeper.getSpawnBiomeIds(p_71515_1_.getEntityWorld());
        for (int id : biomeIds)
        {
            if (sb.length() != 0)
            {
                sb.append(", ");
            }
            sb.append(BiomeGenBase.getBiome(id).biomeName);
        }
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.tofucreeperspawn.result", sb.toString()));
    }
}
