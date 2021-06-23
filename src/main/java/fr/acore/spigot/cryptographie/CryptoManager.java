package fr.acore.spigot.cryptographie;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;

public class CryptoManager implements IManager{

	private ACoreSpigotAPI plugin;
	public ACoreSpigotAPI getPlugin() { return this.plugin;}
	
	private CryptoType usedAlgo;
	private int strenght;
	
	public CryptoManager(ACoreSpigotAPI plugin, CryptoType usedAlgo) {
		this(plugin, usedAlgo, 10);
	}
	
	public CryptoManager(ACoreSpigotAPI plugin, CryptoType usedAlgo, int strenght) {
		this.plugin = plugin;
		this.usedAlgo = usedAlgo;
		this.strenght = strenght;
	}
	
	public String hashString(String message) {
		switch (usedAlgo) {
		case BCRYPT:
			return BCrypt.hashpw(message, BCrypt.gensalt(strenght));
		}
		return message;
	}
	
	public boolean checkString(String message, String hashedMessage) {
		switch (usedAlgo) {
		case BCRYPT:
			return BCrypt.checkpw(message, hashedMessage);
		}
		return false;
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
