package fr.acore.spigot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.menu.IMenu;
import fr.acore.spigot.commands.cmds.CommandReload;
import fr.acore.spigot.commands.manager.CommandManager;
import fr.acore.spigot.jedis.manager.RedisManager;
import fr.acore.spigot.jedis.packet.impl.server.InitServerPacket;
import fr.acore.spigot.jedis.packet.impl.server.StopServerPacket;
import fr.acore.spigot.jedis.packet.impl.server.UpdateServerPacket;
import fr.acore.spigot.menu.manager.MenuManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import fr.acore.spigot.api.config.ISetupable;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.Informable;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.api.storage.database.DBUser;
import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.database.driver.DatabaseDriver;
import fr.acore.spigot.api.storage.exception.DBNotFoundException;
import fr.acore.spigot.api.storage.exception.schema.SchemaNotFounException;
import fr.acore.spigot.api.storage.factory.IDataFactory;
import fr.acore.spigot.api.version.Version;
import fr.acore.spigot.api.version.Version.ParseVersionException;
import fr.acore.spigot.config.Setupable;
import fr.acore.spigot.config.manager.ConfigManager;
import fr.acore.spigot.config.utils.Conf;
import fr.acore.spigot.cryptographie.CryptoManager;
import fr.acore.spigot.cryptographie.CryptoType;
import fr.acore.spigot.event.manager.EventWrapperManager;
import fr.acore.spigot.hook.manager.HookManager;
import fr.acore.spigot.logger.LoggerManager;
import fr.acore.spigot.module.manager.AModuleManager;
import fr.acore.spigot.nms.manager.NMSManager;
import fr.acore.spigot.packet.manager.PacketsManager;
import fr.acore.spigot.player.manager.PlayerManager;
import fr.acore.spigot.runnable.manager.RunnableManager;
import fr.acore.spigot.storage.StorageManager;
import fr.acore.spigot.storage.database.MySqlDatabase;
import net.md_5.bungee.api.ChatColor;

public class ACoreSpigotAPI extends JavaPlugin implements IPlugin<IManager>{

	/*
	 * 
	 * Instance ACoreSpigotAPI
	 * 
	 */
	
	private static ACoreSpigotAPI instance;
	public static ACoreSpigotAPI getInstance() { return instance;}
	
	/*
	 * Plugin information (ServerName, PluginName, PluginVersion)
	 * 
	 */
	
	public String getServerName() { return new File("").getAbsoluteFile().getName();}
	public String getPluginName() { return getClass().getSimpleName();}
	public Version getPluginVersion() throws ParseVersionException { return Version.fromString(getDescription().getVersion());}
	
	/*
	 * fileConfiguration (yaml format)
	 * 
	 */
	
	private File configFile = new File(getDataFolder() + File.separator + "config.yml");
	public File getConfigFile() { return this.configFile;}
	private FileConfiguration config;
	public FileConfiguration getConfig() { return this.config;}
	
	/*
	 * IManager list par module
	 * 
	 */
	private List<IManager> managers;
	

	/*

	Gestion de la précence du ACoreMain

	 */

	private static boolean acoreMainPresence;
	public static boolean getAcoreMainPresence(){ return acoreMainPresence;}
	public void setAcoreMainPresence(){ acoreMainPresence = true;}


	/*
	 * gestion du temps de lancement du ACore
	 * 
	 */
	
	protected Long startMillis;
	@Override
	public long getStartMillis() {return this.startMillis;}
	
	
	/*
	 * 
	 * Methods appel�e quand le plugin change d'etat herit�e de JavaPlugin
	 * 
	 * 
	 */
	
