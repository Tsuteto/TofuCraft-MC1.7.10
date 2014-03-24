package tsuteto.tofu;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class Message
{
    public static void load()
    {
        $("itemGroup.TofuCraft", "en_US", "TofuCraft");
        $("itemGroup.TofuCraft", "ja_JP", "豆腐Craft");

        $("container.tofucraft.SaltFurnace", "en_US", "Salt Furnace");
        $("container.tofucraft.SaltFurnace", "ja_JP", "製塩用かまど");
        
        $("container.tofucraft.TfStorage", "en_US", "Tofu Force Storage");
        $("container.tofucraft.TfStorage", "ja_JP", "トーフフォース貯蔵装置");
        
        $("potion.glowing", "en_US", "Glowing");
        $("potion.glowing", "ja_JP", "蛍光");

        $("potion.glowing.postfix", "en_US", "Potion of Glowing");
        $("potion.glowing.postfix", "ja_JP", "蛍光のポーション");

        $("potion.filling", "en_US", "Filling");
        $("potion.filling", "ja_JP", "腹持ち");

        $("potion.filling.postfix", "en_US", "Potion of Filling");
        $("potion.filling.postfix", "ja_JP", "腹持ちのポーション");

        $("entity.TofuSlime.name", "en_US", "Tofu Slime");
        $("entity.TofuSlime.name", "ja_JP", "豆腐スライム");

        $("entity.TofuCreeper.name", "en_US", "Tofu Creeper");
        $("entity.TofuCreeper.name", "ja_JP", "豆腐クリーパー");

        $("entity.Tofunian.name", "en_US", "Tofunian");
        $("entity.Tofunian.name", "ja_JP", "トーフニアン");

        $("commands.tofuslimecheck.usage", "/tofuslimecheck");

        $("commands.tofuslimecheck.found", "en_US", "Tofu Slimes habit in this chunk");
        $("commands.tofuslimecheck.found", "ja_JP", "このチャンクには豆腐スライムが生息しています");
        $("commands.tofuslimecheck.notFound", "en_US", "Tofu Slimes cannot be seen in this chunk");
        $("commands.tofuslimecheck.notFound", "ja_JP", "このチャンクには豆腐スライムはいないようだ");
    }
    
    private static void $(String key, String value)
    {
        LanguageRegistry.instance().addStringLocalization(key, value);
    }
    
    private static void $(String key, String lang, String value)
    {
        LanguageRegistry.instance().addStringLocalization(key, lang, value);
    }
}
