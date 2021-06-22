package fr.azefgh456.acore.hook.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.azefgh456.acore.hook.utils.faction.IPlayer;

public class AFactionCreateEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	private String factionName;
	private String factionId;
	private IPlayer<?,?> player;
	private CommandSender sender;
	
	public AFactionCreateEvent(String factionName, String factionId, IPlayer<?,?> player, CommandSender sender) {
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
	
	public IPlayer<?,?> getPlayer(){
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
