package fr.azefgh456.acore.jedis.manager;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.jedis.JedisMessage;
import fr.azefgh456.acore.jedis.config.JedisConfig;
import fr.azefgh456.acore.jedis.listener.JedisListener;
import fr.azefgh456.acore.jedis.pubsub.JedisPublisher;
import fr.azefgh456.acore.jedis.pubsub.JedisSubscriber;
import fr.azefgh456.acore.jedis.pubsub.action.JedisActions;
import fr.azefgh456.acore.jedis.pubsub.handler.ACoreSpigotSubHandler;
import fr.azefgh456.acore.jedis.pubsub.handler.JedisSubHandler;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.utils.time.TimerBuilder;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class JedisManager extends AManager implements ARunnable{

	private JedisConfig jedisConfig;
	public JedisConfig getJedisConfig() { return this.jedisConfig;}
	private JedisSubscriber subscriber;
	private JedisPublisher publisher;
	
	private boolean isInit;
	public boolean getIsInit() { return this.isInit;}
	public void setInit() { this.isInit = true;}
	private TimerBuilder redisMainCheckTimer;
	
	public JedisManager(IPlugin plugin, JedisConfig jedisConfig) {
		super(plugin, false);
		this.isInit = false;
		this.jedisConfig = jedisConfig;
		this.publisher = new JedisPublisher(jedisConfig.getJedisHost(), jedisConfig.getJedisPort(), jedisConfig.getJedisPassword());
		this.publisher.runTaskTimerAsynchronously(plugin, 0, 3);
		this.subscriber = new JedisSubscriber(this);
		this.subscriber.subscribeToServerChannel();
		
		addHandler(new ACoreSpigotSubHandler(this));
		registerListener(new JedisListener(this));
		redisMainCheckTimer = new TimerBuilder(15000l);
		registerSyncRunnable(this);
		sendInitMessage();
	}
	
	public void addHandler(JedisSubHandler handler) {
		subscriber.addHandler(handler);
	}
	
	public void sendInitMessage() {
		JsonObject json = new JsonObject();
		json.addProperty("serverName", plugin.getServerName());
		json.addProperty("serverVersion", plugin.getDescription().getVersion());
		json.addProperty("whitelisted", plugin.getServer().hasWhitelist());
		sendMessageToMain(JedisActions.INIT_SERVER, json);
		
	}
	
	public void sendMessageToMain(JedisActions action, JsonObject json) {
		sendMessage(new JedisMessage("acore-main", action, json));
	}
	
	public void sendMessage(JedisMessage message) {
		this.publisher.addToSendMessage(message);
	}
	
	@Override
	public void ticks() {
		
		if(isInit) return;
		
		if(this.redisMainCheckTimer.hisFished()) {
			getPlugin().logErr("Disable Server Connection au ACoreMain Impossible");
			MinecraftServer.getServer().safeShutdown();
		}
		
		
	}
	public void disable() {
		publisher.cancel();
		subscriber.unsubscribe();
	}
	
}
