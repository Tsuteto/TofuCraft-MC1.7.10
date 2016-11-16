package tsuteto.tofu;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import tsuteto.tofu.api.TfMaterialRegistry;
import tsuteto.tofu.api.recipe.TfCondenserRecipeRegistry;
import tsuteto.tofu.command.CommandTofuCreeperSpawn;
import tsuteto.tofu.command.CommandTofuSlimeCheck;
import tsuteto.tofu.data.MorijioManager;
import tsuteto.tofu.data.TcSaveHandler;
import tsuteto.tofu.enchantment.TcEnchantment;
import tsuteto.tofu.entity.TofuCreeperSeed;
import tsuteto.tofu.eventhandler.*;
import tsuteto.tofu.fishing.TofuFishing;
import tsuteto.tofu.gui.TcGuiHandler;
import tsuteto.tofu.init.*;
import tsuteto.tofu.init.block.LoaderDecorationBlock;
import tsuteto.tofu.init.block.TcBlockLoader;
import tsuteto.tofu.init.item.TcItemLoader;
import tsuteto.tofu.network.PacketManager;
import tsuteto.tofu.potion.TcPotion;
import tsuteto.tofu.recipe.Recipes;
import tsuteto.tofu.recipe.craftguide.CraftGuideLoader;
import tsuteto.tofu.texture.TcTextures;
import tsuteto.tofu.tileentity.TileEntityMorijio;
import tsuteto.tofu.util.ModLog;
import tsuteto.tofu.util.UpdateNotification;
import tsuteto.tofu.world.TcChunkProviderEvent;
import tsuteto.tofu.world.WorldProviderTofu;
import tsuteto.tofu.world.biome.TcBiomes;

/**
 * The Main Class of the TofuCraft
 *
 * @author Tsuteto
 *
 */
@Mod(
    modid = TofuCraftCore.modid,
    name = "TofuCraft",
    version = TofuCraftCore.version,
    acceptedMinecraftVersions = "[1.7.10,1.8)",
    dependencies = "after:BambooMod;after:IC2"
)
public class TofuCraftCore
{
    public static final String modid = "TofuCraft";
    public static final String version = "2.1.7-MC1.7.10";
    public static final String resourceDomain = "tofucraft:";

    @Mod.Instance(modid)
    public static TofuCraftCore instance;

