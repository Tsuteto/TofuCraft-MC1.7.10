package tsuteto.tofu.util;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import org.apache.logging.log4j.Level;
import tsuteto.tofu.TofuCraftCore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class UpdateNotification
{
    private UpdateInfo updateInfo = null;
    private ModContainer container = Loader.instance().activeModContainer();
    private boolean isCompleted = false;

    public boolean isCompleted()
    {
        return this.isCompleted;
    }

    public UpdateInfo getUpdateInfo()
    {
        return this.updateInfo;
    }
    
    @SuppressWarnings("unchecked")
    public void checkUpdate()
    {
        new Thread("TofuCraft update check")
        {
            @Override
            public void run()
            {
                try
                {
                    // Update info reception
                    String receivedData;
                    try
                    {
                        URL url = new URL(TofuCraftCore.metadata.updateUrl);
                        InputStream con = url.openStream();
                        receivedData = new String(ByteStreams.toByteArray(con));
                        con.close();
                        ModLog.debug("receivedData:%n%s", receivedData);
                    } catch (IOException e)
                    {
                        ModLog.log(Level.WARN, e, "Failed to receive update info.");
                        return;
                    }

                    // Convert into Json
                    List<Map<String, Object>> updateInfoList;
                    try
                    {
                        updateInfoList = new Gson().fromJson(receivedData, List.class);
                    } catch (JsonSyntaxException e)
                    {
                        ModLog.log(Level.WARN, e, "Malformed update info.");
                        return;
                    }

                    // Retrieve update info for this MC version
                    Map<String, String> updateInfoJson = findUpdateInfoForMcVersion(updateInfoList);

                    if (updateInfoJson == null)
                    {
                        ModLog.info("No update info for this MC version.");
                        return;
                    }

                    String currVersion = container.getVersion();
                    currVersion = currVersion.substring(0, currVersion.indexOf("-"));

                    String newVersion = updateInfoJson.get("version");
                    if (!currVersion.equals(newVersion))
                    {
                        updateInfo = new UpdateInfo();
                        updateInfo.version = updateInfoJson.get("version");
                        updateInfo.downloadUrl = updateInfoJson.get("downloadUrl");
                    }
                }
                finally
                {
                    isCompleted = true;
                }
            }

            /**
             * Retrieve update info for current MC version
             * @param list
             * @return
             */
            private Map<String, String> findUpdateInfoForMcVersion(List<Map<String, Object>> list)
            {
                String currentVer = container.getVersion();
                for (Map<String, Object> map : list)
                {
                    boolean isMatched = container.acceptableMinecraftVersionRange()
                            .containsVersion(new DefaultArtifactVersion((String)map.get("mcversion")));
                    if (isMatched)
                    {
                        return (Map<String, String>)map.get("updateinfo");
                    }
                }
                return null;
            }
        }.start();
    }

    public void notifyUpdate(ICommandSender sender, Side side)
    {
        if (updateInfo != null)
        {
            if (side == Side.SERVER)
            {
                sender.addChatMessage(new ChatComponentTranslation(
                        "tofucraft.update.server", updateInfo.version, updateInfo.downloadUrl));
            }
            else
            {
                ChatStyle style = new ChatStyle();
                style.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, updateInfo.downloadUrl));

                sender.addChatMessage(new ChatComponentTranslation(
                        "tofucraft.update.client", updateInfo.version,
                        new ChatComponentTranslation("tofucraft.update.link").setChatStyle(style)));
            }

            ModLog.debug("Update available! %s", updateInfo.version);
        }

    }

    public static class UpdateInfo
    {
        public String version;
        public String downloadUrl;
    }
}
