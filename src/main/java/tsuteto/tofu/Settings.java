package tsuteto.tofu;

import net.minecraftforge.common.config.Configuration;

public class Settings
{
    public static EnumCreativeTabOptions creativeTab = EnumCreativeTabOptions.ORIGINAL;

    public static int entityIdTofuSlime = -1;
    public static int entityIdTofuCreeper = -1;
    public static int entityIdTofunian = -1;

    public static int tofuDimNo = 12;

    public static int tofuBiomeId = 40;

    public static int professionIdTofucook = 1212;
    public static int professionIdTofunian = 1213;

    //public static int potionGlowingId;
    
    public static boolean autoAssign = true;
    public static boolean achievement = true;
    public static int clientGlowTofuLightInterval = 2;
    public static int serverGlowTofuLightInterval = 5;
    public static boolean updateCheck = true;

    public static void load(Configuration conf)
    {
        tofuDimNo = conf.get("general", "tofuDim", tofuDimNo).getInt();
        tofuBiomeId = conf.get("general", "tofuBiomeId", tofuBiomeId).getInt();

        professionIdTofucook = conf.get("villager", "tofucookId", professionIdTofucook).getInt();
        professionIdTofunian = conf.get("villager", "tofunianId", professionIdTofunian).getInt();

        conf.addCustomCategoryComment("entity", "Entity IDs to be assigned. -1 for auto assignment.");
        entityIdTofuSlime = conf.get("entity", "tofuSlimeId", entityIdTofuSlime).getInt();
        entityIdTofuCreeper = conf.get("entity", "tofuCreeperId", entityIdTofuCreeper).getInt();
        entityIdTofunian = conf.get("entity", "tofunianId", entityIdTofunian).getInt();
        
        //potionGlowingId = conf.get("potion", "glowingId", TcPotion.assignNewId()).getInt();

        creativeTab = EnumCreativeTabOptions.values()[conf.get("general", "creativeTab", creativeTab.ordinal(),
                "Creative tab register shows TofuCraft items and blocks. 0=original tab, 1=sorted into vanilla tabs, 2=both").getInt()];
        achievement = conf.get("general", "achievement", achievement).getBoolean(true);

        updateCheck = conf.get("general", "updateCheck", updateCheck).getBoolean(true);
    }

    public enum EnumCreativeTabOptions {
    	ORIGINAL, SORTED, BOTH;
    }

}