    @Mod.Metadata(modid)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "tsuteto.tofu.TofuCraftCore$ClientProxy", serverSide = "tsuteto.tofu.TofuCraftCore$ServerProxy")
    public static ISidedProxy sidedProxy;

    public static final BiomeDictionary.Type BIOME_TYPE_TOFU;

    public static UpdateNotification update = null;
    private Configuration conf;
    private TcSaveHandler saveHandler = null;
    private MorijioManager morijioManager = null;

    static
    {
        ModLog.modId = TofuCraftCore.modid;
        ModLog.isDebug = Settings.debug;

        BIOME_TYPE_TOFU = EnumHelper.addEnum(BiomeDictionary.Type.class, "TOFU", new Class[] { BiomeDictionary.Type[].class }, new Object[]{ new BiomeDictionary.Type[0] });
    }

    @Mod.EventHandler
    public void preload(FMLPreInitializationEvent event)
    {
        ModInfo.load(metadata);

        conf = new Configuration(event.getSuggestedConfigurationFile());
        conf.load();

        Settings.load(conf);

        conf.save();

        // Register basic features
        TcBlockLoader.loadAll();
        TcItemLoader.loadAll();
        TcEntity.register(this);

        // Register liquid blocks
        TcFluids.register(event.getSide());
        
        // Prepare Tofu Force Materials
        TfMaterialRegistry.init();

        TfCondenserRecipeRegistry.init();

        // Add Achievements
        if (Settings.achievement)
        {
            TcAchievements.load();
        }

        // Update check!
        if (Settings.updateCheck)
        {
            update = new UpdateNotification();
            update.checkUpdate();
        }
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event)
    {
        // Register usage of the bone meal
        MinecraftForge.EVENT_BUS.register(new EventBonemeal());

        // Register usage of the empty bucket
        MinecraftForge.EVENT_BUS.register(new EventFillBucket());

        // Register event on player interact
        MinecraftForge.EVENT_BUS.register(new EventPlayerInteract());

        // Register event on EntityLiving
        MinecraftForge.EVENT_BUS.register(new EventEntityLiving());

        // Register event on tofu fishing
        MinecraftForge.EVENT_BUS.register(new TofuFishing());

        // Register world event handler
        MinecraftForge.EVENT_BUS.register(new EventWorld());

        // Register event on player
        FMLCommonHandler.instance().bus().register(new EventPlayer());

        // Register gui handler
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TcGuiHandler());

        // Register crafting handler
        FMLCommonHandler.instance().bus().register(new TcCraftingHandler());

        // Register pick-up Handler
        FMLCommonHandler.instance().bus().register(new EventItemPickup());

        {
            TcChunkProviderEvent eventhandler = new TcChunkProviderEvent();
            
            // Nether populating
            MinecraftForge.EVENT_BUS.register(eventhandler);
    
            // Ore generation
            MinecraftForge.ORE_GEN_BUS.register(eventhandler);
        }

        // Register biomes
        TcBiomes.register(conf);

        // Register the Tofu World
        DimensionManager.registerProviderType(Settings.tofuDimNo, WorldProviderTofu.class, false);
        DimensionManager.registerDimension(Settings.tofuDimNo, Settings.tofuDimNo);

        // Village and Villager Related
        TcVillages.register();

        // Register Packets
        PacketManager.init(modid);

        // Add chest loot
        this.registerChestLoot(new ItemStack(TcItems.defattingPotion), 1, 1, 8);
        this.registerChestLoot(new ItemStack(TcBlocks.tcSapling, 1, 0), 1, 4, 8);
        this.registerChestLoot(new ItemStack(TcItems.doubanjiang), 1, 1, 4);

        // Register recipes
        Recipes.unifyOreDicItems();
        Recipes.register();
        Recipes.registerExternalModRecipes();

        // CraftGuide
        if (Loader.isModLoaded("craftguide"))
        {
            CraftGuideLoader.load();
        }

        // Register sided components
        sidedProxy.registerComponents();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        TcEntity.addSpawns();

        // Register potion effects
        TcPotion.register(conf);
        // Register enchantments
        TcEnchantment.register(conf);

        conf.save();
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        // Register commands
        event.registerServerCommand(new CommandTofuSlimeCheck());
        event.registerServerCommand(new CommandTofuCreeperSpawn());

        // Initialize world save handler
        SaveHandler saveHandler = (SaveHandler)event.getServer().worldServerForDimension(0).getSaveHandler();
        this.saveHandler = new TcSaveHandler(saveHandler.getWorldDirectory());

        // Load Morijio info
        this.morijioManager = this.saveHandler.loadMorijioData();

        // To handle spawn of Tofu Creeper ;)
        TofuCreeperSeed.initialize(12L);
        TofuCreeperSeed.instance().initSeed(event.getServer().worldServerForDimension(0).getSeed());

        // Notify if update is available
        if (update != null && event.getSide() == Side.SERVER)
        {
            update.notifyUpdate(event.getServer(), event.getSide());
        }
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event)
    {
    }

    public void registerChestLoot(ItemStack loot, int min, int max, int rarity)
    {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
                new WeightedRandomChestContent(loot, min, max, rarity));
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR,
                new WeightedRandomChestContent(loot, min, max, rarity));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR,
                new WeightedRandomChestContent(loot, min, max, rarity));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING,
                new WeightedRandomChestContent(loot, min, max, rarity));
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY,
                new WeightedRandomChestContent(loot, min, max, rarity));
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST,
                new WeightedRandomChestContent(loot, min, max, rarity));
    }

    public static TcSaveHandler getSaveHandler()
    {
        return instance.saveHandler;
    }

    public static MorijioManager getMorijioManager()
    {
        return instance.morijioManager;
    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy implements ISidedProxy
    {
        @Override
        public void registerComponents()
        {
            MinecraftForge.EVENT_BUS.register(new TcTextures());
            MinecraftForge.EVENT_BUS.register(new GameScreenHandler());

            LoaderDecorationBlock.registerBlockRenderer();
            TcEntity.registerEntityRenderer();

            MinecraftForgeClient.registerItemRenderer(TcItems.zundaBow, (IItemRenderer)TcItems.zundaBow);
            MinecraftForgeClient.registerItemRenderer(TcItems.bugle, (IItemRenderer)TcItems.bugle);

            VillagerRegistry vill = VillagerRegistry.instance();
            vill.registerVillagerSkin(Settings.professionIdTofucook, new ResourceLocation("tofucraft", "textures/mob/tofucook.png"));
        }
    }

    @SideOnly(Side.SERVER)
    public static class ServerProxy implements ISidedProxy
    {
        @Override
        public void registerComponents()
        {
            GameRegistry.registerTileEntity(TileEntityMorijio.class, "TmMorijio");
        }
    }

    public static interface ISidedProxy
    {
        public void registerComponents();
    }
}
