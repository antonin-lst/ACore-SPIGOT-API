package fr.azefgh456.acore.player.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.player.data.PlayerData;
import fr.azefgh456.acore.player.listener.PlayerListener;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.player.players.HookedPlayer;
import fr.azefgh456.acore.plugin.IPlugin;

public class PlayerManager extends AManager{

	private List<CorePlayer> players;
	
	private int registeredPlayerCount;
	public int getRegisteredPlayerCount() { return this.registeredPlayerCount;}
	public void setRegisteredPlayerCount(int size) { this.registeredPlayerCount = size;}
	
	public PlayerManager(IPlugin plugin) {
		super(plugin, false);
		players = new ArrayList<CorePlayer>();
		registerListener(new PlayerListener(this));
		
		if(Conf.isUseStorage()) {
			registerData(new PlayerData(this));
		}else {
			logConsol("CorePlayerData Desactivé");
		}
	}

	public void firstLoadPlayer(Player player) {
		players.add(new CorePlayer(player, true));
		registeredPlayerCount++;
	}
	
	public CorePlayer addCorePlayer(CorePlayer corePlayer) {
		players.add(corePlayer);
		return corePlayer;
	}
	
	public void removeCorePlayer(CorePlayer corePlayer) {
		players.remove(corePlayer);
	}
	
	public CorePlayer getCorePlayer(Player target) {
		
		if(!Conf.isUseStorage()) return new CorePlayer(target);
		
		for(CorePlayer player : players) {
			if(player.getPlayer().equals(target)) {
				return player;
			}
		}
		return null;
	}
	
	public HookedPlayer getOffPlayer(OfflinePlayer player) {
		return new HookedPlayer(player) {};
	}
	
	

	public List<CorePlayer> getCorePlayer() {
		return this.players;
	}
	

}