	@Override
	public void onEnable() {
		//instance de l'api
		instance = this;
		//timestamp du demarage
		startMillis = System.currentTimeMillis();
		//initialisation des managers
		managers = new ArrayList<>();
		//registration du Logger
		registerManager(new LoggerManager(this));
		//chargement du fichier de configuration (config.yml)
		reloadConfig();
		//registration de la configuration
		registerManager(new ConfigManager(this));
		//registration du systeme de packet Redis
		RedisManager redisManager;
		registerManager(redisManager = new RedisManager(this));
		//registration des packets //id 1 est pour le packet de test
		//packets serveurs
		redisManager.getPacketFactory().addPacket(2, InitServerPacket.class);
		redisManager.syncCheckACoreMainPresence();
		redisManager.getPacketFactory().addPacket(3, StopServerPacket.class);
		redisManager.getPacketFactory().addPacket(4, UpdateServerPacket.class);


		//registration du syteme de Task
		registerManager(new RunnableManager(this));
		//registration wrapper des nouveaux event
		registerManager(new EventWrapperManager(this));
		//mise en route du printter asynchrone (utilise RunnableManager & EventWrapperManager)
		getInternalManager(LoggerManager.class).enableAsyncPrintter();

		try {
			//création du storage en fonction de la configuration (config.yml) !! support actuelle MYSQL !!
			IDatabase<?> database = Conf.getStorageType().equals(DatabaseDriver.MYSQL) ? new MySqlDatabase("maindb", new DBUser(Conf.getUser(), Conf.getPass()), Conf.getHost()) : null;
			StorageManager storageM = new StorageManager(this, database);
			storageM.load();
			
			//registration du storage
			registerManager(storageM);
			
			//registration de la base de donnée par default
			storageM.setDefaultDatabase("maindb");
			
			//registration du schema en configuration
			storageM.getDefaultDatabase().addSchema(Conf.getDatabase());
			storageM.getDefaultDatabase().setDefaultSchema(Conf.getDatabase());
		} catch (DBNotFoundException | SchemaNotFounException e) {e.printStackTrace();}
		
		//registration du module de hashing
		registerManager(new CryptoManager(this, CryptoType.BCRYPT));
		//register du systeme de mapping NMS
		registerManager(new NMSManager(this));
		//register du systeme de fork de plugin, manager et serviceProvider
		registerManager(new HookManager(this));
		//registration du systeme de joueur
		registerManager(new PlayerManager(this));
		//registration du systeme de packet post player car utilise les joueurs
		registerManager(new PacketsManager(this));
		//registration du systeme de gestion des menus
		registerManager(new MenuManager(this));
		//registration du systeme de gestion des modules
		registerManager(new AModuleManager(this));

		registerCommand(new CommandReload(this));
		//registerForkCommand("reload", new RestartCommand());

		long enablingTime = System.currentTimeMillis() - startMillis;

		log("Enabled took : " + enablingTime + "ms");
	}
	
	@Override
	public void onDisable() {
		
		/*
		 * Save and disable Storage
		 * 
		 */
		getInternalManager(StorageManager.class).save();
		
		/*
		 * 
		 * Save other close packet ... 
		 * 
		 */
		
	}
	
	
	/*
	 * Gestion configuration en UTF-8 (load,save,reload)
	 * 
	 */
	
	public void loadCustomConfig() {
	    config = new YamlConfiguration();
	    try {
	        config.loadFromString(Files.toString (configFile, Charset.forName("UTF-8")));
	        log(ChatColor.YELLOW + getName() + ChatColor.GRAY + " config.yml chargée avec succes");
	    } catch (IOException | InvalidConfigurationException e) {
	       log(ChatColor.YELLOW + getName() + ChatColor.RED + " Une erreur est survenue pendant le chargement du fichier config.yml");
	    }
	}
	
	public void reloadConfig() {
		try {
			saveDefaultConfig();
		}catch(Exception ex) {}
		loadCustomConfig();
	}
	
	@Override
	public void registerSetupable(ISetupable<IPlugin<?>> setupable) {
		getInternalManager(ConfigManager.class).addSetupable(setupable);
	}
	
	@Override
	public void unregisterSetupable(ISetupable<IPlugin<?>> setupable) {
		getInternalManager(ConfigManager.class).removeSetupable(setupable);
	}
	
	
	/*
	 * 
	 * Gestion des managers internes au ACoreSpigotAPI
	 * 
	 * 
	 */
	
	@Override
	public void registerManager(IManager manager) {
		if(manager == null) { return;}
		
		if(manager instanceof Setupable && ((Setupable) manager).getUseConfig()) registerSetupable((Setupable) manager);
		
		if(manager instanceof Informable) {
			((Informable) manager).informe();
		}
		
		this.managers.add(manager);
		log(manager.logEnabled());
	}

