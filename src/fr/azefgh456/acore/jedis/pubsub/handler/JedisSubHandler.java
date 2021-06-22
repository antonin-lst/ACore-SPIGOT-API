package fr.azefgh456.acore.jedis.pubsub.handler;

import com.google.gson.JsonObject;

public abstract class JedisSubHandler {
	
	private String channelHandled;
	
	public JedisSubHandler(String channelHandled) {
		this.channelHandled = channelHandled;
	}
	
	public String getChannelHandled() {
		return this.channelHandled;
	}
	
	public abstract void handle(JsonObject json);

}
