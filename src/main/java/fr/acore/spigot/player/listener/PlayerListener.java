package fr.acore.spigot.player.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.acore.spigot.api.listener.IListener;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.event.events.player.PlayerKillPlayerEvent;
import fr.acore.spigot.hook.hooks.FactionHook;
import fr.acore.spigot.hook.manager.HookManager;
import fr.acore.spigot.player.manager.PlayerManager;

public class PlayerListener extends IListener<PlayerManager> {

	
	public PlayerListener(PlayerManager manager) {
		super(manager);
	}
	
	@EventHandler
	public void playerPreJoinEvent(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		OfflineCorePlayer offPlayer = manager.getOfflineCorePlayer(player);
		manager.setPlayerOnline(offPlayer, player);
	}
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		CorePlayer<?> player = manager.getOnlinePlayer(event.getPlayer());
		if(manager.getPlugin().getManager(HookManager.class).getHook(FactionHook.class).isHooked())
			player.setIFactionPlayer(manager.getPlugin().getManager(HookManager.class).getHook(FactionHook.class).getFactionManager().getFactionPlayer(player));
	}
	
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent event) {
		CorePlayer<?> onlinePlayer = manager.getOnlinePlayer(event.getPlayer());
		manager.setPlayerOffline(onlinePlayer);
	}
	
	@EventHandler
	public void playerKillPlayerEvent(PlayerKillPlayerEvent event) {
		CorePlayer<?> player = event.getDamager();
		player.addKill();
	}
	
	@EventHandler
	public void playerDeathEvent(PlayerDeathEvent event) {
		OfflineCorePlayer player = manager.getOfflineCorePlayer(event.getEntity());
		player.addDeath();
	}

}
