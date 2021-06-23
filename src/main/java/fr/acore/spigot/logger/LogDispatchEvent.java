package fr.acore.spigot.logger;

import java.io.IOException;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.acore.spigot.api.logger.LoggerPrintter;

public abstract class LogDispatchEvent<T> extends Event implements LoggerPrintter{

	private static final HandlerList handlers = new HandlerList();
	
	private T messages;
	
	public LogDispatchEvent(T messages) {
		this.messages = messages;
	}
	
	public T getMessages() {
		return this.messages;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public abstract void printStackTrace() throws IOException;
	
	public void asyncPrintStackTrace() throws IOException{
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof LogDispatchEvent<?>) {
			
			if(((LogDispatchEvent<?>) obj).getMessages().equals(messages)) return true;
			
		}
		return false;
	}
}