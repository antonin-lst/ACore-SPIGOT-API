package fr.azefgh456.acore.jedis;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.jedis.pubsub.action.JedisActions;

public class JedisMessage {
	
	private String channel;
	private JedisActions action;
	private JsonObject json;
	
	public JedisMessage(String channel, JedisActions action, JsonObject json) {
		this.channel = channel;
		this.action = action;
		this.json = json;
	}
	
	public String getChannel() {
		return this.channel;
	}
	
	public JedisActions getAction() {
		return this.action;
	}
	
	public JsonObject getJson() {
		return this.json;
	}

}
