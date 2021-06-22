package fr.azefgh456.acore.hook.utils.faction;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.hook.exception.UnsuportedVersionException;

public interface IPlayer<V, W> {

	public IFaction<V,W> getIFaction();
	
	public double getPower();
	
	public double getBalance();
	
	public int getKills() throws UnsuportedVersionException;
	
	public int getDeaths() throws UnsuportedVersionException;
	
	public String getChatTag(IPlayer<V, W> player) throws UnsuportedVersionException;
	
	public W getFPlayer();
	public Player getPlayer();
	
	public String getFactionGrade();
}
