package fr.azefgh456.acore.jedis.pubsub;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParser;

import fr.azefgh456.acore.jedis.manager.JedisManager;
import fr.azefgh456.acore.jedis.pubsub.handler.JedisSubHandler;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class JedisSubscriber extends JedisPubSub{

	private JedisManager jedisM;
	private JedisPool jedisClient;
	private List<JedisSubHandler> handlers;
	private JsonParser parser;
	
	public JedisSubscriber(JedisManager jedisManager) {
		this.jedisM = jedisManager;
		this.jedisClient = new JedisPool(jedisManager.getJedisConfig().getJedisHost(), jedisManager.getJedisConfig().getJedisPort());
		handlers = new ArrayList<>();
		parser = new JsonParser();
	}
	
	public void subscribeToServerChannel() {
		try {
			System.out.println("Subscribe to " + jedisM.getPlugin().getServerName());
			new SubscribeTask(this, jedisClient.getResource(), jedisM.getPlugin().getServerName()).start();
		}catch(Exception e) {
			System.err.println("Crash subsribe");
			try {
				jedisClient.close();
			}catch(Exception ex) {
				
			}
			System.out.println("try re subsribe");
			jedisClient = new JedisPool(jedisM.getJedisConfig().getJedisHost(), jedisM.getJedisConfig().getJedisPort());
			subscribeToServerChannel();
		}
		
		/*
		new Thread(new Runnable() {
			@Override
			public void run() {
				jedisClient.subscribe(this, jedisM.getPlugin().getServerName());
			}
		}).start();*/
	}
	
	
	// test comment fonction le add de handler
	
	public void addHandler(JedisSubHandler handler) {
		handlers.add(handler);
	}
	
	/*
	public void subcribeHandlers() {
		StringBuilder channels = new StringBuilder("");
		for(JedisSubHandler handler : handlers) {
			channels.append(handler).append(" ");
		}
		subscribe(channels.toString().split(" "));
	}*/
	
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		System.out.println("subscribe to : " + channel);
		super.onSubscribe(channel, subscribedChannels);
	}
	
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// TODO Auto-generated method stub
		super.onUnsubscribe(channel, subscribedChannels);
	}
	
	@Override
	public void onMessage(String channel, String message) {
		System.out.println("MessageACoreMain \nchannel : " + channel + " \nmessage : " + message);
		for(JedisSubHandler handler : handlers) {
			if(handler.getChannelHandled().equalsIgnoreCase(channel)) {
				try {
					handler.handle(parser.parse(message).getAsJsonObject());
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
			}
		}
	}


	
}
