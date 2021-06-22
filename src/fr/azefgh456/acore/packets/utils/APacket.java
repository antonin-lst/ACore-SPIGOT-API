package fr.azefgh456.acore.packets.utils;

import org.bukkit.entity.Player;

public abstract class APacket<T> {
	
	private T packet;
	
	public APacket(T packet) {
		this.packet = packet;
	}
	
	public T getPacket() {
		return packet;
	}
	
	public abstract void sendPacket(Player player);

}
