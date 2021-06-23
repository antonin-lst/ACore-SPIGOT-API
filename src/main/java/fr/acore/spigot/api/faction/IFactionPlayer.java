package fr.acore.spigot.api.faction;

import fr.acore.spigot.api.player.impl.CorePlayer;

public interface IFactionPlayer {

public IFaction getFaction();
	
	public double getPower();
	
	public String getChatTag(CorePlayer<?> player);
	
	public String getFactionGrade();
	
}
