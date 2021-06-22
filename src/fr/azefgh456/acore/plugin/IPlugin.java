package fr.azefgh456.acore.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.logger.utils.ILogger;
import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.menu.AMenu;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.player.players.HookedPlayer;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.IStorage;
import net.md_5.bungee.api.ChatColor;

public abstract class IPlugin extends JavaPlugin implements ILogger{
	
	private FileConfiguration config;
	public FileConfiguration getConfig() { return this.config;}
	private String serverName = new File("").getAbsoluteFile().getName();
	public String getServerName() { return this.serverName;}
	public File file = new File(getDataFolder() + File.separator + "config.yml");
	
	@Override
	public void onEnable() {
		reloadConfig(file);
	}

	
	/*
	 * 
	 * CONFIG
	 * 
	 */
	
	
	public void loadCustomConfig(File file) {
	    config = new YamlConfiguration();
	    try {
	        config.loadFromString (Files.toString (file, Charset.forName("UTF-8")));
	        log(ChatColor.YELLOW + getName() + ChatColor.GRAY + " config.yml charché avec succes");
	    } catch (IOException | InvalidConfigurationException e) {
	       log(ChatColor.YELLOW + getName() + ChatColor.RED + " Une erreur est survenue pendant le chargement du fichier config.yml");
	    }
	}
	
	public void reloadConfig(File file) {
		try {
			saveDefaultConfig();
		}catch(Exception ex) {
			
		}
		
		loadCustomConfig(file);
	}
	
	public abstract void registerManager(IManager manager);
	public abstract <T> T getManager(Class<? extends IManager> clazz);
	
	public abstract void registerSetupable(Setupable setupable);
	public abstract Data<?> registerData(Data<?> data);
	public abstract void registerCommand(String name, ICommand command);
	public abstract void registerForkCommand(String name, ICommand command);
	public abstract Listener registerListener(Listener listener);
	
	public abstract ARunnable registerSyncRunnable(ARunnable runnable);
	public abstract ARunnable registerAsyncRunnable(ARunnable runnable);
	
	public abstract IStorage getStorage();
	
	public abstract <T extends IHook> T getHook(String name);
	public abstract void registerHook(IHook hook);
	public abstract CorePlayer getCorePlayer(Player player);

	@Deprecated
	public abstract void callEvent(Event event);
	
	public abstract void checkLicencePlugin(String name);
	public abstract int getRegisteredSize();
	public abstract double getTPS();


	public abstract HookedPlayer getOfflinePlayer(OfflinePlayer player);
	
	
	public abstract void openMenu(Player player, AMenu menu);
	public abstract boolean playerHasMenuOpen(Player player);
	
}
