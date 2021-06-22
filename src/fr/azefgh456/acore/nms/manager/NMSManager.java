package fr.azefgh456.acore.nms.manager;

import org.bukkit.ChatColor;

import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.manager.utils.Informable;
import fr.azefgh456.acore.nms.INetMinecraftServer;
import fr.azefgh456.acore.nms.utils.ServerVersion;
import fr.azefgh456.acore.nms.utils.ServerVersion.Version;
import fr.azefgh456.acore.plugin.IPlugin;

public class NMSManager implements IManager, Informable{
	
	private IPlugin plugin;
	
	private INetMinecraftServer mapping;
	public INetMinecraftServer getNMS() { return this.mapping;}
	
	public NMSManager(IPlugin plugin) {
		this.plugin = plugin;
		loadMapping();
	}
	
	private void loadMapping() {
		Version version = ServerVersion.getVersion();
		try {
			mapping = (INetMinecraftServer) Class.forName("fr.azefgh456.acore.nms.version.NMSMapping" + version.name().toUpperCase()).getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logs() {
		Version version = ServerVersion.getVersion();
		plugin.log(ChatColor.GOLD + "NMSVersion " + ChatColor.YELLOW + version.name());
		plugin.log(ChatColor.GOLD + "NMSPackage " + ChatColor.YELLOW + version.getShortVersion());
	}
}
