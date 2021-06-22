package fr.azefgh456.acore.jedis.pubsub;

import redis.clients.jedis.Jedis;

public class SubscribeTask extends Thread {
	
	private JedisSubscriber subscriber;
	private Jedis jedisClient;
	private String[] channels;
	
	public SubscribeTask(JedisSubscriber subscriber, Jedis jedisClient, String... channels) {
		super(new Runnable() {
			public void run() {
				jedisClient.subscribe(subscriber, channels);
			}
		});
		this.subscriber = subscriber;
		this.jedisClient = jedisClient;
		this.channels = channels;
	}
	
	public JedisSubscriber getSubscriber() {
		return subscriber;
	}
	
	public Jedis getJedisClient() {
		return jedisClient;
	}
	
	public String[] getChannels() {
		return channels;
	}
	

}
