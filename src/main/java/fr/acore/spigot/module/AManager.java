package fr.acore.spigot.module;

import java.io.File;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import fr.acore.spigot.api.config.ISetupable;
import fr.acore.spigot.api.listener.IListener;
import fr.acore.spigot.api.manager.IManager;
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

public class AManager extends Setupable implements IManager, IModule {

	private IListener<? extends AManager> listener;
	
	private IModule module;
	
	public AManager(IModule key, boolean useConfig) {
		super(key, useConfig);
		this.module = key;
	}
	
	/*
	 * 
	 * Method Override si utilisation de la config
	 * 
	 */
	@Override
	public void setup(FileConfiguration config) {
		
	}
	
	/*
	 * 
	 * Method heritée de IManager
	 * 
	 */
	
	@Override
	public IPlugin<?> getPlugin() {
		return this.getKey();
	}
	
	/*
	 * 
	 * Extention du plugin enregistrée void AModule
	 * 
	 */
	
	@Override
	public String getServerName() {
		return key.getPluginName();
	}
	
	@Override
	public String getPluginName() {
		return key.getPluginName();
	}
	
	@Override
	public Version getPluginVersion() throws ParseVersionException {
		return key.getPluginVersion();
	}
	
	@Override
	public Version getApiVersion() throws ParseVersionException {
		return module.getApiVersion();
	}
	
	@Override
	public File getConfigFile() {
		return key.getConfigFile();
	}
	
	@Override
	public FileConfiguration getConfig() {
		return key.getConfig();
	}
	
	@Override
	public boolean isValidLicence() {
		return module.isValidLicence();
	}
	
	@Override
	public boolean isLicenceChecked() {
		return module.isLicenceChecked();
	}
	
	@Override
	public void setLicenceChecked() {
		logErr("Do not check licence with AManager");
	}
	
	@Override
	public void setValidLicence() {
		logErr("Do not set valid licence with AManager");
	}
	
	@Override
	public void loadCustomConfig() {
		key.loadCustomConfig();
	}
	
	@Override
	public void reloadConfig() {
		key.reloadConfig();
	}
	
	@Override
	public void registerManager(AManager manager) {
		module.registerManager(manager);
	}
	
	@Override
	public void unregisterManager(AManager manager) {
		module.unregisterManager(manager);
	}
	
	@Override
	public List<AManager> getInternalManagers() {
		return module.getInternalManagers();
	}
	
	@Override
	public <T extends IManager> T getInternalManager(Class<T> clazz) {
		return module.getInternalManager(clazz);
	}
	
	@Override
	public <T extends IManager> T getManager(Class<T> clazz) {
		return module.getManager(clazz);
	}
	
	@Override
	public void registerSetupable(ISetupable<IPlugin<?>> setupable) {
		key.registerSetupable(setupable);
	}
	
	@Override
	public void unregisterSetupable(ISetupable<IPlugin<?>> setupable) {
		key.unregisterSetupable(setupable);
	}
	
	@Override
	public Listener registerListener(Listener listener) {
		return key.registerListener(listener);
	}
	
	@Override
	public void unregisterAllListeners() {
		key.unregisterAllListeners();
	}
	
	@Override
	public void callEvent(Event event) {
		key.callEvent(event);
	}
	
	@Override
	public IRunnable registerSyncRunnable(IRunnable runnable) {
		return key.registerSyncRunnable(runnable);
	}
	
	@Override
	public IRunnable registerAsyncRunnable(RunnableUsage usage, IRunnable runnable) {
		return key.registerAsyncRunnable(usage, runnable);
	}
	
	@Override
	public List<OfflineCorePlayer> getOfflineCorePlayers() {
		return key.getOfflineCorePlayers();
	}
	
	@Override
	public List<CorePlayer<?>> getCorePlayer() {
		return key.getCorePlayer();
	}
	
	@Override
	public CorePlayer<?> getCorePlayer(Player player) {
		return key.getCorePlayer(player);
	}
	
	@Override
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer player) {
		return key.getOfflineCorePlayer(player);
	}
	
	@Override
	public void registerDataFactory(IDataFactory<?, ?> factory) {
		key.registerDataFactory(factory);
	}
	
	@Override
	public long getStartMillis() {
		return key.getStartMillis();
	}
	
	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		key.log(args);
	}

	@Override
	public void logWarn(String... args) {
		key.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		key.logErr(args);
	}

	@Override
	public void log(Object... args) {
		key.log(args);
	}

	@Override
	public void logWarn(Object... args) {
		key.logWarn(args);
	}

	@Override
	public void logErr(Object... args) {
		key.logErr(args);
	}

}
