package fr.acore.spigot.api.plugin;

import java.io.File;
import java.util.List;

import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.menu.IMenu;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import fr.acore.spigot.api.config.ISetupable;
import fr.acore.spigot.api.logger.ILogger;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.IManagerCollection;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.api.storage.factory.IDataFactory;
import fr.acore.spigot.api.version.Version;
import fr.acore.spigot.api.version.Version.ParseVersionException;

public interface IPlugin<T extends IManager> extends IManagerCollection<T>, ILogger{
	
	/*
	 * Plugin information (ServerName, PluginName, PluginVersion)
	 * 
	 */
	
	public String getServerName();
	public String getPluginName();
	public Version getPluginVersion() throws ParseVersionException;
	
	/*
	 * fileConfiguration (yaml format)
	 * 
	 */
	
	public File getConfigFile();
	public FileConfiguration getConfig();
	public void loadCustomConfig();
	public void reloadConfig();
	
	public long getStartMillis();
	
	/*
	 * 
	 * Gestion de la configuration
	 * 
	 */
	
	public void registerSetupable(ISetupable<IPlugin<?>> setupable);
	public void unregisterSetupable(ISetupable<IPlugin<?>> setupable);
	
	/*
	 * 
	 * gestion des listener
	 * 
	 */
	
	public Listener registerListener(Listener listener);
	public void unregisterAllListeners();
	public void callEvent(Event event);
	
	/*
	 * 
	 * Gestion des runnable
	 * 
	 */
	
	public IRunnable registerSyncRunnable(IRunnable runnable);
	public IRunnable registerAsyncRunnable(RunnableUsage usage, IRunnable runnable);


	/*
	 * 
	 * Gestion des DataFactory
	 * 
	 */
	
	public void registerDataFactory(IDataFactory<?,?> factory);
	
	
	/*
	 * 
	 * Gestion des joueurs
	 * 
	 * 
	 */
	
	public List<OfflineCorePlayer> getOfflineCorePlayers();
	public List<CorePlayer<?>> getCorePlayer();
	
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer player);
	public CorePlayer<?> getCorePlayer(Player player);

	/*

	Gestion des hooks

	 */

	public <T> void registerHook(IHook<T> hook) throws HookFailException;
	public <T extends IHook<?>> T getHook(Class<T> clazz);

	/*

	Gestion des menus

	 */

	public void openMenu(CorePlayer<?> corePlayer, IMenu menu);

	/*

	Gestion des Commandes

	 */

	public void registerCommand(ICommand<?> command);
	public void registerForkCommand(ICommand<?> command);

}
