package fr.azefgh456.acore;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import fr.azefgh456.acore.commands.cmds.CommandReload;
import fr.azefgh456.acore.commands.cmds.RestartCommand;
import fr.azefgh456.acore.commands.manager.CommandManager;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.config.manager.ConfigManager;
import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.cryptographie.CryptoType;
import fr.azefgh456.acore.cryptographie.manager.CryptoManager;
import fr.azefgh456.acore.event.EventWrapper;
import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import fr.azefgh456.acore.hook.hooks.EssentialsHook;
import fr.azefgh456.acore.hook.manager.HookManager;
import fr.azefgh456.acore.jedis.config.JedisConfig;
import fr.azefgh456.acore.jedis.manager.JedisManager;
import fr.azefgh456.acore.logger.manager.LoggerManager;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.manager.utils.Informable;
import fr.azefgh456.acore.menu.AMenu;
import fr.azefgh456.acore.menu.manager.MenuManager;
import fr.azefgh456.acore.nms.manager.NMSManager;
import fr.azefgh456.acore.packets.manager.PacketsManager;
import fr.azefgh456.acore.player.manager.PlayerManager;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.player.players.HookedPlayer;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.plugin.manager.APluginManager;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.runnable.CoreRunnable;
import fr.azefgh456.acore.storage.json.JsonStorage;
import fr.azefgh456.acore.storage.json.manager.JsonManager;
import fr.azefgh456.acore.storage.manager.StorageManager;
import fr.azefgh456.acore.storage.sql.SqlStorage;
import fr.azefgh456.acore.storage.sql.manager.SqlManager;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.IStorage;
import fr.azefgh456.acore.storage.utils.StorageType;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class ACore extends IPlugin{

	public static ACore plugin;
	private Long startMillis;
	private List<IManager> managers;
	
	@Override
	public void onEnable() {
		startMillis = System.currentTimeMillis();
		plugin = this;
		managers = new ArrayList<>();
		registerManager(new LoggerManager(this));
		super.onEnable();
		registerManager(new ConfigManager(this));
		registerManager(new CoreRunnable(this));
		registerManager(new JedisManager(this, new JedisConfig(Conf.getJedisHost(), Conf.getJedisPort(), Conf.getJedisPassword())));
		registerManager(setStorage());
		registerManager(new NMSManager(this));
		registerManager(new HookManager(this)); 
		registerManager(new CryptoManager(this, CryptoType.BCRYPT));
		registerManager(new PlayerManager(this));
		registerManager(new CommandManager(this));
		registerManager(new EventWrapper(this));
		registerManager(new MenuManager(this));
		registerManager(new PacketsManager(this));
		registerManager(new APluginManager(this));
		registerCommand("reload", new CommandReload(getManager(ConfigManager.class)));
		registerForkCommand("reload", new RestartCommand());
		long enablingTime = System.currentTimeMillis() - startMillis;
		
		log("Enabled took : " + enablingTime + "ms");
		
	}
	
	@Override
	public void onDisable() {
		if(Conf.isUseStorage())
			((StorageManager<?>) getManager(StorageManager.class)).disabled();
		((CoreRunnable) getManager(CoreRunnable.class)).disable();
		((LoggerManager) getManager(LoggerManager.class)).disable();
		((JedisManager) getManager(JedisManager.class)).disable();
		super.onDisable();
	}
	
	@Override
	public double getTPS() {
		int tpsCount = 1;
		double tps = MinecraftServer.getServer().recentTps[0];
		
		EssentialsHook essHook = getHook("Essentials");
		if(essHook.hisHooked()) {
			tps += essHook.getEssentials().getTimer().getAverageTPS();
			tpsCount++;
		}
		
		//tps += ((CoreRunnable) getManager(CoreRunnable.class)).getTps();
		//tpsCount++;
		return tps/tpsCount;
	}
	
	/*
	 * APlugin / Manager
	 * 
	 * 
	 */
	
	@Override
	public void checkLicencePlugin(String name) {
		((APluginManager) getManager(APluginManager.class)).checkPlugin(name);
	}
	
	@Override
	public <T> T getManager(Class<? extends IManager> clazz){
		for(IManager m : managers) {
			if(m.getClass().equals(clazz)) return (T) m;
		}
		return null;
	}
	
	@Override
	public void registerManager(IManager manager) {
		if(manager == null) { return;}
		
		if(manager instanceof AManager && ((AManager) manager).getUseSetup()) registerSetupable((AManager)manager);
		
		if(manager instanceof Informable) {
			((Informable) manager).logs();
		}
		managers.add(manager);
		log(manager.logEnabled());
	}
	
	public void registerManager(IManager... managers) {
		for(IManager manager : managers) {
			registerManager(manager);
		}
	}
	
	
	/*
	 * Register
	 * 
	 * 
	 */
	
	
	@Override
	public void registerSetupable(Setupable setupable) {
		((ConfigManager) getManager(ConfigManager.class)).addSetupable(setupable);
	}
	
	@Override
	public void registerCommand(String name, ICommand command) {
		registerCommand(this, name, command);
	}
	
	public void registerCommand(IPlugin plugin, String name, ICommand command) {
		plugin.getCommand(name).setExecutor(((CommandManager) getManager(CommandManager.class)).addCommand(command.setName(name)));
	}
	
	@Override
	public Data<?> registerData(Data<?> data) {
		((StorageManager<?>) getManager(StorageManager.class)).addData(data);
		return data;
	}
	
	@Override
	public Listener registerListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
		return listener;
	}
	@Override
	public ARunnable registerSyncRunnable(ARunnable runnable) {
		((CoreRunnable) getManager(CoreRunnable.class)).addTickable(runnable);
		return runnable;
	}
	@Override
	public ARunnable registerAsyncRunnable(ARunnable runnable) {
		((CoreRunnable) getManager(CoreRunnable.class)).addAsyncTickable(runnable);
		return runnable;
	}
	
	public void registerForkCommand(String name, ICommand command) {
		((CommandManager) getManager(CommandManager.class)).addForkCommand(command.setName(name));
	}
	
	
	/*
	 * 
	 * Logger
	 * 
	 */
	
	
	@Override
	public void log(String... args) {
		((LoggerManager) getManager(LoggerManager.class)).log(args);
	}

	@Override
	public void logWarn(String... args) {
		((LoggerManager) getManager(LoggerManager.class)).logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		((LoggerManager) getManager(LoggerManager.class)).logErr(args);
	}

	@Override
	public void log(Object... args) {
		((LoggerManager) getManager(LoggerManager.class)).log(args);
	}

	@Override
	public void logWarn(Object... args) {
		((LoggerManager) getManager(LoggerManager.class)).logWarn(args);
	}

	@Override
	public void logErr(Object... args) {
		((LoggerManager) getManager(LoggerManager.class)).logErr(args);
	}


	/*
	 * 
	 * StorageManager
	 * 
	 */
	
	@Override
	public IStorage getStorage() {
		return ((StorageManager<? extends IStorage>) getManager(StorageManager.class)).getStorage();
	}

	public IManager setStorage() {
		StorageManager<? extends IStorage> storageManager = getManager(StorageManager.class);
		
		if(storageManager != null && storageManager.getStorage() instanceof SqlStorage && ((SqlStorage)storageManager.getStorage()).getConnection() != null) {
			((SqlManager) storageManager.getStorage().getManager()).disconnect();
			managers.remove(storageManager);
		}
		
		if(!Conf.isUseStorage()) {
			logWarn("Storage Disabled");
			return null;
		}
		
		if(Conf.getStorageType().equals(StorageType.SQL)) {
			storageManager = new StorageManager<SqlStorage>(this, new SqlStorage(this));
		}else {
			storageManager = new StorageManager<JsonStorage>(this, new JsonStorage(new JsonManager(this)));
		}
		return storageManager;
	}
	
	/*
	 * 
	 * Hooks
	 * 
	 */

	@Override
	public <T extends IHook> T getHook(String name) {
		return (T) ((HookManager) getManager(HookManager.class)).getHook(name);
	}

	@Override
	public void registerHook(IHook hook) {
		HookManager hookM = getManager(HookManager.class);
		try {
			hookM.registerHook(hook);
		} catch (NullPointerException | PluginNotFoundException e) {
			
		}
	}
	
	/*
	 * 
	 * CorePlayer 
	 * (LoginPlayer/HookedPlayer/NMSPlayer/AbstractPlayer)
	 * 
	 */
	
	@Override
	public CorePlayer getCorePlayer(Player player) {
		return ((PlayerManager) getManager(PlayerManager.class)).getCorePlayer(player);
	}
	
	@Override
	public HookedPlayer getOfflinePlayer(OfflinePlayer player) {
		return ((PlayerManager) getManager(PlayerManager.class)).getOfflinePlayer(player);
	}
	
	@Override
	public int getRegisteredSize() {
		return ((PlayerManager) getManager(PlayerManager.class)).getRegisteredPlayerCount();
	}
	
	/*
	 * 
	 * EventWrapper
	 * this methods is deprecated 
	 * use the static method
	 * 
	 */

	@Deprecated
	@Override
	public void callEvent(Event event) {
		EventWrapper.callEvent(this, event);
	}
	
	
	
	@Override
	public void openMenu(Player player, AMenu menu) {
		((MenuManager) getManager(MenuManager.class)).openMenu(player, menu);
	}
	
	@Override
	public boolean playerHasMenuOpen(Player player) {
		return ((MenuManager) getManager(MenuManager.class)).containPlayer(player);
	}
	
	

}