package fr.azefgh456.acore.player.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.packets.manager.PacketsManager;
import fr.azefgh456.acore.player.data.PlayerData;
import fr.azefgh456.acore.player.manager.PlayerManager;
import fr.azefgh456.acore.player.players.CorePlayer;

public class PlayerListener implements Listener{

	private PlayerManager playerM;
	
	public PlayerListener(PlayerManager playerM) {
		this.playerM = playerM;
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event) {
		
		if(!Conf.isUseStorage()) return;
		
		PlayerData data = (PlayerData) playerM.getData();
		
		if(event.getResult().equals(Result.ALLOWED)) {
			if(data.contain(event.getPlayer())) {
				data.loadAsyncPlayer(event.getPlayer());
				playerM.logConsol(ChatColor.AQUA + "Connexion de " + ChatColor.YELLOW + event.getPlayer().getName());
			}else {
				
				playerM.firstLoadPlayer(event.getPlayer());
				playerM.logConsol(ChatColor.DARK_AQUA + "Premiere " + ChatColor.BLUE + "Connexion de " + ChatColor.YELLOW + event.getPlayer().getName());
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		((PacketsManager) playerM.getPlugin().getManager(PacketsManager.class)).injectPlayer((Player)player);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		if(!Conf.isUseStorage()) return;
		
		PlayerData data = (PlayerData) playerM.getData();
		Player player = event.getPlayer();
		CorePlayer cp = playerM.getCorePlayer(player);
		((PacketsManager) playerM.getPlugin().getManager(PacketsManager.class)).removePlayer((Player)player);
		data.saveAsyncPlayer(cp);
		playerM.removeCorePlayer(cp);
		
		playerM.logConsol(ChatColor.AQUA + "Le joueur " + ChatColor.YELLOW + player.getName() + ChatColor.AQUA + " vient de quitter");
	}
	
}
