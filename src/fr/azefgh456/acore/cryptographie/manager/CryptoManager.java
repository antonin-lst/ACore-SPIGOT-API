package fr.azefgh456.acore.cryptographie.manager;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.cryptographie.CryptoType;
import fr.azefgh456.acore.cryptographie.bcript.BCrypt;
import fr.azefgh456.acore.manager.IManager;

public class CryptoManager implements IManager{

	private ACore plugin;
	public ACore getPlugin() { return this.plugin;}
	
	private CryptoType usedAlgo;
	private int strenght;
	
	public CryptoManager(ACore plugin, CryptoType usedAlgo) {
		this(plugin, usedAlgo, 10);
	}
	
	public CryptoManager(ACore plugin, CryptoType usedAlgo, int strenght) {
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
	
	
}
