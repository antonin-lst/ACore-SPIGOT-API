package fr.acore.spigot.event.events.player;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerServerListPingerEvent extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private String message;
	//private WrappedChatComponent motd;
	private int pingVersion;
	private String pingVersionName;
	private int onlinePlayer;
	private int maxOnlinePlayer;
	
	public PlayerServerListPingerEvent(Player player, int pingVersion, String pingVersionName, int onlinePlayer, int maxOnlinePlayer/*, WrappedChatComponent motd*/) {
		this.player = player;
		this.pingVersion = pingVersion;
		this.pingVersionName = pingVersionName;
		this.message = "";
		this.onlinePlayer = onlinePlayer;
		this.maxOnlinePlayer = maxOnlinePlayer;
		//this.motd = motd;
	}
	
	public int getPingVersion() {
		return this.pingVersion;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getPingVersionName() {
		return this.pingVersionName;
	}
	
	public void setPingVersionName(String pingVersionName) {
		this.pingVersionName = pingVersionName;
	}
	
	public void setPingVersion(int pingVersion) {
		this.pingVersion = pingVersion;
	}
	
	public void setMessage(List<String> ligne) {
		StringBuilder messageBuilder = new StringBuilder("");
		for(String l : ligne) {
			messageBuilder.append(l).append("\n");
		}
		message = messageBuilder.toString();
	}
	
	public int getOnlinePlayer() {
		return this.onlinePlayer;
	}
	
	public void setOnlinePlayer(int onlinePlayer) {
		this.onlinePlayer = onlinePlayer;
	}
	
	public int getMaxOnlinePlayer() {
		return this.maxOnlinePlayer;
	}
	
	public void setMaxOnlinePlayer(int maxOnlinePlayer) {
		this.maxOnlinePlayer = maxOnlinePlayer;
	}
	/*
	public WrappedChatComponent getMotd() {
		return this.motd;
	}
	
	public void setMotd(String motd) {
		this.motd = WrappedChatComponent.fromText(motd);
	}*/
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
