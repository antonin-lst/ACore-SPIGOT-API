package fr.acore.spigot.api.config.color;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public interface IColorHelper {
	
	public default String convertColor(String message) {
		if(message == null) { return null;}
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public default List<String> convertColor(List<String> data){
		if(data.isEmpty()) return new ArrayList<>();
		
		List<String> datamaped = new ArrayList<>();
		for(String d : data) {
			datamaped.add(convertColor(d));
		}
		return datamaped;
	}

}
