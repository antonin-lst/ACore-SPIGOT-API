package fr.acore.spigot.event.events.player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.acore.spigot.api.player.impl.CorePlayer;

	
public class PlayerKillPlayerEvent extends Event{
		
	private static final HandlerList handlers = new HandlerList();
	
	private CorePlayer<?> target;
	private CorePlayer<?> damager;
	
	public PlayerKillPlayerEvent(CorePlayer<?> target, CorePlayer<?> damager) {
		this.target = target;
		this.damager = damager;
	}
	
	public CorePlayer<?> getTarget() {
		return this.target;
	}
	
	public CorePlayer<?> getDamager() {
		return this.damager;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
	
