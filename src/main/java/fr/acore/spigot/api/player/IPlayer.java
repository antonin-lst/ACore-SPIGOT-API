package fr.acore.spigot.api.player;

import java.util.UUID;

public interface IPlayer<T> {

	public T getPlayer();
	
	public String getName();
	public UUID getUuid();
	
}
