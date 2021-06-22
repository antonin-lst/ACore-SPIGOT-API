package fr.azefgh456.acore.jedis.pubsub;

import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.jedis.JedisMessage;
import redis.clients.jedis.Jedis;

public class JedisPublisher extends BukkitRunnable{
	
	private String host;
	private int port;
	private String password;
	private Queue<JedisMessage> messages;
	
	private Jedis jedis;
	
	public JedisPublisher(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.messages = new LinkedList<>();
	}
	
	@Override
	public void run() {
		try {
			if(jedis == null) {
				jedis = new Jedis(host, port);
			}

			if(!messages.isEmpty()) {
				if (password != null && !password.isEmpty()) {
					jedis.auth(password);
				}
				
				while (!messages.isEmpty()) {
					
						JedisMessage message = messages.poll();
		
						JsonObject json = new JsonObject();
						json.addProperty("action", message.getAction().name());
						json.add("data", message.getJson());
						jedis.publish(message.getChannel(), json.toString());
						System.out.println("sendMessage to " + message.getChannel() + "\nContent: " + json.toString());
	
					
				}		
			}
		}catch(Exception e) {
			e.printStackTrace();
			try {
				jedis.close();
			}catch(Exception ex) {
				e.printStackTrace();
			}
			jedis = new Jedis(host, port);
			System.out.println("reconnection a  redis (JedisPublisher)");
		}	
	}
	
	public void addToSendMessage(JedisMessage message) {
		this.messages.add(message);
	}
	
	public void stop() {
		
	}

}
