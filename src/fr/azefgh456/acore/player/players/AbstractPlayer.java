package fr.azefgh456.acore.player.players;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.packets.utils.APacket;

public abstract class AbstractPlayer {

	protected ACore plugin;
	
	protected OfflinePlayer player;
	public Player getPlayer() { return (Player)this.player;}
	
	
	public AbstractPlayer(OfflinePlayer player) {
		plugin = ACore.plugin;
		this.player = player;
	}

	public void sendMessage(String message) {
		getPlayer().sendMessage(message);
	}
	
	public void sendMessage(String... message) {
		getPlayer().sendMessage(message);
	}
	
	public void sendMessage(Object... message) {
		for(Object obj : message) getPlayer().sendMessage((String)obj);
	}
	
	

	
	
	/*
	 * NMSPlayer
	 * 
	 */
	
	public abstract int getPing();
	
	public abstract void sendPacket(APacket<?> packet);
	
	public abstract void sendTitle(String message);
	public abstract void sendTitle(String message, int fadin, int delay, int fadout);
	public abstract void sendSubTitle(String message);
	public abstract void sendSubTitle(String message, int fadin, int delay, int fadout);
	public abstract void sendTimesForTitle(int fadin, int delay, int fadout);
	public abstract void clearTitle();
	
	/*
	 * HookPlayer
	 * 
	 */
	
	public abstract double getBalance();
	public abstract void removeMoney(double somme);
	public abstract void removeMoney(int somme);
	public abstract boolean removeMoneySafe(double somme);
	public abstract boolean removeMoneySafe(int somme);
	public abstract void addMoney(double somme);
	public abstract void addMoney(int somme);
	
	public abstract void randomTeleport(Player player, int distance);
	public abstract void randomTeleport(Player player, int distance, Location centerLocation);
	public abstract void randomTeleport(Player player, World world, int distance);
	public abstract void randomTeleport(Player player, World world, int distance, Location centerLocation);
	public abstract void randomTeleport(Player player, World world, int distance, int cost);
	public abstract void randomTeleport(Player player, World world, int distance, int cost, Location centerLocation);
	public abstract void randomTeleport(Player player, World world, int distance, int cost, long delay);
	public abstract void randomTeleport(Player player, World world, int distance, int cost, long delay, Location centerLocation);
	
	public abstract boolean isInRegion(String name);

	public abstract void sendPacketPlayOutEntityVelocity(int playerid, double x, double y, double z);

}
