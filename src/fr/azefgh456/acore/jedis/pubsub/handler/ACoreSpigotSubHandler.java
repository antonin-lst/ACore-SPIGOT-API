package fr.azefgh456.acore.jedis.pubsub.handler;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.jedis.manager.JedisManager;
import fr.azefgh456.acore.jedis.pubsub.action.JedisActions;

public class ACoreSpigotSubHandler extends JedisSubHandler {

	
	//private IPlugin plugin;
	private JedisManager jedisM;
	
	public ACoreSpigotSubHandler(JedisManager jedisM) {
		super(jedisM.getPlugin().getServerName());
		this.jedisM = jedisM;
	}

	@Override
	public void handle(JsonObject json) {
		JedisActions action = JedisActions.valueOf(json.get("action").getAsString());
		if(action == null) return;
		
        JsonObject data = json.get("data").isJsonNull() ? null : json.get("data").getAsJsonObject();
        
		switch (action) {
			case PING_GLOBAL:
				jedisM.logConsol("ACoreMain Global PING");
				break;
			case SEND_PLAYER_MESSAGE:
				Player player = Bukkit.getPlayer(UUID.fromString(data.get("playerUUID").getAsString()));
				String message = data.get("message").getAsString();
				player.sendMessage(jedisM.convertColor(message));
				break;
			case STOP_SERVER:
				jedisM.logConsol("Disable server");
				jedisM.getPlugin().getServer().dispatchCommand(jedisM.getPlugin().getServer().getConsoleSender(), "stop");
				break;
			case TELEPORT_PLAYER:
				break;
			case UPDATE_SERVER:
				JsonObject upjson = new JsonObject();
				upjson.addProperty("serverName", jedisM.getPlugin().getServerName());
				upjson.addProperty("whitelist", String.valueOf(jedisM.getPlugin().getServer().hasWhitelist()));
				jedisM.sendMessageToMain(JedisActions.UPDATE_SERVER, upjson);
				break;
			case INIT_SERVER:
				jedisM.setInit();
				jedisM.log("ACoreMain Registered");
				break;
			case CHECK_UPDATE_MODULE:
				String moduleName = data.get("moduleName").getAsString();
				if(!jedisM.getIsInit()) jedisM.setInit();
				jedisM.log("ACoreMain init by module");
				ACore.plugin.checkLicencePlugin(moduleName);
			default:
				break;
		}
	}
}
