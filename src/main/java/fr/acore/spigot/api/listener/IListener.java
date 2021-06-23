package fr.acore.spigot.api.listener;

import org.bukkit.event.Listener;

import fr.acore.spigot.api.manager.IManager;

public abstract class IListener<T extends IManager> implements Listener {

	protected T manager;
	
	public IListener(T manager) {
		this.manager = manager;
	}
	
	
	
}
