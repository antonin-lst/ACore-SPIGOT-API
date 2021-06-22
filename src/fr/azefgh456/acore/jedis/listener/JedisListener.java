package fr.azefgh456.acore.jedis.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.jedis.manager.JedisManager;
import fr.azefgh456.acore.jedis.pubsub.action.JedisActions;

public class JedisListener implements Listener{

	private JedisManager jedisManager;
	
	public JedisListener(JedisManager jedisManager) {
		this.jedisManager = jedisManager;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		JsonObject json = new JsonObject();
		json.addProperty("serverName", jedisManager.getPlugin().getServerName());
		jedisManager.sendMessageToMain(JedisActions.PLAYER_ADD, json);
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {
		JsonObject json = new JsonObject();
		json.addProperty("serverName", jedisManager.getPlugin().getServerName());
		jedisManager.sendMessageToMain(JedisActions.PLAYER_REMOVE, json);
	}
	
}
