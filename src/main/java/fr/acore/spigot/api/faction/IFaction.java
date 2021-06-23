package fr.acore.spigot.api.faction;

import java.util.List;

import org.bukkit.ChatColor;

public interface IFaction {
	
	public String getFactionName();
	public String getFactionId();
	public int getFactionSize();
	public double getFactionPower();
	public double getFactionMaxPower();
	public int getFactionLandCount();
	public double getFactionBalance();
	public int getFactionKills();
	public int getFactionDeaths();
	public List<IFactionPlayer> getFactionPlayers();
	
	public void sendMessage(String message);
	public ChatColor getRelationColor(IFaction target);
	

}
