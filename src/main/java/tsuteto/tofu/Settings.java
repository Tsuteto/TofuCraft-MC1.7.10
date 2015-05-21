package tsuteto.tofu;

import net.minecraftforge.common.config.Configuration;

public class Settings
{
    public static int entityIdTofuSlime = -1;
    public static int entityIdTofuCreeper = -1;
    public static int entityIdTofunian = -1;

    public static int tofuDimNo = 12;
    public static int tofuBiomeId = 40;
    public static boolean canSaltPanUseAlpha;

    public static int professionIdTofucook = 1212;
    public static int professionIdTofunian = 1213;

    //public static int potionGlowingId;
    
    public static boolean autoAssign = true;
    public static boolean achievement = true;
    public static int clientGlowTofuLightInterval = 2;
    public static int serverGlowTofuLightInterval = 5;
    public static boolean updateCheck = true;

    public static boolean debug = Boolean.valueOf(System.getProperty("tofucraft.debug", "false"));

    public static void load(Configuration conf)
    {
        tofuDimNo = conf.get("general", "tofuDim", tofuDimNo).getInt();
        tofuBiomeId = conf.get("general", "tofuBiomeId", tofuBiomeId).getInt();
        canSaltPanUseAlpha = conf.get("general", "saltPanUsesAlpha", true, "Whether the Salt Pan uses transparent texture. If you feel weird in the Salt Pan's appearance, set false.").getBoolean();

        professionIdTofucook = conf.get("villager", "tofucookId", professionIdTofucook).getInt();
        professionIdTofunian = conf.get("villager", "tofunianId", professionIdTofunian).getInt();

        conf.addCustomCategoryComment("entity", "Entity IDs to be assigned. -1 for auto assignment.");
        entityIdTofuSlime = conf.get("entity", "tofuSlimeId", entityIdTofuSlime).getInt();
        entityIdTofuCreeper = conf.get("entity", "tofuCreeperId", entityIdTofuCreeper).getInt();
        entityIdTofunian = conf.get("entity", "tofunianId", entityIdTofunian).getInt();
        
        //potionGlowingId = conf.get("potion", "glowingId", TcPotion.assignNewId()).getInt();

        achievement = conf.get("general", "achievement", achievement).getBoolean(true);

        updateCheck = conf.get("general", "updateCheck", updateCheck).getBoolean(true);
    }
}
