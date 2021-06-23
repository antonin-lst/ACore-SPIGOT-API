package fr.acore.spigot.event.events.faction;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.acore.spigot.api.faction.IFaction;

public class AFactionNameChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private IFaction faction;
	private String newFactionName;
	
	public AFactionNameChangeEvent(IFaction faction, String newFactionName) {
		this.faction = faction;
		this.newFactionName = newFactionName;
	}
	
	public IFaction getFaction(){
		return this.faction;
	}

	public String getNewFactionName() {
		return this.newFactionName;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
