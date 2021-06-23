package fr.acore.spigot.event.events.faction;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.acore.spigot.api.faction.IFactionPlayer;

public class AFactionCreateEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private String factionName;
	private String factionId;
	private IFactionPlayer player;
	private CommandSender sender;
	
	public AFactionCreateEvent(String factionName, String factionId, IFactionPlayer player, CommandSender sender) {
		this.factionName = factionName;
		this.factionId = factionId;
		this.player = player;
		this.sender = sender;
	}
	
	public String getFactionName() {
		return this.factionName;
	}
	
	public String getFactionId() {
		return this.factionId;
	}
	
	public IFactionPlayer getPlayer(){
		return this.player;
	}
	
	public CommandSender getCmdSender() {
		return this.sender;
	}

	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
