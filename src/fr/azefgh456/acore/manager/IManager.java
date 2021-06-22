package fr.azefgh456.acore.manager;

import org.bukkit.ChatColor;

public interface IManager{
	
	default public String logEnabled() {
		return getClass().getSimpleName() + ChatColor.YELLOW + " Enabled";
	}
}