	@Override
	public void unregisterManager(IManager manager) {
		this.managers.remove(manager);
	}
	
	@Override
	public List<IManager> getInternalManagers() {
		return this.managers;
	}

	@Override
	public <T extends IManager> T getInternalManager(Class<T> clazz) {
		for(IManager manager : managers) {
			if(manager.getClass().equals(clazz)) return (T) manager;
		}
		return null;
	}
	
	
	/*
	 * 
	 * Gestion des Managers interne et externe
	 * 
	 */
	
	@Override
	public <T extends IManager> T getManager(Class<T> clazz) {
		IManager manager = getInternalManager(clazz);
		if(manager == null) {
			log("Trying to found manager on module");
			manager = getInternalManager(AModuleManager.class).getInModulesManager(clazz);
		}
		return (T) manager;
	}
	
	
	/*
	 * 
	 * Event / Listener
	 * 
	 */
	
	public Listener registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
		return listener;
	}
	
	public void unregisterAllListeners() {
		HandlerList.unregisterAll(this);
	}
	
	public void callEvent(Event event) {
		getInternalManager(EventWrapperManager.class).call(event);
	}

	
	/*
	 * 
	 * Gestion des runnable
	 * 
	 */
	
	@Override
	public IRunnable registerSyncRunnable(IRunnable runnable) {
		getInternalManager(RunnableManager.class).addTickable(runnable);
		return runnable;
	}
	
	@Override
	public IRunnable registerAsyncRunnable(RunnableUsage usage, IRunnable runnable) {
		getInternalManager(RunnableManager.class).addAsyncTickable(usage, runnable);
		return runnable;
	}
	
	/*
	 * 
	 * Gestion des joueurs
	 * 
	 */
	
	@Override
	public List<OfflineCorePlayer> getOfflineCorePlayers() {
		return getInternalManager(PlayerManager.class).getOfflineCorePlayers();
	}
	
	@Override
	public List<CorePlayer<?>> getCorePlayer() {
		return getInternalManager(PlayerManager.class).getOnlineCorePlayers();
	}
	
	@Override
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer player) {
		return getInternalManager(PlayerManager.class).getOfflineCorePlayer(player);
	}
	
	@Override
	public CorePlayer<?> getCorePlayer(Player player) {
		return getInternalManager(PlayerManager.class).getOnlinePlayer(player);
	}

	/*
	 * 
	 * gestion des factories
	 * 
	 */
	@Override
	public void registerDataFactory(IDataFactory<?, ?> factory) {
		getInternalManager(StorageManager.class).addDataFactory(factory);
	}

	/*

	Gestion des hooks

	 */

	@Override
	public <T> void registerHook(IHook<T> hook) throws HookFailException {
		getInternalManager(HookManager.class).registerHook(hook);
	}

	@Override
	public <T extends IHook<?>> T getHook(Class<T> clazz) {
		return getInternalManager(HookManager.class).getHook(clazz);
	}

	/*

	Gestion des menus

	 */

	@Override
	public void openMenu(CorePlayer<?> corePlayer, IMenu menu) {
		getInternalManager(MenuManager.class).openMenu(corePlayer, menu);
	}

	/*

	Gestion des commandes

	 */

	@Override
	public void registerCommand(ICommand command) {
		registerCommand(this, command);
	}

	public void registerCommand(IPlugin plugin, ICommand command) {
		CommandManager commandManager = getManager(CommandManager.class);
		commandManager.addCommand(command);
		((JavaPlugin) plugin).getCommand(command.getName()).setExecutor(commandManager);
	}

	/*
	 * 
	 * Gestion des logs
	 * 
	 */

	@Override
	public void log(String... args) {
		getInternalManager(LoggerManager.class).log(args);
	}
	
	@Override
	public void log(Object... args) {
		getInternalManager(LoggerManager.class).log(args);
	}


	@Override
	public void logWarn(String... args) {
		getInternalManager(LoggerManager.class).logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		getInternalManager(LoggerManager.class).logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		getInternalManager(LoggerManager.class).logErr(args);
	}


	@Override
	public void logErr(Object... args) {
		getInternalManager(LoggerManager.class).logErr(args);
	}
	
}