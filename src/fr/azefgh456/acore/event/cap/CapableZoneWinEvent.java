package fr.azefgh456.acore.event.cap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.azefgh456.acore.hook.utils.worldedit.capping.CapableZone;
import fr.azefgh456.acore.hook.utils.worldedit.capping.Capper;

public class CapableZoneWinEvent<T, U extends CapableZone<T>> extends Event{
	
private static final HandlerList handlers = new HandlerList();
	
	private Capper<T> capper;
	private U zone;
	
	public CapableZoneWinEvent(Capper<T> capper, U zone) {
		this.capper = capper;
		this.zone = zone;
	}
	
	public Capper<T> getWinner() {
		return this.capper;
	}
	
	public U getCapableZone() {
		return this.zone;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
