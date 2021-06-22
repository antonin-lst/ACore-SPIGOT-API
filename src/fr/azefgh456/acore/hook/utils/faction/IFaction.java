package fr.azefgh456.acore.hook.utils.faction;

import java.util.List;

import org.bukkit.ChatColor;

public interface IFaction<V, W>{

	public V getFaction();
	public String getFactionName();
	public String getFactionId();
	public int getFactionSize();
	public double getFactionPower();
	public double getFactionMaxPower();
	public int getFactionLandCount();
	public double getFactionBalance();
	public int getFactionKills();
	public int getFactionDeaths();
	public List<IPlayer<V,W>> getFactionPlayers();
	
	public void sendMessage(String message);
	public ChatColor getRelationColor(IFaction<?, ?> target);
	
	public int getKothWin();
	public int getTotemWin();
	//public List<ABastion> getCappedBastion();
}
