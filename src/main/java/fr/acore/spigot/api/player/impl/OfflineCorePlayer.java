package fr.acore.spigot.api.player.impl;

import org.bukkit.OfflinePlayer;

import fr.acore.spigot.api.faction.IFactionPlayer;
import fr.acore.spigot.api.player.IPlayer;

public interface OfflineCorePlayer extends IPlayer<OfflinePlayer> {

	
	/*
	 * 
	 * L'ensemble du OfflineCorePlayer est en read-only
	 * 
	 */
	
	/*
	 * 
	 * Gestion de l'ender chest
	 * 
	 */
	//public InventoryEnderChest getEnderChest();
	
	/*
	 * 
	 * Stats du joueur
	 * 
	 */
	
	public long getCurrentTimePlayed();
	public int getKills();
	public int getMorts();
	public void addDeath();
	public void addKill();
	public double getRatio();
	public String getFormatedRatio();
	
	/*
	 * 
	 * check si le joueur est en ligne
	 */
	public boolean isOnline();
}
