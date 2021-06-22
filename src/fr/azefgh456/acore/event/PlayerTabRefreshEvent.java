package fr.azefgh456.acore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTabRefreshEvent extends Event implements Cancellable{


	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private String header;
	public String getHeader() { return this.header;}
	private String foother;
	public String getFoother() { return this.foother;}
	
	private boolean cancelled;
	
	public PlayerTabRefreshEvent(Player player) {
		this.player = player;
		header = null;
		foother = null;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setListName(String name) {
		player.setPlayerListName(name);
	}
	
    
    public void setHeaderFoother(String header, String foother) {
        this.header = header;
        this.foother = foother;
    }
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}
	

}
