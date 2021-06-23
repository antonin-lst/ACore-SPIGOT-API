package fr.acore.spigot.nms.manager;

import org.bukkit.ChatColor;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.Informable;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.nms.INetMinecraftServer;
import fr.acore.spigot.nms.utils.ServerVersion;
import fr.acore.spigot.nms.utils.ServerVersion.Version;

public class NMSManager implements IManager, Informable{
	
	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private INetMinecraftServer mapping;
	public INetMinecraftServer getNMS() { return this.mapping;}
	
	public NMSManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		loadMapping();
	}
	
	private void loadMapping() {
		Version version = ServerVersion.getVersion();
		try {
			mapping = (INetMinecraftServer) Class.forName(getClass().getName().replace("manager." + getClass().getSimpleName(), "") + "version.NMSMapping" + version.name().toUpperCase()).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void informe() {
		Version version = ServerVersion.getVersion();
		plugin.log(ChatColor.GOLD + "NMSVersion " + ChatColor.YELLOW + version.name());
		plugin.log(ChatColor.GOLD + "NMSPackage " + ChatColor.YELLOW + version.getShortVersion());
	}

	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}
}
