package fr.acore.spigot.api.config;

import org.bukkit.configuration.file.FileConfiguration;

import fr.acore.spigot.api.config.color.IColorHelper;

public interface ISetupable<T> extends IColorHelper, IStringHelper{
	
	public T getKey();
	public void setup(FileConfiguration config);
	

}
