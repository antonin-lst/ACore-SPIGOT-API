package fr.acore.spigot.api.packet;

import fr.acore.spigot.api.player.impl.CorePlayer;

public interface IPacket<T> {

	public T getPacket();
	
	public void sendPacket(CorePlayer<?> player);
	
	
}
