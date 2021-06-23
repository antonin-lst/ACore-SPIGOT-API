package fr.acore.spigot.api.manager;

import org.bukkit.ChatColor;

import fr.acore.spigot.api.logger.ILogger;
import fr.acore.spigot.api.plugin.IPlugin;

public interface IManager extends ILogger {

	default public String logEnabled() {
		return getClass().getSimpleName() + ChatColor.YELLOW + " Enabled";
	}
	
	public IPlugin<?> getPlugin();
	
}
