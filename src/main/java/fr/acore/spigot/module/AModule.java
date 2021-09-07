package fr.acore.spigot.module;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.menu.IMenu;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.config.ISetupable;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.Informable;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.plugin.module.IModule;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.api.storage.factory.IDataFactory;
import fr.acore.spigot.api.version.Version;
import fr.acore.spigot.api.version.Version.ParseVersionException;
import fr.acore.spigot.config.Setupable;
import fr.acore.spigot.module.manager.AModuleManager;
import net.md_5.bungee.api.ChatColor;

public class AModule extends JavaPlugin implements IModule{

	/*
	 * 
	 * Instance de l'api
	 * 
	 */
	private ACoreSpigotAPI instance;
	
	/*
	 * 
	 * Gestion de la licence par module
	 * 
	 */
	
	private boolean licenceChecked;
	private boolean licenceValid;
	
	/*
	 * Plugin information (ServerName, PluginName, PluginVersion)
	 * 
	 */
	
	public String getServerName() { return new File("").getAbsoluteFile().getName();}
	public String getPluginName() { return getClass().getSimpleName();}
	public Version getPluginVersion() throws ParseVersionException { return Version.fromString(getDescription().getVersion());}
	public Version getApiVersion() throws ParseVersionException { return instance.getPluginVersion();}

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
	private List<AManager> managers;
	
	
	
	@Override
	public void onEnable() {
		this.instance = ACoreSpigotAPI.getInstance();
		this.managers = new ArrayList<>();
		reloadConfig();
		getManager(AModuleManager.class).addModule(this);
	}
	
	
	@Override
	public void onDisable() {
		
	}
	
	/*
	 * 
	 * Gestion de l'etat de la licence
	 * 
	 */
	
	@Override
	public boolean isValidLicence() {
		return this.licenceValid;
	}
	
	@Override
	public boolean isLicenceChecked() {
		return this.licenceChecked;
	}

	@Override
	public void setLicenceChecked() {
		this.licenceChecked = true;
	}
	
	@Override
	public void setValidLicence() {
		this.licenceValid = true;
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
	
	
	/*
	 * 
	 * Gestion des managers interne au Module
	 * 
	 * 
	 */
	
	@Override
	public void registerManager(AManager manager) {
		if(manager == null) { return;}

		if(manager instanceof Setupable && ((Setupable) manager).getUseConfig()) registerSetupable((Setupable) manager);
		
		if(manager instanceof Informable) {
			((Informable) manager).informe();
		}
		
		this.managers.add(manager);
		log(manager.logEnabled());
	}

	@Override
	public void unregisterManager(AManager manager) {
		this.managers.remove(manager);
	}
	
	@Override
	public List<AManager> getInternalManagers() {
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
	 * Method explicite pour les modules qui utilise les composents de l'api
	 * 
	 */

	@Override
	public <T extends IManager> T getManager(Class<T> clazz) {
		return instance.getManager(clazz);
	}

	@Override
	public void registerSetupable(ISetupable<IPlugin<?>> setupable) {
		instance.registerSetupable(setupable);
	}

	@Override
	public void unregisterSetupable(ISetupable<IPlugin<?>> setupable) {
		instance.unregisterSetupable(setupable);
	}

	@Override
	public Listener registerListener(Listener listener) {
		return instance.registerListener(listener);
	}

	@Override
	public void unregisterAllListeners() {
		instance.unregisterAllListeners();
	}

	@Override
	public void callEvent(Event event) {
		instance.callEvent(event);
	}

	@Override
	public IRunnable registerSyncRunnable(IRunnable runnable) {
		return instance.registerSyncRunnable(runnable);
	}

	@Override
	public IRunnable registerAsyncRunnable(RunnableUsage usage, IRunnable runnable) {
		return instance.registerAsyncRunnable(usage, runnable);
	}
	
	@Override
	public List<OfflineCorePlayer> getOfflineCorePlayers() {
		return instance.getOfflineCorePlayers();
	}
	
	@Override
	public List<CorePlayer<?>> getCorePlayer() {
		return instance.getCorePlayer();
	}
	
	@Override
	public CorePlayer<?> getCorePlayer(Player player) {
		return instance.getCorePlayer(player);
	}
	
	@Override
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer player) {
		return instance.getOfflineCorePlayer(player);
	}

	@Override
	public void registerDataFactory(IDataFactory<?, ?> factory) {
		instance.registerDataFactory(factory);
	}

	@Override
	public <T> void registerHook(IHook<T> hook) throws HookFailException {
		instance.registerHook(hook);
	}

	@Override
	public <T extends IHook<?>> T getHook(Class<T> clazz) {
		return instance.getHook(clazz);
	}

	@Override
	public void openMenu(CorePlayer<?> corePlayer, IMenu menu) {
		instance.openMenu(corePlayer, menu);
	}

	@Override
	public void registerCommand(ICommand command) {
		instance.registerCommand(this, command);
	}

	@Override
	public void registerForkCommand(ICommand<?> command) {
		instance.registerForkCommand(command);
	}

	/*
	 * 
	 * Gestion des logs
	 * 
	 */

	@Override
	public void log(String... args) {
		instance.log(args);
	}

	@Override
	public void logWarn(String... args) {
		instance.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		instance.logErr(args);
	}

	@Override
	public void log(Object... args) {
		instance.log(args);
	}

	@Override
	public void logWarn(Object... args) {
		instance.logWarn(args);
	}

	@Override
	public void logErr(Object... args) {
		instance.logErr(args);
	}
	
	@Override
	public long getStartMillis() {
		return 0l;
	}

}
