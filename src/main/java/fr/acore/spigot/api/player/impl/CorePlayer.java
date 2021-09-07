package fr.acore.spigot.api.player.impl;

import java.util.List;

import fr.acore.spigot.api.nms.INMSPacket;
import fr.acore.spigot.player.online.board.ABoard;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import org.bukkit.entity.Player;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.command.ICommandCooldown;
import fr.acore.spigot.api.faction.IFactionPlayer;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.packet.IPacket;
import fr.acore.spigot.api.plugin.IPlugin;

public interface CorePlayer<T extends ICommandCooldown<?>> extends OfflineCorePlayer, IFactionPlayer{
	
	public static final IPlugin<IManager> plugin = ACoreSpigotAPI.getInstance();
	
	/*
	 * Instance du joueur
	 * 
	 */
	
	public Player getPlayer();
	public OfflineCorePlayer getOfflinePlayer();
	
	/*
	 * 
	 * Messaging
	 * 
	 */

	public void sendMessage(String message);
	
	public void sendMessage(String... message);
	
	public void sendMessage(Object... message);
	
	
	/*
	 * 
	 * Player Commands
	 * 
	 */

	public List<T> getCommandsCooldowns();
	
	
	
	/*
	 * NMSPlayer
	 * 
	 */
	
	public int getPing();
	
	public void sendPacket(INMSPacket<?> packet);
	
	public void sendTitle(String message);
	public void sendTitle(String message, int fadin, int delay, int fadout);
	public void sendSubTitle(String message);
	public void sendSubTitle(String message, int fadin, int delay, int fadout);
	public void sendTimesForTitle(int fadin, int delay, int fadout);
	public void clearTitle();
	
	/*
	 * 
	 * Netty Pipline (NMS)
	 * 
	 */
	public ChannelPipeline getPipeline();
	public Channel getChannel();
	

	/*

	Gestion du board

	 */

	public void initBoard(String name);

	public ABoard getBoard();

	/*
	 * HookPlayer
	 * 
	 */
	
	public double getBalance();
	public void removeMoney(double somme);
	public void removeMoney(int somme);
	public boolean removeMoneySafe(double somme);
	public boolean removeMoneySafe(int somme);
	public void addMoney(double somme);
	public abstract void addMoney(int somme);
	
	
	public OfflineCorePlayer setIFactionPlayer(IFactionPlayer faction);
	
	
	/*
	public void randomTeleport(Player player, int distance);
	public void randomTeleport(Player player, int distance, Location centerLocation);
	public void randomTeleport(Player player, World world, int distance);
	public void randomTeleport(Player player, World world, int distance, Location centerLocation);
	public void randomTeleport(Player player, World world, int distance, int cost);
	public void randomTeleport(Player player, World world, int distance, int cost, Location centerLocation);
	public void randomTeleport(Player player, World world, int distance, int cost, long delay);
	public void randomTeleport(Player player, World world, int distance, int cost, long delay, Location centerLocation);
	*/
	public abstract boolean isInRegion(String name);

	public abstract void sendPacketPlayOutEntityVelocity(int playerid, double x, double y, double z);

}
