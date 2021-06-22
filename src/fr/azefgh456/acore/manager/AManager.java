package fr.azefgh456.acore.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.logger.utils.ILogger;
import fr.azefgh456.acore.player.manager.PlayerManager;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.player.players.HookedPlayer;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.storage.requette.sync.IContainRequette;
import fr.azefgh456.acore.storage.requette.sync.IRequette;
import fr.azefgh456.acore.storage.utils.AsyncData;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;
import fr.azefgh456.acore.storage.utils.IStorage;

public abstract class AManager extends Setupable implements IManager, ILogger{

	private Data<?> data;
	private Listener listener;
	private List<ICommand> commands;
	
	private String moduleRacine;
	private String prefix;
	public String getPrefix() { return this.prefix;}
	private String version;
	public String getVersion() { return this.version;}
	
	private boolean useSetup;
	public boolean getUseSetup() { return this.useSetup;}
	
	public AManager(IPlugin plugin, boolean useSetup) {
		super(plugin, useSetup);
		this.useSetup = useSetup;
		moduleRacine = "";
		commands = new ArrayList<>();
	}
	
	public AManager(IPlugin plugin, String moduleRacine) {
		super(plugin, true);
		this.useSetup = true;
		this.moduleRacine = moduleRacine;
		commands = new ArrayList<>();
	}
	
	public void callEvent(Event event) {
		plugin.callEvent(event);
	}
	
	public Listener getListener() {
		return this.listener;
	}
	
	public <T> T registerListener(Listener listener) {
		this.listener = plugin.registerListener(listener);
		return (T) listener;
	}
	
	public Data<?> getData(){
		return data;
	}
	
	public void registerData(Data<?> data) {
		this.data = plugin.registerData(data);
	}
	
	public void registerCommand(String name, ICommand cmd) {
		plugin.registerCommand(name, cmd);
		if(commands == null) commands = new ArrayList<>();
		commands.add(cmd);
	}
	
	public void registerSyncRunnable(ARunnable runnable) {
		plugin.registerSyncRunnable(runnable);
	}
	
	public void registerAsyncRunnable(ARunnable runnable) {
		plugin.registerAsyncRunnable(runnable);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}
	
	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Deprecated
	public void logConsol(String... args) {
		plugin.log(args);
	}
	
	public IStorage getStorage() {
		return plugin.getStorage();
	}
	
	public boolean containRequette(IContainRequette requette) {
		return getStorage().executeBooleanRequette(requette);
	}
	
	public void simpleRequette(IRequette requette) {
		getStorage().executeSimpleRequette(requette);
	}
	
	public DataBuilder executeCustomTypeRequette(IRequette requette) {
		return getStorage().executeCustomTypeRequette(requette);
	}
	
	public void executeAsyncData(AsyncData<?> asyncPlayerData) {
		getStorage().executeSimpleAsyncRequette(asyncPlayerData);
	}
	
	@Override
	public void setup(FileConfiguration config) {
		if(!moduleRacine.isEmpty());
			setupFile(config, moduleRacine);
	}
	
	public void setupFile(FileConfiguration config, String moduleRacine) {
		prefix = config.getString(moduleRacine + ".prefix");
		version = config.getString(moduleRacine + ".version");
	}
	
	public CorePlayer getCorePlayer(Player player) {
		return plugin.getCorePlayer(player);
	}
	
	public HookedPlayer getOfflinePlayer(OfflinePlayer player) {
		return plugin.getOfflinePlayer(player);
	}
	
	public int getRegisteredSize() {
		return plugin.getRegisteredSize();
	}
	
	public <T> T getCommand(Class<?> clazz) {
		if(commands == null) commands = new ArrayList<>();
		for(ICommand cmd : commands) {
			if(cmd.getClass().equals(clazz)) return (T) cmd;
		}
		return null;
	}
	
	public List<CorePlayer> getCorePlayer(){
		return ((PlayerManager) plugin.getManager(PlayerManager.class)).getCorePlayer();
	}

	public String replaceAll(String string, Player player, Player target, Object... datas) {
		return "";
	}
	
}
