package fr.azefgh456.acore.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import fr.azefgh456.acore.plugin.IPlugin;

public abstract class Setupable {

	protected IPlugin plugin;
	public IPlugin getPlugin() { return this.plugin;}
	
	public Setupable(IPlugin plugin, boolean log) {
		this.plugin = plugin;
		try {
			setup(plugin.getConfig());
			plugin.log("La configuration de la classe " + ChatColor.YELLOW + getClass().getSimpleName() + ChatColor.GREEN + " est chargée avec succes");
		}catch(Exception ex) {
			if(!log) return;
			
			plugin.logErr(ChatColor.GREEN + "Erreur de chargement de la configuration de la classe " + ChatColor.YELLOW + getClass().getSimpleName());
			if(ex.getMessage() != null && !ex.getMessage().isEmpty()) {
				plugin.logErr(ChatColor.YELLOW + "Error Message : " + ChatColor.RED + ex.getMessage());
			}else {
				ex.printStackTrace();
			}
		
		}
	}
	
	public String convertColor(String message) {
		if(message == null) { return null;}
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public List<String> convertColor(List<String> data){
		if(data.isEmpty()) return new ArrayList<>();
		
		List<String> datamaped = new ArrayList<>();
		for(String d : data) {
			datamaped.add(convertColor(d));
		}
		return datamaped;
	}
	
	public static String replace(String chaine, String regex, String data) {
		chaine = chaine.replace(regex, data);
		return chaine;
	}
	
	public abstract void setup(FileConfiguration config);
	
	
}
