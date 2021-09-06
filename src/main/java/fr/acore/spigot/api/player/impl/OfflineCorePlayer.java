package fr.acore.spigot.api.player.impl;

import org.bukkit.OfflinePlayer;

import fr.acore.spigot.api.faction.IFactionPlayer;
import fr.acore.spigot.api.player.IPlayer;

public interface OfflineCorePlayer extends IPlayer<OfflinePlayer> {


	
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

	Gestion Authentification

	 */

	public String getEncryptedPassword();
	public void setEncryptedPassword(String password);

	public boolean isPremium();
	
	/*
	 * 
	 * check si le joueur est en ligne
	 */
	public boolean isOnline();

    String getPremiumUuid();
}
